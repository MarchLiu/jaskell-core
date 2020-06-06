package jaskell.parsec

/**
 * Int try to parse a long as long int from state and with signed.
 *
 * @author mars
 * @version 1.0.0
 */
class Int extends Parsec[String, Char] {
  val sign = new Try(Ch('-'))
  val uint = new UInt()

  override def ask(s: State[Char]): Either[Exception, String] = {
    var sb: StringBuilder = new StringBuilder()
    if (sign.ask(s).isRight) {
      sb += '-'
    }

    uint ? s match {
      case Right(value) => sb ++= value
      case left: Left[_, _] => return left
    }
    Right(sb.toString())
  }
}

object Int {
  def apply: Int = new Int()
}