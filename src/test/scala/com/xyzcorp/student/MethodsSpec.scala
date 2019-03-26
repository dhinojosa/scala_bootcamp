package com.xyzcorp.student

import org.scalatest.{FunSuite, Matchers}

class MethodsSpec extends FunSuite with Matchers {
  test("Case 1: A method is structured like the following:") {
    def foo(x: Int): Int = {
      x + 1
    }

    foo(4) should be(5)
  }

  test("Case 2: Also a method can be inlined if there is only one statement:") {
    def foo(x: Int): Int = x + 1

    foo(4) should be(5)
  }

  test(
    """Case 3: Methods can be embedded, in case one method is
      |  exclusively only being used by another""".stripMargin) {
    def foo(x: Int, y: Int): Int = {
      def bar(): Int = x + y + 10

      bar()
    }

    foo(4, 10) should be(24)
  }

  test(
    """Lab: Recursion is supported just like another language,
      |  we can do a factorial using recursion""".stripMargin) {
    pending

    def divide(numerator: Int, denominator: Int): Option[Int] = ???

    divide(1, 0) should be(None)
    divide(1, 1) should be(Some(1))
    divide(4, 2) should be(Some(2))
    divide(10, 2) should be(Some(5))
    divide(9, 3) should be(Some(3))
    divide(5, 5) should be(Some(1))
  }

  test(
    """Case 4: Multi-parameter lists are groups or argument lists,
      |  the purpose are two fold: The get group like terms, and
      |  they make it easy to be partially applied""".stripMargin) {

    def foo(x:Int)(y:Int)(a:String, b:String) = {
      a + (x + y) + b
    }

    foo(3)(5)("<<<", ">>>") should be ("<<<8>>>")
  }


  test(
    """Case 6: Repeated parameters are the equivalent of varargs in Java, they
      |  allow additional parameters and inside the method they
      |  are just a collection called WrappedArray""".stripMargin) {

    def varargs[A, B](arg1: A, rest: B*) = {
      s"arg1=$arg1, rest=$rest"
    }

    pending
  }

  test(
    """Case 7: Repeated parameters can be sent a list or any other collection,
      |  but the problem is what happens when we just send collection
      |  it would treat it as a single unit instead you can expand the units
      |  with a :_*""".stripMargin) {

    def varargs[A, B](arg1: A, rest: B*) = {
      s"arg1=$arg1, rest=$rest"
    }

    pending
  }

  test("Case 8: Generics with methods: Convert the following with generics") {
    def decide(b: Boolean, x: Any, y: Any): Any = if (b) x else y
  }

  test(
    """Case 9: Default methods have just methods that have a value
      |  in case you don't have one at the moment.
      |  Another item you'll see with this example
      |  is the named parameter. You can set a parameter
      |  explicitly by the name to avoid any confusion as to what you
      |  are setting""".stripMargin) {

    pending
  }
}
