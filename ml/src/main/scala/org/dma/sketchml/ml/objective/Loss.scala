package org.dma.sketchml.ml.objective

import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.dma.sketchml.ml.util.Maths

trait Loss extends Serializable {
  def loss(pre: Double, y: Double): Double

  def grad(pre: Double, y: Double): Double

  def predict(w: Vector, x: Vector): Double

  def isL1Reg: Boolean

  def isL2Reg: Boolean

  def getRegParam: Double

  def getReg(w: Vector): Double
}

abstract class L1Loss extends Loss {
  protected var lambda: Double

  override def isL1Reg: Boolean = this.lambda > Maths.EPS

  override def isL2Reg: Boolean = false

  override def getRegParam: Double = lambda

  override def getReg(w: Vector): Doub