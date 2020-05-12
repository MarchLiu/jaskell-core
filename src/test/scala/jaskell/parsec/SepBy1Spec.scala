package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import jaskell.parsec.Txt.ch
/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 21:31
 */
class SepBy1Spec extends AnyFlatSpec with Matchers {
  "Basic" should "Run some basic tests" in {
    val state = State("hlhlhlhlhlhll")
    val p = SepBy1(ch('h'), ch('l'))

    val re = p(state)
    re.size should be(6)
    re.foreach(item => {
      item should be('h')
    })
  }

  "2 Times" should "Match 2 times sepBy1" in {
    val state = State("hlh,h.hlhlhll")
    val p = SepBy1(ch('h'), ch('l'))

    val re = p(state)
    re.size should be(2)
    re foreach { item =>
      item should be('h')
    }
  }

  "Fail" should "Failed at " in {
    val state = State("hlh,h.hlhlhll")
    val p = SepBy1(ch('h'), ch('l'))
    p(state)
    a[ParsecException] should be thrownBy {
      p apply state
    }
  }
}
