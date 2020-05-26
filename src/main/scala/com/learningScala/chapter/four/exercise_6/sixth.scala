package com.learningScala.chapter.four.exercise_6

object sixth extends App{
  /**
   * 한 쌍의 2차원 점(x와 y) 사이의 거리를 계산하는 함수를 작성하고 그 값을 점으로 반환하라.
   * 힌트: 튜플을 사용하는 것이 좋다.
   */
  def distance(x: (Double, Double), y: (Double, Double)): Double = {
    math.sqrt(math.pow(x._1 - y._1, 2) + math.pow(x._2 - y._2, 2))
  }
  println(distance((2,23), (12,5)))
}
