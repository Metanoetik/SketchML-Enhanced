package org.dma.sketchml.sketch.binary;

import org.dma.sketchml.sketch.base.BinaryEncoder;
import org.dma.sketchml.sketch.base.SketchMLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.BitSet;

/**
 * This is the special case for DeltaAdaptiveEncoder
 * where numIntervals equals to 4 and number of flag bits is constant
 *
 * */
public class DeltaBinaryEncoder implements BinaryEncoder {
    private static final Logger LOG = LoggerFactory.getLogger(DeltaBinaryEncoder.class);

    private int size;
    private BitSet deltaBits;
    private BitSet flagBits;

    @Override
    public void encode(int[] values) {
        size = values.length;
        flagBits = new BitSet(size * 2);
        deltaBits = new BitSet(size * 12);
        int offset = 0, prev = 0;
        for (int i = 0; i < size; i++) {
            int delta = values[i] - prev;
            int bytesNeeded = needBytes(delta);
            BinaryUtils.setBits(flagBits, 2 * i, bytesNeeded - 1, 2);
            BinaryUtils.setBytes(deltaBits, offset, delta, bytesNeeded);
            prev = values[i];
            offset += bytesNeeded * 8;
        }
    }

    @Override
    public int[] decode() {
        int[] res = new int[size];
        int offset = 0, prev = 0;
        for (int i = 0; i < size; i++) {
            int bytesNeeded = BinaryUtils.getBits(flagBits, i * 2, 2) + 1;
            int delta = BinaryUtils.getBytes(deltaBits, offset, bytesNeeded);
            res[i] = prev + delta;
            prev = res[i];
            offset += bytesNeeded * 8;
        }
        return res;
    }

    public static int needBytes(int x) {
        if (x < 0) {
     