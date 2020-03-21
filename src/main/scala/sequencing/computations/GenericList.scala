package sequencing.computations

//TODO: rename packages and organise code better
//TODO: write concise learning's about each section

/**
  * In this section we are going to use
  *   1. Generics to abstract over data types
  *   2. Functions to abstract over methods
  *
  * 1. will enable our code to be more flexible and be used
  * over any number of different types, including ones not defined yet
  *
  * 2. Will enable us to reduce the amount of duplication in our code
  */

object GenericList extends App {

  // Generics allow us to abstract over types
  // they are useful for all sorts of data structures but commonly
  // encountered in collections
  println(Box(2))
  println(Box("Hi"))

  println(generic(6.7))
  println(generic[String]("foo"))
  println(generic(1))

  def generic[A](in: A): A = in
  // when we construct a class with a type parameter, the type
  // parameter is bound to the concrete type within the method or class body
  // wo when we call
  generic(1)
  // the type param A is bound to int in the body of generic

}

// the syntax [A] is called a type parameter
// we can also add type parameters to methods, which limits
// the scope of the parameter to the method declaration + body
final case class Box[A](value: A)

/**
  * Type param syntax
  *
  * We declare generic types with a list of type names within square
  * brackets like [A, B, C]. By convention we use single uppercase letters
  * for generic types
  *
  * Generic types can be declared in a class or trait declaration in which case
  * tye are visible throughout the rest of the declaration
  *
  *   case class Name[A](...){...}
  *   trait Name[A]{...}
  *
  * Alternatively the may be declared in a method declaration, in which
  * case they are only visible within the method
  *
  *   def name[A](...){...}
  */


/**
  * Generic Algebraic Data Types
  *
  * Extending a trait, as we do in a sum type, is the type level equivalent of calling
  * a method and we must supply values for any type parameters of the trait we're
  * extending.
  *
  *   sealed trait Calculation
  *   final case class Success(result: Double) extends Calculation
  *   final case class Failure(reason: String) extends Calculation
  *
  * Lets generalise this so that our result is not restricted to a Double but can
  * be some generic type. In doing so let's change the name from Calculation to Result as
  * we're not restricted to numeric calculations anymore. Now our data definition becomes
  */

sealed trait Result[A]
case class Success[A](result: A) extends Result[A]
case class Failure[A](reason: String) extends Result[A]


/**
  * Notice that both Success and Failure introduce a type parameter A which is passed to
  * Result when it is extended. Success also has a value of type A, but Failure only
  * introduces A so it can pass it onward to Result. In a later section we'll introduce
  * variance, giving us a cleaner way to implement this, but for now this is the pattern we'll use
  */

// Invariant Generic Sum Type Pattern
// If A of type T is a B or C
sealed trait A_[T]
final case class B_[T]() extends A_[T]
final case class C_[T]() extends A_[T]
// case class need parameter list -> hence the empty params list