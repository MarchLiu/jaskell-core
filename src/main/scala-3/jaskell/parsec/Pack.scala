package jaskell.parsec

import scala.util.{Try, Success}

case class Pack[E, T](val value: T) extends Parsec[E, T] {
  /**
   * pack is the return operation in haskell 
   * @param state 
   * @return create a parser just return the item
   */
  def apply(state: State[E]): Try[T] = Success(value)
  
}

object Pack {
    def apply[E, T](value: T): Pack[E, T] = new Pack(value)
}