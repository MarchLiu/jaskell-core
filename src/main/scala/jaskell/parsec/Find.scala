package jaskell.parsec

import java.io.EOFException

import scala.util.{Failure, Success, Try}
import scala.util.control.Breaks._
/**
 * Find try and next util success or eof.
 *
 * @author mars
 * @version 1.0.0
 */
class Find[E, T](val psc: Parsec[E, T]) extends Parsec[E, T] {

  override def ask(s: State[E]): Try[T] = {
    var error: Exception = null
    breakable {
      while (true){
        psc ? s match {
          case right: Success[_] =>
            return right
          case Failure(err: ParsecException) =>
            if(error == null) {
              error = err
            }
          case Failure(err) =>
            if (error == null) {
              return Failure(err)
            } else {
              return Failure(error)
            }
        }
      }
    }
    s.trap("find parsec should not run to this!")
  }
}

object Find {
  def apply[E, T](psc: Parsec[E, T]): Find[E, T] = new Find(psc)
}
