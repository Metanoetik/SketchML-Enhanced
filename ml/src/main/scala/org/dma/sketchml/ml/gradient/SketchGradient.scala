
package org.dma.sketchml.ml.gradient

import org.dma.sketchml.ml.gradient.Kind.Kind
import org.dma.sketchml.sketch.base.SketchMLException
import org.dma.sketchml.sketch.quantization.QuantileQuantizer
import org.dma.sketchml.sketch.sketch.frequency.GroupedMinMaxSketch

class SketchGradient(d: Int, binNum: Int, groupNum: Int, rowNum: Int, colRatio: Double) extends Gradient(d) {
