
package org.dma.sketchml.sketch.sketch.quantile;

import org.dma.sketchml.sketch.util.Maths;

import java.util.Arrays;
import java.util.Random;

public class QSketchUtils {
    private static final Random rand = new Random();

    protected static void checkK(int k) {
        if (k < 1)
            throw new QuantileSketchException("Invalid value of k: k should be positive");
        else if (k >= 65535)
            throw new QuantileSketchException("Invalid value of k: k should not be larger than 65536");
        else if (!Maths.isPowerOf2(k))
            throw new QuantileSketchException("Invalid value of k: k should be power of 2");
    }

    protected static int needBufferCapacity(int k, long estimateN) {
        int numLevels = 1 + (63 - Long.numberOfLeadingZeros(estimateN / (k * 2)));
        return k * (numLevels + 2);
    }

    protected static void checkBitPattern(long bitPattern, long n, int k) {
        if (bitPattern != n / (k * 2))
            throw new QuantileSketchException("Bit Pattern not match");
    }

    protected static void checkFraction(double fraction) {
        if (fraction < 0.0 || fraction > 1.0)
            throw new QuantileSketchException("Fraction should be in range [0.0, 1.0]");
    }

    protected static void checkFractions(double[] fractions) {
        for (double f: fractions)
            checkFraction(f);
    }

    protected static void checkEvenPartiotion(int evenPartition) {
        if (evenPartition <= 1)
            throw new QuantileSketchException("Invalid partition number: " + evenPartition);
    }

    protected static void compactBuffer(final double[] srcBuf, int srcOffset,
                                        final double[] dstBuf, int dstOffset, int dstSize) {
        int offset = rand.nextBoolean() ? 1 : 0;
        int bound = dstOffset + dstSize;
        for (int i = srcOffset + offset, j = dstOffset; j < bound; i += 2, j++)
            dstBuf[j] = srcBuf[i];
    }

    protected static void mergeArrays(final double[] src1, int srcOffset1,
                                      final double[] src2, int srcOffset2,
                                      final double[] dst, int dstOffset, int size) {
        int bound1 = srcOffset1 + size;
        int bound2 = srcOffset2 + size;
        int i1 = srcOffset1, i2 = srcOffset2, i3 = dstOffset;
        while (i1 < bound1 && i2 < bound2) {
            if (src1[i1] < src2[i2])
                dst[i3++] = src1[i1++];
            else
                dst[i3++] = src2[i2++];
        }
        if (i1 < bound1)
            System.arraycopy(src1, i1, dst, i3, bound1 - i1);
        else
            System.arraycopy(src2, i2, dst, i3, bound2 - i2);
    }

    protected static void levelwisePropagation(long bitPattern, int k,