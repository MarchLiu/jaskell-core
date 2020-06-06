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
 */
class Try[T, E](val parsec: Parsec[T, E]) extends Parsec[T, E] {

  override def ask(s: State[E]): Either[Exception, T] = {
    val tran = s.begin()
    parsec ? s match {
      case right: Right[_, _] =>
        s commit tran
        right
      case left: Left[_, _] =>
        s rollback tran
        left
    }
  }
}

object Try {
  def apply[T, E](parsec: Parsec[T, E]): Try[T, E] = new Try(parsec)
}
