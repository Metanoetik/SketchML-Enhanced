
package org.dma.sketchml.ml.conf

import org.apache.spark.SparkConf
import org.dma.sketchml.ml.common.Constants._
import org.dma.sketchml.sketch.base.{Quantizer, SketchMLException}
import org.dma.sketchml.sketch.sketch.frequency.{GroupedMinMaxSketch, MinMaxSketch}

object MLConf {
  // ML Conf
  val ML_ALGORITHM: String = "spark.sketchml.algo"
  val ML_INPUT_PATH: String = "spark.sketchml.input.path"
  val ML_INPUT_FORMAT: String = "spark.sketchml.input.format"
  //val ML_TEST_DATA_PATH: String = "spark.sketchml.test.path"
  //val ML_NUM_CLASS: String = "spark.sketchml.class.num"
  //val DEFAULT_ML_NUM_CLASS: Int = 2
  val ML_NUM_WORKER: String = "spark.sketchml.worker.num"
  val ML_NUM_FEATURE: String = "spark.sketchml.feature.num"
  val ML_VALID_RATIO: String = "spark.sketchml.valid.ratio"
  val DEFAULT_ML_VALID_RATIO: Double = 0.25
  val ML_EPOCH_NUM: String = "spark.sketchml.epoch.num"
  val DEFAULT_ML_EPOCH_NUM: Int = 100
  val ML_BATCH_SAMPLE_RATIO: String = "spark.sketchml.batch.sample.ratio"
  val DEFAULT_ML_BATCH_SAMPLE_RATIO: Double = 0.1
  val ML_LEARN_RATE: String = "spark.sketchml.learn.rate"
  val DEFAULT_ML_LEARN_RATE: Double = 0.1
  val ML_LEARN_DECAY: String = "spark.sketchml.learn.decay"
  val DEFAULT_ML_LEARN_DECAY: Double = 0.9
  val ML_REG_L1: String = "spark.sketchml.reg.l1"
  val DEFAULT_ML_REG_L1: Double = 0.1
  val ML_REG_L2: String = "spark.sketchml.reg.l2"
  val DEFAULT_ML_REG_L2: Double = 0.1
  // Sketch Conf
  val SKETCH_GRADIENT_COMPRESSOR: String = "spark.sketchml.gradient.compressor"
  val DEFAULT_SKETCH_GRADIENT_COMPRESSOR: String = GRADIENT_COMPRESSOR_SKETCH