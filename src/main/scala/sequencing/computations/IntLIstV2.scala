package sequencing.computations

class IntListV2 {

}

sealed trait IntList2 {
  def lenght: Int =
    this match {
      case End => 0
      case Pair(h, t) => 1 + t.lenght
    }

  def double: IntList2 =
    this match {
      case End => End
      case Pair(h, t) => Pair(h * 2, t.double)
    }

  def product: Int =
    this match {
      case End => 1
      case Pair(h, t) => h * t.product
    }

  def sum: Int =
    this match {
      case End => 0
      case Pair(h, t) => h + t.sum
    }
}


case object End extends IntList2
final case class Pair(head: Int, tail: IntList2) extends IntList2
