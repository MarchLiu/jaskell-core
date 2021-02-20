package jaskell.parsec

import scala.util.{Success, Try}

/**
 * Return just lift a value to Parsec Parser.
 *
 * @author mars
 * @version 1.0.0
 */
class Return[E, T](val element: T) extends Parsec[E, T] {

  override def apply(s: State[E]): Try[T] = Success(element)
}

object Return {
  def apply[E, T](element: T): Return[E, T] = new Return(element)
}
