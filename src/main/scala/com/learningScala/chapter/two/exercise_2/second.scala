package com.learningScala.chapter.two.exercise_2

object second extends App{
  val c = 47
  val f1 = c * 9
  println(s"f1: ${f1}")  // 423

  val f2 = f1 / 5
  println(s"f2: ${f2}")  // 84.6

  val F: Int = f2 + 32  // 116.6
  println(s"F: ${F}")
}
