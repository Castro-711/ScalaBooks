package traits.exercises

import java.util.Date

sealed trait Visitor {
  def id: String
  def createdAt: Date

  def age: Long = new Date().getTime - createdAt.getTime
}

final case class Anonymous(id: String, createdAt: Date = new Date()) extends Visitor

final case class User(id: String, email: String, createdAt: Date = new Date()) extends Visitor

/**
  * We want the ability to send emails to visitors
  *
  * We can only email signed in users, and sending an email requires a log of knowledge
  * about SMTP settings, MIME headers, and so on.
  *
  * Would an email method be better implemented using polymorphism on the Visitor trait
  * or using pattern matching in an EmailService object? Why?
  */

class EmailService {

}

/**
  * I think implementing an EmailService object would be better
  *
  * Anonymous doesn't need the functionality as it is only signed in users
  *
  * We can encapsulate the information about SMTP & MIME headers in the EmailService
  * object which makes more sense and keeps our User more practical
  *
  * Applying pattern matching is a more functional approach and could be a better practice here?
  *
  */