package extended.examples

/**
  * represent Json in an abstract data type
  * ADT within scala is essentially a case class and more
  * likely a final case class at that
  */

sealed trait Json {
  // create a method to translate Json representation into a String
  // make sure to enclose strings in "" quotes and handle arrays & objects properly
  def marshall(): String = {
    this match {
      case SeqCell(h, t)           => s"${h.marshall()}, ${t.marshall()}"
      case SeqCell(h, SeqEnd)      => s"${h.marshall()}"
      case JsonCell(h, v, t)       => s"$h:${v.marshall()}, ${t.marshall()}"
      case JsonCell(k, v, JsonEnd) => s"$k:${v.marshall()}"
      case JsonNumber(n)           => s"$n"
      case JsonString(s)           => s"'$s'"
      case JsonBoolean(b)          => s"$b"
    }
  }
}

final case class JsonNumber(num: Double) extends Json
final case class JsonString(str: String) extends Json
final case class JsonBoolean(bool: Boolean) extends Json
case object JsonNull extends Json

sealed trait JsonSequence extends Json
final case class SeqCell(h: Json, t: JsonSequence) extends JsonSequence
case object SeqEnd extends JsonSequence

sealed trait JsonObject extends Json
final case class JsonCell(k: String, v: Json, tail: JsonObject) extends JsonObject
case object JsonEnd extends JsonObject
