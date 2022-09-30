
package org.dma.sketchml.ml.util

import org.apache.spark.ml.linalg.{DenseVector, SparseVector, Vector, Vectors}

import scala.collection.mutable.ArrayBuffer

object Maths {
  val EPS = 1e-8
