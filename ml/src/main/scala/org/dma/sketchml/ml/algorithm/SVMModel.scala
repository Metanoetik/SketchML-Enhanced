package org.dma.sketchml.ml.algorithm

import org.apache.spark.ml.linalg.DenseVector
import org.dma.sketchml.ml.algorithm.GeneralizedLinearModel.Model._
import org.dma.sketchml.ml.common.Constants
import org.dma.sketchml.ml.conf.MLConf
import org.dma.sketchml.ml.objective.{Adam, L2HingeLoss}
import org.slf4j.{Logger, LoggerFactory}

object SVMModel {
  private val logger: Logger = LoggerFactory.getLogger(SVMModel.getClass)

  def apply(conf: MLConf): SVMModel = new SVMModel(conf)

  def getName: String = Constants.ML_SUPPORT_VECTOR_MACHINE
}

class SVMModel(_conf: MLConf) extends GeneralizedLinearModel(_conf) {
  @transient override protected val logger: