package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

class ObjectsSpec extends FunSuite with Matchers {

    test("""Case 1: An object is a singleton, lets prove it""") {
        object MySingleton

        val a = MySingleton
        val b = MySingleton

      (a == b) should be (true)
      (a eq b) should be (true)
    }

    test(
      """Case 1.1: A method within a singleton looks like classic
        |  static call of other languages""".stripMargin) {

      object MySingleton {
        def foo(x:Int, y:Int) = x + y
      }

      MySingleton.foo(3, 10) should be (13)
    }

    test("""Case 2: A companion object has access to the private members of the class.
        | They have to be in the same file in the same name""".stripMargin) {
        pending
    }



    test(
      """Case 3: Use Object to create a 'static-like'
        | method factory for the following Employee class""".stripMargin) {

      class Employee private (val firstName: String,
                              val middleName: Option[String],
                              val lastName: String)

      object Employee {
          def apply(firstName:String,
                    middleName:String,
                    lastName:String) = new Employee(firstName,
                                                    Some(middleName),
                                                    lastName)
          def apply(firstName:String,
                  lastName:String) = new Employee(firstName,
                                                  None,
                                                  lastName)
      }

      val emp = Employee("Bob", "Barker")
      emp.firstName should be ("Bob")
      emp.lastName should be ("Barker")
      emp.middleName should be (None)
    }
}
