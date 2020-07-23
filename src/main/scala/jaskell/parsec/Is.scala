package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/07/23 14:18
 */
class Is[T](val predicate: Function[T, Boolean]) extends Parsec[T, T] {
  override def ask(s: State[T]): Either[Exception, T] = {
    s.next().flatMap(item => {
      if(predicate(item)) {
        Right(item)
      } else {
        s.trap(s"expect anything pass predicate check but get $item")
      }
    })
  }
}
