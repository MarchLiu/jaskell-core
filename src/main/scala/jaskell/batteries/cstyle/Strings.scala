package jaskell.batteries.cstyle

import jaskell.Monad.toMonad
import jaskell.parsec.Combinator.many
import jaskell.parsec.{Parsec, State}
import jaskell.parsec.Txt.{ch, mkString, nch}

import scala.language.implicitConversions
import scala.util.{Failure, Success, Try}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/02/07 09:33
 */
object Strings {

  def escapedChar: Parsec[Char, Char] = ch('\\') *> { (s: State[Char]) =>
    s.next() flatMap {
      case 'n' =>
        Success('\n')
      case 'r' =>
        Success('\r')
      case '"' =>
        Success('"')
      case 't' =>
        Success('\t')
      case c: Char =>
        s.trap(f"\\$c isn't a valid escape char")
    }
  }

  def char: Parsec[Char, Char] = (s: State[Char]) =>
    s.next() flatMap {
      case '\n' =>
        s.trap(f"new line is invalid in a literal token, maybe use \\n?")
      case '"' =>
        s.trap("stop at \"")
      case c: Char =>
        Success(c)

    }

  def string: Parsec[Char, String] =
    ch('"') *> many(escapedChar <|> char) <* ch('"') >>= mkString


}
