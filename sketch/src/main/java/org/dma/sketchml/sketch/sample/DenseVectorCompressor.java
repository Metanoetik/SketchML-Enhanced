
package org.dma.sketchml.sketch.sample;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dma.sketchml.sketch.base.Quantizer;
import org.dma.sketchml.sketch.base.SketchMLException;
import org.dma.sketchml.sketch.base.VectorCompressor;
import org.dma.sketchml.sketch.util.Maths;
import org.dma.sketchml.sketch.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class DenseVectorCompressor implements VectorCompressor {
    private static final Logger LOG = LoggerFactory.getLogger(DenseVectorCompressor.class);

    private int size;

    private Quantizer.QuantizationType quantType;
    private int quantBinNum;
    private Quantizer quantizer;

    public DenseVectorCompressor(
            Quantizer.QuantizationType quantType, int quantBinNum) {
        this.quantType = quantType;
        this.quantBinNum = quantBinNum;
    }
