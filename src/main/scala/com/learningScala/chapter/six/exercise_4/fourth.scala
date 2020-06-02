package com.learningScala.chapter.six.exercise_4

object fourth {

  def longest(l: List[String]): String = {
    l.sortBy(_.length).reverse.head
  }

  def longestFold(l: List[String]): String = {
    l.fold("")((x, y) => if (x.length < y.length) y else x)
  }

  def longestReduce(l: List[String]): String = {
    l.reduce((x, y) => if (x.length < y.length) y else x)
  }

  def longestAll[A](l: List[A]): A = {
    l.reduce((x, y) => if (x.toString.length < y.toString.length) y else x)
  }

  def main(args: Array[String]): Unit ={
    val names = List("Harry", "Hermione", "Ron", "Snape")
    val numbers = List(124, 31, 12345, 3)
    println(longest(names))
    println(longestFold(names))
    println(longestReduce(names))
    println(longestAll(names))
    println(longestAll(numbers))

  }
}
