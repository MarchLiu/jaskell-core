package jaskell.parsec

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class Skip1Spaces extends Parsec[Char, Unit] {
  val parsec = new Skip1[Char](new Space())

  override def apply(s: State[Char]): Try[Unit] = parsec ? s
}

