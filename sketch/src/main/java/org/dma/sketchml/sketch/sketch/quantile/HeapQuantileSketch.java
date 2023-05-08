package org.dma.sketchml.sketch.sketch.quantile;

import org.dma.sketchml.sketch.base.QuantileSketch;

import java.util.Arrays;

/**
 * Implementation of quantile sketch on the Java heap
 * bashed on `DataSketches` of Yahoo!
 */
public class HeapQuantileSketch extends QuantileSketch {
    private int k; // parameter that controls space usage
    public static final int DEFAULT_K = 128;

    /**
     * This single array contains the base buffer plus all levels some of which may not be used.
     * A level is of size K and is either full and sorted, or not used. A "not used" buffer may have
     * garbage. Whether a level buffer used or not is indicated by the bitPattern_.
     * The base buffer has length 2*K but might not be full and isn't necessarily sorted.
     * The base buffer precedes the level buffers.
     *
     * The lev