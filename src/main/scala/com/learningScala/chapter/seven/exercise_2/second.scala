package com.learningScala.chapter.seven.exercise_2

/**
 * Array 컬렉션의 예제에서 (127쪽 '배열' 참조) 우리는 현재 디렉터리에 있는 파일의 배열을 반환하는 java.io.File(<path>).listFiles를 사용했다.
 * 하나의 디렉터리에 동일한 작업을 하고, 각 항목을 toString 메소도를 이용하여 String 표시로 전환하는 함수를 작성하라.
 * 파일명이 점(.)으로 시작하는 모든 파일을 걸러내고, 나머지 파일들을 세미콜론(;)으로 구분하여 출력하라.
 * 여러분 컴퓨터에서 파일이 많은 디렉터리에서 시험해보자.
 */


object second {

  def printFiles(path: String): Unit = {
    val files = new java.io.File(path).listFiles.toList.map(x => x.toString)
    val oldFiles = files.map(_.replace(path, ""))
    println(oldFiles)
    val newFiles = oldFiles.filter(!_.startsWith("."))
    println(newFiles)
    println(newFiles.mkString(";"))

  }

  def main(args: Array[String]): Unit ={
    val path = "C:\\workspace\\learning-scala\\"
    printFiles(path)
  }
}
