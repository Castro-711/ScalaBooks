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
  it should "return 3 for (1 + (5 - 3))" in {
    Addition(1.0, Subtraction(5.0, 3.0)).eval shouldBe 3.0
  }

  it should "return 7 for ((9 + 20) - (13 + 9))" in {
    Subtraction(Addition(9, 20), Addition(13, 9)).eval shouldBe 7
  }
}
