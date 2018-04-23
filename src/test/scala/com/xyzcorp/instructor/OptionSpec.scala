package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

class OptionSpec extends FunSuite with Matchers {
   test("""Case 1: Options are meant to avoid the ambiguities of null
       |
       | If in a database, the middle name field has a null, does it mean:
       |
       | * The person doesn’t have a middle name
       | * No one has entered the middle name
       |
       |
       |Option[+A] is the super type of Some[T] and None
       |Option[+A] is an abstract class""".stripMargin) {

     pending
   }

   test("Case 2: Using get, which not desireable") {
     pending
   }

   test("Case 3: Using get or else which works better") {

   }
}
