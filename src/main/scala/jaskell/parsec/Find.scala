package jaskell.parsec

import java.io.EOFException

import scala.util.control.Breaks._
/**
 * Find try and next util success or eof.
 *
 * @author mars
 * @version 1.0.0
 */
class Find[T, E](val psc: Parsec[T, E]) extends Parsec[T, E] {

  override def ask(s: State[E]): Either[Exception, T] = {
    var error: Exception = null
    breakable {
      while (true){
        psc ? s match {
          case right: Right[_, _] =>
            return right
          case Left(err: ParsecException) =>
            if(error == null) {
              error = err
            }
          case Left(err: Exception) =>
            if (error == null) {
              return Left(err)
            } else {
              return Left(error)
            }
        }
      }
    }
    Left(new ParsecException(s.status, "find parsec should not run to this!"))
  }
}

object Find {
  def apply[T, E](psc: Parsec[T, E]): Find[T, E] = new Find(psc)
}
