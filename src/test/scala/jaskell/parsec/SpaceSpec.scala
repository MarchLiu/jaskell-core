package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Txt.space

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 22:29
 */
class SpaceSpec extends AnyFlatSpec with Matchers {
  "Simple" should "Run a simple test" in {
    val state = State(" ")
    val s = space
    val a = s(state)
    a should be (' ')
  }

  "Fail" should "Failed when test" in {
    val state = State("\t");
    val s = space
    a[ParsecException] should be thrownBy {
      s apply state
    }
  }
}
