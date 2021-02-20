package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import jaskell.parsec.Combinator.skip1
import jaskell.parsec.Txt.text

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 22:13
 */
class Skip1Test extends AnyFlatSpec with Matchers{
  "Simple" should "Just run a simple test" in {
    val state = State("left right left right")
    val parser = skip1(text("left"))
    parser apply state
    state.status  should be (4)
  }

  "Status More" should "Test status after twice matches" in {
    val state = State("left left right left right")
    val parser = skip1(text("left "))
    parser apply state
    state.status  should be (10)
  }

  "Fail" should "Failed after twice matches" in {
    val state = State("right left right left right")
    val parser = skip1(text("left "))
    a[ParsecException] should be thrownBy {
      parser ! state
    }
  }
}
