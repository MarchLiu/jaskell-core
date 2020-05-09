package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:05
 */
class Text(val text: String) extends Parsec[String, Char] {
  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[Char]](s: S): String = {
    var idx = 0
    for (c <- this.text) {
      val data = s.next()
      if (c != data) {
        throw  new ParsecException(s.status(), s"Expect $c of $text [$idx] at ${s.status()} but get $data")
      }
      idx += 1
    }
    text
  }
}