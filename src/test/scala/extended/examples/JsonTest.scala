package extended.examples

import org.scalatest.{FlatSpecLike, Matchers}

class JsonTest extends FlatSpecLike with Matchers {

  behavior of "Json"

  it should "marshall the objects into the correct Json string" in {
    println(SeqCell(JsonString("a string"), SeqCell(JsonNumber(1.0), SeqCell(JsonBoolean(true), SeqEnd))).marshall())
  }
}
