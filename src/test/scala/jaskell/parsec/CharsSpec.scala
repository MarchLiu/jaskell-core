package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 22:48
 */
class CharsSpec extends AnyFlatSpec with Matchers {
  "Simple" should "Run a simple test" in {
    val state = State("23413214")

    val parser = Chars("1234567890")

    val re = parser ! state

    re should be ("23413214")
  }

  "Part" should "Run a match part" in {
    val state = State("234234abdef2342334")

    val parser = Chars("1234567890")

    val re = parser ! state

    re should be ("234234")
  }

}
