package jaskell.parsec

import scala.util.{Success, Try}

/**
 * If get a char match any of content, return it.
 *
 * @author mars
 * @version 1.0.0
 */
class ChIn(val content: String, val caseSensitive: Boolean) extends Parsec[Char, Char] {
  val contentSet: Set[Char] = if (caseSensitive) content.toSet else content.toLowerCase.toSet

  override def ask(s: State[Char]): Try[Char] = {
    s.next() flatMap { c =>
      if (caseSensitive) {
        if (contentSet contains c) {
          return Success(c)
        }
      } else {
        if (contentSet contains c.toLower) {
          return Success(c)
        }
      }
      s.trap(s"expect any char in $content (case sensitive $caseSensitive) but get $c")
    }
  }
}

object ChIn {
  def apply(content: String, caseSensitive: Boolean): ChIn = new ChIn(content, caseSensitive)

  def apply(content: String): ChIn = new ChIn(content, true)
}
