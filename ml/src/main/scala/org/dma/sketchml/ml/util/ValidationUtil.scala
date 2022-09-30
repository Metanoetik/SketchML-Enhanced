
package org.dma.sketchml.ml.util

import org.apache.spark.ml.linalg.Vector
import org.dma.sketchml.ml.data.DataSet
import org.dma.sketchml.ml.objective.Loss
import org.dma.sketchml.sketch.util.Sort
import org.slf4j.{Logger, LoggerFactory}

object ValidationUtil {
  private val logger: Logger = LoggerFactory.getLogger(ValidationUtil.getClass)

  def calLossPrecision(weights: Vector, validData: DataSet, loss: Loss): (Double, Int, Int, Int, Int, Int) = {
    val validStart = System.currentTimeMillis()
    val validNum = validData.size
    var validLoss = 0.0
    var truePos = 0  // ground truth: positive, prediction: positive
    var falsePos = 0 // ground truth: negative, prediction: positive
    var trueNeg = 0  // ground truth: negative, prediction: negative
    var falseNeg = 0 // ground truth: positive, prediction: negative

    for (i <- 0 until validNum) {
      val ins = validData.get(i)
      val pre = loss.predict(weights, ins.feature)
      if (pre * ins.label > 0) {
        if (pre > 0) truePos += 1
        else trueNeg += 1
      } else if (pre * ins.label < 0) {
        if (pre > 0) falsePos += 1
        else falseNeg += 1
      }
      validLoss += loss.loss(pre, ins.label)
    }

    val precision = 1.0 * (truePos + trueNeg) / validNum
    val trueRecall = 1.0 * truePos / (truePos + falseNeg)
    val falseRecall = 1.0 * trueNeg / (trueNeg + falsePos)
    logger.info(s"validation cost ${System.currentTimeMillis() - validStart} ms, "
      + s"loss=$validLoss, precision=$precision, "
      + s"trueRecall=$trueRecall, falseRecall=$falseRecall")
    (validLoss, truePos, trueNeg, falsePos, falseNeg, validNum)
  }

  def calLossAucPrecision(weights: Vector, validData: DataSet, loss: Loss): (Double, Int, Int, Int, Int, Int) = {
    val validStart = System.currentTimeMillis()
    val validNum = validData.size
    var validLoss = 0.0
    val scoresArray = new Array[Double](validNum)
    val labelsArray = new Array[Double](validNum)
    var truePos = 0  // ground truth: positive, precision: positive
    var falsePos = 0 // ground truth: negative, precision: positive
    var trueNeg = 0  // ground truth: negative, precision: negative
    var falseNeg = 0 // ground truth: positive, precision: negative

    for (i <- 0 until validNum) {
      val ins = validData.get(i)
      val pre = loss.predict(weights, ins.feature)
      if (pre * ins.label > 0) {
        if (pre > 0) truePos += 1
        else trueNeg += 1
      } else if (pre * ins.label < 0) {