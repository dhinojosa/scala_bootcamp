package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}


class ValsAndVarsSpec extends FunSuite with Matchers  {
  test("Case 1: Vals are like final") {
    val a = 10
    a should be (10)
  }

  test("Case 2: No reassignment of val is possible, we prefer vals") {
    val a = 10
    //a = 40 //Will not compile
  }

  test("Case 3: A var is reassignable") {
    var a = 10
    a = 19
    a should be (19)
  }

  test("""Case 4: A lazy val is a val that will not be evaluated,
      |  run this in REPL for understanding""".stripMargin) {
    lazy val a = {println("evaluated"); 5}
    println(a)
  }

  test("Case 5: lazy val forward reference") {
    lazy val a:Int = 10 + b
    lazy val b:Int = 15
    a should be (25)
  }

  test("Case 6: Exceptions in lazy val") {
    var divisor = 0
    lazy val quotient = 40 / divisor
    an [ArithmeticException] should be thrownBy {
       quotient
    }
    divisor = 2
    quotient should be (20)
  }

  test("Case 7: Bending vals and var to your will") {
    val isIncluded_? = true
    val `This value is the value I was telling you about!` = 40
    val `true` = false
  }
}
