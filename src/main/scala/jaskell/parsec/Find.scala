package jaskell.parsec

/**
 * Find try and next util success or eof.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:22
 */
class Find[T, E](val psc: Parsec[T, E]) extends Parsec[T, E] {
  override def apply[S <: State[E]](s: S): T = {
    var error: ParsecException = null
    var re: Option[T] = Option.empty
    while (re.isEmpty) {
      psc.ask(s) match {
        case Right(value) =>
          re = Some(value)
        case Left(err: ParsecException) =>
          if (error == null) {
            error = err
          }
        case Left(err: Exception) =>
          if (error != null) {
            throw error
          } else {
            throw err
          }
      }
    }
    re.get
  }
}

object Find {
  def apply[T, E](psc: Parsec[T, E]): Find[T, E] = new Find(psc)
}
