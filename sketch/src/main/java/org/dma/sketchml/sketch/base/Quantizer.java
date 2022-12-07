package org.dma.sketchml.sketch.base;

import org.dma.sketchml.sketch.common.Constants;
import org.dma.sketchml.sketch.quantization.QuantileQuantizer;
import org.dma.sketchml.sketch.quantization.UniformQuantizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class Quantizer imple