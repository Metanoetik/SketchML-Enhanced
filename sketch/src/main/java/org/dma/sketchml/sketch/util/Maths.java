package org.dma.sketchml.sketch.util;

import org.dma.sketchml.sketch.base.SketchMLException;

import java.util.Random;

public class Maths {
    public static boolean isPowerOf2(int k) {
        for (int i = 1; i < 65536; i <<= 1) {
            if (k == i)
                return true;
        }
        return false;
    }

    public static int log2nlz(int k) {
        if (k <= 0)
            throw new SketchMLException("Log for " + k);
        else
            return 31 - Integer.numberOfLeadingZeros(k);
    }

    public static int max(int[] array) {
        int res = array[0];
        for 