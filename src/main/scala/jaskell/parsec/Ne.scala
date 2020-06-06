package jaskell.parsec

/**
 * Ne success if get item not equals the prepared one.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class Ne[E](val element: E) extends Parsec[E, E] {
  override def ask(s: State[E]): Either[Exception, E] = {
    s.next() flatMap { data =>
      if (element != data) {
        Right(data)
      } else {
        Left(new ParsecException(status = s.status, s"expect a object not $element at ${s.status} but $data"))
      }
    }
  }
}

object Ne {
  def apply[E](element: E): Ne[E] = new Ne(element)
}