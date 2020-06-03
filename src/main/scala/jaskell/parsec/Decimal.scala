package jaskell.parsec

import scala.collection.mutable

/**
 * Decimal parser parse a decimal number with signed or no.
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 14:21
 */
class Decimal extends Parsec[String, Char]{
  val sign: Try[Char, Char] = new Try(Ch('-'))
  val udicemal = new UDecimal()
  override def apply[S <: State[Char]](s: S): String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()
    sign.opt(s).foreach(x => sb += x)
    sb ++= udicemal(s)
    sb.mkString
  }
}

object Decimal {
  def apply(): Decimal = new Decimal()
}