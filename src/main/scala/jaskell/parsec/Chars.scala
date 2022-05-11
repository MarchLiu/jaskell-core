package jaskell.parsec

import jaskell.Monad.toMonad

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/05/06 00:59
 */
case class Chars(elements: String, caseSensitive: Boolean=true) extends Parsec[Char, String] {
  val parser: Parsec[Char, Seq[Char]] = new Many(new ChIn(elements, caseSensitive))
  val p: Parsec[Char,String] = parser >>= (new MkString())
  override def apply(s: State[Char]): Try[String] = p(s)
}
