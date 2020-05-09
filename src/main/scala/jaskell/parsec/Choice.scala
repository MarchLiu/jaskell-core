package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:06
 */
class Choice[T, E](val parsecs: Seq[Parsec[T, E]]) extends Parsec[T, E] {

  override def apply[S <: State[E]](s: S): T = {
    var err = null
    val status = s.status()
    for (psc <- this.parsecs) {
      try return psc(s)
      catch {
        case e@(_: EofException | _: ParsecException) =>
          err = e
          if (s.status != status) throw e
      }
    }
    if (err == null) {
      throw new ParsecException(status, "Choice Error : All parsec parsers failed.")
    }
    else {
      throw new ParsecException(status, s"Choice Error $err, stop at $status")
    }
  }

}

object Choice {
  def apply[T, E](parsecs: Parsec[T, E]*): Choice[T, E] = new Choice(parsecs.toSeq)

  def apply[T, E](parsecs: Seq[Parsec[T, E]]): Choice[T, E] = new Choice(parsecs.toSeq)

}
