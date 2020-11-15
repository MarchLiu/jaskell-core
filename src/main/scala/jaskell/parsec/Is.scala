package jaskell.parsec

import scala.util.{Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/07/23 14:18
 */
class Is[T](val predicate: Function[T, Boolean]) extends Parsec[T, T] {
  override def ask(s: State[T]): Try[T] = {
    s.next().flatMap(item => {
      if(predicate(item)) {
        Success(item)
      } else {
        s.trap(s"expect anything pass predicate check but get $item")
      }
    })
  }
}
