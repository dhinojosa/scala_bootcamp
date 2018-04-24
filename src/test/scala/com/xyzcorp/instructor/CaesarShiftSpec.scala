package com.xyzcorp.instructor

import org.scalatest.{FunSuite, GivenWhenThen, Matchers}

class CaesarShiftSpec extends FunSuite with Matchers with GivenWhenThen {
  test("Case 1: A shift of zero, and an empty string") {
    //input: word to encode, int shift
    //output: encoded word

    //What is simplest, dumbest thing
    val caesarShift = new CaesarShift(0)
    caesarShift.encode("") should be ("")
  }

  test("""A shift of zero, and a string of "a"""") {
    val caesarShift = new CaesarShift(0)
    caesarShift.encode("a") should be ("a")
  }

  test("""A shift of 1, and a string of "a"""") {
    val caesarShift = new CaesarShift(1)
    caesarShift.encode("a") should be ("b")
  }

  test("""A shift of -1, and string of b""") {pending}
}
