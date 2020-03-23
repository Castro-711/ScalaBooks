package sequencing.computations.functions

/**
  * Functions allow us to abstract over methods, turning methods into values that we
  * can pass around and manipulate within our programs.
  */
object Functions extends App {

}

sealed trait IntList {
  /**
    * See IntList in the recursion section
    * As all of the methods have the same general pattern, structural recursion. It would be nice to
    * remove the duplication.
    *
    * Lets start by focusing on methods that return Int: length, product & sum
    */

  def length: Int =
    this match {
      case End => 0
      case Pair(h, t) => 1 + t.length
    }

  def double: IntList =
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

  /**
    * We want to create a method like
    *
    * def abstraction(end: Int, f: ???): Int =
    *   this match {
    *     case End = end
    *     case Pair(h, t) => f(h, t.abstraction(end, f))
    *   }
    *
    * f denotes some kind of object that does the combination of the head
    * & recursive call for the Pair case. A function
    *
    * A function is like a method: we can call it with parameters and it evaluates to a result.
    * Unlike a method a function is value. We can pass a function to a method or to another function.
    * We can return a function from a method and so on.
    *
    * Much earlier in this course we introduced the apply method, which lets us treat objects as functions in
    * syntactic sense.
    *
    * This is a big step towards doing real functional programming in Scala but we're missing one important component: types
    *
    * As we have seen, types allow us to abstract across values. We've seen special case functions like Adders, but
    * what we really want is a generalised set of types that allow us to represent computations of any kind.
    *
    * Enter Scala function types.
    */
}

case object End extends IntList
case class Pair(h: Int, t: IntList) extends IntList
