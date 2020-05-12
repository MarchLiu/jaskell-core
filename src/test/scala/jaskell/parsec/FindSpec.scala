package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Txt._
import jaskell.parsec.Combinator._

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 18:56
 */
class FindSpec extends AnyFlatSpec with Matchers {
  "Simple" should " find token in content" in {
    val data = "It is a junit test case for find parsec."
    val state = State(data)
    val parser = Find(text("find"))
    val re = parser(state)
    re should be ("find")
  }

  "Failed" should "mismatch any content" in {
    val data = "It is a junit test case for find parsec."
    val state = State(data)
    val parsec = find(text("Fail"))
    a[ParsecException] should be thrownBy {
      parsec(state)
    }
  }
}
