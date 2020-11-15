package jaskell.parsec

import java.io.EOFException

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 10:02
 */
class EqSpec extends AnyFlatSpec with Matchers{
  "Eq" should "Test" in {
    val state = State("hello")

    val eof = new Eof[Char]

    (Eq('h') ? state) should be (Success('h'))
    (Eq('e') apply state) should be ('e')
    (Eq('l') apply state) should be ('l')
    (Eq('l') apply state) should be ('l')
    a[ParsecException] should be thrownBy {
      Attempt(Eq('l')) apply state
    }
    (Eq('o') apply state) should be ('o')
    eof apply state
    a[EOFException] should be thrownBy {
      Eq('o') apply state
    }
  }

}
