
package org.dma.sketchml.sketch.sketch.frequency;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dma.sketchml.sketch.base.BinaryEncoder;
import org.dma.sketchml.sketch.binary.DeltaAdaptiveEncoder;
import org.dma.sketchml.sketch.common.Constants;
import org.dma.sketchml.sketch.util.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class GroupedMinMaxSketch implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(GroupedMinMaxSketch.class);

    private int groupNum;
    private int rowNum;
    private double colRatio;
    private int binNum;
    private int zeroValue;
    private MinMaxSketch[] sketches;
    private BinaryEncoder[] encoders;

    public static final int DEFAULT_MINMAXSKETCH_GROUP_NUM = 8;
    public static final double DEFAULT_MINMAXSKETCH_COL_RATIO = 0.3;