package sequencing.computations.genericfolds

/**
  * There are a few tricks in Scala for working with functions and methods that accept functions(Higher Order functions).
  *
  *   1. a compact syntax for writing functions;
  *   2. converting methods to functions; and
  *   3. a way to write higher-order methods that assists type inference.
  */

object WorkingWithFunctions extends App {

  /**
    * Placeholder syntax
    *
    * In very simple situations we can write inline functions using an extreme short-hand called placeholder syntax
    */

  val doubler = (_:Int) * 2
  println(doubler(7))

  /**
    * (_: Int) * 2 is expanded by the compiler to (a: Int) => a * 2.
    * It is more idiomatic to use the placeholder syntax only in cases where the compiler can infer the types
    * Best practice to use only use this in small functions
    */
}
