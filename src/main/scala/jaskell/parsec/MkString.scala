package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/12 19:03
 */
class MkString extends Binder[String, Seq[Char], Char] {
  override def apply(value: Seq[Char]): Parsec[String, Char] = Return(value.mkString)
}

object MkString {
  def apply(): MkString = new MkString()
}