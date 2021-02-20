package jaskell.parsec

import scala.util.{Success, Try}

/**
 * Char None get next char, return it if none of content chars match.
 *
 * @author mars
 * @version 1.0.0
 */
class ChNone(val content: String, val caseSensitive: Boolean) extends Parsec[Char, Char] {
  val contentSet: Set[Char] = if (caseSensitive) content.toSet else content.toLowerCase.toSet

  override def apply(s: State[Char]): Try[Char] = {
    s.next() flatMap { c =>
      if (caseSensitive) {
        if (!(contentSet contains c)) {
          return Success(c)
        }
      } else {
        if (!(contentSet contains c.toLower)) {
          return Success(c)
        }
      }
      s.trap(s"expect any char none of $content (case sensitive $caseSensitive) but get $c")
    }
  }
}

object ChNone {
  def apply(content: String, caseSensitive: Boolean): ChNone = new ChNone(content, caseSensitive)

  def apply(content: String): ChNone = new ChNone(content, true)
}