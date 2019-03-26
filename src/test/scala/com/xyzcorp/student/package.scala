package com.xyzcorp

package object student {
  implicit val tuple2ToList: Tuple2[Int, String] => List[String]
  = t => List.fill(t._1)(t._2)
}
