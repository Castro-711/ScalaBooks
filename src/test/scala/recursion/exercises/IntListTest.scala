package recursion.exercises

import org.scalatest.{FlatSpecLike, Matchers}

class IntListTest extends FlatSpecLike with Matchers {

  val testList = Pair(1, Pair(2, Pair(3, Pair(4, Pair(5, End)))))

  behavior of "IntList"

  it should "the length method should return an Int matching the number of Pairs" in {
    testList.length shouldBe 5
  }

  it should "return 0 for the length of End" in {
    End.length shouldBe 0
  }

  it should "return the correct result when the product method is invoked" in {
    testList.product shouldBe 120
  }

  it should "return 1 for the product of End" in {
    End.product shouldBe 1
  }

  it should "return a new IntList with each element double its original value" in {
    testList.double == Pair(2, Pair(4, Pair(6, Pair(8, Pair(10, End)))))
  }

  it should "return End for the double of End" in {
    End.double == End
  }
}
