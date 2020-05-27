package com.learningScala.chapter.five.exercise_6

object sixth extends App{
  /**
   * 'conditional'이라는 이름의 함수를 작성하라.
   * 이 함수는 값 x와 두 함수 p와 f를 취하고, x와 동일한 타입의 값을 반환한다.
   * p 함수는 조건자로 값 x를 취하여 Boolean b를 반환한다.
   * 함수 f 역시 값 x를 취하고 동일한 타입의 새로운 값을 반환한다.
   * 여러분의 'conditional' 함수는 p(x)가 참일 떄에만 f(x)를 호출해야 하며, 그 외의 경우에는 x를 반환해야 한다.
   * 'conditional' 함수는 몇개의 타입 매개변수를 필요로 할까?
   */

  def conditional[A](x: A, p: A => Boolean, f: A => A): A = {
    if (p(x)) f(x) else x
  }

  println(conditional[String]("Dongju", _.length > 6, _.reverse ))
  println(conditional[String]("Dongju", _.length > 3, _.reverse ))
  println(conditional[Int](1234, _.equals(52), _*10 ))
  println(conditional[Int](1234, _.equals(1234), _*10 ))
}
