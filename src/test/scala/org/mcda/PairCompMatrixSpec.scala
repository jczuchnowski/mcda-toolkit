package org.mcda

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import org.specs2.matcher.DataTables

@RunWith(classOf[JUnitRunner])
class PairCompMatrixSpec extends Specification with DataTables {

  "matrix row" should {
    
    "return correct row value" in {
      
      "x1"   | "x2" | "x3" | "x4" | "x5" | "res" |
      Pos(5) ! Neg(5) ! Diag   ! Pos(3) ! Neg(3) ! 1.0 |
      Pos(5) ! Pos(5) ! Pos(5) ! Pos(5) ! Pos(5) ! 5.0 |
      Pos(2) ! Pos(8) ! Neg(2) ! Diag   ! Pos(4) ! 2.0 |> {
        (x1, x2, x3, x4, x5, res) => MatrixRow(Vector(x1, x2, x3, x4, x5)).rowVal must_== res
      }
      
    }
    
  }
  
  "pairwise comparison matrix" should {
    
    "throw exception when diagonals are different than 1" in {
      PairCompMatrix(
        Vector(
          MatrixRow(Vector(Diag, Pos(7), Neg(2), Pos(5))),
          MatrixRow(Vector(Pos(2), Neg(4), Diag, Neg(3))),
          MatrixRow(Vector(Neg(7), Diag, Pos(4), Pos(9))),
          MatrixRow(Vector(Neg(5), Neg(9), Pos(3), Diag))
        )
      ) must throwA[IllegalArgumentException](message = "Diagonals must be equal 1.")
    }
    
    "throw exception when row sizes are different" in {
      PairCompMatrix(
        Vector(
          MatrixRow(Vector(Diag, Pos(7), Neg(2), Pos(5))),
          MatrixRow(Vector(Neg(7), Diag, Pos(4))),
          MatrixRow(Vector(Pos(2), Neg(4), Diag, Neg(3))),
          MatrixRow(Vector(Neg(5), Neg(9), Pos(3), Diag))
        )
      ) must throwA[IllegalArgumentException](message = "Different row sizes - matrix must be square.")
    }
    
    "throw exception when number of columns is different than rows" in {
      PairCompMatrix(
        Vector(
          MatrixRow(Vector(Diag, Pos(7), Neg(2), Pos(5))),
          MatrixRow(Vector(Neg(7), Diag, Pos(4), Pos(9))),
          MatrixRow(Vector(Pos(2), Neg(4), Diag, Neg(3)))
        )
      ) must throwA[IllegalArgumentException](message = "Different column and row sizes - matrix must be square.")
    }
    
    "return new matrix" in {
      PairCompMatrix(
        Vector(
          MatrixRow(Vector(Diag, Pos(7), Neg(2), Pos(5))),
          MatrixRow(Vector(Neg(7), Diag, Pos(4), Pos(9))),
          MatrixRow(Vector(Pos(2), Neg(4), Diag, Neg(3))),
          MatrixRow(Vector(Neg(5), Neg(9), Pos(3), Diag))
        )
      ) must not beNull
    }
    
    
  }
}