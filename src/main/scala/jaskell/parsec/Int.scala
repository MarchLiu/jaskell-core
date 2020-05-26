package jaskell.parsec

/**
 * Int try to parse a long as long int from state and with signed.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 15:23
 */
class Int extends Parsec[String, Char] {
  val sign = new Try(Ch('-'))
  val uint = new UInt()

  override def apply[S <: State[Char]](s: S): String = {
    var sb: StringBuilder = new StringBuilder()
    if (sign.either(s).isRight) {
      sb += '-'
    }

    sb ++= uint(s)
    sb.toString()
  }
}

object Int {
  def apply: Int = new Int()
}