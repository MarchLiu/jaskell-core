package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 21:40
 */
class SepBySpec extends AnyFlatSpec with Matchers {
  "Basic" should "Run some basic tests" in {
    val state = State("hlhlhlhlhlhll")

    val p = SepBy(Eq('h'), Eq('l'))

    val re = p parse state
    re.size should be (6)
    re foreach { item =>
      item should be ('h')
    }
  }
}
