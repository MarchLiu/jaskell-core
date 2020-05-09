package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/08 16:53
 */
class Eof extends Parsec [Unit, _]{

  @throws[ParsecException]
  override def apply[S <: State[_]](s: S): Unit = try {
    val re = s.next()
    throw new ParsecException(s.status(), s"exception eof but $re")
  } catch {
    case _: EofException =>
  }
}
