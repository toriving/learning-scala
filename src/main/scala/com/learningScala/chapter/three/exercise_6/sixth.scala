package com.learningScala.chapter.three.exercise_6

object sixth extends App{
  for (i <- 1 to 100) { if (i % 15 == 0) println("typesafe") else if (i % 5 == 0) println("safe") else if (i % 3 == 0) println("type") else println(s"${i}")}
}
