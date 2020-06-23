package com.learningScala.chapter.eight.exercise_2
import scala.annotation.tailrec

/**
 * 연결 리스트를 객체지향 스타일로 생성하라.
 */

object secondD {
  /**
   * d
   * head, tail, filter, size, map 컬렉션 메소드를 반복 대신 재귀를 사용하여 구현 하라.
   * 이 메소드 모두 대량의 컬렉션으로 인한 스택 오버플로우 에러를 방지하기 위해 꼬리 재귀를 사용할 수 있다고 보장할 수 있는가?
   */



  class ListyHelper {
    def create[A](items: A*) = {
      var result: Listy[A] = new EmptyList[A]
      for (item <- items.reverse) {
        result = new NonEmptyList[A](item, result)
      }
      result
    }
  }

  abstract class Listy[A] {
    def foreach(f: A => Unit): Unit
    def apply(index: Int): Option[A]

    def headOption: Option[A] = apply(0)

    lazy val head: A = headOption.get

    def tail: Listy[A]

    def ::(a: A): Listy[A] = new NonEmptyList[A](a, this)

    def filter(f: A => Boolean): Listy[A] = {

      @tailrec
      def filterLists(src: Listy[A], dest: Listy[A], f: A => Boolean): Listy[A] = {
        src.headOption match {
          case Some(i) if f(i) => filterLists(src.tail, i :: dest, f)
          case Some(i) => filterLists(src.tail, dest, f)
          case None => dest
        }
      }

      val result: Listy[A] = filterLists(this, new EmptyList[A], f)
      result.reverse
    }


    lazy val size: Int = {

      @tailrec
      def count(src: Listy[A], total: Int): Int = {
        src.headOption match {
          case Some(i) => count(src.tail, total + 1)
          case None => total
        }
      }

      count(this, 0)
    }

    def map[B](f: A => B): Listy[B] = {

      @tailrec
      def mapLists[B](src: Listy[A], dest: Listy[B], f: A => B): Listy[B] = {
        src.headOption match {
          case Some(i) => mapLists(src.tail, f(i) :: dest, f)
          case None => dest
        }
      }

      val result: Listy[B] = mapLists(this, new EmptyList[B], f)
      result.reverse
    }

    lazy val reverse: Listy[A] = {

      @tailrec
      def reverseLists(src: Listy[A], dest: Listy[A]): Listy[A] = {
        src.headOption match {
          case Some(i) => reverseLists(src.tail, i :: dest)
          case None => dest
        }
      }

      reverseLists(this, new EmptyList[A])
    }
  }

  class NonEmptyList[A](val item: A, val tail: Listy[A]) extends Listy[A] {

    override def foreach(f: A => Unit): Unit = {
      f(item)
      tail.foreach(f)
    }

    override def apply(index: Int): Option[A] = {
      if (index < 1) Some(item) else tail.apply(index - 1)
    }
  }

  class EmptyList[A] extends Listy[A] {
    override def foreach(f: A => Unit): Unit = {}
    override def apply(index: Int): Option[A] = None
    override def tail: Listy[A] = null
  }

  def main(args: Array[String]): Unit ={
    val ll = new ListyHelper().create(1,2,3,4,10)
    println(ll.head)
    ll.foreach(println)
    println(ll(0))
    println(ll(1))
    println(ll(2))
    println(ll(3))
    println(ll(4))
    println(ll.head)
    println(ll.tail)
    println(ll.size)
    println(ll.filter(x => x >1))
  }
}
