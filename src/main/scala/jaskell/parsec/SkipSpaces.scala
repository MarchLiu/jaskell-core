package jaskell.parsec

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class SkipSpaces extends Parsec[Char, Unit] {
  val parsec = new Skip[Char](new Space())

  override def apply(s: State[Char]): Try[Unit] = parsec ? s
}

object SkipSpaces {
  def apply(): SkipSpaces = new SkipSpaces()
}