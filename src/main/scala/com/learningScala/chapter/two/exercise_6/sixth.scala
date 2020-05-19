package com.learningScala.chapter.two.exercise_6

import scala.util.matching.Regex

object sixth extends App{
  val s = "Frank,123 Main,925-555-1943,95122"
  val pattern: Regex = """.*,(\d{3})-(\d{3})-(\d{4}),.*""".r
  val pattern(p1, p2, p3) = s
  val number = (p1.toInt, p2.toInt, p3.toInt)
  println(number)
}
