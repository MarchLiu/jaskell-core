package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 13:43
 */
class Between[T, E](val open: Parsec[_, E], val close: Parsec[_, E], val parsec: Parsec[T, E])
  extends Parsec[T, E] {
  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): T = {
    open(s)
    val re = parsec(s)
    close(s)
    re
  }
}

object Between {

  class In[T, E](val open: Parsec[_, E], val close: Parsec[_, E]) {
    def pack(parser: Parsec[T, E]) = new Between[T, E](this.open, this.close, parser)
  }

}

