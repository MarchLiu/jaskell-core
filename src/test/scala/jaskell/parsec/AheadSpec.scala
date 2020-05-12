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
      val re = text("this")(s)
      ahead(text(" is"))(s)
      re
    }
    parser(state) should be("this")
    state.status should be(4)
  }

  "Then" should "Check status get result and stop at is" in {
    val content: String = "this is a string data."
    val state = State.apply(content)
    val parser = Parsec[String, Char] { s =>
      text("this")(s)
      space.apply(s)
      ahead(text("is"))(s)
    }
    val re = parser(state)
    re should be("is")
    state.status should be(5)
  }

  "Fail" should "throw parsec exception from parser" in {
    val content: String = "this is a string data."
    val state = State apply content
    val parser = Parsec[String, Char] { s =>
      text("this")(s)
      space apply s
      ahead(text(" is"))(s)
    }

    a[ParsecException] should be thrownBy {
      parser(state)
    }
  }
}
