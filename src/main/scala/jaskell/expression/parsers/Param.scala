package jaskell.expression.parsers

import jaskell.Monad.Implicits._
import jaskell.expression.{Expression, Parameter}
import jaskell.parsec.{Parsec, State}

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
  import jaskell.parsec.Parsec.Implicits._
  import jaskell.parsec.Txt._

  val head: Parsec[Char, Char] = letter
  val tail: Parsec[Char, Seq[Char]] = many(attempt(letter) <|> attempt(digit))
  val t: Parsec[Char, String] = tail >>= mkString
  val parser: Parsec[Char, String] = (s: State[Char]) => for {
    h <- head ? s
    tv <- t ? s
  } yield s"$h$tv"

  override def apply(s: State[Char]): Try[Expression] = {
    parser ? s map {
      new Parameter(_)
    }
  }
}
