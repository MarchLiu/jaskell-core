package jaskell.expression.parsers

import jaskell.expression.Expression
import jaskell.parsec.{Ahead, Attempt, Ch, Eof, Parsec, SkipWhitespaces, State}

import scala.util.{Failure, Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:45
 */
class Parser extends Parsec[Char, Expression] {

  import jaskell.parsec.Txt.{skipWhiteSpaces, ch}
  import jaskell.parsec.Combinator.{attempt, ahead}
  import jaskell.parsec.Atom.eof

  val rq: Attempt[Char, Char] = attempt(ch(')'))
  val skips: SkipWhitespaces = skipWhiteSpaces
  val e: Eof[Char] = eof

  val end: Ahead[Char, Unit] = ahead((s: State[Char]) => {
    rq ? s match {
      case Failure(_) =>
        e ? s
      case Success(_) =>
        Success()
    }
  })

  override def apply(s: State[Char]): Try[Expression] = {
    val np = attempt(attempt(new Num) <|> attempt(new Param) <|> new Q)

    np ? s flatMap { left =>
      (for {
        _ <- skips ? s
        e <- end ? s
      } yield {
        e
      }) match {
        case Success(_) => Success(left)
        case Failure(_) =>
          val next = attempt(new A(left)) <|> attempt(new S(left)) <|> attempt(new P(left)) <|> new D(left)
          next ? s
      }
    }
  }
}
