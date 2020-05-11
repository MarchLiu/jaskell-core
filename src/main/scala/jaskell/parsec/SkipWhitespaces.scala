package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:20
 */
class SkipWhitespaces extends Parsec[Unit, Char] {
  val parsec = new Skip[Char](new Whitespace())
  override def apply[S <: State[Char]](s: S): Unit = {
    parsec(s)
  }
}
