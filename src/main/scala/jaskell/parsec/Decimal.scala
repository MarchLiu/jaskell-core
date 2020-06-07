package jaskell.parsec

import scala.collection.mutable

/**
 * Decimal parser parse a decimal number with signed or no.
 *
 * @author mars
 * @version 1.0.0
 */
class Decimal extends Parsec[String, Char] {
  val sign: Parsec[String, Char] = new Try(Text("-")) <|> Return("")
  val udicemal = new UDecimal()

  override def ask(st: State[Char]): Either[Exception, String] = {
    for {
      s <- sign ? st
      num <- udicemal ? st
    } yield {
      s + num
    }
  }
}

object Decimal {
  def apply(): Decimal = new Decimal()
}