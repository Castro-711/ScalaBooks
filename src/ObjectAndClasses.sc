/** Defining a class */
class Person {
  val firstName = "Peter"
  val lastName = "Parker"
  def name = firstName + " " + lastName
}

// like an object declaration, a class binds a name and is not an expression
// unlike an object name, we cannot use a class name in an expression
// a class is not a value, and there is a different namespace in which classes live

val spidey = new Person

spidey.firstName
// type of object is Person@xxxxxxxx,
// @xxxxxxxx is unique identifier for that particular object
spidey

val newSpidey = new Person
// can write a method that takes any Person as a param

object alien {
  def greet(p: Person) =
    "Greeting " + p.firstName + " " + p.lastName
}

alien.greet(spidey)
alien.greet(newSpidey)

// scala classes are all subclasses of java.lang.Object and are, for the most part,
// usable from Java as well as Scala. The default printing behaviour of Person
// comes from the toString method defined in java.lang.Object

/** Constructors */
// as it stands Person class is rather useless
// we can create as many new objects as we want but they all have the same firstName & lastName

class Superhero(first: String, last: String) {
  val firstName = first
  val lastName = last
  def name = firstName + " " + lastName
}

val spiderman = new Superhero("Peter", "Parker")
val ironman = new Superhero("Tony", "Stark")

spiderman.name
ironman.name

// we must declare a field or method to access the data form outside the object
// constructor arguments and fields are often redundant
// fortunately scala provides us a useful short-hand way of declaring both in one go
class Person1(firstName: String, lastName: String) {
  def name = firstName + " " + lastName
}

// val fields are immutable - they are initialized once after which we cannot change their values
// Scala also provides the var keyword for defining mutable fields

// Scala programmers tend to prefer to write immutability and side-effect-free code so we can
// reason about it using the substitution model

/** Default and Keyword Params */
// all scala methods and constructors support keyword params and default params values
// when we call a method or constructor we can use param names as keywords to specify
// the params in arbitrary order

class Person2(firstName: String = "Some", lastName: String = "Body") {
  def greet() = "Greetings " + firstName + " " + lastName
}

val frank = new Person2("Frank", "Wright")
val johnDoe = new Person2()

frank.greet()
johnDoe.greet()

// keyword params are robust to changes in a number and order of params
// e.g. if we add title param to the greet method, the meaning of keywordless
// method calls changes but keyworded calls remain the same
class Person3(){
  def greet(title: String = "Citizen", fName: String = "Some", lNmae: String = "Body") =
    "Greetings, " + title + " " + fName + " " + lNmae + "!"
}

val jDoe = new Person3()
jDoe.greet("Busy")
// this is now inc`orrect

// this is particularly useful when creating methods
// and constructors with large number of params

/** Scalas type Heirarchy */
// everything in scala is an object
// unlike java that separates objects & primitives

// Any is the grand super type in scala
// it has two subtypes
// AnyVal & AnyRef

// AnyVal is super type to all value types
// AnyRef is the supertype to all "reference types" or classes
// all Scala and Java classes are subtypes of AnyRef
// some of these types are simply scala aliases of types that exist
// in Java -> Int - int, Boolean - boolean, AnyRef - java.lang.Object

// two special types at the bottom
// Nothing is the type of throw expressions
// Null is the type of the value null
// these are sub types of everything else,
// which helps us assign types to throw & null
// while keeping other types in our code sane.

def badness = throw new Exception("ERROR")

def otherbadness = null

val bar = if(true) 123 else badness

val baz = if(false) "it worked" else otherbadness
// although the types of badness and res are Nothing and Null respectively
// the types of bar and baz are still sensible. This is because Int is the least
// common supertype of Int and Nothing, and String is the least common
// sup;ertype of String and Null

// I included vals to make the params fields but not required
class Cat(val name: String, val colour: String, val food: String) {
  def meow() = "Meow, I am " + name + " I am a " + colour + " cat and I like " + food
}

val oswald = new Cat("Oswald", "Black", "Milk")
val hendo = new Cat("Henderson", "Ginger", "Chips")
val quentin = new Cat("Quentin", "Tabby & white", "Curry")

oswald.meow()
hendo.meow()
quentin.meow()

class ChipShop {
  def willServe(c: Cat): Boolean =
    if (c.food == "Chips") true
    else false
}

val chippy = new ChipShop

chippy.willServe(oswald)
chippy.willServe(hendo)
chippy.willServe(quentin)

// original without case classes
/**
  * class Director(val firstName: String, val lastName: String, val yearOfBirth: Int) {
  * def name(): String =
  * firstName + " " + lastName
  *
  * def copy(fName: String = this.firstName,
  * lName: String = this.lastName,
  * yob: Int = this.yearOfBirth): Director =
  * new Director(fName, lName, yob)
  *
  * override def toString: String =
  * s"Director($firstName, $lastName, $yearOfBirth)"
  * }
  *
  * class Film(val name: String, val yearOfRelease: Int, val imdbRating: Double, val director: Director) {
  * def directorsAge: Int = yearOfRelease - director.yearOfBirth
  * def isDirectedBy(d: Director): Boolean =
  * if (d == director) true else false
  *
  * def copy(name:String = this.name,
  * yearOfRelease: Int = this.yearOfRelease,
  * imdbRating: Double = this.imdbRating,
  * director: Director = this.director): Film =
  * new Film(name, yearOfRelease, imdbRating, director)
  *
  * override def toString: String =
  * s"Film(($name, $yearOfRelease, $imdbRating, $director)"
  * }
  *
  * @param firstName
  * @param lastName
  * @param yearOfBirth
  */

case class Director(firstName: String, lastName: String, yearOfBirth: Int) {
  def name(): String =
    firstName + " " + lastName
}

case class Film(name: String, yearOfRelease: Int, imdbRating: Double, director: Director) {
  def directorsAge: Int =
    yearOfRelease - director.yearOfBirth
  def isDirectedBy(d: Director): Boolean =
    if (d == director) true else false
}

val eastwood          = new Director("Clint", "Eastwood", 1930)
val mcTiernan         = new Director("John", "McTiernan", 1951)
val nolan             = new Director("Christopher", "Nolan", 1970)
val someBody          = new Director("Just", "Some Body", 1990)

val memento           = new Film("Memento", 2000, 8.5, nolan)
val darkKnight        = new Film("Dark Knight", 2008, 9.0, nolan)
val inception         = new Film("Inception", 2010, 8.8, nolan)

val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7, eastwood)
val outlawJoseyWales  = new Film("The Outlaw Josey Wales", 1976, 7.9, eastwood)
val unforgiven        = new Film("Unforgiven", 1992, 8.3, eastwood)
val granTorino        = new Film("Gran Torino", 2008, 8.2, eastwood)
val invictus          = new Film("Invictus", 2009, 7.4, eastwood)

val predator          = new Film("Predator", 1987, 7.9, mcTiernan)
val dieHard           = new Film("Die Hard", 1988, 8.3, mcTiernan)
val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6, mcTiernan)
val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8, mcTiernan)

eastwood.yearOfBirth

dieHard.director.name

invictus.isDirectedBy(nolan)
invictus.isDirectedBy((eastwood))

highPlainsDrifter.copy(name = "L'homme des hautes plaines")

thomasCrownAffair.copy(yearOfRelease = 1968, director = new Director("Norman", "Jewison", 1926))

inception.copy().copy().copy()

case class Counter(n: Int = 0) {
  def inc = copy(n = n + 1)
  def dec = copy(n = n - 1)

  override def toString: String = s"$n"
}

val twelve = new Counter(10).inc.dec.inc.inc

class Adder(amount: Int) {
  def add(in: Int) = in + amount

  override def toString: String = s"$amount"
}

class FastCounter(n: Int) {
  def inc(x: Int = 1): FastCounter = new FastCounter(n + x)
  def dec(x: Int = 1): FastCounter = new FastCounter(n - x)

  def adjust(adder: Adder): FastCounter = new FastCounter(adder.add(n))

  override def toString: String = s"$n"
}

val fifty = new FastCounter(12).inc(40).dec(5).inc(2).inc()

val adr = new Adder(30)
val count = new FastCounter(7)

count.adjust(adr)

// this is an interesting pattern that will become more powerful as we learn more features of Scala
// We are using Adders to capture computations and pass them to Counter. Remember from our earlier discussion that methods
// are not expressions - they cannot be stored in fields or passed around as data.
// However, Adders are both objects and computations

// Using objects as computations is a common paradigm in object oriented programming languages.
// the disadvantage of this is that they are limited to use in one circumstance. Scala includes much more general concept
// called functions that allow us to represent any kind of computation as an object

/** Objects as Functions */
// in the above discussion we described an Adder as an object representing a computation - a bit like having a method
// that we can pass around as a value

// This is such a powerful concept that Scala has a fully blown set of language features for creating objects that
// behave like computations
// These objects are called functions and are the basis of functional programming

/** The apply method */
// for now we are going to look at just one of Scala features supporting functional programming -
// function application syntax

// In Scala by convention, an object can be "called" like a function if it has a method called apply.
// Naming a method apply affords us a special shortened call syntax:
// foo.apply(args) becomes
// foo(args)

// e.g. let's rename the add method in Adder to apply;
class _Adder(amount: Int) {
  def apply(in: Int) = in + amount

  override def toString: String = s"$amount"
}

val add3 = new _Adder(3)

add3.apply(2)

add3(4) // shorthand for add3.apply(4)

// with this one simple trick objects can "look" syntactically like functions. There are lots of things that we
// can do with objects that we can't do with methods, including assign them to variables and pass them around
// as arguments

// Functional Application Syntax
// object.apply(param, ...) can also be written as object(param, ...)
// it lets us call an object as if it is a function
// it is available for any object defining an apply method

// with function application syntax, we now have first class values that behave like computations. Unlike methods,
// objects can be passed around as data. This takes us one step closer towards true functional programming in scala

// how close does functional application syntax get us to creating truly reusable objects to do computations for us?
// what are we missing?

// The main thing we are missing is types, which are the way we properly abstract across values

// At the moment we can define a class called adder to capture the idea of adding to a number, but that code isn't properly portable
// across code bases - other developers need to know about our specific call to use

// We could define a library of common function types with names like Handler, Callback, Adder, BinaryAdder, and so on,
// but this quickly becomes impractical

// Later on we will se how Scala copes with this problem by defining a generic set of function types that we can
// use in a wide variety of situations

/** Companion Objects */
// sometimes we want to create a method that logically belongs to a class but is independent of any particular object
// in Java, we would use a static method for this, but Scala has a simpler solution that we've seen already
// Singleton Objects

// One common use case is auxiliary constructors.
// Although Scala does have syntax that lets us define multiple constructors for a class, Scala programmers almost always
// prefer to implement additional constructors as apply methods on an object with the same name as the class
// we refer to the object as the companion object of the class

class Timestamp(val seconds: Long)

object Timestamp {
  def apply(hours: Int, minutes: Int, seconds: Int): Timestamp =
    new Timestamp(hours*60*60 + minutes*60 + seconds)
}

Timestamp(1, 1, 1).seconds

// use :paste when creating a companion object in the REPL
// as companion objects must be defined in the same compilation unit

// as we say earlier, Scala has two namespaces: a space for type names AnyRef, and a space for value names AnyVal
// This separation allows us to name our class and companion object the same thing without conflict

// it is important to note a companion object is not an instance of the class - it is a single object with its own type

Timestamp
// Timestamp.type not Timestamp

// Companion objects provide us with a means to associate functionality with a class without associating it with any
// instance of that class.
// They are commonly used to provide additional constructors

// they replace Java's static methods - provide equivalent functionality and are more flexible

// it shares its name with its associated class. This doesn't cause a naming conflict because scala has 2 namespaces
// they must be defined in the same file as the associated class


object Person1 {
  def apply(name: String): Person1 = {
    val names = name.split(" ")
    new Person1(names(0), names(1))
  }
}

val hulk = Person1("Bruce Banner")
hulk.name

object Director {
  def apply(firstName: String, lastName: String, yearOfBirth: Int): Director =
    new Director(firstName, lastName, yearOfBirth)

  def older(d1: Director, d2: Director): Director =
    if (d1.yearOfBirth > d2.yearOfBirth) d2.copy() // > is younger
    else d1.copy()
}

object Film {
  def apply(name: String, yearOfRelease: Int, imdbRating: Double, director: Director): Film =
    new Film(name, yearOfRelease, imdbRating, director)

  def highestRating(f1: Film, f2: Film): Film =
    if (f1.imdbRating > f2.imdbRating) f1.copy()
    else f2.copy()

  def oldestDirectorAtTheTime(d1: Director, d2: Director): Director =
    if (d1.yearOfBirth > d2.yearOfBirth) d1.copy()
    else d2.copy()
}

Director.older(eastwood, nolan)

Film.highestRating(inception, darkKnight)

Film.oldestDirectorAtTheTime(someBody, nolan)

// the similarity in naming of classes and companion objects tend to cause confusion for new Scala developers
// When reading a block of code it is important to know which parts refer to a class or type and which parts
// refer to a singleton object or value

// This is the inspiration for the new hit quiz, Type or Value?

/** Case Classes */
// defining a class, a companion object and a lot of sensible defaults in one go
// ideal for creating lightweight data-holding classes with the minimum of hassle

case class Human(firstName: String, lastName: String) {
  def name = firstName + " " + lastName
}

// scala automatically generates a class and a companion object
val rick = new Human("Rick", "Sanchez")
Human
// it is also prepopulated with some very useful features

/** Features of a case class */
// 1. a field for each constructor argument - no need to write val, although no harm in doing so
rick.firstName

// 2. a default toString method that prints a sensible constructor-like representation of the class

// 3. Sensible equals, and hasCode methods that operate on the field values in the object
// making it easy to use case classes with collections like Lists, Sets, and Maps.
// It also means we can compare objects on the basis of their contents rather than their reference identity **!

new Human("Morty", "Smith").equals(new Human("Morty", "Smith"))

new Human("Morty", "Smith") == new Human("Morty", "Smith")

// 4. a copy method that creates a new object with the same field values as the current one
rick.copy(lastName = "C157")
// the copy method creates and returns a new object
// it accepts optional params matching each of the constructor params - proven above

// Value and Reference Equality !!!!
// Scalas == operater is different from Javas - it delegates to equals rather than comparing values on reference identity

// Scala has an operator called eq with the same behaviours as Java's ==, However, it is rarely used in application code

new Human("Summer", "Smith") eq (new Human("Summer", "Smith"))

rick eq rick

// 5. Case classes implement two traits: java.io.Serializable and scala.Product. Neither are used directly.
// the latter provides methods for inspecting the number of fields and the name of the case class


/** Features of a case class copmanion object */
// companion object contains an apply method with the same arguments as the class constructor
// Scala devs prefer to use the apply method over the constructor for the brevity of omitting new,
// which makes constructors much easier to read inside expressions
Human("Summer", "Smith") == Human("Summer", "Smith")

// Finally, the companion object also contains code to implement an extractor pattern for use in pattern matching


/** Case objects */
// A final note, If you find yourself defining a case class with no constructor arguments you can instead define a case
// object. A case object is defined just like a regular singleton object, but has a more meaningful toString method and
// extends the Product and Serializable traits:
case object Citizen {
  def firstName = "John"
  def lastName  = "Doe"
  def name = firstName + " " + lastName
}

Citizen.toString

// Case classes are the bread and butter of Scala data types, use them, learn them, love them
// they have numerous auto-gen methods and features that save typing. We can override this behaviour one a
// piece-by-piece basis by implementing the relevant methods ourselves
// scala 2.10 or earlier can define case class containing 0 to 22 fields
// in 2.11 we gain the ability to define arbitrarily-sized case classes\

// if a class has a companion object that already exists but then we make the class a case class
// the code generator still works as expected - this is why we need to place the class and companion in a single
// compilation unit

// this means with end up with a companion with an overloaded apply method with two possible type signatures.

case class Kitty(colour: String, food: String)


/** Pattern Matching */
// Pattern matching is like an extedned if expression that allows us to evaluate an expression depending on
// the shape of the data

case class ConsciousEntity(first: String, last: String) {
  def name = first + " " + last
}

object Stormtrooper {
  def inspect(p: ConsciousEntity): String =
    p match {
      case ConsciousEntity("Luke", "Skywalker") => "Stop, rebel scum !!"
      case ConsciousEntity("Han", "Solo") => "Stop, that rebel scum !!!"
      case ConsciousEntity(first, _) => s"Move along, $first"
    }
}

Stormtrooper.inspect(ConsciousEntity("Rick", "Sanchez"))
Stormtrooper.inspect(ConsciousEntity("Han", "Solo"))

// expr0 match {
//  case pattern1 => expr1
//  case pattern2 => expr2
// }

// expr0 - evaluates to the value we match
// the patterns, guards, pattern1, pattern2, and so on are checked against this value in order
// the right-hand side expression (expr1, expr2, and so) of the first pattern that matches is evaluated

// Pattern matching is itself an expression and thus evaluates to a value - the value of the matched expression

/** Pattern Syntax */
// Scala has an expressive syntax for writing patterns or guards. For case classes the pattern syntax matches the
// constructor syntax
ConsciousEntity("Morty", "Smith")

// a pattern to match against the Person type is written
// ConsciousEntity(pat0, pat1)
// where pat0 and pat1 are patterns to match against the firstName & lastName respectively

// there are four possible patterns we could use in place of pat0 or pat1:

// 1. A name, which matches any value at that position and binds it to the given name
// e.g. Person(first, last) binds the name first to the value "Morty" and the last to the value "Smith"

// 2. an underscore (_), which matches any value and ignores it.
// e.g., as Stormtroopers only care about the first name we could just write ConsciousEntity(first, _) to
// avoid binding a name to the value of the lastName

// 3. A literal, which successfully matches only the value the literal represents.
// e.g. the pattern ConsciousEntity("Han", "Solo") matches the CE with the name "Han" and the last name "Solo"

// 4. Another case class using the same constructor style syntax

// Note there is alot more we can do with pattern matching, and pattern matching is actually extensible.


// Case classes allow us to pattern match
// take apart a case class, and evaluate different expressions depending on what the case class contains

val oswaldo = Kitty("Yellow", "Dogs")
val henderson = Kitty("Blue and Pink", "Chips")
val quentino = Kitty("Silver", "Priests")

object ChippyShop {
  def willServe(k: Kitty): Boolean = {
    k match {
      case Kitty(_, "Chips") => true
      case Kitty(_, _) => false
    }
  }
}

ChippyShop.willServe(oswaldo)
ChippyShop.willServe(henderson)
ChippyShop.willServe(quentino)


object Dad {
  def rate(f: Film): Double = {
    f.director match {
      case Director(_, "Eastwood", _) => 10.0
      case Director(_, "McTiernan", _)=> 7.0
      case Director(_, _, _) => 3.0
    }
  }
}

Dad.rate(darkKnight)
Dad.rate(dieHard)
Dad.rate(unforgiven)

// we have seen that classes allow us to abstract over objects
// That is, to define objects that share properties in common and have a common type

// Companion objects, which are used in Scala to define auxillary constructors and other utility methods
// that don't belong on a class

// case classes greatly reduce boilerplate code and allow pattern-matching, a new way of interacting with objects,
// in addition to method calls