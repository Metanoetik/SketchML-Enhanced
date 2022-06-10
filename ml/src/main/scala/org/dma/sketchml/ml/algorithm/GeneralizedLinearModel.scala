
package org.dma.sketchml.ml.algorithm

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.ml.linalg.DenseVector
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkContext, SparkEnv}
import org.dma.sketchml.ml.data.{DataSet, Parser}
import org.dma.sketchml.ml.conf.MLConf
import org.dma.sketchml.ml.gradient.Gradient
import org.dma.sketchml.ml.objective.{GradientDescent, Loss}
import org.dma.sketchml.ml.util.ValidationUtil
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object GeneralizedLinearModel {
  private val logger: Logger = LoggerFactory.getLogger(GeneralizedLinearModel.getClass)

  object Model {
    var weights: DenseVector = _
    var optimizer: GradientDescent = _
    var loss: Loss = _
    var gradient: Gradient = _
  }
