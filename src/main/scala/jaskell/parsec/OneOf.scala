package jaskell.parsec

import java.io.EOFException

/**
 * OneOf success if item equals one of prepared.
 *
 * @author mars
 * @version 1.0.0
 */
class OneOf[T](val items:Set[T]) extends Parsec[T, T] {

  override def ask(s: State[T]): Either[Exception, T] = {
    s.next() flatMap {v => {
      if(items.contains(v)){
        Right(v)
      }else{
        s.trap(s"expect a value in ${items} but get $v")
      }
    }}
  }
}

object OneOf {
  def apply[T](items: Set[T]): OneOf[T] = new OneOf(items)
}
