package jaskell.parsec

import java.io.EOFException

/**
 * Eof check state if get to end, It return nothing. Eof just success or failed.
 *
 * @author mars
 * @version 1.0.0
 */
class Eof[E] extends Parsec [Unit, E]{
  override def ask(s: State[E]): Either[Exception, Unit] = {
    s.next() match {
      case Right(re) =>
        Left(new ParsecException(s.status, s"exception eof but $re"))
      case Left(_:EOFException) =>
        Right()
      case left: Left[Exception, E] => left.asInstanceOf
    }
  }
}

object Eof {
  def apply[E](): Eof[E] = new Eof()
}