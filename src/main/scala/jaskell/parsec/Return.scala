package jaskell.parsec

/**
 * Return just lift a value to Parsec Parser.
 *
 * @author mars
 * @version 1.0.0
 */
class Return[T, E](val element: T) extends Parsec[T, E] {

  override def ask(s: State[E]): Either[Exception, T] = {
    Right(element)
  }
}

object Return {
  def apply[T, E](element: T): Return[T, E] = new Return(element)
}
