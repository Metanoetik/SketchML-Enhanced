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
        if (x < splits[0]) {
            return 0;
        } else if (x >= splits[binNum - 2]) {
            return binNum - 1;
        } else {
            int l = zeroIdx, r = zeroIdx;
            if (x < 0.0) l = 0;
            else r = binNum - 2;
            while (l + 1 < r) {
                int mid = (l + r) >> 1;
                if (splits[mid] > x) {
                    if (mid == 0 || splits[mid - 1] <= x)
                        return mid;
                    else
                        r = mid;
                } else {
                    l = mid;
                }
            }
            int mid = (l + r) >> 1;
            return splits[mid] <= x ? mid + 1 : mid;
        }
    }

    protected void findZeroIdx() {
        if (min > 0.0)
            zeroIdx = 0;
        else if (max < 0.0)
            zeroIdx = binNum - 1;
        else {
            int t = 0;
            while (t < binNum - 1 && splits[t] < 0.0)
                t++;
            zeroIdx = t;
        }
    }

    protected void quantizeToBins(double[] values) {
        int size = values.length;
        bins = new int[size];
        for (int i = 0; i < size; i++)
            bins[i] = indexOf(values[i]);
    }

    protected void parallelQuantizeToBins(double[] values) throws InterruptedException, ExecutionException {
        int s