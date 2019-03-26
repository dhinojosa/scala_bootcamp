package com.xyzcorp.student

import com.xyzcorp._
import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ArrayBuffer

class TraitsSpec extends FunSuite with Matchers {
  test("Case 1: A trait is analogous to an interface in Java") {
    trait Vehicle {
      def increaseSpeed(ms: Int): Vehicle

      def decreaseSpeed(ms: Int): Vehicle

      def currentSpeedMetersPerHour: Int
    }

    class Bicycle(val currentSpeedMetersPerHour: Int) extends Vehicle {
      override def increaseSpeed(ms: Int): Vehicle =
        new Bicycle(currentSpeedMetersPerHour + ms)

      override def decreaseSpeed(ms: Int): Vehicle =
        new Bicycle(currentSpeedMetersPerHour - ms)
    }

    new Bicycle(1)
      .increaseSpeed(3)
      .decreaseSpeed(1)
      .currentSpeedMetersPerHour should be(3)
  }

  test(
    """Case 2: Just like Java 8 interfaces, you can have concrete
      |  methods (known as default methods in Java)""".stripMargin) {
    trait Vehicle {
      def increaseSpeed(ms: Int): Vehicle

      def decreaseSpeed(ms: Int): Vehicle

      def currentSpeedMetersPerHour: Int

      final def currentSpeedMilesPerHour: Double =
        currentSpeedMetersPerHour * 0.000621371
    }

    class Bicycle(val currentSpeedMetersPerHour: Int) extends Vehicle {
      override def increaseSpeed(mh: Int): Vehicle =
        new Bicycle(currentSpeedMetersPerHour + mh)

      override def decreaseSpeed(mh: Int): Vehicle =
        new Bicycle(currentSpeedMetersPerHour - mh)
    }

    new Bicycle(4).currentSpeedMilesPerHour should be(0.002 +- .005)
  }


  test(
    """Case 3: Traits are specifically called that just for mixing in functionality.
      |  Let's create a trait called whoAmI_? that will return a string
      |  of the class simple name and apply to the stamp class""".stripMargin) {

    val stamp = Stamp("Susan B. Anthony", 2001)
    stamp.whoAmI_?() should be("Stamp")
  }

  test(
    """Case 4: You can extends from a trait that was
      |  not built in to begin with""".stripMargin) {

    trait Log {
      private val _log: ArrayBuffer[String] = ArrayBuffer[String]()

      def log(s: String): Unit = _log += s

      def entries: ArrayBuffer[String] = _log
    }

    val o = new Object with Log
    o.log("Sent one statement")
    o.log("Sent two statements")

    o.entries should contain inOrder
      ("Sent one statement", "Sent two statements")
  }

  test(
    """Case 5: The confusing thing about traits is that if you
      |  extends from a class then extend with extends, and then use with
      |  to list all the traits you wish to inherit, if you do extends from
      |  a superclass then you will extends with one trait and with the
      |  remaining traits""".stripMargin) {

    trait MyMixin {
      def bar(z: Int): Int = z + 3
    }
    class MySuperclass(val x: Int)
    class MySubclass(x: Int, val y: String) extends MySuperclass(x) with MyMixin

    val mySubclass = new MySubclass(3, "Foo")
    mySubclass.bar(10) should be(13)
  }

  test(
    """Lab: Given our bicycle example, we need to mixin
      |  a fun factor! Create a trait called FunFactor
      |  inside of this test!
      |  FunFactor should have one abstract method called
      |  funFactor that returns an Int.
      |  Either mix in the trait into the Bicycle class
      |  or mix it onto a already
      |  created Bicycle instance, be sure to
      |  have an implementation of the funFactor method""".stripMargin) {

    pending

    trait Vehicle {
      def increaseSpeed(ms: Int): Vehicle

      def decreaseSpeed(ms: Int): Vehicle

      def currentSpeedMetersPerSecond: Int

      final def currentSpeedMilesPerHour: Double = currentSpeedMetersPerSecond *
        0.000621371
    }

    class Bicycle(val currentSpeedMetersPerSecond: Int) extends Vehicle {
      override def increaseSpeed(ms: Int): Vehicle =
        new Bicycle(currentSpeedMetersPerSecond + ms)

      override def decreaseSpeed(ms: Int): Vehicle =
        new Bicycle(currentSpeedMetersPerSecond - ms)
    }
  }

  test(
    """Case 6: A global counter, can also be created with a
      |  trait and mix it in where needed""".stripMargin) {

    trait Counter {
      var counter = 0

      def incrementCounter = counter = counter + 1
    }

    class Country(name: String) {
      Country.incrementCounter

      def currentCountOfAll: Int = Country.counter
    }

    object Country extends Counter

    val country1 = new Country("Germany")
    val country2 = new Country("Zaire")
    val country3 = new Country("China")
    val country4 = new Country("India")

    country4.currentCountOfAll should be(4)
  }

  test(
    """Case 7: Avoiding the diamond of death.
      |  https://en.wikipedia.org/wiki/Multiple_inheritance#The_diamond_problem.
      |  In Scala, since traits can inherit as a diamond shape,
      |  there has to be a strategy. Instantiation goes from left to right
      |  (used to be right to left),
      |  and instantiation is marked for reuse.""".stripMargin) {

    var list = List[String]()

    trait T1 {
      list = list :+ "Instantiated T1"
    }

    trait T2 extends T1 {
      list = list :+ "Instantiated T2"
    }

    trait T3 extends T1 {
      list = list :+ "Instantiated T3"
    }

    trait T4 extends T1 {
      list = list :+ "Instantiated T4"
    }

    class C1 extends T2 with T3 with T4 {
      list = list :+ "Instantiated C1"
    }

    list = list :+ "Creating C1"
    new C1
    list = list :+ "Created C1"

    list.mkString(", ") should be(
      "Creating C1, Instantiated T1, Instantiated T2, Instantiated T3, Instantiated T4, Instantiated C1, Created C1")
  }

  test(
    """Case 8: Stackable traits are traits stacked one atop another,
      |  make sure that all overrides
      |  are labelled, abstract override.  The order of the mixins are important.
      |  Traits on the right take effect first""".stripMargin) {

    abstract class IntQueue {
      def get(): Int

      def put(x: Int)
    }

    import scala.collection.mutable.ArrayBuffer

    class BasicIntQueue extends IntQueue {
      private val buf = new ArrayBuffer[Int]

      def get() = buf.remove(0)

      def put(x: Int) {
        buf += x
      }
    }

    trait Doubling extends IntQueue {
      abstract override def put(x: Int) {
        super.put(2 * x)
      } //abstract override is necessary to stack traits
    }

    trait Incrementing extends IntQueue {
      abstract override def put(x: Int) {
        super.put(x + 1)
      }
    }

    trait Filtering extends IntQueue {
      abstract override def put(x: Int) {
        if (x >= 0) super.put(x)
      }
    }

    val myQueue = new BasicIntQueue with Doubling with Incrementing

    myQueue.put(4)
    myQueue.get() should be(10)
  }

  test(
    """Case 9: Self types declares that a trait must be mixed into another trait.
      |  The relationship is the following:
      |
      |     * B extends A, then B is an A.
      |     * When you use self-types, B requires an A
      |
      |  This is used for a pattern called the cake pattern, but is also a
      |  way for the class to define the non-inheritance behaviors"""
      .stripMargin) {
    trait Moveable {
      def increaseSpeed(ms: Int): Moveable

      def decreaseSpeed(ms: Int): Moveable
    }

    trait Vehicle {
      self: Moveable =>
      def make: String

      def model: String
    }

    class Car(val make: String, val model: String, val currentSpeed: Int)
      extends Vehicle with Moveable {
      override def increaseSpeed(ms: Int) = new Car(make, model,
        currentSpeed + ms)

      override def decreaseSpeed(ms: Int) = new Car(make, model,
        currentSpeed - ms)
    }

    val ford = new Car("Ford", "Fiesta", 110).decreaseSpeed(20)
    ford.make should be("Ford")
    ford.currentSpeed should be(90)
  }

  test(
    """Case 10: A sealed trait is a trait that will have children,
      |  but it will define all it's children and not one else will have the
      |  ability to extend the number of children any further. All children
      |  must be produced within the same file. This will also create what
      |  is called a union type if you are familiar with Haskell, Elm, F#,
      |  and other functional languages. Let's create a node""".stripMargin) {
    val a: Node[Int] = Node(Leaf(1), Node(Leaf(2), Leaf(4)))
    a.left.asInstanceOf[Leaf[_]].value should be(1)
  }

  test(
    """Case 11: You can also have sealed abstract classes, which will operate under
      |  the same rules, the children must all be inside the same file,
      |  and the children should be final. Why would you choose
      |  one over the other? You can multiple inherit a trait and mixin traits.
      |  Abstract classes offer stronger "is-a" relationships
      |  A popular sealed abstract class is Option[+T], Some[T], None, let's
      |  take a look at the API for Option and try it out""".stripMargin) {
    val middleName: Option[String] = Some("Diego")
    middleName.getOrElse("No Middle Name") should be("Diego")

    val danMiddleName: Option[String] = None
    danMiddleName.getOrElse("No Middle Name") should be("No Middle Name")
  }

  test(
    """Case 12: A popular sealed abstract class is Also List[A], ::,
      |  and Nil let's take a look at the API.""".stripMargin) {
    val myList = new ::(1, Nil)
    myList should be(List(1))
  }

  test(
    """Case 13: Sealed traits are also a good idea for pattern matching
      |  exhaustiveness. The compiler will be able to recognize the subclasses
      |  of all sealed traits. Let's perform pattern matching on the Node,
      |  Leaf and Empty""".stripMargin) {
    val a: Tree[Int] = Node(Leaf(1), Node(Leaf(2), Leaf(4)))

    val result = a match {
      case Empty => "Empty"
      case Leaf(x) => s"Leaf of value $x"
      case Node(Leaf(x), Leaf(y)) => s"Two leaves: $x, $y"
      case Node(l, r) => s"Node of $l and $r"
    }

    result should be("Node of Leaf(1) and Node(Leaf(2),Leaf(4))")
  }

  test("""Lab: Research Try or Either,
         |  research either of them in the Scala API,
         |  and put a sample in this test to verify your conclusions.  Notice
         |  that they may either be a sealed abstract class or a sealed trait.
         |  Given your knowledge of this you should be confident in how they work,
         |  even if you haven't used them before.""".stripMargin) {

    pending
    val numerator = 20
    val denominator = 10
  }
}
