package jaskell.parsec

/**
 * Newline match \n char
 *
 * @author mars
 * @version 1.0.0
 */
class Newline extends Parsec[Char, Char]{

  override def ask(s: State[Char]): Either[Exception, Char] = {
    s.next() flatMap { c =>
      if(c == '\n') {
        Right(c)
      } else {
        Left(new ParsecException(s.status, s"Expect a newline char but get $c"))
      }
    }
  }
}

object Newline {
  def apply(): Newline = new Newline()
}
