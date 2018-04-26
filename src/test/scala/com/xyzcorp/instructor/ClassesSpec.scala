package com.xyzcorp.instructor

import com.xyzcorp.{BaseballCard, Collectible, SportsCard}
import org.scalatest.{FunSuite, Matchers}

class ClassesSpec extends FunSuite with Matchers {
  test("""Case 1: Create a class, and the class should be
         |  instantiable with the elements, but without a val
         |  I cannot get information. Let's create a stamp with elements
         |  name and year""".stripMargin) {
      val jimiHendrixStamp = new Stamp("Jimi Hendrix", 2014)
      jimiHendrixStamp.name should be ("Jimi Hendrix")
      jimiHendrixStamp.year should be (2014)
  }

  test(
    """Case 1.1 To be deleted because I am going to show how to
      | make it mutable """.stripMargin) {
    //val jimiHendrixStamp = new Stamp("Jimi Hendrix", 2014)
    //jimiHendrixStamp.year = 2016
    //jimiHendrixStamp.year should be (2016)
  }

  test("""Case 1.2 How do we effect in a class?""") {
    val jimiHendrixStamp = new Stamp("Jimi Hendrix", 2014)
    val newJimi = jimiHendrixStamp.copy(year = 2017)
    newJimi.year should be (2017)
    newJimi.name should be ("Jimi Hendrix")
    jimiHendrixStamp.year should be (2014)
  }

  test(
    """Case 1.3: I need to do a lot of stuff to make this full featured.
      | including: making a toString, equals, hashCode
    """.stripMargin) {
    val jimiHendrixStamp = new Stamp("Jimi Hendrix", 2014)


  }

  test("""Case 2: Case classes have automatic functionality for accessor, toString,
          | equals, hashCode, apply, copy and basic pattern matching. Let's
          | create a case class Computer with make, model, and year""".stripMargin) {
    val jimiHendrixStamp = Stamp("Jimi Hendrix", 2014)
    jimiHendrixStamp.toString should be ("Stamp(Jimi Hendrix,2014)")

    //Pattern matching, assignment
    val Stamp(n, y) = jimiHendrixStamp
    n should be ("Jimi Hendrix")
    y should be (2014)


    val result = jimiHendrixStamp match {
      case Stamp(_, 2014) => "2014 good year for Stamps"
      case Stamp("Jimi Hendrix", _) => "Excuse Me"
      case Stamp("Janis Joplin", q) => s"Ah Janis! And $q, a good year"
      case Stamp(_, q) if q < 1980 => "Ah the classics"
      case _ => "I don't know what you're telling me"
    }

    result should be ("2014 good year for Stamps")
  }

  test("Braja's question about nested") {
    val x:Any = Some(Some(Stamp("Prince", 2017)))

    def matchIt(x:Any) = x match {
      case Some(Some(Stamp(n3, y3))) => s"Stamp of $n3 from $y3"
      case Some(m) =>
        val name = "Single"
        s"$name Some $m"
      case "Foo" => "What? Why foo?"
      case None => "None"
    }

    matchIt(x) should be ("Stamp of Prince from 2017")
  }


  test("""Case 3: Preconditions can be made with require. Let's make sure that the name
      | for the stamp cannot be blank.""".stripMargin) {

    an [IllegalArgumentException] should be thrownBy {
      Stamp("", 2014)
    }
  }

  test("""Case 4: Subclassing. Let's create:
      |    * Sports Card with year, manufacturer, name
      |    * Baseball Card with the same fields Sports Card but adding
      |      league and division""".stripMargin) {

    val baseballCard = new BaseballCard("Ken Griffey Jr", "Topps", 1996,
      "American", "West")

    baseballCard.year should be (1996)
    baseballCard.league should be ("American")
  }

  test("""Case 5: Abstract Classes. We can also add an abstract class called
      |  Collectible with a field year. Verify that all types are
      |  polymorphic""".stripMargin) {

    val baseballCard = new BaseballCard("Ken Griffey Jr", "Topps", 1996,
      "American", "West")

    baseballCard shouldBe a [BaseballCard]
    baseballCard shouldBe a [SportsCard]
    baseballCard shouldBe a [Collectible]
    baseballCard shouldBe a [AnyRef]
    baseballCard shouldBe a [AnyVal] //Bug
    baseballCard shouldBe a [Any]
  }

  test("""Case 6: Generic Classes, Let's create a box that will be generic that will
      | hold a baseball card or anything else for that matter""".stripMargin) {

    val baseballCard = new BaseballCard("Ken Griffey Jr", "Topps", 1996,
      "American", "West")
    val box: Box[BaseballCard] = new Box(baseballCard)
    val boxInt: Box[Int] = new Box(4)
    val boxString: Box[String] = new Box("Foo")

    val result: Box[(BaseballCard, Int)] = box.tupleItWith(20)

    result.a._2 should be (20)
  }
}
