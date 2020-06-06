package jaskell.parsec

/**
 * Char None get next char, return it if none of content chars match.
 *
 * @author mars
 * @version 1.0.0
 */
class ChNone(val content: String, val caseSensitive: Boolean) extends Parsec[Char, Char] {
  val contentSet: Set[Char] = if (caseSensitive) content.toSet else content.toLowerCase.toSet

  override def ask(s: State[Char]): Either[Exception, Char] = {
    s.next() flatMap { c =>
      if (caseSensitive) {
        if (!(contentSet contains c)) {
          return Right(c)
        }
      } else {
        if (!(contentSet contains c.toLower)) {
          return Right(c)
        }
      }
      Left(new ParsecException(s.status,
        s"expect any char none of $content (case sensitive $caseSensitive) but get $c"))
    }
  }
}

object ChNone {
  def apply(content: String, caseSensitive: Boolean): ChNone = new ChNone(content, caseSensitive)

  def apply(content: String): ChNone = new ChNone(content, true)
}