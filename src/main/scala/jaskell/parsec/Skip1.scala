package jaskell.parsec

import java.io.EOFException

/**
 * Skip1 p applies the parser p one or more times, skipping its result.
 *
 * @author mars
 * @version 1.0.0
 */
class Skip1[E](val psc: Parsec[_, E]) extends Parsec[Unit, E] {
  val skip = new Skip(psc)

  override def ask(s: State[E]): Either[Exception, Unit] = {
    psc ? s flatMap {_ => skip ? s}
  }
}

object Skip1 {
  def apply[E](psc: Parsec[_, E]): Skip1[E] = new Skip1[E](psc)
}