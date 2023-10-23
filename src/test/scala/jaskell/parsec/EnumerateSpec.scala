package jaskell.parsec

import jaskell.parsec.Txt.ch
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/11 10:50
 */
class EnumerateSpec extends AnyFlatSpec with Matchers {

  import Combinator.ahead
  import Txt.{space, text}

  "Simple" should "Expect match ab from abc" in {
    import jaskell.parsec.Combinator.enumerate

    val content: String = "abc"
    val state = State.apply(content)
    val parser: Enumerate[Char, Char] = enumerate(ch('a'), ch('b'))
    parser ! state should be(Set('a', 'b'))
    state.status should be(2)
  }

  "More" should "Check status get result and stop at is" in {
    val content: String = "rf'abcdefg'"
    val state = State.apply(content)
    val parser: Enumerate[Char, Char] = Txt.enumerate("rf")
    val re = parser ! state
    re.contains('f') should be(true)
    re.contains('r') should be(true)
    re.contains('\'') should be(false)
    state.status should be(2)
  }

  "Separate" should "read abc from a|b|c,d" in {
    val content: String = "a|b|c,d"
    val state = State apply content
    val parser = Txt.enumerate("abc", '|')

    val re = parser ! state
    re.contains('a') should be(true)
    re.contains('b') should be(true)
    re.contains('c') should be(true)
    re.contains('|') should be(false)
    re.contains(',') should be(false)
    re.contains('d') should be(false)

  }
}
