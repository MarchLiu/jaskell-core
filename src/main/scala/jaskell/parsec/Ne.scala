package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:20
 */
class Ne[E](val element: E) extends Parsec[E, E] {
  override def apply[S <: State[E]](s: S): E = {
    val data = s.next()
    if(element != data){
      element
    } else {
      throw new ParsecException(status = s.status(), s"expect a object not $element at ${s.status()} but $data")
    }
  }
}
