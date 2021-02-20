package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Combinator._

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 19:48
 */
class ManyTillSpec extends AnyFlatSpec with Matchers{
  "Simple" should "Test a many till match" in {
    val state = State("hhhhhhlhhhll")

    val m = manyTill(Eq('h'), Eq('l'))

    val re = m parse state
    re.size should be (6)
    for(item <- re) {
      item should be ('h')
    }
  }
}
