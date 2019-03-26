package com.xyzcorp.student

import com.xyzcorp._
import org.scalatest.{FunSuite, Matchers}

class ClassesSpec extends FunSuite with Matchers {
  test(
    """Case 1: Create a class, and the class should be
      |  instantiable with the elements, but without a val
      |  I cannot get information. Let's create a stamp with elements
      |  name and year""".stripMargin) {
    val stamp = Stamp("Jimi Hendrix", 2014)
    stamp.name should be("Jimi Hendrix")
    stamp.year should be(2014)
    stamp.age should be(5)
  }

  test(
    """Case 2: Case classes have automatic functionality for getters, toString,
      | equals, hashCode, apply, and basic pattern matching. Let's
      | create a case class Computer with make, model, and year""".stripMargin)
  {
    val computer = Computer("Commodore", "64", 1983)
    val computer2 = computer.copy(model = "128", year = 1986)
    computer2.year should be(1986)
    computer.year should be(1983)
  }

  test(
    """Case 3: Preconditions can be made with require. Let's make sure that the name
      | for the stamp cannot be blank.""".stripMargin) {
    val exception = the[IllegalArgumentException] thrownBy {
      Stamp("", 1776)
    }
    exception.getMessage should be("requirement failed: Name cannot be empty")
  }

  test(
    """Case 4: Subclassing. Let's create:
      |* Sports Card with year, manufacturer, and year
      |* Baseball Card with the same fields Sports Card but adding
      |  league and division""".stripMargin) {
    val baseballCard =
      new BaseballCard(1952, "Topps",
        "Mickey Mantle", "American", "Eastern")
    baseballCard.year should be(1952)
    baseballCard.manufacturer should be("Topps")
    baseballCard.playerName should be("Mickey Mantle")
  }

  test(
    """Case 5: Abstract Classes. We can also add an abstract class called
      | Collectible with a field year. Verify that all types are
      | polymorphic""".stripMargin) {
    val baseballCard =
      new BaseballCard(1952, "Topps",
        "Mickey Mantle", "American", "Eastern")
    baseballCard shouldBe a[Collectible]
    baseballCard shouldBe a[BaseballCard]
  }

  test(
    """Case 6: Generic Classes, Let's create a box that will be generic that will
      | hold a baseball card or anything else for that matter""".stripMargin) {
    val baseballCard =
      new BaseballCard(1952, "Topps",
        "Mickey Mantle", "American", "Eastern")
    val box = new Box(baseballCard)
    box.contents.year should be(1952)
    box.contents.playerName should be("Mickey Mantle")
  }
}
