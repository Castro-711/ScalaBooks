/**
  * A particular use of algebraic data types that comes up very often is defining recursive data
  *
  * This is data that is defined in terms of itself, and allows us to create data of potentially
  * unbounded size (though any concrete instance will be finite)
  *
  *
  * We can't define recursive data like
  */

final case class Broken(broken: Broken)

/**
  * as we could never actually create an instance of such a type - the recursion never ends
  *
  * To define valid recursive data we must deine a base case, which is the case that ends the recurrsion
  *
  * Here is a more useful recursive definition: an IntList is either the empty list End, or a Pair
  * Containing an Int and an IntList. We can directly translate this to code using our familiar patterns
  */

sealed trait IntList
case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

// here End is the base case. We construct the list containing 1, 2, 3 as follows
Pair(1, Pair(2, Pair(3, End)))

/**
  * This data structure is known as a singly-linked list. In this example we have four
  * links in our chain. We can write this out in a longer form to better understand the structure of the list
  *
  * We can write this out in a longer form to better understand the structure of the list
  * Below, d represents an empty list, and a, b, and c are the pairs built on top of that
  */

val d = End
val c = Pair(3, d)
val b = Pair(2, c)
val a = Pair(1, b)

// in addition to being links in a chain, these data structures all represent complete sequences of integers
// a represents the sequence 1, 2, 3
// b .. 2, 3
// c .. 3
// d .. empty sequence

/**
  * Using this implementation, we can build lists of arbitrary length by repeatedly taking an existing list and
  * prepending a new element
  *
  * We can apply the same structural recursion patterns to process a recursive algebraic data type
  * The only wrinkle is that we must make a recursive call when the data definition is recursion
  *
  * Let's add together all the elements of an IntList
  * We'll use pattern matching but as we know the same process applies to using polymorphism
  *
  * Start with the tests and method declaration
  */

def sum(list: IntList): Int = {
  list match {
    case End => 0
    case Pair(head, tail) => head + sum(tail)
  }
}

val example = Pair(1, Pair(2, Pair(3, End)))
println(sum(example))
println(sum(example.tail)) // all less head
println(sum(End))

/**
  * The recursive call will return the sum of the tail of the list, by definition. Thus the
  * correct thing to do is to add head to this result
  */


/**
  * Understanding the Base Case and Recursive Case
  *
  * Our patterns will carry us most of the way to a acorrect answer, but we still need to supply
  * the method bodies for the base and recursive cases. There is some general guidance we can use
  *
  * * for the base we should generally return the identity for the function
  *   The identity is an element that doens't change the result, e.g. 0 is the identity
  *   for addition, because a + 0 == a for any a. If we were calculating the product of elements
  *   the identity would be 1 as a * 1 == a for all a
  *
  * * for the recursive case, assume the recursion will return the correct result and work out what
  *   you need to add to get the correct answer. We saw this for sum, where we assume the recursive call
  *   will give us the correct result for the tail of the list and we then just add on the head
  */

/**
  * Recursive Algebraic Data Types Pattern
  *
  * When defining recursive algebraic data types, there must be at least two cases: one that is recursive,
  * and one that is not. Case that are not recursive are known as the base cases. In code the general skeleton is
  */

sealed trait RecursiveExample
final case class RecursiveCase(recursion: RecursiveExample) extends RecursiveExample
case object BaseCase extends RecursiveExample

/**
  * Recursive Structural Recursion Pattern
  *
  * When writing structurally recursive code on the a recursive algebraic data type
  *
  * * whenever we encounter a recursive element in the data we make a recursive call to our method; and
  * * whenever we encounter a base case in the data we return the identity for the operation performing
  */


/**
  * You may be concerned that recursive calls will consume excessive stack space
  * Scala can apply an optimisation, called tail recursion, to many recursive functions to
  * stop them consuming stack space
  *
  * A tail call is a method call where the caller immediately returns the value. So this is a tail call
  */
def method1: Int = 1

def tailCall: Int = method1

// because tailCall immediately returns the result of calling method1 while

def notATailCall: Int = method1 + 2

// because notATailCall does not immediately return - it adds a number to the result of the call

/**
  * A tail call can be optimised to not use stack space. Due to limitations in the JVM, Scala only optimises
  * tail calls where the caller calls itself. Since tail recursion is an important property to maintain, we can
  * use the @tailrec annotation to aks the compiler to check that methods we believe are tail recursion really are.
  * here we have two versions of sum annotated. One is tail recursive and one is not. You can see the compiler
  * complains about the method that is not tail recursive ( the definition above  !!!!!! )
  */
import scala.annotation.tailrec

@tailrec
def sum(list: IntList, total: Int = 0): Int =
  list match {
    case End => total
    case Pair(head, tail) => sum(tail, total + head)
  }

/**
  * Any non-tail recursive function can be transformed into a tail recursive version by adding an accumulator as
  * we have done with sum above. This transforms stack allocation into heap allocation, which sometimes is a win,
  * and other times is not.
  *
  * In Scala we tend not to work directly with tail recursive functions as there is a rich collections library
  * that covers the most common cases where tail recursion is used. Should you need to go beyond this, because your
  * implementing your own datatypes or are optimising code, it is useful to know about tail recursion.
  */