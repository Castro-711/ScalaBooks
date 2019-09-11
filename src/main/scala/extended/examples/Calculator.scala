package extended.examples

/**
  * we will implement a simple interpreter for programs containing only numeric operations
  *
  * We start by defining some types to represent the expressions we'll be operating on.
  * In the compiler literature this is known as an abstract syntax tree
  *
  * Our representation is:
  *   * An Expression is an Addition, Subtraction, or a Number
  *   * An Addition has a left and right Expression
  *   * A Subtraction has a left and right Expression
  *   * A number has a value of type Double
  */

sealed trait Expression {
  def eval: Double = {
    this match {
      case Addition(l, r) => l.eval + r.eval
      case Subtraction(l, r) => l.eval - r.eval
      case Number(v) => v
    }
  }
}

final case class Addition(left: Expression, right: Expression) extends Expression
case object Addition {
  def apply(l: Expression, r: Expression): Addition = new Addition(l, r)
}

final case class Subtraction(left: Expression, right: Expression) extends Expression
final case class Number(value: Double) extends Expression

case object Number {
  /**
    * creating an implicit conversion for practice
    * from an Double to an Expression
    */
  implicit def double2Number(value: Double): Number = new Number(value)

  /**
    * another implicit to convert Ints to Doubles
    * more practice and easier implementation
    */
  implicit def int2Number(value: Int): Number = new Number(value.toDouble)
}

/**
  * now implementing some expressions that can fail: Division & SquareRoot
  */
final case class Division(num: Expression, den: Expression) extends Expression
final case class SquareRoot(root: Expression) extends Expression
