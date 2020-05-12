package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Txt._
import jaskell.parsec.Combinator._

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 19:40
 */
class ManySpec extends AnyFlatSpec with Matchers {
  "Simple" should "Run match 2 times" in {
    val state = State("HelloHello")
    val parser = many(text("hello", caseSensitive = false))
    val re = parser(state)
    re.size should be (2)
    re.head should be ("Hello")
    re(1) should be ("Hello")
  }
}
