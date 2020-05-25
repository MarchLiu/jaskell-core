package jaskell.parsec

import java.io.EOFException

/**
 * check next content if a \n char or \r\n
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:05
 */
class EndOfLine extends Parsec[String, Char] {
  final private val parsec = Choice[String, Char](Text("\n"), Text("\r\n"))

  @throws[EOFException]
  @throws[ParsecException]
  override def apply[S <: State[Char]](s: S): String = parsec(s)
}

object EndOfLine {
  def apply(): EndOfLine = new EndOfLine()
}