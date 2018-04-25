package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

import scala.annotation.tailrec

class MethodsSpec extends FunSuite with Matchers {
  test("Case 1: A method is structured like the following:") {
    def add(x:Int, y:Int):Int = {
      return x + y
    }

    add(4, 5) should be (9)
  }

  test("Case 2: Also a method can be inlined if there is only one statement:") {
    def add(x:Int, y:Int) = x + y
    add(4, 5) should be (9)
  }

  test("""Case 3: Methods can be embedded, in case one method is
      |  exclusively only being used by another""".stripMargin) {
    def foo(x: Int, y: Int) = {
      def bar(z: Int) = {
        z + 10
      }
      bar(x + y)
    }

    foo(4, 10) should be(24)
  }

  test("""Case 4: Recursion is supported just like another language,
      |  we can do a factorial using recursion.""".stripMargin) {

    def factorial(n:Int):Int = {
      require(n >= 0)
      if (n == 0) 1
      else n * factorial(n - 1)
    }


    factorial(0) should be (1)
    factorial(1) should be (1)
    factorial(2) should be (2)
    factorial(3) should be (6)
    factorial(4) should be (24)

    a [IllegalArgumentException] should be thrownBy {
      factorial(-1)
    }
  }


  test("""Case 4.5: Recursion is supported just like another
         |  language, we can do a factorial 
         |  using tail recursion.""".stripMargin) {

    def factorial(i: Int):Int = {
      require(i >= 0)
      @tailrec
      def fact(n: Int, acc: Int): Int =
        if (n == 0) acc
        else fact(n - 1, n * acc)
      fact(i, 1)
    }


    factorial(0) should be (1)
    factorial(1) should be (1)
    factorial(2) should be (2)
    factorial(3) should be (6)
    factorial(4) should be (24)

    a [IllegalArgumentException] should be thrownBy {
      factorial(-1)
    }
  }

  test("""Case 5: Multi-parameter lists are groups or argument lists,
      |  the purpose are two fold: The get group like terms, and
      |  they make it easy to be partially applied""".stripMargin) {
    def multiParameters(w:Int)(x:Int)(y:String, z:String) = {
      y + (w + x) + z
    }
    multiParameters(10)(20)("<<", ">>") should be ("<<30>>")
  }

  test("""Case 5.1 Multi-parameter with partial application""".stripMargin) {
    def multiParameters(w:Int)(x:Int)(y:String, z:String) = {
      y + (w + x) + z
    }

    val a = multiParameters(10)(20)_
    a("??", "??")
  }

  test(
    """Case 5.2 Multi-parameter with partial application
      |  partially apply the middle int""".stripMargin) {
    def multiParameters(w:Int)(x:Int)(y:String, z:String) = {
      y + (w + x) + z
    }

    val a = multiParameters(10)(_:Int)("<<", ">>")
    a(40)
  }

  test(
    """Case 5.3 Multi-parameter with partial application
      |  partially apply the middle int""".stripMargin) {
    def multiParameters(w:Int)(x:Int)(y:String, z:String) = {
      y + (w + x) + z
    }

    val a = multiParameters(_:Int)(_:Int)("<<", ">>")
    a(30,40) should be ("<<70>>")
  }


  test(
    """Case 5.3 Multi-parameter partially apply everything""".stripMargin) {
    def multiParameters(w:Int)(x:Int)(y:String, z:String) = {
      y + (w + x) + z
    }

    val a = multiParameters _
    a(10)(30)("??", "??")
  }

  test(
    """Case 5.3 Multi-parameter partially from all four""".stripMargin) {
    def multiParameters(w:Int, x:Int, y:String, z:String) = {
      y + (w + x) + z
    }

    val a = multiParameters(_:Int, _:Int, "<<", ">>")
    a(10,30) should be ("<<40>>")
  }

  test("""Case 6: Repeated parameters are the equivalent of varargs in Java, they
      |  allow additional parameters and inside the method they
      |  are just a collection called WrappedArray""".stripMargin) {

    def varargs[A, B](x: A, ys: B*) = {
      s"arg1=$x, rest=$ys"
    }

    varargs(10, "Foo", "Bar", "Baz") should be ("arg1=10, " +
      "rest=WrappedArray(Foo, Bar, Baz)")

    varargs(10, "Foo":_*) should be ("arg1=10, rest=Foo")
  }

  test("""Case 7: Repeated parameters can be sent a list or any other collection,
      |  but the problem is what happens when we just send collection
      |  it would treat it as a single unit instead you can expand the units
      |  with a :_*""".stripMargin) {

    def varargs[A, B](x: A, ys: B*) = {
      s"arg1=$x, rest=$ys"
    }

    varargs(10.0, List(1, 2, 3, 4):_*) should be ("arg1=10.0, " +
      "rest=List(1, 2, 3, 4)")
  }

  test("Case 8: Generics with methods: Convert the following with generics") {
    def decide[A](b:Boolean, x:A, y:A):A = if (b) x else y


    val result0: AnyVal = decide[AnyVal](true, 10, 40)
    val result1: Int = decide(true, 10, 40)  //Preferred
    result1
  }

  test("Case 8.1: Generics with methods: Convert the following with generics") {
    def decide[A, B](b:Boolean, x:A, y:B):Option[(A, B)] =
      if (b) Some(x,y) else None

    val maybeTuple1: Option[(Double, String)] = decide(true, 3.0, "Foo")
    val maybeTuple2: Option[(Option[Int], Char)] =
      decide(true, Some(30):Option[Int], 'a')

    maybeTuple2.getOrElse((None,'a'))
  }

  test("""Case 9: Default methods have just methods that have a value
      |  in case you don't have one at the moment.
      |  Another item you'll see with this example
      |  is the named parameter. You can set a parameter
      |  explicitly by the name to avoid any confusion as to what you
      |  are setting""".stripMargin) {

    class Employee(firstName:String,
                   middleName: Option[String] = None,
                   lastName:String)

    def createEmployeeNoMiddleName(firstName:String,
                                   lastName:String) = {
      new Employee(firstName, middleName = None, lastName)
    }
  }

  test("""Case 10: Careful with types and what gets returned,
      |  numberStatus in this case will return Any""".stripMargin) {
    def numberStatus(x:Int) = {
      if (x > 10) "Greater than 10"
      else if (x < 10) "Less than 10"
      else 10
    }

    numberStatus(10)
  }

  test("""Case 11: Apply method does not need be called""".stripMargin) {
    class Foo(x:Int) {
      def apply(y:Int) = x + y
    }

    val foo = new Foo(10)
    val result1 = foo.apply(20) //with apply
    val result2 = foo(20)
    result1 should be (30)
    result2 should be (30)
  }

  test("""Case 12: Infix operators. As long as there is a
          |  method with one parameter, 
          |  you can use an infix operator""".stripMargin) {
      class Foo(x:Int) {
        def bar(y:Int) = x + y
      }
    
      val foo = new Foo(10)
      val result = foo bar 20 //infix operator
      result should be (30)
  }

  test("""Case 13: Infix operator with an operator overloaded
      |  operator""".stripMargin) {
      class Foo(x:Int) {
        def ~(y:Int) = x + y
      }
    
      val foo = new Foo(10)
      val result = foo ~ 20 //infix operator
      result should be (30)
  }

  test("""Case 14: Infix operator with an operator overloaded that
      |  ends with a ':'""".stripMargin) {
    class Foo(x:Int) {
      def ~:(y:Int) = x + y
    }

    val foo = new Foo(10)
    val result = 20 ~: foo //infix operator, right associative colon
    result should be (30)
  }
}
