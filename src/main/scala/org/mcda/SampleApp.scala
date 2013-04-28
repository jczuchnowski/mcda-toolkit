package org.mcda

import AggFunctions._

object SampleApp extends App {

  //data I'm choosing from
  val data = Map[Int, Map[String, Double]](
      1 -> Map("area" -> 110, "distance" -> 30, "floors" -> 1, "year" -> 2010),
      2 -> Map("area" -> 85, "distance" -> 40, "floors" -> 2, "year" -> 2005),
      3 -> Map("area" -> 140, "distance" -> 50, "floors" -> 2, "year" -> 2009),
      4 -> Map("area" -> 100, "distance" -> 60, "floors" -> 1, "year" -> 2012),
      5 -> Map("area" -> 115, "distance" -> 55, "floors" -> 2, "year" -> 2008)
  )
  
  val ranks = Map[String, Double]("area" -> 1, "distance" -> 2, "floors" -> 3, "year" -> 4)
  
  //not sure if the sum of the ranks shouldn't be 1
  val max = ranks.map(el => el._2).max
  val normRanks = ranks.map(( el ) => el._1 -> el._2 / max)
  
  //my criteria tree
  val root = GlobalCriterion("myDreamHouse", additive, Seq(
      LocalCriterion("area", MembershipFunction(80,100,120,150)),//not too big - 100-120 m2 would be perfect
      LocalCriterion("distance", MembershipFunction(20,40,50,60)),// 40-50km from Warsaw would be perfect
      LocalCriterion("floors", MembershipFunction(0,2,2,2)),//2 floors would be perfect
      LocalCriterion("year", MembershipFunction(2000,2012,2012,2012))//younger is better
  ))
  
  val results = data.map( el => el._1 -> root.calcValue(el._2, normRanks, 1) )
  
  println(results)
  
}