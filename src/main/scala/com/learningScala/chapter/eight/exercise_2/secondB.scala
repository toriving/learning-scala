package com.learningScala.chapter.eight.exercise_2

/**
 * 연결 리스트를 객체지향 스타일로 생성하라.
 */

object secondB {
  /**
   * b
   * 여러분의 연결 리스트가 훌륭할 것이라 확신한다.
   * 그러나 좀 더 흥미로운 접근법으로 이 리스트를 재구조화해보자.
   * 컨테이너 클래스를 두 개의 서브클래스를 가지는 추상 클래스로 만들어보자.
   * 하나는 유효한 항목을 가지는 노드를 나타내며,
   * 또 다른 하나는 유효한 아이템이 없는 노드를 나타내어 리스트의 마지막 아이템을 표시한다.
   * - 두 번째 서브클래스에 하나 이상의 인스턴스가 필요할까?
   * - private이어야 하는 도우미 메소드 (helper method)는 없는가?
   * - 서브클래스에서 구현해야 할 추상 메소드는 없는가?
   * - 만일 apply() 메소드를 구현했다면 각 서브클래스는 자신만의 구현을 가져야 하는가?
   */



  abstract class LinkedList[T] {
    def foreach(f: T => Unit): Unit
    def apply(index: Int): Option[T]
  }

  class DefinedList[T](items: T*) extends LinkedList[T] {
    val head: Option[T] = items.headOption

    val next: Option[LinkedList[T]] = {
      if (items.tail.isEmpty) Some(new EmptyList[T]) else Some(new DefinedList[T](items.tail: _*))
    }

    override def foreach(f: T => Unit): Unit = {
      if (head.isDefined) {
        f(head.get)
        next.get.foreach(f)
      }
    }

    def apply(index: Int): Option[T] = {
      if (index == 0) head else next.flatMap(_.apply(index - 1))
    }

  }

  class EmptyList[T] extends LinkedList[T] {
    override def foreach(f: T => Unit): Unit = {}
    override def apply(index: Int): Option[T] = None
  }

  def main(args: Array[String]): Unit ={
    val ll = new DefinedList(1,2,3,4,10)
    println(ll.head)
    ll.foreach(println)
    println(ll(0))
    println(ll(1))
    println(ll(2))
    println(ll(3))
    println(ll(4))
    println(ll(5))
  }
}
