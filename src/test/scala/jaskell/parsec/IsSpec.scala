package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/07/23 14:23
 */
class IsSpec extends AnyFlatSpec with Matchers {

  "Is" should "get item if predicate passed" in {
    val parser = new Is[scala.Int]({number => (number % 2) == 0})
    val state = State(Seq(2))

    (parser ? state) should be (Right(2))
  }

  "IsNot" should "failed when predicate not passed" in {
    val parser = new Is[scala.Int](number => number % 2== 1)
    val state = State(Seq(2))

    (parser ? state).isLeft should be (true)
    state.current should be (1)
  }
}
