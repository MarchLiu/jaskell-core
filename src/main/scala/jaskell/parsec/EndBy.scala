package jaskell.parsec

import scala.util.Try

/**
 * EndBy p sep parses zero or more occurrences of p, separated and ended by sep. Returns a seq of values returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class EndBy[E, T](val parser: Parsec[E, T], val sep: Parsec[E, _]) extends Parsec[E, Seq[T]] {
  val parsec:Parsec[E, Seq[T]] = new Many((s: State[E]) => {
    for {
      re <- parser ? s
      _ <- sep ? s
    } yield {
      re
    }
  })

  override def ask(s: State[E]): Try[Seq[T]] = parsec ? s
}

object EndBy {
  def apply[E, T](parser: Parsec[E, T], sep: Parsec[E, _]): EndBy[E, T] = new EndBy(parser, sep)
}