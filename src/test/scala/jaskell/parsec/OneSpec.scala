package jaskell.parsec

import java.io.EOFException

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Atom.one

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 20:48
 */
class OneSpec extends AnyFlatSpec with Matchers{
  "Simple" should "Match one item" in {
    val state = State("hello")
    val parser = one[Char]
    (parser apply state) should be ('h')
    (parser apply state) should be ('e')
    (parser apply state) should be ('l')
    (parser apply state) should be ('l')
    (parser apply state) should be ('o')
    a[EOFException] should be thrownBy {
      parser(state)
    }
  }
}
