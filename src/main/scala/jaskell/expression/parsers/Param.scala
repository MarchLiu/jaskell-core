package jaskell.expression.parsers

import jaskell.expression.{Expression, Parameter}
import jaskell.parsec.{Parsec, ParsecException, Return, State}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/10 14:35
 */
class Param extends Parsec[Expression, Char] {

  import jaskell.parsec.Combinator._
  import jaskell.parsec.Txt._

  val head: Parsec[Char, Char] = (s: State[Char]) => {
    s.next() flatMap { v =>
      if (v.isLetter) {
        Right(v)
      } else {
        Left(new ParsecException(s.status, s"expect a letter but get $v"))
      }
    }
  }
  val tail: Parsec[String, Char] = many(attempt(head) <|> attempt(digit)) >>= { v => Return(v.mkString) }
  val parser: Parsec[String, Char] = s => for {
    h <- head ? s
    t <- tail ? s
  } yield s"$h$t"

  override def ask(s: State[Char]): Either[Exception, Expression] = {
    parser ? s map {new Parameter(_)}
  }
}
