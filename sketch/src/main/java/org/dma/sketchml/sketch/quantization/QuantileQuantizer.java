package org.dma.sketchml.sketch.quantization;

import org.dma.sketchml.sketch.base.Quantizer;
import org.dma.sketchml.sketch.common.Constants;
import org.dma.sketchml.sketch.sketch.quantile.HeapQuantileSketch;
import org.dma.sketchml.sketch.util.Maths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class QuantileQuantizer extends Quantizer {
    private static final Logger LOG = LoggerFactory.getLogger(QuantileQuantizer.class);

    public QuantileQuantizer(int binNum) {
        super(binNum);
    }

    public QuantileQuantizer() {
        this(Quantizer.DEFAULT_BIN_NUM);
    }

    @Override
    public void quantize(double[] values) {
        long startTime = System.currentTimeMillis();
        // 1. create quantile sketch summary
        n = values.length;
        HeapQuantileSketch qSketch = new HeapQuantileSketch((long) n);
        for (double v : values) {
            qSketch.update(v);
        }
        min = qSketch.getMinValue();
        max = qSketch.getMaxValue();
        // 2. query quantiles, set them as bin edges
        splits = Maths.unique(qSketch.getQuantiles(binNum));
        if (splits.length + 1 != binNum) {
            LOG.warn(String.format("Actual bin num %d not equal to %d",
                    splits.length + 1, binNum));
            binNum = splits.length + 1;
        }
        // 3. find the zero index
        findZeroIdx();
        // 4. find index of each value
        quantizeToBins(values);
        LOG.debug(String.format("Quantile quantization for %d items cost %d ms",
                n, System.currentTimeMillis() - startTime));
    }

    @Override
    public void parallelQuantize(double[] valu