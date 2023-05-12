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
     * The levels arrays require quite a bit of explanation, which we defer until later.
     */
    private double[] combinedBuffer;
    private int combinedBufferCapacity; // equals combinedBuffer.length
    private int baseBufferCount; // #samples currently in base buffer (= n % (2*k))
    private long bitPattern; // active levels expressed as a bit pattern (= n / (2*k))
    private static final int MIN_BASE_BUF_SIZE = 4;

    /**
     * data structure for answering quantile queries
     */
    private double[] samplesArr; // array of size samples
    private long[] weightsArr; // array of cut points

    public HeapQuantileSketch(int k, long estimateN) {
        super(estimateN);
        QSketchUtils.checkK(k);
        this.k = k;
        reset();
    }

    public HeapQuantileSketch() {
        this(DEFAULT_K, -1L);
    }

    public HeapQuantileSketch(int k) {
        this(k, -1L);
    }

    public HeapQuantileSketch(long estimateN) {
        this(DEFAULT_K, estimateN)