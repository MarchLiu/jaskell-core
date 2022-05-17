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
class ManyInSpec extends AnyFlatSpec with Matchers {
  "Simple" should "Run a simple test" in {
    val state = State(Seq(1, 2, 3, 9, 15, 2, 4, 8))

    val parser = ManyIn(Set(1, 2, 3, 4, 5, 6, 7, 8, 9, 0))

    val re = parser ! state

    re should be (Seq(1, 2, 3, 9))
  }

}
