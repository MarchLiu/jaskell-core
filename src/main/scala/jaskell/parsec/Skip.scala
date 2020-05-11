package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:09
 */
class Skip[E](val psc: Parsec[_, E]) extends Parsec[Unit, E] {
  @throws[EofException]
  @throws[ParsecException]
  override def apply[S <: State[E]](s: S): Unit = {
    var tran: Option[s.Tran] = Option.empty
    try {
      while (true) {
        tran = Some(s.begin())
        psc(s)
        s.commit(tran.get)
      }
    } catch {
      case _: Exception =>
        s.rollback(tran.get)
    }
  }
}

object Skip {
  def apply[E](psc: Parsec[_, E]): Skip[E] = new Skip[E](psc)
}
