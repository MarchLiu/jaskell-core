package jaskell.parsec

import java.io.EOFException

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class Text(val text: String, val caseSensitive: Boolean) extends Parsec[String, Char] {
  val content: String = if (caseSensitive) text else text.toLowerCase

  override def ask(s: State[Char]): Either[Exception, String] = {
    var idx = 0
    val sb: StringBuilder = new StringBuilder
    for(c <- this.text) {
      s.next() match {
        case Right(data) =>
          val dataChar = if (caseSensitive) data else data.toLower
          if (c != dataChar) {
            return Left(new ParsecException(s.status,
              s"Expect $c of $text [$idx] (case sensitive $caseSensitive) at ${s.status} but get $data"))
          }
          idx += 1
          sb += data
        case Left(error) =>
          return Left(error)
      }
    }
    Right(sb.toString())
  }
}

object Text {
  def apply(text: String, caseSensitive: Boolean): Text = new Text(text, caseSensitive)

  def apply(text: String): Text = new Text(text, true)

}