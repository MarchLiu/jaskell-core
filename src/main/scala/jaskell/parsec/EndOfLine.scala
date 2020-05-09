package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:05
 */
class EndOfLine extends Parsec[String, Char] {
  final private val parsec = Choice[String, Char](new Text("\n"), new Text("\r\n"))

  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[Char]](s: S): String = parsec(s)
}