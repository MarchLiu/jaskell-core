package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.{Failure, Success}


/**
 * TODO
 *
 * @author Mars Liu
 * @version 1.0.0
 * @since 2020/06/16 21:13
 */
class InjectionSpec extends AnyFlatSpec with Matchers {

  import jaskell.parsec.Atom.{one, eof}
  import jaskell.parsec.Combinator._
  import jaskell.parsec.Txt._

  val escapeChar: Parsec[Char, Char] = attempt(ch('\\') >> ((s: State[Char]) => {
    s.next() flatMap {
      case 't' => Success('\t')
      case '\'' => Success('\'')
      case 'n' => Success('\n')
      case 'r' => Success('\r')
      case c@_ => Failure(new ParsecException(s.status, s"invalid escape char \\$c"))
    }
  }))
  val notEof: Parsec[Char, Char] = ahead(one[Char])

  val oneChar: Parsec[Char, Char] = escapeChar <|> nch('\'')

  val stringContent: Parsec[Char, String] = new Between(ch('\''), ch('\''), many(oneChar)) >>= mkString

  val noString: Parsec[Char, String] = many1(nch('\'')) >>= mkString

  val content: Parsec[Char, String] = attempt(noString) <|> stringContent

  val parser: Parsec[Char, String] =
    many(notEof >> content) >>= ((value: Seq[String]) => (s: State[Char]) => for {
      _ <- eof ? s
    } yield value.mkString)


  "Simple" should "match some regular content without string" in {
    val content: State[Char] = "a data without text content"
    (parser ? content) should be(Success("a data without text content"))
  }

  "SimpleString" should "match content in string literal" in {
    val content: State[Char] = "'a data included text content'"
    (parser ? content) should be(Success("a data included text content"))
  }

  "Escape" should "match content in string literal" in {
    val content: State[Char] = "'a data without\ttext content'"
    (parser ? content) should be(Success("a data without\ttext content"))
  }

  "Escape More" should "match mixed content in string literal and outer" in {
    val content: State[Char] = "some content included 'a data without\ttext content'"
    (parser ? content) should be(Success("some content included a data without\ttext content"))
  }

  "Injection" should "get a left exception because some injection attack in content" in {
    val content: State[Char] = "some content included 'a data without 'text content'"
    (parser ? content).isFailure should be(true)
  }

  "Not Close" should "get a left exception because some injection attack in content" in {
    val content: State[Char] = "some content included \'a data without 'text content'"
    (parser ? content).isFailure should be(true)
  }
}
