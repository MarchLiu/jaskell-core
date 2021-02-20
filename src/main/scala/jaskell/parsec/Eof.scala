package jaskell.parsec

import java.io.EOFException

import scala.util.{Failure, Success, Try}

/**
 * Eof check state if get to end, It return nothing. Eof just success or failed.
 *
 * @author mars
 * @version 1.0.0
 */
class Eof[E] extends Parsec [E, Unit]{
  override def apply(s: State[E]): Try[Unit] = {
    s.next() match {
      case Success(re) =>
        s.trap(s"exception eof but $re")
      case Failure(_:EOFException) =>
        Success()
      case left: Failure[E] => left.asInstanceOf
    }
  }
}

object Eof {
  def apply[E](): Eof[E] = new Eof()
}