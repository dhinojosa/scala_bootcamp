package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

import scala.collection.immutable

class FunctionsSpec extends FunSuite with Matchers {


  test(
    """Case 1: A function really is an anonymous
      |  instantiation of a trait.""".stripMargin) {

    val f = new Function1[String, Int] {
      override def apply(v1: String): Int = v1.length
    }

    f.apply("Robert") should be(6)
    f("Robert") should be(6)
  }

  test("Case 2: The above can be whittled down to x => f(x):") {
    //All these are the same thing
    val f = (s: String) => s.length
    f.apply("Robert") should be(6)
    f("Robert") should be(6)
  }

  test("Case 2.1: Function2 the hard way") {
    val f2 = new Function2[String, Int, String] {
      override def apply(v1: String,
                         v2: Int): String = "String:" + v1 + v2
    }

    f2("Foo", 12) should be("String:Foo12")
  }

  test("Case 2.2: Function2 the nice way") {
    val f2 = (v1: String, v2: Int) => "String:" + v1 + v2
    f2("Foo", 12) should be("String:Foo12")
  }

  test("Case 2.3: Function2 multiline") {
    val f2 = (v1: String, v2: Int) => {
      val result = v1 + v2
      "String:" + result
    }
    f2("Foo", 12) should be("String:Foo12")
  }

  test(
    """Case 3: If you declare the left hand side you
      |  don't need to declare the right:""".stripMargin) {

    val f1: Function1[String, Int] = s => s.length
  }

  test(
    """Case 4: Also, since the left hand side,
      |  has all the type information,
      |  on the right hand side you can
      |  trim the right hand side
      |  with syntactical tricks
      |  like use the placeholder, _""".stripMargin) {

    val f1: Function1[String, Int] = _.length
    val f2: String => Int = _.length
  }

  test("""Case 4.1 Function2 with a place holder""") {
    val f2_1: Function2[Int, Int, Int] = (x, y) => x + y
    f2_1.apply(40, 50) should be(90)

    val f2_2: (Int, Int) => Int = (x, y) => x + y
    f2_2(40, 50) should be(90)

    val f2_3: (Int, Int) => Int = _ + _
    f2_3(40, 50) should be(90)
  }

  test(
    """Case 5: If the type system has enough information either because
      |  of the left hand side of an assignment, or a parameter in method, or
      |  the way generic types are situated within a class,
      |  you can get rid of some additional code. In the following example, you
      |  can drop the underline and leave 5+, but will come with some warnings
      |  that you can turn off with """.stripMargin) {

    import scala.language.postfixOps

    val add3_1: Int => Int = x => 3 + x
    add3_1(5) should be(8)

    val add3_2: Int => Int = 3 + _
    add3_2(4) should be(7)

    val add3_3: Int => Int = 3 +
    //add3_3(10) should be (13) //Bug
  }

  test(
    """Case 6: A closure is a function that will "wrap" or "close"
      |  around an outside value.""".stripMargin) {
    def createFunction(i: Int): Int => Int = {
      (x: Int) => x + i
    }

    createFunction(4)(5) should be(9)
  }

  test(
    """Case 7: We will create a method called is,
      |  that will take a number,
      |  and a function that will be invoked
      |  and be given a boolean.""".stripMargin) {

    object MyFunctions {
      def lessThan(x: Int): Int => Boolean = (z: Int) => z < x
    }

    val isFreezingCelcius = MyFunctions.lessThan(0)
    val isFreezingFahrenheit = MyFunctions.lessThan(32)
    isFreezingFahrenheit.apply(25) should be(true)
    isFreezingCelcius.apply(25) should be(false)
  }

  test(
    """Case 8: Closure have some particular implications.
      |  One such implication
      |  is called currying. Currying will break a function of one or
      |  more arguments into parts so that they can be applied
      |  partially""".stripMargin) {

    def foo(f: Int => Int) = f(40)

    val f: (Int, Int, Int) => Int = (x: Int, y: Int, z: Int) => x + y + z
    val fc: Int => Int => Int => Int = f.curried
    val f1: Int => Int => Int = fc(3)
    val f2: Int => Int = f1(4)
    foo(f2) should be(47)

    val manuallyCurried = (x: Int) => (y: Int) => (z: Int) => x + y + z
    manuallyCurried(3)(4)(10) should be(17)
  }

  test(
    """Case 9: Compose is the equivalent of f(g(x)).  But what this is takes
      |  some perspective.  g(x) should be evaluated first and then the
      |  result of that (let's call it a) should be applied to f,
      |  therefore f(a) to get result b.  But these functions can be
      |  applied together to form one cohesive function""".stripMargin) {

    val tupleFirst: ((String, Int)) => String = (t: (String, Int)) => t._1
    val getFirstThreeLetters = (s: String) => s.substring(0, 3)

    val newFunction: ((String, Int)) => String = getFirstThreeLetters
      .compose(tupleFirst)
    newFunction("Fellow" -> 100) should be("Fel")
  }

  test(
    """Case 10: andThen is g(f(x)).  f(x) is applied first and
      |  then g is then applied. In the following example we
      |  recreate the compose but using andThen""".stripMargin) {

    val tupleFirst = (t: (String, Int)) => t._1
    val getFirstThreeLetters = (s: String) => s.substring(0, 3)

    val newFunction: ((String, Int)) => String =
      tupleFirst.andThen(getFirstThreeLetters)
    newFunction.apply("Fellow", 100) should be("Fel")
  }


  test(
    """Case 11: Map will apply the given function on all elements of a
      |  Traversable and return a new collection
      |  of the result.""".stripMargin) {
    val vector = Vector(1, 3, 4, 6)
    val result = vector.map(x => x * 4)
    result should be(List(4, 12, 16, 24)) //4
  }

  test("""Case 11.1 Caesar Shift: using closure, map""") {
    def caesarShift(n: Int): String => String = _.map(c => (c + n).toChar)

    val caesarShiftOf1 = caesarShift(1)
    caesarShiftOf1("Foo") should be("Gpp")
  }

  test(
    """Case 12: Map can be applied to a Stream, it is just another collection""")
  {
    Stream
      .from(1, 2)
      .map(x => x * 5)
      .take(4)
      .toVector should contain inOrder(5, 15, 25, 35)
  }

  test(
    """Case 13: Map in an Option, although an Option is not a collection,
      |  it is has some of the same attributes like map that will operate
      |  with its internals. To apply a map to a None will just render
      |  a None""".stripMargin) {
    Some(10).map(x => x + 40) should be(Some(50))
    None.asInstanceOf[Option[Int]].map(x => x + 40) should be(None)
  }

  test(
    """Case 14: We can also use a map on a scala Map, you have two choices,
      |  either map which takes the Tuples, or mapValues which just
      |  maps over the values.""".stripMargin) {
    val mapStructure = Map(1 -> "One", 2 -> "Two", 3 -> "Three")
    val result = mapStructure.map(t => t._1 * 100 -> (t._2 + " Hundred"))
    result should contain(100 -> "One Hundred")
  }

  test(
    """Case 15: foldLeft will take two parameters group, the first
      |  will contain a seed and then a function that will
      |  aggregate the collection into one.""".stripMargin) {

    val result = List(1, 2, 3, 4, 5).foldLeft(0)((total: Int, next: Int) => {
      total + next
    })

    result should be(15)

    val result2 = List(1, 2, 3, 4, 5).foldLeft(0)(_ + _)

    result2 should be(15)

    def factorial(n: Int) = Stream.from(1).take(n).foldLeft(1)(_ * _)

    factorial(5) should be(120)
  }

  test("""Case 15.1: foldRight""") {
    val result = List(1, 2, 3, 4, 5).foldRight(0)((next: Int, total: Int) => {
      total + next
    })

    result should be(15)
  }

  test(
    """Case 16: reduce will collapse all elements of a collection using a function.
      |  It will start the first element as the 'seed' or 'accumulation"""
      .stripMargin) {

    val result = Vector(4, 5, 6).reduce(_ + _)
    result should be(15)

    val result2 = Vector(4, 5, 6).reduce((total, next) => total + next)
  }

  test("""Case 16: reduceRight""") {
    val result = Vector(4, 5, 6).reduceRight((next: Int, total: Int) => {
      total + next
    })

    result should be(15)
  }

  test(
    """Case 17: flatMap will not only apply the given function on all
      |  elements of a Traversable,
      |  but all elements within the elements
      |  and flatten the results""".stripMargin) {

    List(1, 2, 3).flatMap(x => List(-x, x, x + 1))

    (1 to 10).flatMap(x => ('a' to 'c').map(y => (x, y)))
  }

  test(
    """Case 18: A flatMap will take a function that returns
      |  a TraversableOnce like List, Set, Map, and will
      |  combine or flatten the results.""".stripMargin) {
    val list: List[List[List[Int]]] =
      List(List(List(1, 2, 3), List(4, 5, 6)),
        List(List(7, 8, 9), List(10, 11, 12)))


    val result1 = list.flatMap(identity) //identity = x => x

  }

  test(
    """Case 19: Find the average salary of employees of two departments
      |  using flatMap.""".stripMargin) {

    case class Department(name: String, employees: Vector[Employee])
    case class Employee(firstName: String,
                        lastName: String,
                        salary: Int)

    val automotive: Department = Department("Automotive",
      Vector(Employee("Diane", "Lancaster", 65000),
        Employee("Adam", "Viscount", 55000),
        Employee("Sandeep", "Agarwal", 56000)))

    val kitchen: Department = Department("Kitchen",
      Vector(Employee("Ralph", "Quintana", 44000),
        Employee("Rolland", "Chabot", 44300)))

    val list: List[Department] = List(automotive, kitchen)

    //average for all employees, not per department, .size for count
    val employeeList: Seq[Employee] = list.flatMap(dept => dept.employees)

    val salaries = employeeList.map(e => e.salary)

    val average = salaries.sum / salaries.size

    average should be(52860)

    val tt: (Int, Int) = list
      .map(d => d.employees.map(e => e.salary).sum -> d.employees.size)
      .reduce { (total, next) => (total._1 + next._1) -> (total._2 + next._2) }

    //Challenge Acceptance
    val result2 = tt._1 / tt._2

    result2 should be(52860)

  }

  test(
    """Case 20: Using Map with a flatMap. In this lab, use flatMap or map to produce
      |  the following:
      |
      |    Map(1 -> One, 2 -> Two, 3 -> Three,
      |        300 -> Three Hundred, 200 -> Two Hundred,
      |        100 -> One Hundred)
      |
      |""".stripMargin) {

    val origMap = Map(1 -> "One",
      2 -> "Two",
      3 -> "Three")

    val result: Map[Int, String] = origMap.flatMap(t =>
      Map(t._1 -> t._2, (t._1 * 100) -> (t._2 + " Hundred")))

    result should be(Map(1 -> "One", 2 -> "Two", 3 -> "Three",
      300 -> "Three Hundred", 200 -> "Two Hundred",
      100 -> "One Hundred"))
  }

  test("""Case 21: Option and flatMap""") {
    pending

    info("map will render a Some(Some(...))")
    Some(4).map(x => Some(x + 19)) should be(Some(Some(23)))

    info("but...a flapMap will render a Some(...)")
    Some(4).flatMap(x => Some(x + 19)) should be(Some(23))

    info("a flatMap with a None anywhere will render a none")
    None.asInstanceOf[Option[Int]].flatMap(x => Some(x + 19)) should be(None)

    info("trying with a Some with flatMap that resolves to None")
  }

  test(
    """Case 22: flatMap of Options will filter out all Nones and Keep the Somes""")
  {
    val list = List(1, 2, 3, 4, 5)
    val result = list.flatMap(it => if (it % 2 == 0) Some(it) else None)
    result should be(List(2, 4))
  }

  test(
    """Case 23: foreach will apply a function to all elements of a Traversable,
      |  but unlike the map function, it will not return anything
      |  since the return type is Unit, which
      |  is like a void return type in Java, C++""".stripMargin) {


    List(1, 2, 3).foreach(println)
  }

  test(
    """Case 24: groupBy will categorize a collection by a function, and return a
      |  map where the keys were derived by that function""".stripMargin) {

    val lyrics = List(
      "I see trees of green",
      "Red roses too",
      "I see them bloom",
      "for me and you",
      "And I think to myself",
      "What a wonderful world")

    val map: Map[Char, immutable.Seq[String]] =
      lyrics
        .flatMap(_.split(" "))
        .map(_.toLowerCase)
        .groupBy(w => w.head)
  }

  test(
    """Case 24: groupBy will categorize a collection by a function,
      |  and return a
      |  map where the keys were derived by that function, in this case,
      |  we will derive a word count""".stripMargin) {

    val lyrics = List(
      "I see trees of green",
      "Red roses too",
      "I see them bloom",
      "for me and you",
      "And I think to myself",
      "What a wonderful world")

    val wordCount = lyrics
      .flatMap(_.split(" "))
      .map(_.toLowerCase)
      .groupBy(identity)
      .mapValues(_.size)
      .toList
      .sortBy { case (_, n) => n } //Partial Function
    //.sortBy(t => t._2)       //Non Partial Function
    wordCount

  }

  test(
    """Case 25: mkString will create a string from a
      |  collections elements, and offers
      |  multiple ways to do so""".stripMargin) {
    List(1, 2, 3).mkString("{", ",", "}")
  }

  test(
    """Case 26: collect will apply a partial function to all elements
      |  and will return a different collection.""".stripMargin) {
    val doubleEvens: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
      override def isDefinedAt(x: Int): Boolean = x % 2 == 0

      override def apply(v1: Int): Int = v1 * 2
    }

    val tripleOdds: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
      override def isDefinedAt(x: Int): Boolean = x % 2 != 0

      override def apply(v1: Int): Int = v1 * 3
    }

    val doubleEvens2: PartialFunction[Int, Int] = {
      case x: Int if x % 2 == 0 => x * 2
    }

    val tripleOdds2: PartialFunction[Int, Int] = {
      case x: Int if x % 2 != 0 => x * 3
    }

    val doubleEvensOrTripleOdds = doubleEvens2 orElse tripleOdds2

    doubleEvensOrTripleOdds.apply(5) should be(15)
  }

  test(
    """Case 27: scan is like a reduce but maintains a running total
      |  with each iteration""".stripMargin) {
    (1 to 5).scan(1)(_ * _).apply(5) should be(120)
  }

  test(
    """Case 28: zip will interweave two collections together leaving a tuple""")
  {
    (1 to 1000).zip(List('a', 'b', 'c')) should
      be(Seq((1, 'a'), (2, 'b'), (3, 'c')))
  }

  test(
    """Case 29: view will not immediately evaluate a chain until a terminal
      |  operation is called, like reduce, count, or force.  Here lets take a
      |  range of 1 to 10000000 and lazy map and multiply
      |  by 4000""".stripMargin) {
    val result = (1 to 10000000).view.map(x => x * 4000).take(4).force.toList
    result should contain inOrder(4000, 8000, 12000, 16000)
  }

  test(
    """Case 30: sorted will sort the collection based on an implicit ordering
      |  and return that ordered collection""".stripMargin) {
    val list = List("bassoon", "bass", "violin", "guitar", "cello")
    list.sorted should contain inOrder("bass", "bassoon",
      "cello", "guitar", "violin")
  }

  test(
    """Case 31: sortBy will also sort the collection based on an
      |  implicit rule, but will apply a function first, let's find a way
      |  to sort by last name and take the first two last names""".stripMargin)
  {

    val names = List("Ella Fitzgerald",
      "Louis Armstrong",
      "Albert Einstein",
      "Tim Berners Lee",
      "Nikola Tesla",
      "Bob Marley")

    val result = names
      .map(_.split(" "))
      .sortBy(_.last)
      .map(_.mkString(" "))

    result should contain inOrder("Louis Armstrong",
      "Albert Einstein",
      "Ella Fitzgerald",
      "Tim Berners Lee",
      "Bob Marley",
      "Nikola Tesla")

  }

  test(
    """Case 32: Partial Applications with a multi-parameter list
      |  can be knocked out to provide only some of the entries, entries
      |  that you can fill in later. This is how we can convert
      |  a method to a function""".stripMargin) {

    def multiParameters(w: Int)(x: Int)(y: String, z: String)
    = y + (w + x) + z

    val function = multiParameters(10)(20) _
    function("{", "}") should be("{30}")
  }

  test(
    """Case 33: In multi-parameter lists you can use a function. Typically
      |  the function is in the last parameter group, but it's your code,
      |  you can put it wherever you please""".stripMargin) {
    def multiParametersWithAFunction(w: Int)(x: Int)(f: Int => String) = f(
      w * x)

    val result = multiParametersWithAFunction(50)(50) { i =>
      val result = i + 10
      s"The value is $result"
    }
    result should be("The value is 2510")
  }

  test(
    """Case 34: We will use a by name parameter to
      |  create a method that will take a block and
      |  will return a tuple of the time that it took
      |  to execute the block and the block's result""".stripMargin) {

    def timeIt[A](process: => A): (A, Long) = {
      val startTime = System.currentTimeMillis()
      val result = process
      val endTime = System.currentTimeMillis()
      (result, endTime - startTime)
    }

    val tuple = timeIt {
      val a = 10
      val b = 12
      Thread.sleep(10)
      a + b
    }

    tuple._1 should be(22)
  }

  test("""Case 34.1 For Comprehensions""") {
    val nested = List(1, 2, 3)
      .flatMap(x => List('a', 'b', 'c').map(y => (x, y)))

    val nested2 = for (x <- List(1, 2, 3);
                       y <- List('a', 'b', 'c')) yield (x, y)

    nested should be(nested2)
  }


  test("""Case 34.1 For Comprehensions with Options""") {
    val nested: Option[Int] = for {x <- Some(10)
                                   y <- Some(40)} yield x * y
    nested should be(Some(400))

    val nested2 = Some(40).flatMap(x => Some(10).map(y => x * y))

    nested should be(nested2)
  }


  test("""Case 34.2 For Comprehension with my custom Box""") {
      val nested = Box(40).flatMap(x => Box(10).map(y => x * y))
      nested should be (Box(400))


      val nested2 = for (x <- Box(40);
                         y <- Box(10)) yield (x * y)

      nested2 should be (Box(400))
   }

  test("""Case 35: Look up in the API a function in a collection that interests you,
      |  figure it out with one or more tests, pair with someone if you
      |  would like""".stripMargin) {
    pending
  }
}
