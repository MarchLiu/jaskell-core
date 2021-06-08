package jaskell.parsec

import scala.util.{Failure, Success, Try}

/**
 * Opt x p tries to apply parser p. If p fails without consuming input,
 * it returns the value x, otherwise the value returned by p.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class Opt[E, T](val p: Parsec[E, T], val otherwise: T) extends Parsec[E, T] {
  lazy val psc = Attempt(p)

  override def apply(s: State[E]): Try[T] = {
    val before = s.status
    psc ? s match {
      case right: Success[_] =>
        right
      case left: Failure[_] =>
        val after = s.status
        if (before == after) {
          Success(otherwise)
        } else {
          left
        }
    }
  }
}

object Opt {
  def apply[E, T](p: Parsec[E, T], otherwise: T): Opt[E, T] = new Opt(p, otherwise)
}
