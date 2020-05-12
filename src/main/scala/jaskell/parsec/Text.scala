package jaskell.parsec

import java.io.EOFException

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:05
 */
class Text(val text: String, val caseSensitive: Boolean) extends Parsec[String, Char] {
  val content: String = if (caseSensitive) text else text.toLowerCase

  @throws[EOFException]
  @throws[ParsecException]
  override def apply[S <: State[Char]](s: S): String = {
    var idx = 0
    val sb: StringBuilder = new StringBuilder
    for (c <- this.text) {
      val data = s.next()
      val dataChar = if (caseSensitive) data else data.toLower
      if (c != dataChar) {
        throw new ParsecException(s.status,
          s"Expect $c of $text [$idx] (case sensitive $caseSensitive) at ${s.status} but get $data")
      }
      idx += 1
      sb += data
    }
    sb.mkString
  }
}

object Text {
  def apply(text: String, caseSensitive: Boolean): Text = new Text(text, caseSensitive)

  def apply(text: String): Text = new Text(text, true)

}