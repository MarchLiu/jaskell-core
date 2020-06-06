package jaskell.parsec

/**
 * NoWhitespace success if the char is't any whitespace.
 *
 * @author mars
 * @version 1.0.0
 */
class NoWhitespace extends Parsec [Char, Char]{
  override def ask(s: State[Char]): Either[Exception, Char] = {
    s.next() flatMap { c =>
      if(c.isWhitespace){
        Left(new ParsecException(s.status, s"expect a char not whitespace but get $c"))
      } else {
        Right(c)
      }
    }
  }
}

object NoWhitespace {
  def apply(): NoWhitespace = new NoWhitespace()
}