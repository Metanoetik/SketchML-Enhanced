
package org.dma.sketchml.ml.gradient

import org.apache.spark.ml.linalg.{DenseVector, SparseVector}
import org.dma.sketchml.ml.gradient.Kind.Kind
import org.dma.sketchml.ml.util.Maths
import org.dma.sketchml.sketch.base.SketchMLException

class DenseFloatGradient(d: Int, val values: Array[Float]) extends Gradient(d) {
  def this(d: Int) = this(d, new Array[Float](d))

  def this(grad: Gradient) {
    this(grad.dim, new Array[Float](grad.dim))
    grad.kind match {
      case Kind.DenseDouble => fromDense(grad.asInstanceOf[DenseDoubleGradient])
      case Kind.SparseDouble => fromSparse(grad.asInstanceOf[SparseDoubleGradient])
      case _ => throw new SketchMLException(s"Cannot create ${this.kind} from ${grad.kind}")
    }
  }

  def fromDense(dense: DenseDoubleGradient): Unit = {
    val dv = dense.values
    for (i <- 0 until dim)
      values(i) = dv(i).toFloat
  }

  def fromSparse(sparse: SparseDoubleGradient): Unit = {
    val k = sparse.indices
    val v = sparse.values
    for (i <- k.indices)
      values(k(i)) = v(i).toFloat
  }

  override def plusBy(dense: DenseDoubleGradient): Gradient = {
    for (i <- 0 until dim)
      values(i) += dense.values(i).toFloat
    this
  }

  override def plusBy(sparse: SparseDoubleGradient): Gradient = {
    val k = sparse.indices
    val v = sparse.values
    for (i <- k.indices)
      values(k(i)) += v(i).toFloat
    this
  }

  override def plusBy(dense: DenseFloatGradient): Gradient = {
    for (i <- 0 until dim)
      values(i) += dense.values(i)
    this
  }

  override def plusBy(sparse: SparseFloatGradient): Gradient = {
    val k = sparse.indices
    val v = sparse.values
    for (i <- k.indices)
      values(k(i)) += v(i)
    this
  }

  override def plusBy(sketchGrad: SketchGradient): Gradient = plusBy(sketchGrad.toAuto)

  override def plusBy(fpGrad: FixedPointGradient): Gradient = plusBy(fpGrad.toAuto)

  override def plusBy(zipGrad: ZipGradient): Gradient = plusBy(zipGrad.toAuto)
