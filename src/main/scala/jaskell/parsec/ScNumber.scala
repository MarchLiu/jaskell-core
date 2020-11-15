package jaskell.parsec

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class ScNumber extends Parsec [Char, String]{
  val decimal = new Decimal
  val exp: Parsec[Char, String] = Attempt(new Parsec[Char, String] {
    val ep: Text = Text("e", caseSensitive = false)
    val sp: Parsec[Char, String] = Attempt(Text("+")) <|> Attempt(Text("-")) <|> Return("")
    val np: UInt = new jaskell.parsec.UInt
    override def ask(st: State[Char]): Try[String] = {
      for {
        e <- ep ? st
        s <- sp ? st
        n <- np ? st
      } yield {e + s + n}
    }
  })

  override def ask(s: State[Char]): Try[String] = {
    decimal ? s map  { mantissa =>
      exp ? s map {e => mantissa + e} getOrElse mantissa
    }
  }
}
