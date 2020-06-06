package jaskell.parsec

/**
 * EndBy p sep parses zero or more occurrences of p, separated and ended by sep. Returns a seq of values returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class EndBy[T, E](val parser: Parsec[T, E], val sep: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val parsec:Parsec[Seq[T], E] = new Many((s: State[E]) => {
    for {
      re <- parser ? s
      _ <- sep ? s
    } yield {
      re
    }
  })

  override def ask(s: State[E]): Either[Exception, Seq[T]] = parsec ? s
}

object EndBy {
  def apply[T, E](parser: Parsec[T, E], sep: Parsec[_, E]): EndBy[T, E] = new EndBy(parser, sep)
}