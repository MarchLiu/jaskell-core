package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:47
 */
class NeCh(val char: Char, val caseSensitive: Boolean) extends Parsec[Char, Char] {
  val chr: Char = if (caseSensitive) char else char.toLower

  override def apply[S <: State[Char]](s: S): Char = {
    val c = s.next();
    if (caseSensitive) {
      if (chr == c) {
        return c
      }
    } else {
      if (chr == c.toLower) {
        return c
      }
    }
    throw new ParsecException(s.status, s"expect char $char (case sensitive $caseSensitive) but get $c")
  }
}

object NeCh {
  def apply(char: Char, caseSensitive: Boolean): NeCh = new NeCh(char, caseSensitive)

  def apply(char: Char): NeCh = new NeCh(char, true)

}