package com.learningScala.chapter.four.exercise_2
import scala.io.StdIn.readLine
import scala.math.Pi

object second extends App{
  /**
   * 연습문제 1에서 작성한 함수를 반지름을 String으로 취하는 다른 형태의 함수로 바꿔보자.
   * 여러분의 함수가 빈 String으로 호출되면 어떤 일이 발생하는가?
   *
   *   def area(r: Double): Double = r*r*3.141592
   */

//  def area(r: String) = r*r*3.141592
  def area(r: String): Double = {
  if (r.isEmpty) {
    0.0
  } else {
    r.toDouble * r.toDouble * 3.141592
  }
}

  println(area("0"))
}

