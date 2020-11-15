package jaskell.parsec

import scala.util.{Success, Try}

/**
 * OneOf success if item equals one of prepared.
 *
 * @author mars
 * @version 1.0.0
 */
class OneOf[T](val items:Set[T]) extends Parsec[T, T] {

  override def ask(s: State[T]): Try[T] = {
    s.next() flatMap {v => {
      if(items.contains(v)){
        Success(v)
      }else{
        s.trap(s"expect a value in ${items} but get $v")
      }
    }}
  }
}

object OneOf {
  def apply[T](items: Set[T]): OneOf[T] = new OneOf(items)
}
