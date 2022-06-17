package org.dma.sketchml.ml.algorithm

import org.apache.spark.ml.linalg.DenseVector
import org.dma.sketchml.ml.algorithm.GeneralizedLinearModel.Model._
import org.dma.sketchml.ml.common.Constants
import org.dma.sketchml.ml.conf.MLConf
import org.dma.sketchml.ml.objective.{Adam, L2LogLoss}
import org.slf4j.{Logger, LoggerFactory}

object LRModel {
  private val logger: Logger = LoggerFactory.getLogger(LRModel.getClass)

  def apply(conf: MLConf): LRModel = new LRModel(conf)

  def getName: String = Constants.ML_LOGISTIC_REGRESSION
}

class LRModel(_conf: MLConf) extends GeneralizedLinearModel(_conf) {
  @transient override protected val logger: Logger = LRMod