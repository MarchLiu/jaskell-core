package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 */
class ScNumber extends Parsec [String, Char]{
  val decimal = new Decimal
  val exp: Parsec[String, Char] = Try(new Parsec[String, Char] {
    val ep: Text = Text("e", caseSensitive = false)
    val sp: Parsec[String, Char] = Try(Text("+")) <|> Try(Text("-")) <|> Return("")
    val np: UInt = new jaskell.parsec.UInt
    override def ask(st: State[Char]): Either[Exception, String] = {
      for {
        e <- ep ? st
        s <- sp ? st
        n <- np ? st
      } yield {e + s + n}
    }
  })

  override def ask(s: State[Char]): Either[Exception, String] = {
    decimal ? s map  { mantissa =>
      exp ? s map {e => mantissa + e} getOrElse mantissa
    }
  }
}
