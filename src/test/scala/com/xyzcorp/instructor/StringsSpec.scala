package com.xyzcorp.instructor

import org.scalatest.{FunSuite, Matchers}

class StringsSpec extends FunSuite with Matchers {
  test("Case 1: Varying ways to create a String") {
    val s0 = "Scala"
    val s1:String = "Scala"
    val s2 = "Scala":String
  }

  test("Case 2: String.format formats your String in C/Java style") {
    info("Using the java standard")
    String.format("This is a %s", "test") should be ("This is a test")

    info("Using the scala standard")
    "This is a %s".format("test") should be ("This is a test")
  }

  test("Case 3: Changing the order of arguments using format") {
    val stmt = "Because you're %3$s, %2$s, %1$s times a lady"
      .format("Three", "Twice", "Once")
    stmt should be ("Because you're Once, Twice, Three times a lady")
  }

  test("Case 4: Formatting Dates and Times") {
    import java.time._
    val date = LocalDate.of(2018, 4, 12)
    val stmt = "We will be eating lunch on %1$tB the %1$te in the year %1$tY".format(date)
    stmt should be ("We will be eating lunch on April the 12 in the year 2018")
  }

  test("Case 5: Smart Strings") {
    val prose = """I see trees of green,
                   red roses too
                   I see them bloom,
                   for me and you,
                   and I think to myself,
                   what a wonderful world"""
    """\s{2,}""".r.findAllIn(prose) should not be 'empty
  }

  test("Case 6: Smart Strings with stripMargin") {
    val prose = """I see trees of green,
                   |red roses too
                   |I see them bloom,
                   |for me and you,
                   |and I think to myself,
                   |what I wonderful world""".stripMargin
    """\s{2,}""".r.findAllIn(prose) should be ('empty)
  }

  test("Case 7: Smart Strings with stripMargin using other characters") {
    val prose = """I see trees of green,
                  @red roses too
                  @I see them bloom,
                  @for me and you,
                  @and I think to myself,
                  @what I wonderful world""".stripMargin('@')

    """\s{2,}""".r.findAllIn(prose) should be ('empty)
  }

  test("Case 8: Smart Strings with stripMargin using format") {
    val prose = """I see trees of %s,
                  |%s roses too
                  |I see them bloom,
                  |for me and you,
                  |and I think to myself,
                  |what I wonderful world""".stripMargin.format("green", "Red")
    """\s{2,}""".r.findAllIn(prose) should be ('empty)
    prose should not contain "%s"
  }

  test("Lab: String Interpolation") {
     val a = 99
     val result:String = s"${a+1} luftballoons floating in the summer sky"
     result should be ("100 luftballoons floating in the summer sky")
  }


  test("Lab:String Interpolation using a smart string") {
    val a = 99
    val result =
      s"""$a luftbaloons
          |...floating in the summer sky""".stripMargin


  }
  test("""Lab: String Interpolation with the f interpolator.
      |  The f interpolator will use formatting with interpolation
      |  for example $firstName%s would treat first name as a string, $$ for
      |  dollar sign and $myPrice%1.2f for a floating point.
      |  """.stripMargin) {
    val ticketsCost = 50
    val bandName = 404
    val result = f"The $bandName tickets are probably $$$ticketsCost%2.2f"
    result should be ("The 404 tickets are probably $50.00")
  }

  test("""Lab: Formatting and Interpolating, turn the following in a the
          |  statement using f interpolation:
          |
          |  The Psychedelic Furs tickets are probably $50.00
          |  That's a 20% bump because everyone likes New Wave
          |
          |  Use: http://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html
          |  for reference
          | """.stripMargin) {
    val ticketsCost = 50
    val bandName = "Psychedelic Furs"
    val percentIncrease = 20
    val musicGenre = "New Wave"
    val result:String =
      f"""The $bandName tickets are probably $$$ticketsCost%1.2f
         |That's a $percentIncrease%% bump because everyone likes $musicGenre""".stripMargin
    result should be ("The Psychedelic Furs tickets are probably $50.00\nThat's a 20% bump because everyone likes New Wave")

  }
}
