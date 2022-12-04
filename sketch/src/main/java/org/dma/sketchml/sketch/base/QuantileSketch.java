package org.dma.sketchml.sketch.base;


import java.io.Serializable;

public abstract class QuantileSketch implements Serializable {
    protected long n; // total number of data items appeared
    protected long estimateN; // estimated total number of data items there will be,
    // if not -1, sufficient sp