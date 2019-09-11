package traits.exercises

import org.scalatest.FlatSpecLike

class CalculatorTest extends FlatSpecLike {

  behavior of "Calculator"

  it should "pass the following assertions" in {
    assert(Calculator.+(Success(1), 1) == Success(2))
    assert(Calculator.-(Success(1), 1) == Success(0))
    assert(Calculator.+(Failure("Badness"), 1) == Failure("Badness"))
  }
}
