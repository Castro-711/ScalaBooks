package extended.examples

import org.scalatest.{FlatSpecLike, Matchers}

// implicits conversions need to be in scope as a single identifier
// in short it needs to be import below explicitly or with the wild card
import extended.examples.Number.double2Number

import extended.examples.Number.int2Number

class CalculatorTest extends FlatSpecLike with Matchers {

  behavior of "Evaluation"

  // shouldBe should be used for Doubles
  // == checks for type equality
  it should "return Success(3.0) for (1 + (5 - 3))" in {
    Addition(1.0, Subtraction(5.0, 3.0)).eval shouldBe Success(3.0)
  }

  it should "return Success(7)for ((9 + 20) - (13 + 9))" in {
    Subtraction(Addition(9, 20), Addition(13, 9)).eval shouldBe Success(7)
  }

  it should "return a Failure when dividing by 0" in {
    Division(7, 0).eval shouldBe Failure("Attempt to divide by 0")
  }

  it should "return a Success(3.0) when dividing 9 by 3" in {
    Division(9, 3).eval shouldBe Success(3)
  }

  it should "return a Failure when looking for the square root of a negative number" in {
    SquareRoot(-2).eval shouldBe Failure("Square root of negative number")
  }

  it should "return Success(4.0) when SquareRoot(16)" in {
    SquareRoot(16).eval shouldBe Success(4.0)
  }
}
