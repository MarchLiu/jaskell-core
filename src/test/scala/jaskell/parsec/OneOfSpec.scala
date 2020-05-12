package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 20:10
 */
class OneOfSpec extends AnyFlatSpec with Matchers{
  "Simple" should "Just success" in {
    val state = State("hello");

    val oof = OneOf(Seq('b', 'e', 'h', 'f').toSet);

    val c = oof(state);
    c should be ('h')
  }

  "Failed" should "Fail" in {
    val state = State("hello")
    val oof = OneOf(Seq('d', 'a', 't', 'e').toSet)
    a[ParsecException]  should be thrownBy {
      oof(state)
    }
  }
}
