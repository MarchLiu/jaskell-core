package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success


/**
 * Between Tester.
 *
 * @author Mars Liu
 *
 *
 */
class BetweenSpec extends AnyFlatSpec with Matchers {

  import Combinator.many
  import Txt.ch

  "Simple" should "test basic between case" in {

    val state = State("hello");

    val bmw = Between[Char, Char](
      ch('h'),
      ch('l'),
      ch('e')
    );

    val c = bmw(state);

    c should be(Success('e'))
  }

  "Brackets" should "test brackets pairs" in {

    val state = State("[hello]");
    val parser = Between(
      ch('['),
      ch(']'),
      many(Ne(']'))
    )

    val re = parser(state).map(_.mkString)
    re should be (Success("hello"))
  }
} 
