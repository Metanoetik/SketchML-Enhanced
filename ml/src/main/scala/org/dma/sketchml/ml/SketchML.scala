package org.dma.sketchml.ml

import org.apache.spark.{SparkConf, SparkContext}
import org.dma.sketchml.ml.algorithm._
import org.dma.sketchml.ml.common.Constants
import org.dma.sketchml.ml.conf.MLConf

object SketchML {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SketchML")
    implicit val sc = SparkContext.getOrCreate(sparkConf)
    val mlConf = MLConf(sparkConf)
    val model = mlConf.algo match {
      case Constants.M