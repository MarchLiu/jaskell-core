package jaskell.parsec

import scala.util.{Success, Try}

/**
 * If get one char equals the Ch prepared, return it.
 *
 * @author mars
 * @version 1.0.0
 */
class Ch(val char: Char, val caseSensitive: Boolean) extends Parsec[Char, Char] {
  val chr: Char = if (caseSensitive) char else char.toLower

  override def apply(s: State[Char]): Try[Char] = {
    s.next() flatMap { c =>
      if (caseSensitive) {
        if (chr == c) {
          return Success(c)
        }
      } else {
        if (chr == c.toLower) {
          return Success(c)
        }
      }
      s.trap(s"expect char $char (case sensitive $caseSensitive) but get $c")
    }
  }
}

object Ch {
  def apply(char: Char, caseSensitive: Boolean): Ch = new Ch(char, caseSensitive)

  def apply(char: Char): Ch = new Ch(char, true)
}