package jaskell.expression.parsers

import jaskell.expression.{Expression, Parameter}
import jaskell.parsec.{Parsec, ParsecException, Return, State}

import scala.util.Try

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/10 14:35
 */
class Param extends Parsec[Char, Expression] {

  import jaskell.parsec.Combinator._
  import jaskell.parsec.Txt._

  val head: Parsec[Char, Char] = letter
  val tail: Parsec[Char, String] = many(attempt(letter) <|> attempt(digit)) >>= mkString
  val parser: Parsec[Char, String] = s => for {
    h <- head ? s
    t <- tail ? s
  } yield s"$h$t"

  override def ask(s: State[Char]): Try[Expression] = {
    parser ? s map {new Parameter(_)}
  }
}
