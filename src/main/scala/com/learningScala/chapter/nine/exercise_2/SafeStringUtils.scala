package com.learningScala.chapter.nine.exercise_2

import scala.util.{Random, Try}

trait SafeStringUtils {

  /**
   * Returns a trimmed version of the string wrapped in an Option,
   * or None if the trimmed string is empty.
   *
   * @param s the string to trim
   * @return Some with the trimmed string, or None if empty
   */
  def trimToNone(s: String): Option[String] = {
    Option(s) map(_.trim) filterNot(_.isEmpty)
  }

  /**
   * Returns the string as an integer or None if it could not be converted.
   *
   * @param s the string to be converted to an integer
   * @return Some with the integer value or else None if not parseable
   */
  def parseToInt(s: String): Option[Int] = {
    trimToNone(s) flatMap { x => Try(x.toInt).toOption }
  }

  /**
   * Returns a string composed of random lower- and upper-case letters
   *
   * @param size the size of the composed string
   * @return the composed string
   */
  def randomLetters(size: Int): String = {
    val validChars: Seq[Char] = ('A' to 'Z') ++ ('a' to 'z')
    1 to size map { _ => Random nextInt validChars.size } map validChars mkString ""
  }

}

object SafeStringUtils extends SafeStringUtils