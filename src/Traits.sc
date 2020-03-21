import java.util.Date

import traits.exercises

sealed trait Visitor {
  def id: String
  def createdAt: Date

  def age: Long = new Date().getTime - createdAt.getTime
}


final case class Anonymous(id: String, createdAt: Date = new Date()) extends Visitor

final case class User(id: String, email: String, createdAt: Date = new Date()) extends Visitor

/** Traits versus Classes */
// A trait cannot have a constructor - instead use a trait to create a class
// then create objects from that class

// Traits can define abstract methods that have names and type signatures but no implementation.
// we saw this in the Visitor trait
// id
// createdAt

// Visitor is used as a building block for two classes: Anonymous & User
// Each class extends Visitor - meaning it inherits all of its fields and methods

val anon = Anonymous("anon1")

anon.createdAt
anon.age

// id & createdAt are abstract so they must be defined in extending classes
// our classes implement them as vals rather than defs. This is legal in Scala,
// which sees def as a more general version of val

// it is good practice to never define vals in a trait, but rather to use def.!!!
// A concrete implementation can then implement it using a def or val as appropriate


// Traits are a way of abstracting over classes that have similar properties, just like classes are a way of
// abstracting over objects
trait Feline {
  def colour: String
  def sound: String
}

case class Cat(colour: String, food: String) extends Feline {
  val sound = "meow"
}

//case class Lion(colour: String, maneSize: Int) extends Feline {
//  val sound = "roar"
//}
//
//case class Tiger(colour: String) extends Feline {
//  val sound = "roar"
//}
//
//case class Panther(colour: String) extends Feline {
//  val sound = "roar"
//}

// sound is not defined as a constructor argument - since it is a constant, it doesn't make sense to give users
// a chance to modify it.
// There is a lot of duplication in the definition of sound. We could define a default value in Feline
// this is generally bad practice - if we define a default implementation it should be an implementation that
// is suitable for all subtypes

// Another alternative to define an intermediate type, perhaps called BigCat that defines sound as "roar"
// this is a better solution
trait BigCat extends Feline {
  override val sound = "roar"
}

case class Lion(colour: String, maneSize: Int) extends BigCat

case class Tiger(colour: String) extends BigCat

case class Panther(colour: String) extends BigCat

// The colour and the shape
sealed trait Colour {
  def r: Double
  def g: Double
  def b: Double

  def lightOrDark: Double = {
    0.299 * (r * r) +
    0.587 * (g * g) +
    0.114 * (b * b)
  }

  def isDark: Boolean = {
    if(lightOrDark < 127.5) true
    else false
  }
}

case object Yellow extends Colour {
  val r = 0.0
  val g = 1.0
  val b = 1.0
}

case object Red extends Colour {
  val r = 1.0
  val g = 0.0
  val b = 0.0
}

case object Pink extends Colour {
  val r = 1.0
  val g = 0.75
  val b = 0.8
}

final case class CustomColour(r: Double, g: Double, b: Double) extends Colour


/** Shaping up with Traits */
sealed trait Shape {
  def sides: Int
  def perimeter: Double
  def area: Double
  def colour: Colour
}

// ensure your trait is sealed so the compiler can check the exhaustiveness of any code
// you write that handles objects of type Rectangular or Shape
sealed trait Rectangular extends Shape {
  def length: Double
  def breadth: Double
  val sides = 4
  override val perimeter = 2 * length + 2 * breadth
  override val area = length * breadth
}

final case class Circle(radius: Double, col: Colour) extends Shape {
  val sides = 1
  val perimeter = 2 * math.Pi * radius
  val area = math.Pi * (radius * radius)
  val colour = col
}

final case class Square(edgeLength: Double, col: Colour) extends Rectangular {
  val length: Double = edgeLength
  val breadth: Double = edgeLength
  val colour = col
}

final case class Rectangle(length: Double, breadth: Double, col: Colour) extends Rectangular {
  val colour = col
}


/** This or that and Nothing Else: Sealed Traits */
// In many cases we can enumerate all the possible classes that can extend a trait

// e.g. we previously modelled a website visitor as Anonymous or a logged in User
// these two cases cover all the possibilities as one is the negation of the other.
// We can model this case with a sealed trait, which allows the compiler to provide extra checks for us.

// when we mark a trait as sealed we must define all of its subtypes in the same file.
// Once the trait is sealed, the compiler knows the complete set of subtypes and will warn us if a pattern matching expression
// is missing a case

def missingCase(v: Visitor) =
  v match {
    case User(_, _, _) => "Got a user"
  }

// compiler will let us know that the pattern matching expression is missing an Anonymous type
// sealed if we want to prevent extensions outside the file
// final if we want to disallow all extensions

// this is a very powerful pattern and one we will use frequently

object Draw {
  def apply(s: Shape): String = {
    s match {
      case Circle(r, c) => s"A $c circle of radius $r"
      case Square(e, c) => s"A $c square with an edge lenght of $e"
      case Rectangle(l, b, c) => s"A $c rectangle with length: $l and breadth: $b"
    }
  }

  def apply(c: Colour): String = c match {
    case Red => "red"
    case Pink => "pink"
    case Yellow => "yellow"
    case c => if(c.isDark) "Dark" else "Light"
  }
}

Draw(Circle(9.5, Red))
Draw(Square(5, Pink))
Draw(Rectangle(3, 4, Yellow))
Draw(Square(9, CustomColour(1.0, 0.5, 0.3)))

sealed trait DivisionResult

final case class Finite(n: Int) extends DivisionResult

case object Infinite extends DivisionResult

object Divide {
  def apply(numerator: Int, denominator: Int): DivisionResult = {
    if(denominator == 0) Infinite
    else Finite(numerator / denominator)
  }
}

Divide(1, 0)
Divide(1, 1)
Divide(2, 1)


/** Modelling Data with Traits */
// We are going to shift our focus from language features to programming patterns
// in Scala any data model defined in terms of logical ors and ands

// we will express is-a and has-a relationships.
// In the terminology of functional programming we are learning about sum and product types, which are together
// called algebraic data types

// our goal is to see how to translate a data model into Scala code

/** The product Type Pattern */
// our first pattern is to model data that contains other data. We might describe this as "A has a B and C"
// e.g. a Cat has a colour and a favourite food
// a Visitor has an id and a creation date

// formalizing the Product Type Pattern
case class B()
case class C()
case class A(b: B, c: C)

// or

trait _A {
  def b: B
  def c: C
}

/** Sum Type */
// our next pattern is to model data that is two or more distinct cases
// we might describe this as "A is a B or C"
// e.g. a Feline is a Cat, Lion or Tiger
// a Visitor is an Anonymous or User

// we write this using the sealed trait / final case class pattern
sealed trait __A
final case class _B() extends __A
final case class _C() extends __A


/** Algebraic data types */
// an algebraic data type is any data that uses the above two patterns
// in FP literature, data us the "has-a and" pattern is known as a product type, or the "is-a or" pattern is a sum type


/** The Missing Patterns */
//          And           Or
// Is-a                   Sum Type
// Has-a    Product Type

// What about the missing two patterns?
// The "is-a and" pattern means that A is a B and C. This pattern is in some ways the inverse of the sum type
// and can be implemented like so

trait B_
trait C_
trait A_ extends B_ with C_

// in Scala a trait can extend as many traits as we like using the with keyword like A extends B with C
// we won't use this pattern in this course
// if we want to represent that some data conforms to a number of different interfaces we will often be better off
// using a type class, which we will explore later

// there are however several legitimate uses for this pattern:
//  * for modularity, using whats known as the cake pattern
//  * sharing implementation across several classes where it doesn't make sense to make default implementations in the
// main trait

// the "has-a or" patterns means that A has a B or C.
// There are two ways we can implement this
// we can say that A has a d of type D, where D is a B or C
// we can mechanically apply our two patterns to implement this
sealed trait D
final case class B__() extends D
final case class C__() extends D

trait A__ {
  def d: D
}

// Alternatively we could implement this as A is a D or E, and D has a B and E has a C
sealed trait _A_
final case class _D_(b: B) extends _A_
final case class _E_(c: C) extends _A_

//sealed trait TrafficLight {
//  def next: TrafficLight =
//    this match {
//      case RedLight => GreenLight
//      case GreenLight => OrangeLight
//      case OrangeLight => RedLight
//    }
//}
//
//case object RedLight extends TrafficLight
//case object OrangeLight extends TrafficLight
//case object GreenLight extends TrafficLight
// as there are no fields or methods on the three cases, and thus there is no need to create more than one instance of them


// Sum type => A is a B or a C
sealed trait Calculation
final case class Success(n: Int) extends exercises.Calculation
final case class Failure(s: String) extends exercises.Calculation

// product type => A has a B or a C
sealed trait Source
case object Well extends Source
case object Spring extends Source
case object Tap extends Source

final case class BottledWater(size: Int, source: Source, carbonated: Boolean)


/** Working with Data */
// previous section we saw how to define algebraic data types using a combo of sum (or) product type (and) patterns.
// in this section we will see a pattern for using algebraic data types, known as structural recursion
// we actually see two variants of this pattern:
// one using polymorphism
// & the other using pattern matching

// Structural recursion is the precise opposite of the process of building an algebraic data type.
// If A has a B and C (product type) , to construct an A we must have a B and a C

// The sum and product type patterns tell us how to combine data to make bigger data
// Structural recursion says that if we have an A as defined before, we must break it into its constituent B and C that
// we then combine in some way to get closer to our desired answer.

// Structural recursion is essentially the process of breaking down data into smaller pieces !!!

// Like algebraic data types we have two patterns for decoupling them using structural recursion.
// We will actually have two variants for each pattern, one using polymorphism -> typically OOP
// and the other using pattern matching -> typically FP

/** Structural Recursion using Polymorphism */
// Polymorphic dispatch or Polymorphism for short, is a fundamental object-oriented technique
// if we define a method in a trait, and have a different implementations in classes extending that trait,
// when we call that methods the implementation on the actual concrete instance will be used.
// Here is an example - start with a simple definition using familiar sum type (OR) pattern

sealed trait A_PolymorphicDispatch {
  def foo: String
}

final case class B_PD() extends A_PolymorphicDispatch {
  def foo: String = "It's B!!!"
}

final case class C_PD() extends A_PolymorphicDispatch {
  def foo: String = "It's C!!!"
}

val anA: A_PolymorphicDispatch = B_PD()

anA.foo

val aSecondA: A_PolymorphicDispatch = C_PD()

aSecondA.foo

// we can define an implementation in a trait,
// and change the implementation in an extending class using the override keyword

sealed trait Z {
  def foo: String = "It's Z!!!"
}

final case class Y() extends Z {
  override def foo: String = "It's Y!!!"
}

final case class X() extends Z {
  override def foo: String = "It's X!!!"
}

val aZ: Z = Y()

aZ.foo

// behaviour is as before, the implementation on the concrete class is selected

// Remember that if you provide a default implementation in a trait, you should ensure that implementation
// is valid  for all subtypes

// Now we understand how polymoprhism works, how do we use it with an algebraic data types?
// We've actually seen everything we need but let's make it explicit and see the patterns

/** Product type polymorphism Pattern */
// if A has a b(with type B) and a c(with type C), and we want to write a method f returning an F, simply write the
// method in the usual way

case class F()

case class $A(b: B, c: C) {
  def f: F = ???
}
// in the body of the method we must use b, c and any method params to construct the result of type F


/** Sum Type Polymorphism Pattern */
// if A is a B or C, and we want to write a method f returning an F, define f as an abstract method on A and provide
// concrete implementations in B and C
sealed trait A$ {
  def f: F
}

final case class B$() extends A$ {
  def f: F = ???
}

final case class C$() extends A$ {
  def f: F = ???
}


/** Structural Recursion using pattern matching */
// Structural recursion with pattern matching proceeds along the same lines as polymorphism
// we simply have a case for every subtype, and each pattern matching case must extract the fields we are
// interested in...!

/** Product Type Pattern Matching Pattern */
// if A has a b (with type B) and c (with type C), and we want to write a method f that accepts an A and returns an F,
// write
def f(a: A): F =
  a match {
    case A(b, c) => ???
  }
// in the body of the method we must use b, c, and any method params
// to construct the result of type F

/** Sum Type Pattern Matching Pattern */
// If A is a B or C, and we want to write a method f accepting an A and
// returning an F, define a pattern matching case for B and C
def f(a: A$): F =
  a match {
    case B$() => ???
    case C$() => ???
  }


/** Complete Example */
// example of the algebraic data type and structural recursion patterns, using
// our familiar Feline data type

// Start with a description of the data
// A Feline is a Lion, Tiger, Pather, or Cat.
// We will simplify the data descriptiong and just say that a Cat
// has a String favouriteFood
// From this description we can immediately apply our pattern to define the data

sealed trait _Feline {
  def dinner: Food =
    this match {
      case _Lion() => Antelope
      case _Tiger() => TigerFood
      case _Panter() => Licorice
      case _Cat(s) => CatFood(s)
    }
}
final case class _Lion() extends _Feline
final case class _Tiger() extends _Feline
final case class _Panter() extends _Feline
final case class _Cat(food: String) extends _Feline

// now lets implement a method using both polymorphism and pattern matching
// our method, dinner, will return the appropriate food for the feline in question
// For a Cat their dinner is their favouritefood.
// For Lions it is antelope
// For Tigers it is tiger food
// For Panthers it is licorice

// lets go one bit further and define our food as a type instead of a String
// which will help avioid things like spelling mistakes in our code

sealed trait Food
case object Antelope extends Food
case object TigerFood extends Food
case object Licorice extends Food
final case class CatFood(food: String) extends Food

// now we can implement dinner as a method returning Food.
// first using polymorphism

// now using pattern matching. We actually havee two choices when using pattern
// matching. We can implement our code in a single method on Feline
// or we can implement it in a method on another object. Lets see both

// second way
//object Dinner {
//  def dinner(feline: _Feline): Food =
//    feline match {
//      case _Lion => Antelope
//      case _Tiger => TigerFood
//      case _Panter => Licorice
//      case _Cat(f) => CatFood(f)
//    }
//}

// note how we can directly apply the patterns, and the code falls out.
// this is the main point we want to make with structural recursion;
// the code follows the shape of the data and can be produced in an almost mechanical way

/** Choosing which pattern to use */

// we have 3 ways of implementing structural recursion
// 1. polymorphism
// 2. pattern matching in the base trait
// 3. pattern matching in an external object (like Dinner above)

// which should we use? the first two methods give the same result; a method defined on the classes of interest
// we should use whichever is more convenient
// this normally ends up being pattern matching on the base trait as it requires less code duplication

// when we implement a method in the classes of interest we can have only one implementation of the method,
// and everything that method requires to work must be contained within the class and parameters we pass to the method
// when we implement methods using pattern matching in an exteranl object we can provide multiple implementations, one per
// object (multiple Diners in the above example)

// the general rule is: if a method only depends on other fields and methods in a class it is a good candidate
// to be implemented inside the class
// if the method depends on other data (exampled, if we needed a Cook to make Dinner) consider implementing it using
// pattern matching outside of the classes in question
// if we want ot have more than one implementation we should use pattern matching and implement it oustide the classes


/** Object-Oriented vs Functional Extensibility */
// classic functional programming we have no objects, only data withou methods and functions
// this style of programming makes extensive use of pattern matching
// we can mimic it in Scala using the algebraic datatype pattern and pattern matching in methods defined on external objects.

// classic object oriented style uses polymorphism and allow open extension of classes
// in scala terms this means no sealed traits

// what are teh tradeoffs we make in the two different styles?

// one advantage of functional style is it allows the compiler to help us more
// by sealing traites we are telling the compiler it knows all the possible subtypes of that trait.
// it can then tell us if we miss out a case in our pattern matching
// this is especially useful if we add or remove subtypes later in development

// we could argue we get the same benefit from object-oriented style, as we must implement all method defined on the base
// trait in any subtypes
// this is true , but in practice classes with a large number of methods are very difficult to maintain
// and we'll inevitably end up factoring some of the code into different classes -
// essentially duplicating the functional style

// this doesn't mean functional is to be preffered in all cases. There is a fundamental difference
// between the kind of extensibility that object-oriented styl and functional style gives us
// Wit OO style we can easily add new data, by extending a trait, but adding a new method requires us to change existing
// code.
// with Functional style we can easily add a new method but adding new data requires us to modify existing code

//        add new method                add new data
// OO     change existing code          existing code unchanged
// FP     existing code unchanged       change existing code

// in scala we have the flexibility to use both polymorphism and pattern matching, and we should use whichever
// is appropriate. However we generally prefer sealed traits as it gives us greater guarantees
// about our codes semantics, and we can use type classes, which we'll explore later, to get us OO style extensibility

// traffic lights
// pattern matching makes the structure of the state machine clearer than polymorphism
// ultimately there are no hard-and-fast rules, we must consider our design decisions in the context of the larger program

//sealed trait Calculation
//final case class Result(n: Int) extends Calculation
//final case class FailedMessage(s: String) extends Calculation


// we can no longer rely on the patterns to write the bodies of the methods
// we know that + and - are binary operations;
// we need two integers to use them
// we also need to return a Calculation
// looking at the Failure cases, we don't have two Int's available
// the only result that makes sense to return is a Failure
// on the success side we do have two Ints and thus we should return Success
case object Calculator {
  def +(calc: exercises.Calculation, operand: Int): exercises.Calculation = {
    calc match {
      case Success(result) => exercises.Success(result + operand)
      case Failure(reason) => exercises.Failure(reason)
    }
  }

  def -(calc: exercises.Calculation, operand: Int): exercises.Calculation = {
    calc match {
      case Success(result) => exercises.Success(result - operand)
      case Failure(reason) => exercises.Failure(reason)
    }
  }

  def /(calc: exercises.Calculation, denominator: Int): exercises.Calculation = {
    calc match {
      case Success(result) => exercises.Success(result / denominator)
      denominator match {
        case 0 => exercises.Failure("Division by zero")
        case _ => exercises.Success(result / denominator)
      }
      case Failure(reason) => exercises.Failure(reason)
    }
  }
}

assert(exercises.Calculator.+(exercises.Success(1), 1) == exercises.Success(2))
assert(exercises.Calculator.-(exercises.Success(1), 1) == exercises.Success(0))
assert(exercises.Calculator.+(exercises.Failure("Badness"), 1) == exercises.Failure("Badness"))
