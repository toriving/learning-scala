package com.learningScala.chapter.seven.exercise_4

/**
 * 숫자 타입이 아니라 String으로 지정된 두 숫자의 곱을 반환하는 함수를 작성하라.
 * 정수와 부동 소수점 수를 모두 지원하는가?
 * 두 입력값 중 어느 하나라도 유효하지 않은 경우 어떻게 전달할 것인가?
 * 전환된 숫자를 매치 표현식을 이용하여 처리할 수 있는가?
 * for 루프는 어떤가?
 */

object fourth {

  def toDouble(x: String): Option[Double] ={
    util.Try(x.toDouble).toOption
    // x.toDoubleOption 가능
  }

  def product(x: String, y: String): Option[Double] ={
    val xDouble = toDouble(x)
    val yDouble = toDouble(y)

    (xDouble, yDouble) match {
      case (Some(a), Some(b)) => Some(a * b)
      case _ => None
    }
  }

  def productFor(x: String, y: String): Option[Double] ={
    val xDouble = toDouble(x)
    val yDouble = toDouble(y)

    for (a <- xDouble; b <- yDouble) yield a * b
  }

  def main(args: Array[String]): Unit ={
    val x = "111"
    val y = "10"
    val z = "error"

    println(product(x, y))
    println(productFor(x, y))

    println(product(x, z))
    println(productFor(x, z))

  }
}
