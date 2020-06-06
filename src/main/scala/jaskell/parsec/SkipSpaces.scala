package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class SkipSpaces extends Parsec[Unit, Char] {
  val parsec = new Skip[Char](new Space())

  override def ask(s: State[Char]): Either[Exception, Unit] = parsec ? s
}

object SkipSpaces {
  def apply(): SkipSpaces = new SkipSpaces()
}