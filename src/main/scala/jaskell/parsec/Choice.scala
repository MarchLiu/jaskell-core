package jaskell.parsec

import java.io.EOFException

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:06
 */
class Choice[T, E](val parsecs: Seq[Parsec[T, E]]) extends Parsec[T, E] {

  override def apply[S <: State[E]](s: S): T = {
    var err: Throwable = null
    val status = s.status
    for (psc <- this.parsecs) {
      try return psc(s)
      catch {
        case e@(_: EOFException | _: ParsecException) =>
          err = e
          if (s.status != status) throw e
      }
    }
    if (err == null) {
      throw new ParsecException(status, "Choice Error : All parsec parsers failed.")
    }
    else {
      throw new ParsecException(status, s"Choice Error $err, stop at $status")
    }
  }

}

object Choice {
  def apply[T, E](parsecs: Parsec[T, E]*): Choice[T, E] = new Choice(parsecs.toSeq)

}
