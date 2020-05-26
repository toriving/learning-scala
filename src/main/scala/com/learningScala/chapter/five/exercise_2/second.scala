package com.learningScala.chapter.five.exercise_2
import util.Random.nextInt

object second extends App{
  /**
   * 라이브러리 함수 util.Random.nextInt는 무작위로 정수를 반환한다.
   * 주어진 두 개의 정수 중 큰 수를 반환하는 'max' 함수를 호출할 때 이 함수를 사용하여
   * 임의의 두개의 정수 중 큰 수를 반환하도록 하라.
   * 두개의 정수 중 작은 수를 반환하는 함수로 동일한 작업을 해보고,
   * 매번 두 번째 정수를 반환하는 함수로 동일한 작업을 수행하라.
   */

  def max(x: Int, y: Int):Int = if (x > y) x else y
  def min(x: Int, y: Int):Int = if (x > y) y else x
  def second(x: Int, y: Int):Int = y

  val x1 = nextInt()
  val y1 = nextInt()
  println(x1, y1)
  println(max(x1, y1))

  val x2 = nextInt()
  val y2 = nextInt()
  println(x2, y2)
  println(min(x2, y2))

  val x3 = nextInt()
  val y3 = nextInt()
  println(x3, y3)
  println(max(x3, y3))

}
