package com.learningScala.chapter.six.exercise_6

object sixth {

  def palindrome(l:List[String]): (List[String], List[String]) ={
    l.partition(x => x == x.reverse)
  }
//(List[String], List[String])
  def palindrome2(l:List[String]): (List[String], List[String]) ={
    l.foldLeft((List[String](), List[String]())){ (list, word) => if (word == word.reverse) (word :: list._1, list._2) else (list._1, word :: list._2)}
  }

  def main(args: Array[String]) ={
    val pallies = List("Hi", "otto", "yo", "racecar")
    println(palindrome(pallies))
    println(palindrome2(pallies))
  }
}
