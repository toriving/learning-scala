package com.learningScala.chapter.three.exercise_2

object second extends App{
  val amount: Double = 4.2
  println(if (amount > 0) "greater" else if (amount < 0) "less" else "same")

  println(amount match {
    case a if a > 0 => "greater"
    case a if a < 0 => "less"
    case a if a == 0 => "same"
    }
  )
}
