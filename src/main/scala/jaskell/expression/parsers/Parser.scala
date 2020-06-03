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
  val skip: SkipWhitespaces = skipWhiteSpaces
  val end: Ahead[Unit, Char] = ahead(new Parsec[Unit, Char] {
    val e: Eof[Char] = eof
    override def apply[S <: State[Char]](s: S): Unit = {
      rq ? s match {
        case Left(_) =>
          e(s)
        case Right(_) =>
      }
    }
  })

  override def apply[St <: State[Char]](s: St): Expression = {
    val np: Parsec[Expression, Char] = attempt(attempt(new Num) <|> new Q)

    val left = np(s)
    skip(s)
    end ? s match {
      case Right(_) =>
        left
      case Left(_) =>
        val next = attempt(new A(left)) <|> attempt(new S(left)) <|> attempt(new P(left)) <|> new D(left)
        val right = next(s)
        right
    }
  }
}
