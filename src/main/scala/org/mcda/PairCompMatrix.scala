package org.mcda

object PairCompMatrix {  
	implicit def MatrixElToDouble(el: MatrixEl): Double = el.value
	implicit def MatrixRowToVector(row: MatrixRow): Vector[MatrixEl] = row.elements
}

import PairCompMatrix._

sealed trait MatrixEl {
  def pos: Int
  def neg: Int
  lazy val value: Double = pos.toDouble / neg
  
  override def toString = pos + "/" + neg
}

case class Pos(pos: Int) extends MatrixEl {
  val neg = 1
  override def toString = " " + pos + " "
}

case class Neg(neg: Int) extends MatrixEl {
  val pos = 1
}

case object Diag extends MatrixEl {
  val neg = 1
  val pos = 1
  
  override def toString = " " + pos + " "
}

case class MatrixRow(elements: Vector[MatrixEl]) {
  import scala.math._
  
  lazy val rowVal = pow(elements.foldLeft(1.0)(_ * _), 1.0 / elements.size)
  
  override def toString = elements.mkString(" ")
}

/**
 * Pairwise comparison matrix.
 * 
 * Square matrix with all diagonal elements equal 1.
 * Condition must be met that: a_ij = 1 / a_ji.
 * 
 */
case class PairCompMatrix(rows: Vector[MatrixRow]) {

  rows match {
    case Vector(el, _*) =>
      val size = el.size
      if (size != rows.size) throw new IllegalArgumentException("Different column and row sizes - matrix must be square.")
      for(row <- rows) { 
        if (row.size != size) throw new IllegalArgumentException("Different row sizes - matrix must be square.")
      }
      for(i <- 0 until size) {
        if(rows(i)(i).value != 1.0) throw new IllegalArgumentException("Diagonals must be equal 1.")
      }
      for(
          i <- 0 until size;
          j <- 0 until size
      ) {if (rows(i)(j).value != (1.0 / rows(j)(i))) throw new IllegalArgumentException("Violated condition that: a_ij == 1 / a_ji.")}
      
    case _ => //nothing
  }
  
  /**
   * Matrix size.
   */
  val size: Int = rows.size
  
  private lazy val rowsSum = rows.foldLeft(0.0)(_ + _.rowVal)
  
  private def calculateFactor(row: MatrixRow) = row.rowVal / rowsSum * size
  
  lazy val factors: Vector[Double] = rows.map(calculateFactor(_))
  
  override def toString =
    (for(i <- 0 until size) yield {
      rows(i).toString + " = " + factors(i)
    }).mkString("\n","\n", "\n")
}