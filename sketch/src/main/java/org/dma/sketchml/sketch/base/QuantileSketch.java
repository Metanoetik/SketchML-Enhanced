package org.dma.sketchml.sketch.base;


import java.io.Serializable;

public abstract class QuantileSketch implements Serializable {
    protected long n; // total number of data items appeared
    protected long estimateN; // estimated total number of data items there will be,
    // if not -1, sufficient space will be allocated at once

    protected double minValue;
    protected double maxValue;

    public QuantileSketch(long estimateN) {
        this.estimateN = estima