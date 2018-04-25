package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

import scala.collection.immutable.LinearSeq

class CollectionsSpec extends FunSuite with Matchers {
  test("""Case 1: We start our journey into collections with a List.
      |  An ordered collection. Per documentation this is
      |  suited for lifo stack like patterns""".stripMargin) {
    pending
  }

  test("""Case 2: To get the the 2nd element of the list we use apply.""") {
    pending
  }

  test("""Case 3: To create a collection, there are varying signatures in
      |  the companion object of many of the collections that
      |  we will be talking about including a fill from List.""".stripMargin) {
    pending
  }

  test("""Case 4: For most purposes a Vector is preferable, fast random access,
      |  append, prepend, and updates. Lucky for us the methods
      |  are very much the same""".stripMargin) {
    pending
  }

  test("""Case 5: A range can be created with either to, until,
      |  or explicitly with Range.apply or Range.inclusive """.stripMargin) {
    val range1 = 1 to 5
    range1 should contain inOrder(1, 2, 3, 4, 5)
    val range2 = 1 until 5
    range1 should contain inOrder(1, 2, 3, 4)
    val range3 = Range.apply(1, 5)
    range3 should contain inOrder(1, 2, 3, 4)
    val range4 = Range.inclusive(1, 5)
    range4 should contain inOrder(1, 2, 3, 4, 5)
    val range5 = Range.apply(1, 10, 2)
    range5 should contain inOrder(1, 3, 5, 7, 9)
  }

  test("""Case 6: If you have tried Scala, you have definitely encountered a Seq.
      |  A Seq is a base trait for sequences which are iterables with order.
      |  There are two subtraits of a Seq:
      |     - IndexedSeq : fast random access, fast length
      |     - LinearSeq : fast head, fast tail, fast empty operations
      |  The current default Seq when instantiated is a List
      |  The current default IndexedSeq is a Vector
      |  The current default LinearSeq is a List""".stripMargin) {

    Seq(30, 50, 100) shouldBe a[List[_]]
    IndexedSeq(30, 50, 100) shouldBe a[Vector[_]]
    LinearSeq(30, 50, 100) shouldBe a[List[_]]
  }

  test("""Case 7: A stream can be a finite or an infinite collection.
      |  The construction can
      |  be done using recursion. Here we will
      |  make some continous evens""".stripMargin) {

      def continuousEvens(n:Int):Stream[Int] = {
        Stream.cons(n, continuousEvens(n + 2))
      }

      continuousEvens(4).take(5).mkString(",") should be ("4,6,8,10,12")
  }

  test(
    """Case 8: Another way we can write the above Stream  is using
      |   is using the
      |  #:: operator""".stripMargin) {

    def continuousEvens(n:Int):Stream[Int] = n #:: continuousEvens(n + 2)

    continuousEvens(4).take(5).mkString(",") should be ("4,6,8,10,12")
  }


  test(
    """Case 8.1: Operation Stream factorials""".stripMargin) {

    def factorial(n:Int) = {
      def recurseFact(currentIndex: Int, acc: Int): Stream[Int] =
        acc #:: recurseFact(currentIndex + 1, currentIndex * acc)

      recurseFact(1, 1).drop(1)(n -1)
    }

    factorial(5) should be (120)
  }

  test("""Case 9: An array is a java based array with a Scala wrapper
      |  around it to perform the functionality.
      |  Use getClass.getSimpleName to verify, also use reverse to try a
      |  method that is unavailable in Java""".stripMargin) {

    val arrays = Array(1,2,3,4)
    arrays.apply(2)
    arrays.head

  }

  test("""Case 10: Whereas a List is a LIFO, a Queue is a FIFO just like a
      |  line at a grocery store. The queue internally has two sections,
      |  an in collection and an out collection. When out runs out, out
      |  becomes an in.reverse and out replaced with Nil. This is called a
      |  pivot. Use enqueue and dequeue.""".stripMargin) {
    pending
  }

  test("""Case 11: A Set is a collection with unique items with no order. They can
      |  either be as HashSet which is stored is special type of collection
      |  called trie. This will have fast lookup, add, and remove.
      |  Careful with apply""".stripMargin) {
    pending
  }

  test("""Case 12: A Set is can also be a TreeSet.
      |  This will have logarithmic performance. It will win
      |  generally in performance when you need to
      |  find the smallest element
      |  of the set. As far as API is concerned
      |  you should expect no difference.""".stripMargin) {
     pending
  }

  test("""Case 13: A Map is a collection of pairs, also known as Tuple2""".stripMargin) {
    val map3 = Map("Seattle" -> "Sounders", "LA" -> "Galaxy",
                   "LA" -> "FC", "NY" -> "Red Bulls")

    map3.apply("Seattle") should be ("Sounders")

    //Why apply is not preferrable
    a [NoSuchElementException] should be thrownBy {
      map3.apply("Omaha")
    }

    val result = map3.get("Omaha") match {
                   case Some(s) => s"The team is $s"
                   case None => "Huh?"
                 }
  }

  test("""Case 14: There are also mutable collections. Though much of the Scala
      |  programmers like to generally use immutable collections at times
      |  you may want to use mutable collections for efficiency though it
      |  is generally good form to lock collections immutably when returning
      |  from a method. Here is a ListBuffer which allows you to create a
      |  mutable List changing what you need. Note the API differences
      |  with immutable. Here is an ArrayBuffer""".stripMargin) {
    pending
  }
}
