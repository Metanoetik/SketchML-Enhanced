package org.dma.sketchml.ml.algorithm

import org.apache.spark.ml.linalg.DenseVector
import org.dma.sketchml.ml.algorithm.GeneralizedLinearModel.Model._
import org.dma.sketchml.ml.common.Constants
import org.dma.sketchml.ml.conf.MLConf
import org.dma.sketchml.ml.objective.{Adam, L2SquareLoss}
import org.slf4j.{Logger, LoggerFactory}

object LinearRegModel {
  private val logger: Logger = LoggerFactory.getLogger(LinearRegModel.getClass)

  def apply(conf: MLConf): LinearRegModel = new LinearRegModel(conf)

  def getName: String = Constants.ML_LINEAR_REGRESSION
}

class LinearRegModel(_conf: MLConf) extends GeneralizedLinearModel(_conf) {
  @transient override p