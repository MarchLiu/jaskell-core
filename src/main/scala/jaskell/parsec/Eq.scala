package jaskell.parsec

/**
 * Eq return from state if get a item equals its.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 10:52
 */
class Eq[E](val element: E) extends Parsec[E, E] {
  override def apply[S <: State[E]](s: S): E = {
    val data = s.next()
    if(element == data){
      element
    } else {
      throw new ParsecException(status = s.status, s"expect $element at ${s.status} but $data")
    }
  }
}

object Eq {
  def apply[E](element: E): Eq[E] = new Eq(element)
}