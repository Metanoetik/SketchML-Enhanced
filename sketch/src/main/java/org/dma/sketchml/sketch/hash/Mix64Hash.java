package org.dma.sketchml.sketch.hash;

import org.dma.sketchml.sketch.base.Int2IntHash;

public class Mix64Hash extends Int2IntHash {
    public Mix64Hash(int size) {
        super(size);
    }

    public int hash(int key) {
        int code = key;
        code = (~code) + (code << 21); // code = (code << 21) - code - 1;
        code = code ^ (code >> 24);
    