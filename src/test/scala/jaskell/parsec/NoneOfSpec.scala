package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 20:02
 */
class NoneOfSpec extends AnyFlatSpec with Matchers {

  "Simple OK" should "Test success" in {
    val state = State("hello")
    val nof = NoneOf(Seq('k', 'o', 'f').toSet)
    val c = nof ! state
    c should be ('h')
  }

  "Simple Failed" should "Test failed" in {
    val state = State("sound")
    val nof = NoneOf(Seq('k', 'f', 's').toSet)
    a[ParsecException] should be thrownBy  {
      nof ! state
    }
  }
}
