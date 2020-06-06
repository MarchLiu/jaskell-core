package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class SkipWhitespaces extends Parsec[Unit, Char] {
  val parsec = new Skip[Char](new Whitespace())

  override def ask(s: State[Char]): Either[Exception, Unit] = parsec ? s
}

object SkipWhitespaces {
  def apply(): SkipWhitespaces = new SkipWhitespaces()
}