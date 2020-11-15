package jaskell.parsec

import scala.util.{Failure, Success, Try}

/**
 * Int try to parse a long as long int from state and with signed.
 *
 * @author mars
 * @version 1.0.0
 */
class Int extends Parsec[Char, String] {
  val sign = new Attempt(Ch('-'))
  val uint = new UInt()

  override def ask(s: State[Char]): Try[String] = {
    var sb: StringBuilder = new StringBuilder()
    if (sign.ask(s).isSuccess) {
      sb += '-'
    }

    uint ? s match {
      case Success(value) => sb ++= value
      case left: Failure[_] => return left
    }
    Success(sb.toString())
  }
}

object Int {
  def apply: Int = new Int()
}