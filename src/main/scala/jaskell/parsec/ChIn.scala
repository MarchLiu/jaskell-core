package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:51
 */
class ChIn(val content: String, val caseSensitive: Boolean) extends Parsec[Char, Char] {
  val contentSet:Set[Char] = if(caseSensitive) content.toSet else content.toLowerCase.toSet

  override def apply[S <: State[Char]](s: S): Char = {
    val c = s.next()
    if(caseSensitive){
      if(contentSet contains c){
        return c
      }
    }else{
      if(contentSet contains c.toLower){
        return c
      }
    }
    throw new ParsecException(s.status, s"expect any char in $content (case sensitive $caseSensitive) but get $c")
  }
}

object ChIn {
  def apply(content: String, caseSensitive: Boolean): ChIn = new ChIn(content, caseSensitive)

  def apply(content: String): ChIn = new ChIn(content, true)
}
