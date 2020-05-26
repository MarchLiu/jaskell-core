package jaskell.parsec

import jaskell.parsec.Txt.uInteger
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 22:48
 */
class IntSpec extends AnyFlatSpec with Matchers {
  "Simple" should "Run a simple test" in {
    val state = State("23413214")

    val parser = Int

    val re = parser apply state

    re should be ("23413214")
  }

  "Negative Simple" should "Run a simple test" in {
    val state = State("-23413214")

    val parser = Int

    val re = parser apply state

    re should be ("-23413214")
  }

  "Stop" should "Match digits until a letter" in {
    val state = State("23413a214")

    val parser = Int

    val re = parser apply state

    re should be ("23413")
  }

  "Negative Stop" should "Match negative digits until a letter" in {
    val state = State("-23413a214")

    val parser = Int

    val re = parser apply state

    re should be ("-23413")
  }

  "Negative Fail" should "Failed" in {
    val state = State("-x2344")
    val parser = Int
    a[ParsecException] should be thrownBy {
      parser apply state
    }
  }
}
