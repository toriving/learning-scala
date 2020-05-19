package com.learningScala.chapter.three.exercise_4

object fourth extends App{
  for (i <- 1 to 100) { if (i % 5 == 0) println(s"$i,") else print(s"$i, ")}
}
