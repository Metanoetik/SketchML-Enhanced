
package org.dma.sketchml.ml.gradient

import javax.inject.Singleton
import org.apache.spark.ml.linalg.{DenseVector, SparseVector, Vector}
import org.dma.sketchml.ml.common.Constants
import org.dma.sketchml.ml.conf.MLConf
import org.dma.sketchml.ml.gradient.Kind.Kind
import org.dma.sketchml.ml.util.Maths
import org.dma.sketchml.sketch.base.SketchMLException
import org.dma.sketchml.sketch.util.Utils
import org.slf4j.{Logger, LoggerFactory}

object Gradient {
  def zero: ZeroGradient = ZeroGradient.getInstance()

  private def logger: Logger = LoggerFactory.getLogger(Gradient.getClass)

  def compress(grad: Gradient, conf: MLConf): Gradient = {
    val startTime = System.currentTimeMillis()
    val res = conf.compressor match {