package jaskell.parsec

import scala.util.Try

/**
 * EndBy1 p sep parses one or more occurrences of p, separated and ended by sep. Returns a seq of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 */
class EndBy1[E, T](val parser: Parsec[E, T], val sep: Parsec[E, _]) extends Parsec[E, Seq[T]] {
  val parsec: Parsec[E, Seq[T]] = new Many1((s: State[E]) => {
    for {
      re <- parser ? s
      _ <- sep ? s
    } yield {re}
  })
  override def ask(s: State[E]): Try[Seq[T]] = parsec ? s
}

object EndBy1 {
  def apply[E, T](parser: Parsec[E, T], sep: Parsec[E, _]): EndBy1[E, T] = new EndBy1(parser, sep)
}