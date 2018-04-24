package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

class TupleSpec extends FunSuite with Matchers {
  test("Case 1: Creating a tuple") {
    val t = (1, "Foo")
    val t1: (Int, String) = (1, "Foo")
    val t2: Tuple2[Int, String] = (1, "Foo")
    val t3 = (1, "Foo", 4.9)
  }

  test("Case 2: Getting information from a tuple") {
    val t3 = (1, "Foo", 4.9)
    t3._1 should be(1)
    t3._2 should be("Foo")
    val r: Int = t3._1 //We get the type without cast
  }

  test("Case 3: Swapping a Tuple2") {
    val t2 = (1, 3.0)
    t2.swap._1 should be(3.0)
  }

  test("Case 4: Syntactic sugar for Tuple2") {
    val t2 = 1 -> "Zoom"
    t2._2 should be ("Zoom")
  }

  test("Case 4.1: Syntactic sugar for Tuple2") {
    val t2 = 1 -> "Zoom" -> 3.0
    t2 should be (((1, "Zoom"), 3))
  }
}
