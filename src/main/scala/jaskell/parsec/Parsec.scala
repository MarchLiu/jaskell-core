package jaskell.parsec

import java.io.EOFException

/**
 * trait of Parsec parsers.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/08 22:28
 */
trait Parsec[T, E] {
  @throws[EOFException]
  @throws[ParsecException]
  def apply[S <: State[E]](s: S): T

  def parse(content: Seq[E]): T = {
    val st = State(content)
    this apply st
  }

  def ask[S <: State[E]](s: S): Either[Exception, T] = {
    try Right(apply(s))
    catch {
      case e: Exception =>
        Left(e)
    }
  }

  def opt[S <: State[E]](s: S): Option[T] = {
    try {
      Some(apply(s))
    } catch {
      case _: Exception =>
        Option.empty[T]
    }
  }

  def `<|>`(parsec: Parsec[T, E]): Parsec[T, E] = new Choice(Seq(this, parsec))

  def `<?>`(message: String): Parsec[T, E] = new Parsec[T, E] {
    override def apply[S <: State[E]](s: S): T = try {
      this (s)
    } catch {
      case _: ParsecException =>
        throw new ParsecException(s.status, message)
      case _: EOFException =>
        throw new ParsecException(s.status, message)
    }
  }

  def >>[O](p: Parsec[O, E]): Parsec[O, E] = new Parsec[O, E] {
    def apply[S <: State[E]](s: S): O = {
      Parsec.this apply s
      p(s)
    }
  }

  def >>=[O](binder: T => Parsec[O, E]): Parsec[O, E] = new Parsec[O, E] {
    def apply[S <: State[E]](s: S): O = binder(Parsec.this apply s) apply s
  }

  def ?[S <: State[E]](s: S): Either[Exception, T] = ask(s)
}

object Parsec {
  def apply[T, E](parser: State[E] => T): Parsec[T, E] = {
    new Parsec[T, E] {
      override def apply[St <: State[E]](s: St): T = parser(s)
    }
  }

}

