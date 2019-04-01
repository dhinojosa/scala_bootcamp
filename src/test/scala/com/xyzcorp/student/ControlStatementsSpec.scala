package com.xyzcorp.student

import org.scalatest.{FunSuite, Matchers}

class ControlStatementsSpec extends FunSuite with Matchers {
  test(
    """Case 1: if, else if, else done in an imperative fashion. Note
      | the var, which we don't usually like""".stripMargin) {

    val a = 10
    var result = "" // var is usually a code smell
    if (a < 10) {
      result = "Less than 10"
    } else if (a > 10) {
      result = "Greater than 10"
    } else {
      result = "It is 10!"
    }
    result should be("It is 10!")
  }

  test("Case 2: if, else if, else functional. Notice that this uses all vals") {
    val a = 10
    val result = if (a < 10) "Less than 10"
    else if (a > 10) "Greater than 10"
    else "It is 10!"

    result should be ("It is 10!")
  }

  test(
    """Case 3: while loop, underused, but still used by API developers,
      | again, note the var""".stripMargin) {
    var a = 10
    var result = "" // var is usually a code smell
    while (a > 0) {
      result = result + a
      if (a > 1) result = result + ","
      a = a - 1
    }
    result should be("10,9,8,7,6,5,4,3,2,1")
  }

  test(
    """Case 4: The following is the same as the while loop, but functional
      | programming makes things easier. A few of these are things we have
      | not covered yet. Just to give you a flavor""".stripMargin) {
    (10 to 1 by -1).mkString(",") should be("10,9,8,7,6,5,4,3,2,1")
  }

  test("Case 5: do while loop, underused, still used by API Developers") {
    var a = 10 // var is usually a code smell
    var result = ""
    do {
      result = result + a
      if (a > 1) result = result + ","
      a = a - 1
    } while (a > 0)

    result should be("10,9,8,7,6,5,4,3,2,1")
  }

  test(
    """Case 6: for loop, also underused in this current form,
      |  prefer a for comprehension""".stripMargin) {

    var result = "" // var is usually a code smell
    for (a <- 1 to 10) {
      // a for loop
      if (a > 1) result = result + ","
      result = result + a
    }

    result should be("1,2,3,4,5,6,7,8,9,10")
  }

  test("Case 7: Create a simple for comprehension") {
     val result = for (a <- 1 to 5) yield a * 3
     result should contain inOrder (3, 6, 9, 12, 15)
  }

  test(
    """Lab: Take the following two lists and combine them
       |  as a sum using a for comprehension""".stripMargin) {

    pending

    val xs = List(1,2,3,4)
    val ys = List(10,20,30,40)

    val result:List[Int] = ???

    result should be (List(11, 21, 31, 41, 12, 22, 32, 42, 13, 23, 33, 43, 14, 24, 34, 44))
  }
}
