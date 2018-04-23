package com.xyzcorp

import org.scalatest.{FunSuite, Matchers}

class ValsAndVarsSpec extends FunSuite with Matchers {
  test("Vals are like final") {
    val a = 10
  }

  test("No reassignment of val is possible") {
    val a = 10
    //a = 40 //Will not compile
  }

  test("A var is reassignable") {
    var a = 10
    a = 19
    a should be (19)
  }

  test("A lazy val is a val that will not be evaluated, run this in REPL for understanding") {
    lazy val a = {println("evaluated"); 5}
    println(a)
  }

  test("Lab 1: lazy val forward reference") {
    pending
  }

  test("Exceptions in lazy val") {
    var divisor = 0
    lazy val quotient = 40 / divisor
    an [ArithmeticException] should be thrownBy {
       quotient
    }
    divisor = 2
    quotient should be (20)
  }

  test("Bending vals and var to your will") {
    val isIncluded_? = true
    val `This value is the value I was telling you about!` = 40
    val `true` = false
  }
}
