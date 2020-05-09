package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:52
 */
class OneOf[T](val items:Set[T]) extends Parsec[T, T] {
  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[T]](s: S): T = {
    val v = s.next();
    if(items.contains(v)){
      v
    }else{
      throw new ParsecException(s.status(), s"expect a value in ${items} but get $v")
    }
  }
}
