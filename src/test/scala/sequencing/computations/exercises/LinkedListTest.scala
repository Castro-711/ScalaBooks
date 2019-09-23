package sequencing.computations.exercises

import org.scalatest.FlatSpecLike

class LinkedListTest extends FlatSpecLike {

  class Example(d: Double)

  val intLinkedList = Pair(1, Pair(2, Pair(3, End())))
  val stringLinkedList = Pair("1", Pair("2", Pair("3", Pair("4", End()))))
  val exampleLinkedList = Pair(new Example(1.0), Pair(new Example(2.0), End()))


  behavior of "LinkedList"

  it should "return the length of the list regardless of its type" in {
    intLinkedList.length == 3
    stringLinkedList.length == 4
    exampleLinkedList.length == 2
  }

  it should "return true when contains is invoked on a list containing elem" in {
    intLinkedList.contains(2) == true
    intLinkedList.contains(0) == false
    stringLinkedList.contains("1") == true
    stringLinkedList.contains("no") == false
    exampleLinkedList.contains(new Example(1.0)) == true
    exampleLinkedList.contains(new Example(3.0)) == false
  }

  it should "return the nth element when the apply method is invoked" in {
    intLinkedList(1) == Success(1)
    intLinkedList(2) == 2
    stringLinkedList(2) == "2"
    exampleLinkedList(1) == new Example(1.0)

    intLinkedList(5) == Failure("Index out of bounds")
  }
}
