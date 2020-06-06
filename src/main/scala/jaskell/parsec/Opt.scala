package jaskell.parsec

/**
 * Opt x p tries to apply parser p. If p fails without consuming input,
 * it returns the value x, otherwise the value returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class Opt[T, E](val p: Parsec[T, E], val otherwise: T) extends Parsec[T, E] {

  override def ask(s: State[E]): Either[Exception, T] = {
    val before = s.status
    p ? s match {
      case right: Right[_, _] =>
        right
      case left: Left[_, _] =>
        val after = s.status
        if (before == after) {
          Right(otherwise)
        } else {
          left
        }
    }
  }
}

object Opt {
  def apply[T, E](p: Parsec[T, E], otherwise: T): Opt[T, E] = new Opt(p, otherwise)
}
