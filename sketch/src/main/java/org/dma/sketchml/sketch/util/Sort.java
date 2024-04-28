
package org.dma.sketchml.sketch.util;

import it.unimi.dsi.fastutil.doubles.DoubleArrayPriorityQueue;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoublePriorityQueue;
import it.unimi.dsi.fastutil.ints.IntComparator;
import org.dma.sketchml.sketch.base.SketchMLException;

/**
 * Quick sort utils
 */
public class Sort {
    public static int quickSelect(int[] array, int k, int low, int high) {
        if (k > 0 && k <= high - low + 1) {
            int pivot = array[high];
            int ii = low;
            for (int jj = low; jj < high; jj++) {
                if (array[jj] <= pivot) {
                    swap(array, ii++, jj);
                }
            }
            swap(array, ii, high);

            if (ii - low == k - 1) {
                return array[ii];
            } else if (ii - low > k - 1) {
                return quickSelect(array, k, low, ii - 1);
            } else {
                return quickSelect(array, k - ii + low - 1, ii + 1, high);
            }
        }
        throw new SketchMLException("k is more than number of elements in array");
    }

    public static double selectKthLargest(double[] array, int k) {
        return selectKthLargest(array, k, new DoubleArrayPriorityQueue(k));
    }

    public static double selectKthLargest(double[] array, int k, DoubleComparator comp) {
        return selectKthLargest(array, k, new DoubleArrayPriorityQueue(k, comp));
    }

    private static double selectKthLargest(double[] array, int k, DoublePriorityQueue queue) {
        if (k > array.length)
            throw new SketchMLException("k is more than number of elements in array");

        int i = 0;
        while (i < k)
            queue.enqueue(array[i++]);
        for (; i < array.length; i++) {
            double top = queue.firstDouble();
            if (array[i] > top) {
                queue.dequeueDouble();
                queue.enqueue(array[i]);
            }
        }
        return queue.firstDouble();
    }

    public static void quickSort(int[] array, double[] values, int low, int high) {
        if (low < high) {
            int tmp = array[low];
            double tmpValue = values[low];
            int ii = low, jj = high;
            while (ii < jj) {
                while (ii < jj && array[jj] >= tmp) {
                    jj--;
                }

                array[ii] = array[jj];
                values[ii] = values[jj];

                while (ii < jj && array[ii] <= tmp) {
                    ii++;
                }

                array[jj] = array[ii];
                values[jj] = values[ii];
            }
            array[ii] = tmp;
            values[ii] = tmpValue;

            quickSort(array, values, low, ii - 1);
            quickSort(array, values, ii + 1, high);
        }
    }

    public static void quickSort(long[] array, double[] values, int low, int high) {
        if (low < high) {
            long tmp = array[low];
            double tmpValue = values[low];
            int ii = low, jj = high;
            while (ii < jj) {
                while (ii < jj && array[jj] >= tmp) {
                    jj--;
                }