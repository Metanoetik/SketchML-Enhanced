package org.dma.sketchml.ml.objective

import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.dma.sketchml.ml.util.Maths

trait Loss extends Serializable {
  def loss(pre: Double, y: Double): Double

  def grad(pre: Double, y: Double): Double

  def predict(w: Vector, x: Vector): Double

  def 