package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:18
 */
class Whitespace extends Parsec [Char, Char]{

  override def ask(s: State[Char]): Either[Exception, Char] = {
    s.next() flatMap { c =>
      if(c.isWhitespace){
        Right(c)
      } else {
        s.trap(s"expect a whitespace but get $c")
      }
    }
  }
}

object Whitespace {
  def apply(): Whitespace = new Whitespace()
}