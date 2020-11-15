package jaskell.parsec

import scala.util.Try

/**
 * Decimal parser parse a decimal number with signed or no.
 *
 * @author mars
 * @version 1.0.0
 */
class Decimal extends Parsec[Char, String] {
  val sign: Parsec[Char, String] = new Attempt(Text("-")) <|> Return("")
  val udicemal = new UDecimal()

  override def ask(st: State[Char]): Try[String] = {
    for {
      s <- sign ? st
      num <- udicemal ? st
    } yield s + num
  }
}

object Decimal {
  def apply(): Decimal = new Decimal()
}