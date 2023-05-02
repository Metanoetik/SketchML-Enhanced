
package org.dma.sketchml.sketch.sketch.frequency;

import org.dma.sketchml.sketch.base.BinaryEncoder;
import org.dma.sketchml.sketch.base.Int2IntHash;
import org.dma.sketchml.sketch.binary.HuffmanEncoder;
import org.dma.sketchml.sketch.hash.HashFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class MinMaxSketch implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(MinMaxSketch.class);

    protected int rowNum;
    protected int colNum;
    protected int[] table;
    protected int zeroValue;
    protected Int2IntHash[] hashes;

    public static final int DEFAULT_MINMAXSKETCH_ROW_NUM = 2;

    public MinMaxSketch(int rowNum, int colNum, int zeroValue) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.table = new int[rowNum * colNum];
        this.zeroValue = zeroValue;
        int maxValue = compare(Integer.MIN_VALUE, Integer.MAX_VALUE) <= 0