package jaskell.parsec

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class Skip1Whitespaces extends Parsec[Char, Unit] {
  val parsec = new Skip1[Char](new Whitespace())

  override def apply(s: State[Char]): Try[Unit] = for {_ <- parsec ? s} yield ()
}

