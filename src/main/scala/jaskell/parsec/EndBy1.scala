package jaskell.parsec

/**
 * EndBy1 p sep parses one or more occurrences of p, separated and ended by sep. Returns a seq of values returned by p.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/25 20:44
 */
class EndBy1[T, E](val parser: Parsec[T, E], val sep: Parsec[_, E]) extends Parsec[Seq[T], E] {
  val parsec = new Many1(new Parsec[T, E] {
    override def apply[S <: State[E]](s: S): T = {
      val re = parser(s)
      sep(s)
      re
    }
  })
  override def apply[S <: State[E]](s: S): Seq[T] = parsec(s)
}

object EndBy1 {
  def apply[T, E](parser: Parsec[T, E], sep: Parsec[_, E]): EndBy1[T, E] = new EndBy1(parser, sep)
}