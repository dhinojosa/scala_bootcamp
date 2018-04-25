package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

class OptionSpec extends FunSuite with Matchers {
   test("""Case 1: Options are meant to avoid the ambiguities of null
       |
       |  If in a database, the middle name field has a null, does it mean:
       |
       |   * The person doesn’t have a middle name
       |   * No one has entered the middle name
       |
       |  Option[+A] is the super type of Some[T] and None
       |  Option[+A] is an abstract class""".stripMargin) {

     val connorMiddleName:Option[String] = Some("Sean")
     val dannoMiddleName:Option[String] = None
   }

   test("Case 2: Using get, which not desirable") {
     val connorMiddleName:Option[String] = Some("Sean")
     val dannoMiddleName:Option[String] = None

     connorMiddleName.get should be ("Sean")

     a [NoSuchElementException] should be thrownBy {
       dannoMiddleName.get
     }
   }

   test("Case 3: Using getOrElse which works better") {
     type OptionalMiddleName = Option[String]

     val connorMiddleName:OptionalMiddleName = Some("Sean")
     val dannoMiddleName:OptionalMiddleName = None

     connorMiddleName.getOrElse("N/A") should be ("Sean")
     val any:Any = dannoMiddleName.getOrElse(1)
     val str:String = dannoMiddleName.getOrElse("N/A")
   }
}
