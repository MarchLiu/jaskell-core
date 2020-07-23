package jaskell.parsec

import java.io.EOFException

/**
 * NoneOf success if get a item none of any in prepared
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class NoneOf[E](val items: Set[E]) extends Parsec[E, E] {

  override def ask(s: State[E]): Either[Exception, E] = {
    s.next() match {
      case Right(re) =>
        if (items.contains(re)) {
          s.trap(s"expect a item none of $items but got $re")
        } else {
          Right(re)
        }
      case left: Left[_, _] => left
    }
  }
}

object NoneOf {
  def apply[E](items: Set[E]): NoneOf[E] = new NoneOf(items)
}
