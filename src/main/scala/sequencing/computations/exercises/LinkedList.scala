package sequencing.computations.exercises

sealed trait LinkedList[A] {
  def apply(n: Int): Result[A] = {
    this match {
      case Pair(h, t) =>
        if (n == 0) Success[A](h)
        else t(n - 1)
      case End() => Failure[A]("Index out of bounds")
    }
  }

  def length: Int = {
    this match {
      case End() => 0
      case Pair(h, t) => 1 + t.length
    }
  }

  def contains(elem: A): Boolean = {
    this match {
      case End() => false
      case Pair(h, t) if h == elem => true
      case Pair(h, t) => t.contains(elem)
    }
  }
}

final case class End[A]() extends LinkedList[A] // case class not allowed with param list - hence empty one
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

sealed trait Result[A]
case class Success[A](result: A) extends Result[A]
case class Failure[A](reason: String) extends Result[A]

/**
  * There is not much we can do with our LinkedList type. Remember that types
  * define the available operations, and with a generic type like A there isn't a concrete type
  * to define any available operations.
  * (Generic types are made concrete when a class is instantiated, which is too late to make use
  * of the information in the definition of the class).
  *
  * However we can still do some useful things with our LinkedList!
  * Implement length, returning the length of the LinkedList
  */