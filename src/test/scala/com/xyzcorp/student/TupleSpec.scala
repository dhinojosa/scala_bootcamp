package com.xyzcorp.student

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
    val r: Int = t3._1
  }

  test("Case 3: Swapping a Tuple2") {
    val t2 = (1, 3.0)
    t2.swap._1 should be(3.0)
  }

  test("Case 4: Syntactic sugar for Tuple2") {
    val t2 = 1 -> "Zoom"
    t2._2 should be("Zoom")
  }

  test(
    """Lab: Recursion with tuples, except this time return the quotient
      | and the remainder as a tuple, use the following
      | test as guidance. Remove the pending when ready""".stripMargin) {

    pending

    def divide(numerator: Int, denominator: Int): Option[(Int, Int)] = ???

    divide(1, 0) should be(None)
    divide(1, 1) should be(Some((1, 0)))
    divide(4, 2) should be(Some((2, 0)))
    divide(10, 2) should be(Some((5, 0)))
    divide(9, 3) should be(Some((3, 0)))
    divide(5, 5) should be(Some((1, 0)))
    divide(3, 2) should be(Some((1, 1)))
  }
}
