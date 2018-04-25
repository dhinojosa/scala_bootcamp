package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

class InstancesSpec extends FunSuite with Matchers {
  test("Case 1: isInstanceOf determines the instance of a type") {
     val x:Any = 40
     //x shouldBe an [Int] //Bug, https://github.com/scalatest/scalatest/issues/1359
     x.isInstanceOf[Int] should be (true) //Workaround
     x shouldBe an [Number]
     x shouldBe an [AnyVal]
     x shouldBe an [Any]
  }

  test("Case 2: asInstanceOf converts or casts a type") {
    val x = 40
    x.asInstanceOf[Double] should be (40.0)
    a [ClassCastException] should be thrownBy {
      x.asInstanceOf[List[_]]
    }
  }
}
