package jaskell.parsec

/**
 * Skip1 p applies the parser p one or more times, skipping its result.
 *
 * @author mars
 * @version 1.0.0
 */
class Skip1[E](val psc: Parsec[_, E]) extends Parsec[Unit, E] {
  val skip = new Skip(psc)
  val parser: Parsec[Unit, E] = psc >> skip

  override def ask(s: State[E]): Either[Exception, Unit] = parser ? s
}

object Skip1 {
  def apply[E](psc: Parsec[_, E]): Skip1[E] = new Skip1[E](psc)
}