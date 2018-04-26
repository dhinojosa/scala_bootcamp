package com.xyzcorp.instructor

import java.util.concurrent.ExecutorService

import org.scalatest.{FunSpec, Matchers}

class ImplicitsSpec extends FunSpec with Matchers {

  describe(
    """Implicits is like a Map[Class[A], A] where A is any
      |  object and it is tied into the scope,
      |  and it is there when you need it, hence it is implicit.
      |  This provide a lot of great techniques that we
      |  can use in Scala.""".stripMargin) {

    it(
      """Case 1: is done per scope so in the following example,
        |  we will begin with an implicit value
        |  and call it from inside a method which uses
        |  a multiple parameter list where one
        |  one group would """.stripMargin) {

      implicit val rate: Int = 100

      def calculatePayment(hours: Int)(implicit currentRate: Int) = hours * rate

      calculatePayment(50) should be(5000)
    }

    it(
      """Case 2: will allow you to place something manually,
        |  if you want to override the implicit value""".stripMargin) {

      implicit val rate: Int = 100
      //implicit val age:Int = 20

      def calculatePayment(hours: Int)(implicit currentRate: Int) = hours * rate

      calculatePayment(50) should be(5000)
    }

    it(
      """Case 3: will gripe at compile time if there are two
        |  implicit bindings of the same type.  It's
        |  worth noting that what Scala doing are compile time tricks for implicit.
        |  One strategy is to wrap a value in a type to avoid conflict"""
        .stripMargin) {
      case class Rate(value: Int)
      case class Age(value: Int)

      implicit val rate: Rate = Rate(100)
      implicit val age: Age = Age(40)

      def calculatePayment(hours: Int)(implicit currentRate: Rate) = {
        def methodA(x: Int) = x + (hours * currentRate.value)

        methodA(10)
      }
    }


    it("""Case 3.1: The closer scope wins""") {
      case class Rate(value: Int) {
        implicit val rate = new Rate(10) {
          {
            implicit val rate = new Rate(40)
            //def foo(x:Int)(implicit y:Rate) = x * y.value
            println(implicitly[Rate].value * 40)
          }
        }
      }
    }

    it(
      """Case 4: is really used to bind services that require something and
        |  you don't particularly need to inject everywhere explicitly, in this
        |  case let's discuss Future[+T]""".stripMargin) {

      import scala.concurrent._
      import java.util.concurrent.Executors

      //Java
      val executorService = Executors.newFixedThreadPool(10)

      //Scala
      implicit val executionContext = ExecutionContext
        .fromExecutorService(executorService)


      val f = Future {
        println(s"In Future Thread: ${Thread.currentThread().getName}")
        Thread.sleep(2000)
        100 + 400
      }

      f.foreach(x => {
        println(s"x: $x on Thread: ${Thread.currentThread().getName}")
      })


      val f1 = Future {
        Thread.sleep(4000); 1000
      }
      val f2 = Future {
        Thread.sleep(2000); 500
      }

      val f3: Future[(Int, Int)] = f1.flatMap(a1 => f2.map(a2 => a1 -> a2))


      val f4: Future[(Int, Int)] = for (a1 <- f1; a2 <- f2) yield (a1 -> a2)
    }

    it(
      """Case 5: can bring up any implicit directly by merely
        |   calling up implicitly""".stripMargin) {

      case class IceCream(name: String)
      case class Scoops(n: Int, flavor: IceCream)
      implicit val flavorOfTheDay: IceCream = IceCream("Spring Green")

      def orderIceCream(num: Int)(implicit flavorOfTheDay: IceCream) = {
        Scoops(num, flavorOfTheDay)
      }

      orderIceCream(3)(IceCream("Rocky Road"))

      def orderIceCream2(num: Int) = {
        Scoops(num, implicitly[IceCream])
      }

      def orderIceCream3(n: Int, flavor: IceCream = implicitly[IceCream]) = {
        Scoops(n, flavor)
      }
    }

    it(
      """Case 6: the implicit group parameter list, can
        |  contain more than one parameter, but
        |  needs to be in the same implicit parameter group""".stripMargin) {
      implicit val bonus: Int = 5000
      implicit val currency: String = "Euro"

      def calcYearRate(amount: Int)
                      (implicit bonusAmt: Int, preferredCurrency: String) = {
        amount + bonusAmt + " " + preferredCurrency
      }

      calcYearRate(60000) should be("65000 Euro")
    }

    it(
      """Case 7: can also be replaced with default
        |  parameters, choose accordingly""".stripMargin) {
      def calcYearRate(amount: Int, bonusAmt: Int = 5000,
                       currency: String = "Euro") = {
        amount + bonusAmt + " " + currency
      }

      calcYearRate(60000) should be("65000 Euro")
    }


    it(
      """Case 8: If you have a List[String] implicitly will it try
        |  to inject into a List[Double]?""".stripMargin) {

      implicit val listOfString = List("Foo", "Bar", "Baz")
      implicit val listOfDouble = List(1.0, 2.0, 3.0)

      val result = implicitly[List[Double]]

      result should be(List(1.0, 2.0, 3.0))
    }


    it(
      """Case 9: can be used for something like what Ruby has called
        |  monkey patching or Groovy calls mopping where we can add functionality to
        |  a class that we don't have access to, like isOdd/isEven
        |  in the Int class.  This is what we call implicit wrappers.
        |  First we will use a conversion method.""".stripMargin) {

      pending
    }


    it(
      """Case 10: Implicit wrappers can be created using a function and
        |  is often easier to mental map."""
        .stripMargin) {
      pending
    }

    it(
      """Case 11: can be use a short hand version of this called
        |  implicit classes, before using them
        |  there are some rules:
        |
        |  1. They can only be used inside of an object/trait/class
        |  2. They can only take one parameter in the constructor
        |  3. There can not be any colliding method name as that
        |     with the implicit outer scope
        |
        |""".stripMargin) {

      implicit class IntWrapper(x: Int) {
        def isOdd = x % 2 != 0

        def isEven = !isOdd
      }

      10.isOdd should be(false)
    }

    it(
      """Case 12: can also convert things to make it fit into a particular API,
        |  this is called implicit conversion,
        |  in this scenario we will use a method""".stripMargin) {

      import scala.language.implicitConversions

      sealed abstract class Currency
      case class Dollar(value: Int) extends Currency
      case class Yen(value: Int) extends Currency

      implicit def pinkFloydRules(i: Int): Dollar = Dollar(i)

      implicit def zepplinRules(i: Int): Yen = Yen(i)

      def addAmount(x: Dollar, y: Dollar): Dollar =
        Dollar(x.value + y.value)

      addAmount(Dollar(40), Dollar(100)) should be(Dollar(140))

      addAmount(50, 150) should be(Dollar(200))
    }

    it(
      """Case 13: can also convert things to make it fit into a particular API,
        |  this is called implicit conversion,
        |  in this scenario we will use a function""".stripMargin) {
      pending
    }

    it(
      """Case 14: is done automatically in Scala because what is
        |  inside of scala.Predef, for example,
        |  it explains how be can set a scala.Float,
        |  and there is java.lang.Float, java primitive float.
        |  We can investigate this by looking at
        |  the documentation.""".stripMargin) {

      val f1: scala.Float = 4001.00f
      val f2: scala.Float = 5030.40f

      val result = java.lang.Math.min(f1, f2)

      result should be(4001.00f)
    }
  }

  describe("Locating implicits recipes") {
    it(
      """Case 15: has a common way, to store that particular implicit
        |  recipe in an object that makes should make
        |  sense and then import that object""".stripMargin) {

      object MyPredef {

        import scala.language.implicitConversions

        implicit class IntWrapper(x: Int) {
          def isOdd: Boolean = x % 2 != 0

          def isEven: Boolean = !isOdd
        }

      }

      import MyPredef._
      29.isEven should be(false)
    }

    it(
      """Case 16: can also use a companion object to
        |  store any implicit recipes""".stripMargin) {

      class Artist(val firstName: String, val lastName: String)
      object Artist {

        import scala.language.implicitConversions

        implicit def tupleToArtist(t: (String, String)): Artist = new Artist(
          t._1, t._2)
      }

      def playPerformer(a: Artist) = s"Playing now: ${a.firstName} ${a.lastName}"

      playPerformer("Elvis" -> "Presley") should
        be("Playing now: Elvis Presley")
    }

    it(
      """Case 17: can also use a package object to store some
        |  of these implicits""".stripMargin) {
      def numItems(list: List[String]) = list.mkString(",")
      //numItems(3 -> "Whoa") should be("Whoa,Whoa,Whoa")
    }

    it(
      """Case 18: can use JavaConverters to convert a collection
        |  in Java to Scala and vice versa""".stripMargin) {
      import java.time._
      import scala.collection.JavaConverters._
      ZoneId.getAvailableZoneIds.asScala
        .toSet
        .filter(_.startsWith("America"))
        .map(x => x.split("/").last)
        .toList.sorted.groupBy(_.head)
    }
  }

  describe(
    """View Bounds are used to ensure that there is a " +
      |particular recipe for a certain type""".stripMargin) {

    it(
      """Case 19: Uses <% inside of a parameterized type declaration
        |  to determine if there is a conversion available
        |  then within you can treat an object as an object of
        |  that type. It is unorthodox, and has since been
        |  deprecated.""".stripMargin) {

      pending

      import scala.language.implicitConversions

      class Employee(val firstName: String, val lastName: String)

      implicit def str2Employee(s: String): Employee = ???

      def hireEmployee[A <% Employee](a: A) = {
        s"Hired an employee ${a.firstName} ${a.lastName}"
      }

      hireEmployee("Joe Employee") should be("Hired an employee Joe Employee")
    }
  }

  describe(
    """Context Bounds works so that there is a type A, and
      |it requires a B[A] somewhere within the the implicit scope,
      |for example like Ordered[T], or TypeTag[T], or Numeric[T],
      |this provides a way to check that something is something
      |can be implicitly defined but gives the end user no opportunity
      |to the ability to inject a different implementation"""
      .stripMargin) {

    it(
      """Case 20: uses the signature [T:WrappedType], which is
        | equivalent to (t:T)(implicit w:WrappedType[T])
        | let's try it with """.stripMargin) {

      trait Loggable[T] {
        def log(t: T): String
      }

      pending
    }
  }

  describe(
    """Type Constraints are used to ensure that a
      |particular method can run if a particular generic is
      |of a certain type, this is typically used for
      |one method""".stripMargin) {

    it(
      """Case 21: uses one operator, =:= which is actually
        |  the full type =:=[A,B] that
        |  will to see if something is of the same type""".stripMargin) {

      pending

      class MyPair[A, B](val a: A, val b: B) {
        def first: A = a

        def second: B = b

        def toList: List[A] = ???
      }
    }

    it(
      """Case 22: Take a look at the API, and see if it uses the operator, <:<
        |  which will test if A is a subtype of B""".stripMargin) {
      List(1 -> "One", 2 -> "Two").toMap
    }
  }

  describe("Getting around Erasure Using TypeTags") {
    it(
      """Case 23: used to use Manifest but now uses
        |  a type tag to retrieve what is erased""".stripMargin) {

      import scala.reflect.runtime.universe._

      def matchList[A](list: List[A])(implicit tt: TypeTag[A]): String = {
        tt.tpe match {
          case x if x =:= typeOf[String] => "List of String"
          case y if y =:= typeOf[Int] => "List of Int"
          case _ => "List of Something Else"
        }
      }

      matchList(List(1, 2, 3)) should be("List of Int")
    }
  }

  describe(
    """Typeclasses are a way of generating or extending
      |behavior using Java-like interfaces, but operate as outside.
      |There is another term for this,
      |and it's called ad-hoc polymorphism""".stripMargin) {

    it(
      """Case 24: can be used to determine equality, so whether
        |  than make equals inside of an class,
        |  it is now an outside concern""".stripMargin) {
      trait Eq[T] {
        def eqs(x: T, y: T): Boolean
      }
      object Eq {
        def isEquals[T](a: T, b: T)(implicit z: Eq[T]) = {
          z.eqs(a, b)
        }
      }

      implicit val eqCoffee = new Eq[CoffeeDrink] {
        override def eqs(x: CoffeeDrink, y: CoffeeDrink): Boolean = {
          x.name == y.name && x.size == y.size && x.shots == y.shots
        }
      }

      case class CoffeeDrink(name: String, size: String, shots: Int)

      val latte1 = CoffeeDrink("Latte", "Grande", 3)
      val latte2 = CoffeeDrink("Latte", "Grande", 3)

      Eq.isEquals(latte1, latte2) should be(true)
    }

    it(
      """Case 25: can be used for ordering, in this case how`
        |  do we sort an Employee""") {
      case class Employee(firstName: String, lastName: String)

      val list = List(Employee("Taylor", "Swift"),
        Employee("George", "Harrison"),
        Employee("Kanye", "West"),
        Employee("Justin", "Bieber"),
        Employee("John", "Lennon"),
        Employee("Dave", "Matthews"),
        Employee("Freddie", "Mercury"))

      object MyPredef {
        implicit val orderEmployeeByFirstName: Ordering[Employee] = new Ordering[Employee] {
          override def compare(x: Employee, y: Employee): Int = {
            x.firstName.compareTo(y.firstName)
          }
        }

        implicit val orderEmployeeByLastName: Ordering[Employee] = new Ordering[Employee] {
          override def compare(x: Employee, y: Employee): Int = {
            x.lastName.compareTo(y.lastName)
          }
        }
      }

      import MyPredef.orderEmployeeByFirstName
      list.sorted.head should be (Employee("Dave", "Matthews"))
    }
  }
}