package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/05 12:15
 */
class ScNumber extends Parsec [String, Char]{
  val decimal = new Decimal
  val exp: Parsec[String, Char] = Try(new Parsec[String, Char] {
    val ep: Ch = Ch('e', caseSensitive = false)
    val sp: Parsec[String, Char] = Try(Text("+")) <|> Try(Text("-")) <|> Return("")
    val np: UInt = new UInt
    override def apply[S <: State[Char]](st: S): String = {
      val result = for {
        e <- ep ? st
        s <- sp ? st
        n <- np ? st
      } yield {e + s + n}
      result match {
        case Left(err) => throw err
        case Right(re) => re
      }
  }})
  override def apply[S <: State[Char]](s: S): String = {
    val mantissa = decimal(s)
    exp ? s flatMap {e => Right(mantissa +e)} getOrElse(mantissa)
  }
}
