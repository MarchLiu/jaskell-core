package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 21:18
 */
class ReturnSpec extends AnyFlatSpec with Matchers{
  "Simple" should "Just pack a value" in {
    val state = State("Hello World")
    val returns: Return[Char, BigDecimal] = Return(BigDecimal("3.1415926"))
    val status = state.status
    val re = returns ! state
    re should be (BigDecimal("3.1415926"))
    state.status should be (status)
  }
}
