package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:18
 */
class Whitespace extends Parsec [Char, Char]{
  override def apply[S <: State[Char]](s: S): Char = {
    val c = s.next()
    if(c.isWhitespace){
      c
    } else {
      throw new ParsecException(s.status(), s"expect a whitespace but get $c")
    }
  }
}
