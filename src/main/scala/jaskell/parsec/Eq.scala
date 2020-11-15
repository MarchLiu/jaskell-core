package jaskell.parsec

import scala.util.{Success, Try}

/**
 * Eq return from state if get a item equals its.
 *
 * @author mars
 * @version 1.0.0
 */
class Eq[E](val element: E) extends Parsec[E, E] {

  override def ask(s: State[E]): Try[E] = {
    s.next() flatMap  { data =>
      if(element == data) {
        return Success(element)
      }
      s.trap(s"expect $element at ${s.status} but $data")
    }
  }
}

object Eq {
  def apply[E](element: E): Eq[E] = new Eq(element)
}