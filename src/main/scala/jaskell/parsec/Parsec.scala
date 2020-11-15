package jaskell.parsec

import java.io.EOFException

import scala.util.{Try, Success, Failure}

/**
 * trait of Parsec parsers.
 *
 * @author mars
 * @version 1.0.0
 */
trait Parsec[E, T] {
  @throws[EOFException]
  @throws[ParsecException]
  def apply(s: State[E]): T = {
    ask(s) match {
      case Success(result) => result
      case Failure(error) => throw error
    }
  }

  def parse(s: State[E]): T = {
    ask(s) match {
      case Success(result) => result
      case Failure(error) => throw error
    }
  }

  def !(s: State[E]): T = {
    ask(s) match {
      case Success(result) => result
      case Failure(error) => throw error
    }
  }

  def ask(s: State[E]): Try[T]

  def ask(s: Seq[E]): Try[T] = {
    ask(State(s))
  }

  def opt(s: State[E]): Option[T] = {
    try {
      Some(apply(s))
    } catch {
      case _: Exception =>
        Option.empty[T]
    }
  }

  def `<|>`(parsec: Parsec[E, T]): Parsec[E, T] = new Choice(Seq(this, parsec))

  def `<?>`(message: String): Parsec[E, T] = (s: State[E]) => {
    this ask s orElse s.trap(message)
  }

  def >> [O](p: Parsec[E, O]): Parsec[E, O] = (s: State[E]) => {
    this ask s flatMap {_ => p ask s}
  }

  def >>=[O](binder: Binder[E, T, O]): Parsec[E, O] = (s: State[E]) => {
    this ask s flatMap {value => binder(value) ? s}
  }

  def ? (s: State[E]): Try[T] = ask(s)

  def ? (s: Seq[E]): Try[T] = ask(s)

}

object Parsec {
  def apply[E, T](parser: State[E] => Try[T]): Parsec[E, T] = parser(_)

}

