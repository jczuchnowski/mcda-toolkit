package org.decision

case class MembershipFunction(x1: Double, x2: Double, x3: Double, x4: Double) {
  
  lazy private val a1 = a( (x1, 0), (x2, 1) )
  lazy private val b1 = b( (x1, 0), (x2, 1) )
  
  lazy private val a2 = a( (x3, 1), (x4, 0) )
  lazy private val b2 = b( (x3, 1), (x4, 0) )
    
  lazy private val funcX1X2: Double => Double = x => a1 * x + b1
  lazy private val funcX3X4: Double => Double = x => a2 * x + b2
  
  def calcValue(x: Double): Double = {
    x match {
      case x if (x <= x1) => 0
      case x if (x > x1 && x < x2) => funcX1X2(x)
      case x if (x >= x2 && x <= x3) => 1
      case x if (x > x3 && x < x4) => funcX3X4(x)
      case x if (x >= x4) => 0
    }
  }
  
  private def a(A: (Double, Double), B: (Double, Double)) = (B._2 - A._2) / (B._1 - A._1)
  private def b(A: (Double, Double), B: (Double, Double)) = (B._2 * A._1 - A._2 * B._1) / (A._1 - B._1)
}

trait NamedCriterion {
  val name: String
}

case class LocalCriterion(name: String, func: MembershipFunction) extends NamedCriterion {
  
  def calcValue(in: Double, rank: Double): Double = func.calcValue(in) * rank
  
}

case class GlobalCriterion(val name: String, val aggFunc: (Seq[Double]) => Double, val subs: Seq[NamedCriterion]) extends NamedCriterion {
  
  def calcValue(values: Map[String, Double], childRanks: Map[String, Double], rank: Double): Double = {
    val comp = subs.par.map(crit => {
      crit match {
        case c: LocalCriterion => c.calcValue(values(c.name), childRanks(c.name))
        case c: GlobalCriterion => c.calcValue(values, childRanks, childRanks(c.name))
      }
    })
    
    aggFunc(comp.seq) * rank
  }
}

object AggFunctions {
  
  /**
   * Additive method - Laplace criterion
   */
  val additive = (criterions: Seq[Double]) => criterions.reduceLeft(_ + _) / criterions.size
  
}