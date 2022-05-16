package jaskell.parsec

import scala.util.{Try}

case class Combine2[A, B](val left: Parsec[A, B], val right: Parsec[A, B]) extends Parsec[A, B] {
  def apply(state: State[A]): Try[B] = {
    val status = state.status
    val re = left(state)
    if(re.isSuccess){
      return re
    } else {
      if(status == state.status){
        return right(state)
      } else {
        return re
      }
    }
  }
}