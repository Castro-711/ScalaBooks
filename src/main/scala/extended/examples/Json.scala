package extended.examples

/**
  * represent Json in an abstract data type
  * ADT within scala is essentially a case class and more
  * likely a final case class at that
  */

sealed trait Json {
  // create a method to translate Json representation into a String
  // make sure to enclose strings in "" quotes and handle arrays & objects properly
}

final case class JsonNumber(num: Double) extends Json
final case class JsonString(str: String) extends Json
final case class JsonBoolean(bool: Boolean) extends Json
case object JsonNull extends Json

sealed trait JsonSequence extends Json
final case class SeqCell(h: Json, t: SeqCell) extends JsonSequence
case object SeqEnd extends JsonSequence

sealed trait JsonObject extends Json
final case class JsonCell(k: String, v: Json, tail: JsonObject) extends JsonObject
case object JsonEnd extends JsonObject

