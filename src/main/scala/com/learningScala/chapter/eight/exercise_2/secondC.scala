package com.learningScala.chapter.eight.exercise_2

/**
 * 연결 리스트를 객체지향 스타일로 생성하라.
 */

object secondC {
  /**
   * c
   * 표준 head, tail, filter, size, map 컬렉션 메소드를 여러분의 연결 리스트에 추가하라.
   * 이 메소드 중 지연값을 사용하여 구현할 수 있는 것이 있는가?
   * 이들 중 어느 메소드가 서브클래스에 구현되는 것에 비해 부모 클래스에 구현되어야 할까?
   */



  abstract class LinkedList[T] {
    def foreach(f: T => Unit): Unit
    def apply(index: Int): Option[T]
    lazy val head: T = apply(0).get
    lazy val tail: T = apply(size-1).get

    lazy val size: Int = {
      var count = 0
      this.foreach(_ => count+=1)
      count
    }

    def filter(f: T => Boolean): LinkedList[T] = {
      var result: LinkedList[T] = new EmptyList[T]
      foreach { i =>
        if ( f(i) ) {
          result = new DefinedList[T](i)
        }
      }
      result.reverse
    }

    def map[A](f: T => A): LinkedList[A] = {
      var result: LinkedList[A] = new EmptyList[A]
      foreach { i =>
        result = new DefinedList[A](f(i))
      }
      result.reverse
    }

    lazy val reverse: LinkedList[T] = {
      var result: LinkedList[T] = new EmptyList[T]
      foreach { i => result = new DefinedList[T](i) }
      result
    }
  }

  class DefinedList[T](items: T*) extends LinkedList[T] {
    val headOption: Option[T] = items.headOption

    val next: Option[LinkedList[T]] = {
      if (items.tail.isEmpty) Some(new EmptyList[T]) else Some(new DefinedList[T](items.tail: _*))
    }

    override def foreach(f: T => Unit): Unit = {
      if (headOption.isDefined) {
        f(headOption.get)
        next.get.foreach(f)
      }
    }

    def apply(index: Int): Option[T] = {
      if (index == 0) headOption else next.flatMap(_.apply(index - 1))
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
    println(ll.head)
    println(ll.tail)
    println(ll.size)
    val a = ll.filter(x => x >1)
    println("1")
  }
}
