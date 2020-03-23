package sequencing.computations.genericfolds


/**
  * Scala contains another feature that is directly relevant to this section - ability to convert method calls to
  * functions. This is closely related to placeholder syntax - simply follow a method with an underscore.
  */
object ConvertingMethodsToFunctions extends App {

  object Sum {
    def sum(x: Int, y: Int) = x + y
  }

  val x = Sum.sum _ // (Int, Int) => Int
  /**
    * In situations where Scala can infer that we need a function, we can even drop the underscore and simply write
    * the method name - the compiler will promote the method to a function automatically
    */

  object MathStuff {
    def add1(num: Int) = num + 1
  }

  //  Counter(2).adjust(MathStuff.add1)

  /**
    * Multiple Parameter Lists
    *
    * Methods in Scala can actually have multiple parameter lists. Such methods work just like normal methods, except
    * we must bracket each parameter list separately
    */

  def example(x: Int)(y: Int) = x + y
  println(example(1)(2))

  /**
    * Multiple parameter lists have two relevant uses: they look nicer when defining functions inline and they assist
    * with the type inference.
    * The former is the ability to write functions that look like code blocks. For example, if we define fold as
    *
    *   def fold[B](end: B)(pair: (A, B) => B): B =
    *     this match {
    *       case End() => end
    *       case Pair(h, t) => pair(h, t.fold(end, pair)
    *     }
    *
    * Then we can call it as
    *
    *   fold(0){ (total, elt) => total + elt }
    *
    * which is a little easier to read than
    *
    *   fold(0, (total, elt) => total + elt)
    *
    *
    * More important is the use of multiple parameter lists to ease type inference. Scala type inference algorithm
    * cannot use a type inferred for one parameter for another parameter in the same list.
    * e.g. given fold with a signature like
    *
    *   def fold[B](end: B, pair: (A, B) => B): B
    *
    * If Scala infers B for end it cannot the use this inferred type for the B in pair, so we must often write a type
    * declaration on pair. However, Scala can use types inferred for one parameter list in another parameter list. So if
    * we write fold as
    *
    *   def fold[B](end: B)(pair: (A, B) => B): B
    *
    * Then inferring B for end (which is usually easy) allows B to be used when inferring the type pair. This means
    * fewer type declarations and a smoother development process.
    */
}
