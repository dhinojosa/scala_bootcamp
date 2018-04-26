package com.xyzcorp.instructor

import org.scalatest.{FunSpec, Matchers}


import scala.util.matching.Regex

class PatternMatchingSpec extends FunSpec with Matchers {

  describe("Simple Pattern Matches") {
    it("Case 1: can be as simple as an assignment") {
      val x: Int = 40
      x should be(40)
    }

    it("Case 2: can also be more meaningful as something like a tuple") {
      val (x, y) = (100, "Foo")
      x should be(100)
      y should be("Foo")
    }

    it("""Case 3: has the ability to have a value name and an @
        |  to not only capture the individual items but the whole item"""
        .stripMargin) {
      val t@(x, y) = (100, "Foo")
      x should be(100)
      y should be("Foo")
      t should be(100 -> "Foo")
      t should be(100, "Foo")
    }

    it("Case 4: can be used with an Option, and often is " +
      "|  used as such, let's do a Some") {
      val Some(x) = Some(100)
      x should be(100)
    }

    it("""Case 5: can use an _ to signify that you are not
        |  interested in a particular element,
        |  let's try a tuple first.""".stripMargin) {
      val (x, _, z) = (4, 400.2, "Foo")
      x should be(4)
      z should be("Foo")
    }

    it("""Case 6: can use an _ to signify that you are not
        |  interested in a particular element,
        |  and in an Option[T] although there is no way to
        |  extract a value but if you want to ensure a shape,
        |  it would make sense""".stripMargin) {
      val a@Some(_) = Some(100)
      a should be(Some(100))
    }

    it("""Case 7: can use an _ even in an assignment, although,
        |  only if you wish match a particular
        |  shape""".stripMargin) {
      val a@(_: Int) = 40
      //val a:Int = 40
      a should be(40)
    }


    it("""Case 8: We can also match Lists by assignment, start off simple,
        |  this is a match on an empty list""".stripMargin) {
      val all@List() = List()
      all should be('empty)
    }

    it("""Case 9: can also match using a variant form since Nil
        |  represents an empty list""".stripMargin) {
      val xs@Nil = List()
      xs should be('empty)
    }

    it( """Case 10: can also match a single item using the :: form""") {
      val h :: Nil = List("Foo")
      h should be("Foo")
    }

    it( """Case 11: can also match a single item using the List() form""") {
      val List(h) = List(10)
      h should be(10)
    }


    it("""Case 12: can do a list where you care about an
        |  *exact* number of items,
        |  let's try two using :: form""".stripMargin) {
      val fst :: snd :: Nil = List(3, 10)
      fst should be(3)
      snd should be(10)
    }

    it("""Case 13: can do a list where you care about an *exact*
        |  number of items,
        |  now with List() form""".stripMargin) {
      val List(fst, snd) = List(3, 10)
      fst should be(3)
      snd should be(10)
    }

    it("""Case 14: can do a list where you care about an *exact* number of items,
        |  let's try three using :: form""".stripMargin) {
      val fst :: snd :: trd :: Nil = List(40, 19, 100)
      fst should be(40)
      snd should be(19)
      trd should be(100)
    }

    it("""Case 15: can do a list where you care about an *exact* number of items,
        |  let's try three using the List() form""".stripMargin) {
      val List(fst, snd, trd) = 40 :: 19 :: 100 :: Nil
      fst should be(40)
      snd should be(19)
      trd should be(100)
    }

    it("""Case 16: can do a list where you want to capture
        |  any number of items and keep the remainder in an
        |  extra list using the :: form""".stripMargin) {
      val f :: s :: xs = (1 to 5).toList
      f should be(1)
      s should be(2)
      xs should be(List(3, 4, 5))
    }

    it("""Case 17: can do a list where you want to capture any
        |  number of items and keep the remainder in an
        |  extra list using the List() form""".stripMargin) {
      val List(f, s, xs@_*) = (1 to 5).toList
      f should be(1)
      s should be(2)
      xs should be(List(3, 4, 5))
    }

    it("""Case 18: can do a list where you want to capture any number of items and
        |  ignore the remainder in an extra list using the List() form"""
        .stripMargin) {
      val List(fst, snd, _*) = (1 to 5).toList
      fst should be(1)
      snd should be(2)
    }

    it("""Case 19: can also capture a list with @ to capture the entire list while
        |  at the same time capturing elements of a list and the remainder
        |  here using the :: form""".stripMargin) {
      val fst :: snd :: _ = (1 to 5).toList
      fst should be(1)
      snd should be(2)
    }

    it("""Case 20: can also capture a list with @ to capture the entire list while
        |  at the same time capturing elements of a list and ignoring remainder
        |  here using the :: form""".stripMargin) {
      val all@fst :: snd :: rest = (1 to 5).toList
      fst should be(1)
      snd should be(2)
      rest should be(List(3, 4, 5))
      all should be(List(1, 2, 3, 4, 5))
    }

    it("""Case 21: can also capture a list with @ to capture the entire list while
        |  at the same time capturing elements of a list and the remainder
        |  here using the List() form""".stripMargin) {
      val all@List(fst, snd, rest@_*) = (1 to 10).toList
      fst should be(1)
      snd should be(2)
      rest should be(List(3, 4, 5, 6, 7, 8, 9, 10))
      all should be(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    }


    it("""Case 22: will always throw a Match Error if something
        |  doesn't match, for example here
        |  is an attempted match with a tuple""".stripMargin) {
      val z: Any = (1, 4.0, "Foo")
      a[MatchError] should be thrownBy
        { //ScalaTest way of capturing a error or exception
          val (x, y) = z
        }
    }

    it("""Case 23: is rarely used in assignments, it is used inside a match,
        |  which takes the following form, let's start with a Tuple 3
        |  and attempt to match""".stripMargin) {



      def whatAmI_?(item:Any) = item match {
          case (x, y) => s"Tuple 2 $x and $y"
          case (x, y, z) => s"Tuple 3 $x and $y and $z"
          //case _ => s"That this is the default"
        }

      val item: Any = (1, 1.0, "Wow")
      whatAmI_?(item) should be("Tuple 3 1 and 1.0 and Wow")
    }

    it("""Case 24: Let's do up a replicate method using pattern
        |  matching using recursion""".stripMargin) {

      def replicate[A](count: Int, elem: A): List[A] =
        count match {
          case 0 => Nil
          case _:Int => elem :: replicate(count - 1, elem)
        }


      replicate(0, "Whoa") should be(List())
      replicate(1, "Whoa") should be(List("Whoa"))
      replicate(2, "Whoa") should be(List("Whoa", "Whoa"))
      replicate(5, "Whoa") should
        be(List("Whoa", "Whoa", "Whoa", "Whoa", "Whoa"))
    }

    it("""Case 25: Recreate the above but be sure that it is done
        |  in a tail-recursive manner""".stripMargin) {

      def replicate[A](count: Int, elem: A): List[A] = {
        import scala.annotation.tailrec
        @tailrec
        def repl(c: Int, acc:List[A]): List[A] =
          count match {
            case 0 => Nil
            case _:Int => repl(c - 1, elem :: acc)
          }
        repl(count, Nil)
      }

      replicate(0, "Whoa") should be(List())
      replicate(1, "Whoa") should be(List("Whoa"))
      replicate(2, "Whoa") should be(List("Whoa", "Whoa"))
      replicate(5, "Whoa") should
        be(List("Whoa", "Whoa", "Whoa", "Whoa", "Whoa"))
    }

    it("""Case 26: Implement mySecond, which will always return the
        |  second element of any list""".stripMargin) {

      pending

      def mySecond[A](list: List[A]): Option[A] = {
        None
      }

      mySecond(Nil) should be(None)
      mySecond(List(1)) should be(None)
      mySecond(List("a", "b")) should be(Some("b"))
      mySecond(List(1.0, 5.0, 12.0)) should be(Some(5.0))

    }

    it("""Case 27: Can use a pipe or alternate matching to match an item""") {
      val any: Any = List(3)
      val str = any match {
        case List(1) | List(3) => "Single item, 1 or 3"
        case List(_*) => "Any kind of list"
      }
      str should be ("Single item, 1 or 3")
    }

    it("""Case 28: Implement mySecond again, but this time use a pipe to clean
        |  up any redundancy""".stripMargin) {

      pending

      def mySecond[A](list: List[A]): Option[A] = {
        ???
      }

      mySecond(Nil) should be(None)
      mySecond(List(1)) should be(None)
      mySecond(List("a", "b")) should be(Some("b"))
      mySecond(List(1.0, 5.0, 12.0)) should be(Some(5.0))

    }


    it("""Case 28: should have a None in a pattern match,
        | though we have not covered it.
        |  This is just one way
        |  to get the information from an Option[T]""".stripMargin) {

      val map = Map(1 -> "One", 2 -> "Two", 3 -> "Three")

      val result = map.get(1) match {
        case Some(x) => s"Got answer $x"
        case None => "No answer here"
      }

      result should be("Got answer One")
    }

    it("""Case 29: should be careful with only Some vs. Option""") {
      val answerToEverything: Option[Int] = Some(42)

      val result = answerToEverything match {
        case Some(x) => s"Got answer $x"
        case None => "No answer"
      }

      result should be("Got answer 42")
    }

    it("""Case 30: should also match just simple types like Int,
        |  String, etc.""".stripMargin) {
      val a: Any = 40

      val result = a match {
        case x: Int => s"This is an int $x"
        case y: String => s"This is a string $y"
        case _ => s"unknown type"
      }

      result should be("This is an int 40")
    }

    it("""Case 31: of course order is always important in
        |  pattern matching, particularly with types""".stripMargin)
    {
      val a: Any = 40

      val result = a match {
        case n: Number => s"This is a number $n"
        case x: Int => s"This is an int $x"
        case y: String => s"This is a string $y"
        case _ => s"unknown type"
      }

      result should be("This is a number 40")
    }

    it("""Case 32: also works with a scala.collection.immutable.Stream,
        |  let's do a mySecondStream""".stripMargin) {
      pending
    }

    it("""Case 33: should also have guards just in case,
        |   let's say we want to match an Int,
        |   and Int that is even, and a String,
        |   and anything else""".stripMargin) {

      pending

      val a: Any = 40
      val result:String = ???
      result should be(???.asInstanceOf[String])
    }
  }

  describe("A Pattern Match with the following custom class") {
    case class Employee(firstName: String, lastName: String)

    it("""Case 34: can do compound matching where one item is in
        |  another, using the :: form""".stripMargin) {
      val Employee(x, y) :: Nil = List(Employee("Homer", "Simpson"))
      x should be("Homer")
      y should be("Simpson")
    }

    it("""Case 35: can do compound matching where one item is in another,
        |  using the List() form""".stripMargin) {
      val List(Employee(fn, ln)) = List(Employee("Bertrand", "Russell"))
      fn should be("Bertrand")
      ln should be("Russell")
    }

    it("""Case 36: can do compound matching layers deep, like an Employee,
        |  in a Some, in List, using the :: form""".stripMargin) {
      val Some(Employee(fn, ln)) :: Nil = List(Some(Employee("Mark", "Twain")))
      fn should be("Mark")
      ln should be("Twain")
    }

    it("""Case 37: can do compound matching layers deep, like an Employee
        |  , in a Some, in List, using the List() form""".stripMargin) {
      val List(Some(Employee(fn, ln))) = List(Some(Employee("Mark", "Twain")))
      fn should be("Mark")
      ln should be("Twain")
    }

    it("""Case 38: can be as simple as assignments with a custom case class and
        |  it must be a case class or class with an extractor""".stripMargin) {
      val Employee(fn, ln) = Employee("Harry", "Truman")
      fn should be("Harry")
      ln should be("Truman")
    }

    it("""Case 39: can match the whole custom case class when provided with @
        |  along with the pattern match itself""".stripMargin) {
      val a@Employee(fn, ln) = Employee("Lisa", "Simpson")
      fn should be("Lisa")
      ln should be("Simpson")
      a should be(Employee("Lisa", "Simpson"))
    }

  }

  describe("A Regular Pattern Expression Match") {
    it("""Case 40: uses .r after a String to Convert it to a Regex Type,
        |  from there groups can can be determined"""
        .stripMargin) {

      case class PhoneNumber(countryCode: String, areaCode: String,
                             prefix: String, suffix: String)

      def convertString2PhoneNumber(pn: String): Option[PhoneNumber] = {
        val PhoneNumberPlainRegex: Regex = """(\d{3})-(\d{4})""".r
        val PhoneNumberWithAreaRegex: Regex = """(\d{3})-(\d{3})-(\d{4})""".r
        val PhoneNumberWithEverythingRegex: Regex =
          """(\d{1,3})-(\d{3})-(\d{3})-(\d{4})""".r
        pn match {
          case PhoneNumberPlainRegex(pre, suf) => Some(
            PhoneNumber("1", "000", pre, suf))
          case PhoneNumberWithAreaRegex(ac, pre, suf) => Some(
            PhoneNumber("1", ac, pre, suf))
          case PhoneNumberWithEverythingRegex(cc, ac, pre, suf) => Some(
            PhoneNumber(cc, ac, pre, suf))
          case _ => None
        }
      }

      convertString2PhoneNumber("1-303-202-4001") should
        be(Some(PhoneNumber("1", "303", "202", "4001")))
    }
  }

  describe("Partial Functions") {
    it("""Case 41: is like a function, but with an added method called
        |  isDefined.  isDefined() returns
        |  true or false, it also has an `apply` method to invoke
        |  the function iff isDefined returns true.
        |  Partial Functions together should form a complete function."""
        .stripMargin) {


      val doubleEvens = new PartialFunction[Int, Int]() {
        override def isDefinedAt(x: Int): Boolean = x % 2 == 0

        override def apply(v1: Int): Int = v1 * 2
      }

      val tripleOdds = new PartialFunction[Int, Int]() {
        override def isDefinedAt(x: Int): Boolean = x % 2 != 0

        override def apply(v1: Int): Int = v1 * 3
      }

      val result = List(1, 2, 3, 4, 5, 6).map(doubleEvens orElse tripleOdds)

      result should be(List(3, 4, 9, 8, 15, 12))
    }

    it("""Case 42: can also be trimmed down inline with case statements
        |  compare the above with the following below""".stripMargin) {

      val result = List(1, 2, 3, 4, 5, 6).map {
        case x: Int if x % 2 == 0 => x * 2
        case y: Int if y % 2 != 0 => y * 3
      }

      result should be(List(3, 4, 9, 8, 15, 12))
    }
  }

  describe("Custom pattern matching") {

    object Even {

    }

    object Odd {

    }

    it(
      """Case 43: uses unapply to extract elements for a pattern
        |  match so you can do your own pattern matching,
        |  the unapply method should return an Option and either
        |  a tuple or list of the parts"""
        .stripMargin) {

      pending
      val num = 40
      val result:String = ???
      result should be("40 is Even")
    }

    it("""Case 44: while building a pattern match off of another
        | unapply, in this |  case we will try to pattern match A
        | tuple of (Even, Even),
        | (Odd, Odd), etc. """.stripMargin) {

      pending
      val r = (40, 120)
      val result:String = ???
      result should be("Two Evens")
    }

    it("""Case 45: can also be used in composing partial functions to
        |  form a complete function, here we will use a map using a
        |  partial function with our Even and Odd""".stripMargin) {
      pending
    }
  }

  describe("Custom pattern matching with an instance") {

    class AllInt[B](val reduceFunction: (Int, Int) => Int) {
      val regex: Regex = """\d+""".r

      def unapply(s: String): Option[Int] = None
    }

    it(
      """Case 46: can also extract from an instance just in case it
        |  is the instance that contains logic
        |  to extract information, this is the technique used to
        |  for regex grouping"""
        .stripMargin) {

      pending

      val question = "What is the total of 100, 300, 22, 97, 230, 950, and 411?"
      val sumInt = new AllInt(_ + _)

      val result:String = ???

      result should be("Captured: 2110")
    }
  }

  describe("""Custom pattern matching with unapplySeq.
    |  Here we will create WordNumbers that will find all the numbers in a
    |  word.""".stripMargin) {

    object WordNumbers {
      def unapplySeq(s: String): Option[Seq[String]] = ???
    }

    it("Case 47: would require an unapplySeq for extracting collections") {
      pending

      val result:String = ???
      result should be("More than three elements 110, 99, 50, and the rest is 39")
    }
  }

  describe("""Case 48: Companion Object Extractors for classes
              |  where the logic is in the companion object.  Here create
              |  some unapply logic for Genre
              |  and Movie""".stripMargin) {

    class Genre (val name: String)
    object Genre {
      //create extractors and possibly some factories
    }

    class Movie (val title: String, val year: Int, val genre: Genre)
    object Movie {
      //create extractors and possibly some factories
    }

    it("""Case 49: Companion objects will generally have the unapply or
        |  unapplySeq for classes, this also means
        |  that case classes create unapply automatically, but you can
        |  create or override your own
        |  particular rules""".stripMargin) {

      pending

      val movie = new Movie("The Fifth Element", 1998,
                             new Genre("Science Fiction"))

      val result:String = ???

      result should be("The movie presented is of the Science Fiction genre")
    }
  }
}
