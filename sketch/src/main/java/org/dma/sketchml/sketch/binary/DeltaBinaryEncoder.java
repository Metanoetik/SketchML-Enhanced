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
    private BitSe