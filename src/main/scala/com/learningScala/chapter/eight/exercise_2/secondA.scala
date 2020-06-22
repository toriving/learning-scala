package com.learningScala.chapter.eight.exercise_2

import scala.None

/**
 * 연결 리스트를 객체지향 스타일로 생성하라.
 */

object secondA {
  /**
   * a
   * 자신의 인스턴스뿐 아니라 매개변수화된 타입의 인스턴스를 가지는 컨테이너 클래스를 생성하라.
   * 생성자는 가변 매개변수로 구현될 수 있는 다양한 인스턴스를 취해야 한다.
   * 사용자가 리스트의 요소마다 자신의 함수를 호출하며, 그 리스트를 반복하기 위해 호출할 수 있는 'foreach' 메소드를 구현하라.
   * - 리스트의 끝은 어떻게 결정할 것인가?
   * - C-스타일 리스트는 떄로는 null 값을 사용하여 리스트의 끝을 표시한다. 여기에서는 이방식이 최선일까?
   * - 여기에서 apply()를 잘 사용하였는가?
   */

  // items = List(1,2,3,4)
  class LinkedList[T](items: T*) {
    val head: Option[T] = items.headOption

    val next: Option[LinkedList[T]] = {
      if (head.isEmpty) None else Some(new LinkedList[T](items.tail: _*)) // 컬렉션은 _*
    }

    // tail = None
    def foreach(f: T => Unit): Unit = {
      if (head.isDefined) {
        f(head.get)
        next.get.foreach(f)
      }
    }

    def apply(index: Int): Option[T] = {
      if (index == 0) head else next.flatMap(_.apply(index - 1))
    }
  }

  def main(args: Array[String]): Unit ={
    val ll = new LinkedList(1,2,3,4,10)
    println(ll.head)
    println(ll.next.get.head)
    println(ll.next.get.next.get.head)
    ll.foreach(println)
    println(ll(3))
  }
}
