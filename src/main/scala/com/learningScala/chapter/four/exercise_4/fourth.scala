package com.learningScala.chapter.four.exercise_4

object fourth extends App{
  /**
   * 1000분의 1초(millisecond) 값을 취하여 그 값을 일, 시간, 분, 초로
   * 나타내는 문자열로 반환하는 함수를 작성하라.
   * 입력값으로 최적의 타입은 무엇인가?
   */

  def time(ms: Long): String = {
    val secs = ms / 1000

    val days = secs / 3600 / 24
    val hours = (secs % (3600 * 24)) / 3600
    val mins = (secs % 3600) / 60
    val seconds = secs % 60

    s"$days 일 $hours 시간 $mins 분 $seconds 초"
  }
  println(time(12345678900L))
}
