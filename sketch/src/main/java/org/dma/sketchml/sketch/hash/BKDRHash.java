package org.dma.sketchml.sketch.hash;

import org.dma.sketchml.sketch.base.Int2IntHash;

public class BKDRHash extends Int2IntHash {
    private int seed;

    public BKDRHash(int size, int seed) {
        super(size);
        this.seed = seed