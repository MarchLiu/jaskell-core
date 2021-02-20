package jaskell.parsec

import scala.util.{Success, Try}

/**
 * Ne success if get item not equals the prepared one.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class Ne[E](val element: E) extends Parsec[E, E] {
  override def apply(s: State[E]): Try[E] = {
    s.next() flatMap { data =>
      if (element != data) {
        Success(data)
      } else {
        s.trap(s"expect a object not $element at ${s.status} but $data")
      }
    }
  }
}

object Ne {
  def apply[E](element: E): Ne[E] = new Ne(element)
}