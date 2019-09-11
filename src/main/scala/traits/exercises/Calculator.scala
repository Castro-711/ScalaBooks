package traits.exercises


case object Calculator {
  def +(calc: Calculation, operand: Int): Calculation = {
    calc match {
      case Success(result) => Success(result + operand)
      case Failure(reason) => Failure(reason)
    }
  }

  def -(calc: Calculation, operand: Int): Calculation = {
    calc match {
      case Success(result) => Success(result - operand)
      case Failure(reason) => Failure(reason)
    }
  }

  def /(calc: Calculation, denominator: Int): Calculation = {
    calc match {
      case Success(result) => Success(result / denominator)
        denominator match {
          case 0 => Failure("Division by zero")
          case _ => Success(result / denominator)
        }
      case Failure(reason) => Failure(reason)
    }
  }
}

sealed trait Calculation
final case class Result(n: Int) extends Calculation
final case class FailedMessage(s: String) extends Calculation

final case class Success(n: Int) extends Calculation
final case class Failure(s: String) extends Calculation