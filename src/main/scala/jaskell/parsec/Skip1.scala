package jaskell.parsec

import java.io.EOFException

/**
 * Skip1 p applies the parser p one or more times, skipping its result.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:09
 */
class Skip1[E](val psc: Parsec[_, E]) extends Parsec[Unit, E] {
  val skip = new Skip(psc)
  @throws[EOFException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): Unit = {
    psc(s)
    skip(s)
  }
}

object Skip1 {
  def apply[E](psc: Parsec[_, E]): Skip1[E] = new Skip1[E](psc)
}