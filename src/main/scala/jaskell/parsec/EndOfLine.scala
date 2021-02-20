package jaskell.parsec

import scala.util.Try

/**
 * check next content if a \n char or \r\n
 *
 * @author mars
 * @version 1.0.0
 */
class EndOfLine extends Parsec[Char, String] {
  final private val parsec = Choice[Char, String](Attempt(Text("\n")), Text("\r\n"))

  override def apply(s: State[Char]): Try[String] = parsec ? s
}

object EndOfLine {
  def apply(): EndOfLine = new EndOfLine()
}