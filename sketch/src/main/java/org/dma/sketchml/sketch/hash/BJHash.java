package org.dma.sketchml.sketch.hash;

import org.dma.sketchml.sketch.base.Int2IntHash;

public class BJHash extends Int2IntHash {
    public BJHash(int size) {
        super(size);
    }

    public int hash(int key) {
        int code = key;
        code = (code + 0x7ed55d16) + (code << 12);
        code = (code ^ 0xc761c23c) 