package jaskell.parsec

import java.io.EOFException

/**
 * Choice just the operator <|> in Haskell parsec
 *
 * @author mars
 * @version 1.0.0
 */
class Choice[T, E](val parsecs: Seq[Parsec[T, E]]) extends Parsec[T, E] {

  override def ask(s: State[E]): Either[Exception, T] = {
    var err: Exception = null
    val status = s.status
    for (psc <- this.parsecs) {
      psc ? s match {
        case right: Right[Exception, T] =>
          return right
        case Left(error) =>
          err = error
          if (status != s.status) {
            return Left(error)
          }
      }
    }
    if (err == null) {
      Left(new ParsecException(status, "Choice Error : All parsec parsers failed."))
    } else {
      Left(new ParsecException(status, s"Choice Error $err, stop at $status"))
    }
  }
}

object Choice {
  def apply[T, E](parsecs: Parsec[T, E]*): Choice[T, E] = new Choice(parsecs)

}
