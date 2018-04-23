package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

class ClassesSpec extends FunSuite with Matchers {
  test("""Case 1: Create a class, and the class should be
         |  instantiable with the elements, but without a val
         |  I cannot get information. Let's create a stamp with elements
         |  name and year""".stripMargin) {
      pending
  }

  test("""Case 2: Case classes have automatic functionality for getters, toString,
          | equals, hashCode, apply, and basic pattern matching. Let's
          | create a case class Computer with make, model, and year""".stripMargin) {
       pending
  }

  test("""Case 3: Preconditions can be made with require. Let's make sure that the name
      | for the stamp cannot be blank.""".stripMargin) {
      pending
  }

  test("""Case 4: Subclassing. Let's create:
      |* Sports Card with year, manufacturer, and year
      |* Baseball Card with the same fields Sports Card but adding
      |  league and division""".stripMargin) {
     pending
  }

  test("""Case 5: Abstract Classes. We can also add an abstract class called
      | Collectible with a field year. Verify that all types are
      | polymorphic""".stripMargin) {
    pending
  }

  test("""Case 6: Generic Classes, Let's create a box that will be generic that will
      | hold a baseball card or anything else for that matter""".stripMargin) {
    pending
  }
}
