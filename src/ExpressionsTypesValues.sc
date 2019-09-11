"Hello, World!"
// is a literal expression
// literals are so named because they literally look like what they evaluate to

"Hello, World!".toUpperCase()
// evaluation in scala proceeds left to right
// first the literal is evaluated and then the method is called on the result
// returns a new string, consisting of the original value transformed to its uppercase equivalent

/** 2.1.1 Compile-time & Run-time*/
// compilation is a process of checking that a program makes sense
// there are two wasy in which a program must "make sense"

// 1. must be syntactically correct - "on cat mat sat the"
// toUpperCase."Hello, World!"

// 2. it must type check - "the mat sat on the cat
// e.g. 2.toUpperCase

/** 2.1.2 Expressions, Types */
// Expressions exist at compile-time
// it evaluates to a value - a value is info stored in memory
// it exists at run-time
// e.g. 2 evaluates to a particular sequence of bits in a particular location in memory
// we compute with values
// they are entities our programs can pass around and manipulate
2.min(3)

// in scala all values are objects
// most important point at this stage - expressions have types but values do not.!!!

// we can show that types exist at compile-time by asking the Scala console to tell us
// they type of an expression that causes a run-time error
// :type 2 / 0

// 2 / 0
// we can see that 2 / 0 is Int even though the expression fails when we evaluate it
// Types, which exist at compile-time restrict us to writing programs that give a consistent
// interpretation to values. When a program type checks, Scala guarantees that all values
// are used consistently and thus it does not need to record type info in a values representation.
// This process of removing type info is called 'type erasure'

// nulls are considered bad practice in Scala code.
// Scala uses options to reduce the need for nulls and improve the safety of the program


()
// Unit
// it is Scala's equivalent of Java's void
// result of expressions that evaluate to no interesting value, such as printing to standard output

// every literal expression has a type and evaluates to a value - something which is also true for more complex
// scala expressions

"Hello, World!" // String literal
println("Hello, World!") // Unit -> side effect of printing "Hello, World!" to the console
// the former can be used in a larger expression but the latter cannot


/** Object Literals */
// When we write an object literal we use a declaration, whihc is a different kind of program to an expression.

object Test {}
// this is not an expression - it does not evaluate to a value.
// It binds a name (Test) to a value (an empty object)

// Once we have bound the name Test we can use it in expressions, where it evaluates to the object we have declared.
// The simplest expression is just the name on its own, which evaluates to the values itself
Test
// this expression is equivalent to writing a literal like 123 or "abc". Note that the type of the object is reported
// as Test.type
// this is a new type, created just for our object, called a singleton type. We cannot create other values of this type
// within the body we can put expressions. It is more common, however to put declarations such as
// declaring methods, fields or even more objects

object Test2 {
  def name: String = "Probably the best object ever....!!!"
}

Test2.name

object Test3 {
  def hello(name: String) =
    "Hello " + name
}

Test3.hello("hello hello")
Test3.hello("Scala...!")

// syntax for declaring a method is
// def name(parameter: type, ...): resultType =
//    bodyExpression

// or

// def name: resultType =
//    bodyExpression

// name -> name of the method
// params -> given params to the method / argument can be used interchangeably
// type -> types of the params
// resultType -> type of the result of the method
// bodyExpression -> expression that calling the method evaluates to

// return keyword is implicit - there is no need to write return like you would in Java

/** Fields */
// An object can contain other objects, called fields
// val -> for immutable fields
// var -> for mutable fields

object Test4 {
  val name = "Noel"
  def hello(other: String): String =
    name + " says hi to " + other
}

Test4.hello("Dave the Rave")

// scala programmers prefer to use immutable fields wherever possible, as this maintains substitution

/** Expression Evaluation */
// expression evaluation works by rewriting the original expression. Rewriting works by performing simple steps called
// reductions, e.g.
object Test5 {
  def x = 2
  def y = 5
}


(2 * Test5.x) + (4 * Test5.y)
// 4 + (4 * 5)
// 4 + 20
// 24

/** Function Evaluation */
// functions with params get evaluated similar to the operators in expressions.
// rules for function params evaluation

// 1. Evaluates all the function arguments from left to right
// 2. Replace function application by the functions right-hand side, and, at the same time
// 3. Replace all the formal params of the function by actual arguments
object Test6 {
  def square(x: Double) = x * x
}
// defs need to be within an object our class to avoid an error
// Internal error: org/jetbrains/plugins/scala/worksheet/MacroPrinter$$anonfun$1
Test6.square(3 + 3)
// square(6)
// 6 * 6
// 36

/** Function Evaluation Strategy */
// Two evaluation strategies
// 1. call-by-value
// 2. call-by-name

// for expressions that use only pure function and can be reduced with substitution model, both yields same final result
// Let's define the function and evaluate it with both
// call-by-value
object Test7 {
  def sumOfSquares(x: Int, y: Int) = square(x) + square(y)
  def square(x: Double) = x * x
}
// call-by-value evaluates every function argument only once thus it avoids repeated evaluation of arguments
// e.g.
Test7.sumOfSquares(2, 5)
// 2 * 2 + 5 * 5
// 4 + 25
// 29

// call-by-name
// call-by-name avoids evaluation of params if it is not used in the function body
Test7.sumOfSquares(2, 2 + 3)
// square(2) + square(2 + 3)
// square(2) + square(2 + 3)
// 2 * 2 + square(2 + 3)
// 4 + (2 + 3) * (2 + 3)
// 4 + 5 * (2 + 3)
// 4 + 5 * 5
// 4 + 25
// 29

/** Difference between call-by-value & call-by-name */
// call-by-value is more efficient
// if call-by-value (CBV) terminates then call-by-name (CBN) also terminates
// but the other direction is not ture since call-by-value might loop infinitely but call-by-name would terminate
object Test8 {
  def loop: Int = loop
  def test(x: Int, y: => Int) = x
}

Test8.test(1, Test8.loop)
// call-by-value
// reduces to 1

// call-by-name
// leads to infinite loop
// test(1, loop)

// Scala uses call-by-value by default since it is often exponentially more efficient then call-by-name.
// we can force it to use call-by-name by preceding param types with =>
// Test8.test(Test8.loop, 2) // infinite loop
Test8.test(2, Test8.loop) // 2

/** Methods vs Fields */
// a field gives a name to a value, whereas a method gives a name to a computation that produces a value
object Test9 {
  val simpleField = {
    println("Evaluating simpleField")
    42
  } // block expression
  def noParameterMethod = {
    println("Evaluating noParameterMethod")
    42
  }
}

// notice how before now - console says we have defined an object but it hasn't run either of our println
// due to a quirk in Scala and Java called LAZY LOADING

// Objects and classes aren't loaded untle they are referenced by other code
// this is what prevents Scala loading the entire standard library into memory to run a simple "Hello World!"

// lets force Scala to evaluate our object body by referencing Test9
Test9
// when the object is first loaded, Scala runs through the definitions and calculates the values of each of its fields
// This results in the code printing "Evaluating simpleField" as a side-effect

// The body expression of a field is run only once after which the final value is stored in the object
// The expression is never evaluated again - notice the lack of println
Test9.simpleField

Test9.simpleField

// the body of a method, on the other hand is evaluated every time we call the method - notice the
// repeated println out below
Test9.noParameterMethod

Test9.noParameterMethod

// objects, methods and fields are declarations, binding names to values...!
// Declarations are different to expressions - they do not evaluate to a values & do not have a type

// we have also seen the difference between methods and fields - fields refer to values stored within an object
// whereas methods refer to computations that produce values

// Exercises
object Oswald {
  val colour = "Black"
  val food = "Milk"
}

object Henderson {
  val colour = "Ginger"
  val food = "Chips"
}

object Quentin {
  val colour = "Tabby and white"
  val food = "Curry"
}

// square dance
object calc {
  def square(x: Double) = x * x
  def cube(x: Double) = x * square(x)
}

// precise square dance
object calc2 {
  def square(x: Double) = x * x
  def cube(x: Double) = x * square(x)

  def square(x: Int) = x * x
  def cube(x: Int) = x * square(x)
}
// makes use of overloaded methods
// whenever we call overloaded method types, scala automatically determines which variant we need by
// looking at the type of the argument
calc2.cube(3.0)

calc2.cube(3)

// the Scala compiler is able to insert automatic conversions between numeric types wherever you have
// a lower precision and require a higher precision
// e.g. if you write calc.square(2)
// the compiler determines the 'Int' version of 'square'

/** Greetings Human */
object Person {
  val firstName = "Eric"
  val lastName = "Castro"
}

object alien {
  def greet(x: Person.type) = "Greetings Earthling " + x.firstName + " " + x.lastName
}

alien.greet(Person)
// x is of Person.type -> which prevents us from using greet on any other object - this if very
// different from a type such as Int that is shared by all Scala integers

// this imposes a significant limitation on our ability to write programs in Scala
// we can only write methods that work with built-in types or single objects of our own creation.
// Step up to the plate -> classes

// calls to methods are expressions but methods themselves are not expression.
// in addition to methods, Scala also has a concpet call functions, which are objects that can
// be invoked like methods.

/** Writing Methods */
// follow these steps when designing methods in Scala

// 1. Identify the input & output

// 2. Prepare test cases
// prepare some test cases that illustrate the expected behaviour of the method
// use assert function if as poor mans testing library
val test = assert(calc.square(2.0) == 4.0)
println(test)

// 3. Write the Declaration
// with types & test cases ready we can write the method declaration
// we haven't developed the body yet so us ???
//def square(in: Double): Double = ???

// 4. Run the code
// check it compiles and that our tests fail

// 5. Write the body
// 5.1 Consider result type and how we can create an instance of it
// 5.2 Consider the input type and methods we can call to transform it to the result type

// 6. Run the code again
// check that all tests pass

/** Compound Expression */
// 1. Conditionals
// allows us to choose an expression to evaluate based on some condition
// e.g. choose a string based on which of the two numbers is the smallest
if(1 < 2) "Yes" else "No"

// conditionals are expressions - it has a type and returns a value
// condition is an expression with a boolean type

// 2. Blocks
// Blocks are expressions that allow us to sequence computations together.
// they are written as a pair of braces containing sub-expressions separated by
// semicolons or newlines

{ 1; 2; 3 }
// a block is a sequence of expressions or declarations surround by braces.
// a block is also an expression; it executes each of its sub-expressions in order and returns the value from the
// last expression

// One reason to use a block is to used code that produces side-effects before calculating a final value
{
  println("This is a side-effect")
  println("This is a side-effect as well")
  3
}

// we can also use a block when we want to name intermediate results
object Pro {
  def name: String = {
    val title = "Professor" // intermediate result
    val name = "Funkenstein" // intermediate result
    title + " " + name
  }
}

Pro.name

{
  println("syntax of block expression") // declaration or expression - optional
  "I am Mysterio" // expression - determining type and value of block
}

if(false) "hello"
// conditionals without else expressions only evaluate to a value half the time
// scala works around this by returning Unit if the else branch should be evaluated
// we usually only use these expressions for their side-effects

/** Conclusions */
// expressions - which evaluate to values
// declarations - which give names to values

