package jaskell.parsec

import scala.util.Try

/**
 * Int try to parse a long as long int from state and without signed.
 *
 * @author mars
 * @version 1.0.0
 */
class UInt extends Parsec[Char, String]{
  val psc = new Many1[Char, Char](new Digit())

  override def apply(s: State[Char]): Try[String] = {
    psc ? s map {_.mkString}
  }
}

object UInt {
  def apply(): UInt = new UInt()
}