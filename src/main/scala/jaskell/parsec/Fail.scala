package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:14
 */
class Fail[E](val msg: String, val objects: Any*) extends Parsec[E, E] {
  val message: String = msg.format(objects)

  @throws[ParsecException]
  override def apply[S <: State[E]](s: S) = throw new ParsecException(s.status(), message)
}

object Fail {
  def apply[E](msg: String, objects: Any*): Fail[E] = new Fail(msg, objects)

}