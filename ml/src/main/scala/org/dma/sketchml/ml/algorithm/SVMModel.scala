package org.dma.sketchml.ml.algorithm

import org.apache.spark.ml.linalg.DenseVector
import org.dma.sketchml.ml.algorithm.GeneralizedLinearModel.Model._
import org.dma.sketchml.ml.common.Constants
import org.dma.sketchml.ml.conf.MLConf
import org.dma.sketchml.ml.objective.{Adam, L2HingeLoss}
import org.slf4j.{Logger, LoggerFactory}

object SVMModel {
  private val logger: Logger = LoggerFactory.getLogger(SVMModel.getClass)

  def apply(conf: MLConf): SVMModel = new SVMMo