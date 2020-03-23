package sequencing.computations.functions

/**
  * Scala gives us a function literal syntax specifically for creating new functions
  *
  * Function literal syntax
  *
  * (param: type, ...) => expression
  * param => parameter names
  * type  => parameter types
  * expr  => determines the result of the function
  */

object FunctionLiterals extends App {
 // function literal examples
  val sayHi = () => "Hi!"
  println(sayHi())
  println(sayHi) // remember to use the parentheses in order to get the function value & not the object

  val add1 = (x: Int) => x + 1
  println(add1(10))

  val sum = (x: Int, y: Int) => x + y
  println(sum(7, 13))

  /**
    * In code where we sometimes know the argument types, we can sometimes drop the type
    * annotations and allow Scala to infer them. There is no syntax for declaring the result type of a function
    * and it is normally inferred, but if we find ourselves needing to do this we can put a type on the functions body
    */

  val add2 = (x: Int) => (x + 2): Int
  println(add2(2))
}
