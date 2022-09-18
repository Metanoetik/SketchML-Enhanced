
package org.dma.sketchml.ml.objective

import org.apache.spark.ml.linalg.DenseVector
import org.dma.sketchml.ml.conf.MLConf
import org.dma.sketchml.ml.gradient._
import org.dma.sketchml.ml.util.Maths
import org.slf4j.{Logger, LoggerFactory}

object Adam {
  private val logger: Logger = LoggerFactory.getLogger(Adam.getClass)

  def apply(conf: MLConf): GradientDescent =
    new Adam(conf.featureNum, conf.learnRate, conf.learnDecay, conf.batchSpRatio)
}