object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  import org.mcda._
  
  import scala.math._
  
  val a = pow(exp(1.0/3), log(64.0))              //> a  : Double = 3.9999999999999996
  
  val r = MatrixRow(Vector(Pos(2), Pos(8), Neg(2), Diag, Pos(4)))
                                                  //> r  : org.mcda.MatrixRow =  2   8  1/2  1   4 
  
  r.rowVal                                        //> res0: Double = 2.0
  
  val r1 = MatrixRow(Vector(Diag, Pos(8), Pos(8)))//> r1  : org.mcda.MatrixRow =  1   8   8 
  val r2 = MatrixRow(Vector(Neg(8), Diag, Neg(8)))//> r2  : org.mcda.MatrixRow = 1/8  1  1/8
  val r3 = MatrixRow(Vector(Neg(8), Pos(8), Diag))//> r3  : org.mcda.MatrixRow = 1/8  8   1 
  
  r1.rowVal                                       //> res1: Double = 3.9999999999999996
  r2.rowVal                                       //> res2: Double = 0.25
  r3.rowVal                                       //> res3: Double = 1.0
  
  val m = PairCompMatrix(
    Vector(
      r1, r2, r3
    )
  )                                               //> m  : org.mcda.PairCompMatrix = 
                                                  //|  1   8   8  = 2.2857142857142856
                                                  //| 1/8  1  1/8 = 0.14285714285714285
                                                  //| 1/8  8   1  = 0.5714285714285714
                                                  //| 
  
}