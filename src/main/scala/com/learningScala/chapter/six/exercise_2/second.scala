package com.learningScala.chapter.six.exercise_2

object second {

  def factors(num: Long): List[Long] ={
    (2 until num.toInt).filter(num % _ == 0).toList.map(_.toLong)
  }

  def uniqueFactors(l: List[Long]): List[Long] = {
    l.flatMap(factors)
  }

  def main(args: Array[String]): Unit ={
    println(factors(15))
    println(uniqueFactors(List(9, 11, 13, 15)))
  }

}
