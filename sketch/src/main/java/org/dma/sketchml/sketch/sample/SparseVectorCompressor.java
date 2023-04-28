
package org.dma.sketchml.sketch.sample;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dma.sketchml.sketch.sketch.frequency.GroupedMinMaxSketch;
import org.dma.sketchml.sketch.base.Quantizer;
import org.dma.sketchml.sketch.base.SketchMLException;
import org.dma.sketchml.sketch.base.VectorCompressor;
import org.dma.sketchml.sketch.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class SparseVectorCompressor implements VectorCompressor {
    private static final Logger LOG = LoggerFactory.getLogger(SparseVectorCompressor.class);

    private int size;

    private Quantizer.QuantizationType quantType;
    private int quantBinNum;
    private double[] quantValues;

    private GroupedMinMaxSketch mmSketches;

    private int mmSketchGroupNum;
    private int mmSketchRowNum;
    private double mmSketchColRatio;

    public SparseVectorCompressor(
            Quantizer.QuantizationType quantType, int quantBinNum,
            int mmSketchGroupNum, int mmSketchRowNum, double mmSketchColRatio) {
        this.quantType = quantType;
        this.quantBinNum = quantBinNum;
        this.mmSketchGroupNum = mmSketchGroupNum;
        this.mmSketchRowNum = mmSketchRowNum;
        this.mmSketchColRatio = mmSketchColRatio;
    }

    @Override
    public void compressDense(double[] values) {
        LOG.warn("Compressing a dense vector with SparseVectorCompressor");
        int[] keys = new int[values.length];
        Arrays.setAll(keys, i -> i);
        compressSparse(keys, values);
    }

    @Override
    public void compressSparse(int[] keys, double[] values) {
        long startTime = System.currentTimeMillis();
        if (keys.length != values.length) {
            throw new SketchMLException(String.format(
                    "Lengths of key array and value array do not match: %d, %d",
                    keys.length, values.length));
        }
        size = keys.length;
        // 1. quantize into bin indexes
        Quantizer quantizer = Quantizer.newQuantizer(quantType, quantBinNum);
        quantizer.quantize(values);
        quantValues = quantizer.getValues();
        // 2. encode bins and keys
        mmSketches = new GroupedMinMaxSketch(mmSketchGroupNum, mmSketchRowNum,
                mmSketchColRatio, quantizer.getBinNum(), quantizer.getZeroIdx());
        mmSketches.create(keys, quantizer.getBins());
        LOG.debug(String.format("Sparse vector compression cost %d ms, %d key-value " +
                "pairs in total", System.currentTimeMillis() - startTime, size));
    }
