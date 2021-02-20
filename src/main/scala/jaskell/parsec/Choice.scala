package jaskell.parsec

import java.io.EOFException

import scala.util.{Failure, Success, Try}

/**
 * Choice just the operator <|> in Haskell parsec
 *
 * @author mars
 * @version 1.0.0
 */
class Choice[E, T](val parsecs: Seq[Parsec[E, T]]) extends Parsec[E, T] {

  override def apply(s: State[E]): Try[T] = {
    var err: Throwable = null
    val status = s.status
    for (psc <- this.parsecs) {
      psc ? s match {
        case right: Success[T] =>
          return right
        case Failure(error) =>
          err = error
          if (status != s.status) {
            return Failure(error)
          }
      }
    }
    if (err == null) {
      s.trap("Choice Error : All parsec parsers failed.")
    } else {
      s.trap(s"Choice Error $err, stop at $status")
    }
  }
}

object Choice {
  def apply[E, T](parsecs: Parsec[E, T]*): Choice[E, T] = new Choice(parsecs)

}
