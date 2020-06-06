package jaskell.parsec

import java.io.EOFException

/**
 * check next content if a \n char or \r\n
 *
 * @author mars
 * @version 1.0.0
 */
class EndOfLine extends Parsec[String, Char] {
  final private val parsec = Choice[String, Char](Text("\n"), Text("\r\n"))

  override def ask(s: State[Char]): Either[Exception, String] = parsec ? s
}

object EndOfLine {
  def apply(): EndOfLine = new EndOfLine()
}