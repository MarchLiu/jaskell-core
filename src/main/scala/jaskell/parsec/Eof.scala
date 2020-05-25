package jaskell.parsec

import java.io.EOFException

/**
 * Eof check state if get to end, It return nothing. Eof just success or failed.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/08 16:53
 */
class Eof[E] extends Parsec [Unit, E]{

  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): Unit = try {
    val re = s.next()
    throw new ParsecException(s.status, s"exception eof but $re")
  } catch {
    case _: EOFException =>
  }
}

object Eof {
  def apply[E](): Eof[E] = new Eof()
}