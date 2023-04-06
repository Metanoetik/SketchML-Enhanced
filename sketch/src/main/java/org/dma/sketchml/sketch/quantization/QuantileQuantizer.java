package org.dma.sketchml.sketch.quantization;

import org.dma.sketchml.sketch.base.Quantizer;
import org.dma.sketchml.sketch.common.Constants;
import org.dma.sketchml.sketch.sketch.quantile.HeapQuantileSketch;
import org.dma.sketchml.sketch.util.Maths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executi