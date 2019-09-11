package extended.examples

import org.scalatest.{FlatSpecLike, Matchers}

// implicits conversions need to be in scope as a single identifier
// in short it needs to be import below explicitly or with the wild card
import extended.examples.Number.double2Number

class CalculatorTest extends FlatSpecLike with Matchers {

  behavior of "Evaluation"

  // shouldBe should be used for Doubles
  // == checks for type equality
  it should "calculate the correct result when eval is invoked" in {
    Addition(1.0, Subtraction(5.0, 3.0)).eval shouldBe 3.0

    Number(7.13).eval shouldBe 7.13
  }

}
