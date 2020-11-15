package jaskell.parsec

import scala.util.Try

/**
 * Fail do nothing but failed.
 *
 * @author mars
 * @version 1.0.0
 */
class Fail[E](val msg: String, val objects: Any*) extends Parsec[E, E] {
  val message: String = msg.format(objects)

  override def ask(s: State[E]): Try[E] = {
    s.trap(message)
  }
}

object Fail {
  def apply[E](msg: String, objects: Any*): Fail[E] = new Fail(msg, objects)

}