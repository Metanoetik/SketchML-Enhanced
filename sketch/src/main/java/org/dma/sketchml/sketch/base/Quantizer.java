package org.dma.sketchml.sketch.base;

import org.dma.sketchml.sketch.common.Constants;
import org.dma.sketchml.sketch.quantization.QuantileQuantizer;
import org.dma.sketchml.sketch.quantization.UniformQuantizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class Quantizer implements Serializable {
    public static Logger LOG = LoggerFactory.getLogger(Quantizer.class);

    protected int binNum;
    protected int n;
    protected double[] splits;
    protected int zeroIdx;
    protected double min;
    protected double max;

    protected int[] bins;
    public static final int DEFAULT_BIN_NUM = 256;

    public Quantizer(int binNum) {
        this.binNum = binNum;
    }

    public abstract void quantize(double[] values);

    public abstract void parallelQuantize(double[] values) throws InterruptedException, ExecutionException;

    public double[] getValues() {
        double[] res = new double[binNum];
        int splitNum = binNum - 1;
        res[0] = 0.5 * (min + splits[0]);
        for (int i = 1; i < splitNum; i++)
            res[i] = 0.5 * (splits[i - 1] + splits[i]);
        res[splitNum] = 0.5 * (splits[splitNum - 1] + max);
        return res;
    }

    public int indexOf(double x) {
        if (x < split