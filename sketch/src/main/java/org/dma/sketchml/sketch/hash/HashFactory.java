package org.dma.sketchml.sketch.hash;

import org.dma.sketchml.sketch.base.Int2IntHash;
import org.dma.sketchml.sketch.base.SketchMLException;
import org.dma.sketchml.sketch.util.Maths;

import java.util.*;

public class HashFactory {
    private static final Int2IntHash[] int2intHashes =
            new Int2IntHash[]{new BJHash(0), new Mix64Hash(0),
            new TWHash(0), new BKDRHash(0, 31), new BKDRHash(0, 131),
            new BKDRHash(0, 267), new BKDRHash(0, 1313), new BKDRHash(0, 13131)};
    private static final Random random = new Random();

    public static Int2IntHash getRandomInt2IntHash(int size) {
        int idx = random.nextInt(int2intHashes.length);
        Int2IntHash res = int2intHashes[idx].clone();
        res.setSize(size);
        return res;
    }

    public static Int2IntHash[] getRandomInt2IntHashes(int hashNum, int size) {
        if (hashNum > int2intHashes.length) {
     