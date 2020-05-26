package com.learningScala.chapter.four.exercise_8

object eighth extends App{
  /**
   * 세 개의 구성 요소를 가지는(3-사이즈) 튜플을 취하여 6-사이즈 튜플을 반환하는데,
   * 원래의 매개변수 다음에 그 매갭견수의 String 표기를 추가하여 반환하는 함수를 작성하라.
   * 예를 들어, 함수를 (true, 22.25, "yes")로 호출하면 (true, "true", 22.5, "22.5", "yes", "yes")를 반환해야 한다.
   * 여러분이 작성한 함수가 모든 가능한 타입의 튜플과 호환되는가?
   * 이 함수를 호출할 때, 함수 결과뿐 아니라 결과를 저장할 떄 사용 하는 값에도 명시적 타입을 지정하여 호출할 수 있는가?
   */

  def identityString[A, B, C](x: (A,B,C)): (A, String, B, String, C, String) = {
    (x._1, x._1.toString, x._2, x._2.toString, x._3, x._3.toString)
  }

  val test = identityString((true, 22.25, "yes"))
  for (x <- test.productIterator) {
    println(s"$x -> ${x.getClass}")
  }
}
