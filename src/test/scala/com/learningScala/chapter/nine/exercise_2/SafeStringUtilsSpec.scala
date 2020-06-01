package com.learningScala.chapter.nine.exercise_2
import org.scalatest._

class SafeStringUtilsSpec extends FlatSpec with Matchers {

  "The Safe String Utils object" should "trim empty strings to None" in {
    SafeStringUtils.trimToNone("") should be(None)
    SafeStringUtils.trimToNone(" ") should be(None)
    SafeStringUtils.trimToNone("           ") should be(None) // tabs and spaces
  }

  it should "handle null values safely" in {
    SafeStringUtils.trimToNone(null) should be(None)
  }

  it should "trim non-empty strings" in {
    SafeStringUtils.trimToNone(" hi there ") should equal(Some("hi there"))
  }

  it should "leave untrimmable non-empty strings alone" in {
    val testString = "Goin' down that road feeling bad ."
    SafeStringUtils.trimToNone(testString) should equal(Some(testString))
  }

  it should "parse valid integers from strings" in {
    SafeStringUtils.parseToInt("5") should be(Some(5))
    SafeStringUtils.parseToInt("0") should be(Some(0))
    SafeStringUtils.parseToInt("99467") should be(Some(99467))
  }

  it should "trim unnecessary white space before parsing" in {
    SafeStringUtils.parseToInt("  5") should be(Some(5))
    SafeStringUtils.parseToInt("0  ") should be(Some(0))
    SafeStringUtils.parseToInt("  99467  ") should be(Some(99467))
  }

  it should "safely handle invalid integers" in {
    SafeStringUtils.parseToInt("5 5") should be(None)
    SafeStringUtils.parseToInt("") should be(None)
    SafeStringUtils.parseToInt("abc") should be(None)
    SafeStringUtils.parseToInt("1!") should be(None)
  }

  it should "generate random strings with only lower- and upper-case letters" in {
    SafeStringUtils.randomLetters(200).replaceAll("[a-zA-Z]","") should equal("")
  }

  it should "be sufficiently random" in {
    val src = SafeStringUtils.randomLetters(100).toList.sorted
    val dest = SafeStringUtils.randomLetters(100).toList.sorted
    src should not equal dest
  }

  it should "handle invalid input" in {
    SafeStringUtils.randomLetters(-1) should equal("")
  }


}