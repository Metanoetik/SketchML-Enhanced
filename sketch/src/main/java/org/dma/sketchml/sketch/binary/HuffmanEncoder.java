package org.dma.sketchml.sketch.binary;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import org.dma.sketchml.sketch.base.BinaryEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.IntStream;

public class HuffmanEncoder implements BinaryEncoder {
    private static final Logger LOG = LoggerFactory.getLogger(HuffmanEncoder.class);

    private Item[] items;
    private BitSet bitset;
    private int size;


    private class