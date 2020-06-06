package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/11 10:50
 */
class AheadSpec extends AnyFlatSpec with Matchers {

  import Txt.{text, space}
  import Combinator.ahead

  "Simple" should "Expect status stop after this" in {
    val content: String = "this is a string data."
    val state = State.apply(content)
    val parser =  Parsec[String, Char] { s =>
      for {
        re <- text("this") ? (s)
        _ <- ahead (text(" is")) ? (s)
      } yield re
    }
    parser(state) should be("this")
    state.status should be(4)
  }

  "Then" should "Check status get result and stop at is" in {
    val content: String = "this is a string data."
    val state = State.apply(content)
    val parser = Parsec[String, Char] { s =>
      for {
        _ <- text("this") ? s
        _ <- space ? s
        re <- ahead (text("is")) ? s
      } yield re
    }
    val re = parser(state)
    re should be("is")
    state.status should be(5)
  }

  "Fail" should "throw parsec exception from parser" in {
    val content: String = "this is a string data."
    val state = State apply content
    val parser = Parsec[String, Char] { s =>
      for {
        _ <- text("this") ? s
        _ <- space ? s
        re <- ahead(text(" is")) ? s
      } yield re
    }

    a[ParsecException] should be thrownBy {
      parser(state)
    }
  }
}
