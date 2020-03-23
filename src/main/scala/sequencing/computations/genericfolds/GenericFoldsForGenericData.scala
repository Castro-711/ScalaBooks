package sequencing.computations.genericfolds

/**
  * We've seen that when we define a class with generic data, we cannot implement very
  * many methods on that class. The user supplies the generic type, and thus we must ask the user
  * to supply functions that work with that type.
  * Nonetheless, there are some common patterns for using generic data. We have already seen fold in the context
  * of our IntList. here we will explore fold in more detail, and learn the pattern for implementing fold for any ADT.
  */

object GenericFoldsForGenericData {

  /**
    * Fold Pattern
    *
    * For an algebraic datatype A, fold converts it to a generic type B. Fold is structural recursion
    * with:
    *   * one function param for each case in A;
    *   * each function takes as params the fields for its associated class;
    *   * if A is recursive, any function params that refer to a recursive field take a param of type B
    *
    * The right-hand side of pattern matching cases, or the polymorphic methods as appropriate, consists of
    * calls to the appropriate function.
    */
}


/**
  * We saw fold working with a list of integers. Let's generalise to a list of generic type. We've already seen all
  * the tools we need. First our data definition, in this instance slightly modified to use the invariant sum type.
  */
sealed trait LinkedList[A] {
  def fold[B](end: B, f: (A, B) => B): B =
    this match {
      case End() => end // () => B
      case Pair(h, t) => f(h, t.fold(end, f))
    }
}
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
final case class End[A]() extends LinkedList[A]

/**
  * Fold is just and adaption of structural recursion where we allow the user to pass in the functions we apply at
  * each case.
  * As structural recursion is the generic pattern for writing any function that transforms an ADT, fold is the
  * concrete realisation of this generic pattern.
  * That is, fold is the generic transformation or iteration method. Any function you care to write on an ADT
  * can be written in terms of fold.
  */