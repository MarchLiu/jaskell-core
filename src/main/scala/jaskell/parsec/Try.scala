package jaskell.parsec

/**
 * The parser try p behaves like parser p, except that it pretends that it hasn't consumed any input
 * when an error occurs.
 *
 * This combinator is used whenever arbitrary look ahead is needed. Since it pretends that it hasn't
 * consumed any input when p fails, the (<|>) combinator will try its second alternative even when the
 * first parser failed while consuming input.
 *
 * @author Mars Liu
 * @version 1.0.0
 * @since 2020/05/09 14:22
 */
class Try[T, E](val parsec: Parsec[T, E]) extends Parsec[T, E] {
  override def apply[S <: State[E]](s: S): T = {
    val tran = s.begin()
    try {
      val re = parsec(s)
      s commit tran
      re
    } catch {
      case error: Exception =>
        s rollback tran
        throw error
    }
  }
}

object Try {
  def apply[T, E](parsec: Parsec[T, E]): Try[T, E] = new Try(parsec)
}
