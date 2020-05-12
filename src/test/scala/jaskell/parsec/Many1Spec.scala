package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Combinator._
import jaskell.parsec.Txt._

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 19:22
 */
class Many1Spec extends AnyFlatSpec with Matchers{
  "One" should "Get first char and return" in {
    val state = State("hello")
    val parser = many1(ch('h'))
    val re = parser(state)
    re.head should be ('h')
    re.size should be (1)
  }

  "All" should "success all content" in {
    val state = State("hello")
    val parser = many1(text("hello"))
    val re = parser(state)
    re.head should be ("hello")
  }

  "Failed" should "Throw error " in {
    val state = State("Hello")
    val parser = many1(ch('h'))
    a[ParsecException] should be thrownBy {
      parser(state)
    }
  }
}
