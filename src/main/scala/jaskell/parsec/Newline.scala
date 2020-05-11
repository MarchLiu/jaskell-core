package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/11 11:50
 */
class Newline extends Parsec[Char, Char]{
  override def apply[S <: State[Char]](s: S): Char = {
    val c:Char = s.next();
    if(c == '\n') {
      c
    } else {
      throw new ParsecException(s.status(), s"Expect a newline char but get $c")
    }
  }
}
