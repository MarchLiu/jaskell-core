package jaskell.expression.parsers

import jaskell.expression.Expression
import jaskell.parsec.{Ahead, Ch, Eof, Parsec, SkipWhitespaces, State, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:45
 */
class Parser extends Parsec[Expression, Char] {

  import jaskell.parsec.Txt.{skipWhiteSpaces, ch}
  import jaskell.parsec.Combinator.{attempt, ahead}
  import jaskell.parsec.Atom.eof

  val rq: Try[Char, Char] = attempt(ch(')'))
  val skips: SkipWhitespaces = skipWhiteSpaces
  val end: Ahead[Unit, Char] = ahead(new Parsec[Unit, Char] {
    val e: Eof[Char] = eof

    override def ask(s: State[Char]): Either[Exception, Unit] = {
      rq ? s match {
        case Left(_) =>
          e ? s
        case Right(_) =>
          Right()
      }
    }
  })

  override def ask(s: State[Char]): Either[Exception, Expression] = {
    val np = attempt(attempt(new Num) <|> new Q)

    np ? s flatMap { left =>
      (for {
        _ <- skips ? s
        e <- end ? s
      } yield {
        e
      }) match {
        case Right(_) => Right(left)
        case Left(_) =>
          val next = attempt(new A(left)) <|> attempt(new S(left)) <|> attempt(new P(left)) <|> new D(left)
          next ? s
      }
    }
  }
}
