package sequencing.computations.functions.exercises


object Fold extends App {
  val list = Pair(1, Pair(2, Pair(3, Pair(4, Pair(5, End)))))
  println(list.length)
  println(list.sum)
  println(list.product)
  println(list.double)
}

sealed trait IntList {
  def fold(end: Int, f: (Int, Int) => Int): Int =
    this match {
      case End => end
      case Pair(h, t) => f(h, t.fold(end, f))
    }

  // a generalised version of fold
  def generalFold[A](end: A, f: (Int, A) => A): A =
    this match {
      case End => end
      case Pair(h, t) => f(h, t.generalFold(end, f))
    }

  def length: Int =
    fold(0, (_, b: Int) => 1 + b)

  def sum: Int =
    fold(0, (a:Int, b:Int) => a + b)

  def product: Int =
    fold(1, (a:Int, b:Int) => a * b)

  def double: IntList =
    generalFold(End, (a:Int, b:IntList) => Pair(a * 2, b))
}

case object End extends IntList
case class Pair(h: Int, t: IntList) extends IntList

/**
  * Is it more convenient to rewrite methods in terms of fold if they were implemented using pattern matching or
  * polymorphic? What does this tell us about the use of fold?
  *
  *  * Using fold in polymorphic implementations we have a lot of duplication; the polymorphic implementations without
  *    fold were simpler to write.
  *    The pattern matching implementations benefitted from fold as we removed the duplication in the pattern matching.
  *
  *  * In general fold makes a good interface for users outside the class, but not necessarily for use inside the class.
  */

/**
  * Why can't we write our double method in terms of fold? Is it feasible we could if we made some change to fold?
  *
  *  * We need to return an IntList with the original IntList elements doubled.
  *  * If we change the signature to return an IntList and have an accumulator method we could return the doubled IntList
  *  * So, the general structure of double is covered by fold, if we could generalise the types of fold from INt to some
  *  general type then we could write double.
  */