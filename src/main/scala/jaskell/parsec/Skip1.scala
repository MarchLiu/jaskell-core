package jaskell.parsec

import scala.util.{Success, Try}

/**
 * Skip1 p applies the parser p one or more times, skipping its result.
 *
 * @author mars
 * @version 1.0.0
 */
class Skip1[E](val psc: Parsec[E, _]) extends Parsec[E, Unit] {
  import jaskell.parsec.Parsec.Implicits._

  val skip = new Skip(psc)
  val parser: Parsec[E, _] = (s: State[E]) => for {
    _ <- psc ask s
    _ <- skip ask s
  } yield ()

  override def apply(s: State[E]): Try[Unit] = (parser ? s).flatMap(_ => Success())
}

object Skip1 {
  def apply[E](psc: Parsec[E, _]): Skip1[E] = new Skip1[E](psc)
}