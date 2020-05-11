package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:59
 */
class ChNone(val content: String, val caseSensitive: Boolean) extends Parsec[Char, Char] {
  val contentSet:Set[Char] = if(caseSensitive) content.toSet else content.toLowerCase.toSet

  override def apply[S <: State[Char]](s: S): Char = {
    val c = s.next()
    if(caseSensitive){
      if(!(contentSet contains c)){
        return c
      }
    }else{
      if(!(contentSet contains c.toLower)){
        return c
      }
    }
    throw new ParsecException(s.status(),
      s"expect any char none of $content (case sensitive $caseSensitive) but get $c")
  }
}

object ChNone {
  def apply(content: String, caseSensitive: Boolean): ChNone = new ChNone(content, caseSensitive)

  def apply(content: String): ChNone = new ChNone(content, true)

}