package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ArrayBuffer

class TraitsSpec extends FunSuite with Matchers{
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


  test("""Case 3: Traits are specifically called that just for mixing in functionality.
      |  Let's create a trait called whoAmI_? that will return a string
      |  of the class simple name and apply to the stamp class""".stripMargin) {

    //stamp.whoAmI_?() should be("Stamp")
  }

  test("Case 4: You can extends from a trait that was not built in to begin with") {
    trait Log {
      private val _log: ArrayBuffer[String] = ArrayBuffer[String]()
      def log(s:String):Unit = _log += s
      def entries: ArrayBuffer[String] = _log
    }

    val o = new Object with Log
    o.log("Sent one statement")
    o.log("Sent two statements")

    o.entries should contain inOrder("Sent one statement", "Sent two statements")
  }

  test("""Case 5: extends vs. with, if the class extends from an abstract
      |  class then use with, otherwise extend from trait.
      |  Try the Introspection with Baseball Card""".stripMargin) {
    pending
  }

  test("Case 6: Avoiding the diamond of death") {
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

    class C1 extends T2 with T3 {
      list = list :+ "Instantiated C1"
    }

    list = list :+ "Creating C1"
    new C1
    list = list :+ "Created C1"

    //list should contain inOrder(???)
    pending
  }

  test("Case 7: Stackable traits") {

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
    //myQueue.get should be(???)
    pending
  }

  test("Case 8: Self types") {
    trait Moveable {
      def increaseSpeed(ms: Int): Moveable
      def decreaseSpeed(ms: Int): Moveable
    }

    trait Vehicle {self: Moveable =>
      def make: String
      def model: String
    }

    class Car(val make: String, val model: String, val currentSpeed: Int)
      extends Vehicle with Moveable {
      override def increaseSpeed(ms: Int): Moveable = new Car(make, model,
        currentSpeed + ms)

      override def decreaseSpeed(ms: Int): Moveable = new Car(make, model,
        currentSpeed + ms)
    }

    val car = new Car("Peugeot", "308", 0)
    car.currentSpeed should be(0)
    car.make should be ("Peugeot")
  }

  test("""Case 9: A sealed trait is a trait that will have children,
         |  but it will define all it's children and not one else will have the
         |  ability to extend the number of children any further. All children
         |  must be produced within the same file. This will also create what
         |  is called a union type if you are familiar with Haskell, Elm, F#,
         |  and other functional languages. Let's create a node""".stripMargin) {
    pending
  }

  test("""Case 10: You can also have sealed abstract classes, which will operate under
      |  the same rules, the children must all be inside the same file,
      |  and the children should be final. Why would you choose
      |  one over the other? You can multiple inherit a trait and mixin traits.
      |  Abstract classes offer stronger "is-a" relationships
      |  A popular sealed abstract class is Option[+T], Some[T], None, let's
      |  take a look at the API for Option and try it out""".stripMargin) {
     pending
  }

  test("""Case 11: A popular sealed abstract class is Also List[A], ::,
         |  and Nil let's take a look at the API.""".stripMargin) {
    pending
  }

  test("""Case 12: Sealed traits are also a good idea for pattern matching
         |  exhaustiveness. The compiler will be able to recognize the subclasses
         |  of all sealed traits. Let's perform pattern matching on the Node,
         |  Leaf and Empty""".stripMargin) {
    //val a:Tree[Int] = Node(Leaf(5), Leaf(10))
    pending
  }
}
