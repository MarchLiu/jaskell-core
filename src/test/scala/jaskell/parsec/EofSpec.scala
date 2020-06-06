package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 10:02
 */
class EofSpec extends AnyFlatSpec with Matchers{
  import Txt._
  "Eof" should "Test" in {
    val state:State[Char] = "hello"

    val eof = new Eof[Char]

    Text("hello") apply state
    eof(state)
  }

  "Eof Exception" should "Throw exception" in{
    val state = State("hello")

    val eof = new Eof[Char]

    a[ParsecException] should be thrownBy {
      eof(state)
    }
  }
}
