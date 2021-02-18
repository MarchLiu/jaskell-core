package jaskell.parsec

import scala.util.{Failure, Success, Try}

/**
 * NoneOf success if get a item is none in prepared
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class NoneOf[E](val items: Set[E]) extends Parsec[E, E] {

  override def ask(s: State[E]): Try[E] = {
    s.next() match {
      case Success(re) =>
        if (items.contains(re)) {
          s.trap(s"expect a item none of $items but got $re")
        } else {
          Success(re)
        }
      case left: Failure[_] => left
    }
  }
}

object NoneOf {
  def apply[E](items: Set[E]): NoneOf[E] = new NoneOf(items)
}
