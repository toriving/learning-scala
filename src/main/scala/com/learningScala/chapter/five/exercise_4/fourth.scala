package com.learningScala.chapter.five.exercise_4

object fourth extends App{
  /**
   * 여러분이 다른 개발자의 코드를 검토하다가 이 함수를 보게 되었다고 가정하자.
   * def fzero[A](x: A)(f: A => Unit): A = { f(x); x }
   * 이 함수는 무엇을 수행하는가? 어떻게 이 함수를 호출할 것인지 예를 들 수 있는가?
   */
  def fzero[A](x: A)(f: A => Unit): A = { f(x); x }

  println(fzero[Boolean](false) { a => println(s"a was $a") })
  println(fzero[Int](52) { b => println(s"b was $b") })
  println(fzero[String]("Dongju") { c => println(s"c was $c") })

  // A타입의 x와 A타입을 arg로 갖는 함수x를 arg로 받아서 x에 대해 함수 f를 시행하고 리턴으로 x를 돌려줌
}
