package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Txt.uInteger

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 22:48
 */
class UIntSpec extends AnyFlatSpec with Matchers {
  "Simple" should "Run a simple test" in {
    val state = State("23413214")

    val parser = uInteger

    val re = parser apply state

    re should be ("23413214")
  }

  "Stop" should "Match digits until a letter" in {
    val state = State("23413a214")

    val parser = uInteger

    val re = parser apply state

    re should be ("23413")
  }

  "Fail" should "Failed" in {
    val state = State("x2344")
    val parser = uInteger
    a[ParsecException] should be thrownBy {
      parser apply state
    }
  }
}
