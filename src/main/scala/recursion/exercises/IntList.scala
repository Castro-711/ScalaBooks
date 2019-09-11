package recursion.exercises

sealed trait IntList {
  // the length method could be good here
  // so we can do some pattern matching and a recursive check
  def length: Int = {
    this match {
      case End => 0
      case Pair(h, t) => 1 + t.length
    }
  }

  def product: Int = {
    this match {
      case End => 1
      case Pair(h, t) => h * t.product
    }
  }

  def double: IntList = {
    this match {
      case End => End
      case Pair(h, t) => Pair(h * 2, t.double)
    }
  }
}

case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList


