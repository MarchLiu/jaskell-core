package jaskell.parsec

/**
 * EndBy1 p sep parses one or more occurrences of p, separated and ended by sep. Returns a seq of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 */
class EndBy1[T, E](val parser: Parsec[T, E], val sep: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val parsec: Parsec[Seq[T], E] = new Many1((s: State[E]) => {
    for {
      re <- parser ? s
      _ <- sep ? s
    } yield {re}
  })
  override def ask(s: State[E]): Either[Exception, Seq[T]] = parsec ? s
}

object EndBy1 {
  def apply[T, E](parser: Parsec[T, E], sep: Parsec[_, E]): EndBy1[T, E] = new EndBy1(parser, sep)
}