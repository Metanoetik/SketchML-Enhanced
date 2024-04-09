package org.dma.sketchml.sketch.util;

import org.dma.sketchml.sketch.base.SketchMLException;

import java.util.Random;

public class Maths {
    public static boolean isPowerOf2(int k) {
        for (int i = 1; i < 65536; i <<= 1) {
            if (k == i)
                return true;
     