package jaskell.batteries.python

import jaskell.Monad.Implicits._
import jaskell.parsec.Combinator.{BuiltIn, many}
import jaskell.parsec.Txt.{ch, chIn, text}
import jaskell.parsec.{Parsec, State}

import scala.util.Success

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/02/16 12:18
 */
object Strings {
  import jaskell.parsec.Parsec.Implicits._

  def startQuote: Parsec[Char, Char] = ch('f').opt *> chIn("\"'")

  def startTriQuote: Parsec[Char, String] = ch('f').opt *> (text("\"\"\"") <|> text("'''"))

  def escapedChar: Parsec[Char, Char] = ch('\\') *> { (s: State[Char]) =>
    s.next() flatMap {
      case '\'' =>
        Success('\'')
      case '\"' =>
        Success('\"')
      case '\\' =>
        Success('\\')
      case '\r' =>
        Success('\r')
      case '\n' =>
        Success('\n')
      case '\t' =>
        Success('\t')
    }
  }

  def char(stop: Char): Parsec[Char, Char] = { (s: State[Char]) =>
    s.next() flatMap { c =>
      if (c == stop) {
        s.trap(f"stop at $stop")
      } else {
        c match {
          case '\\' =>
            s.trap("except a unescaped char but get \\")
          case '\n' =>
            s.trap("except single line but get \n")
          case '\r' =>
            s.trap("except single line but get \r")
          case char: Char =>
            Success(char)
        }
      }
    }
  }


  def char(stop: String): Parsec[Char, Char] = { (s: State[Char]) =>
    val tryIt = text(stop).attempt ? s
    if (tryIt.isSuccess) {
      s.trap(f"stop at $stop")
    } else {
      s.next() flatMap {
        case '\\' =>
          s.trap("except a unescaped char but get \\")
        case char: Char =>
          Success(char)
      }
    }
  }

  def rawChar(stop: Char): Parsec[Char, Char] = { (s: State[Char]) =>
    s.next() flatMap { c =>
      if (c == stop) {
        s.trap(f"stop at $stop")
      } else {
        c match {
          case '\n' =>
            s.trap("except single line but get \n")
          case '\r' =>
            s.trap("except single line but get \r")
          case char: Char =>
            Success(char)
        }
      }
    }
  }


  def rawChar(stop: String): Parsec[Char, Char] = { (s: State[Char]) =>
    val tryIt = text(stop).attempt ? s
    if (tryIt.isSuccess) {
      s.trap(f"stop at $stop")
    } else {
      s.next()
    }
  }

  def singleLineString:Parsec[Char, String] = { (s: State[Char]) =>
    for {
      stop <- startQuote ? s
      content <- many(escapedChar <|> char(stop)) ? s
      _ <- char(stop) ? s
    } yield {
      content.mkString
    }
  }

  def multiLineString:Parsec[Char, String] = { (s: State[Char]) =>
    for {
      stop <- startTriQuote ? s
      content <- many(escapedChar <|> char(stop)) ? s
      _ <- text(stop) ? s
    } yield {
      content.mkString
    }
  }

  def singleLineRawString:Parsec[Char, String] = { (s: State[Char]) =>
    for {
      stop <- (ch('r') *> startQuote) ? s
      content <- many(rawChar(stop)) ? s
      _ <- char(stop) ? s
    } yield {
      content.mkString
    }
  }

  def multiLineRawString:Parsec[Char, String] = { (s: State[Char]) =>
    for {
      stop <- (ch('r') *> startTriQuote) ? s
      content <- many(rawChar(stop)) ? s
      _ <- text(stop) ? s
    } yield {
      content.mkString
    }
  }

  def string: Parsec[Char, String] = multiLineString <|> singleLineString
}
