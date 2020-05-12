package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Combinator.skip
import jaskell.parsec.Txt.chIn

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 22:21
 */
class SkipSpec extends AnyFlatSpec with Matchers{
  "Once" should "Run match once" in {
    val state = State("hello World");
    val s = skip(Eq('h'));
    s apply state

    state.status should be (1)
  }

  "Stop1" should "Stop at start" in {
    val state = State("hello World")
    val s = skip(Eq('e'))
    s apply state
    state.status should be (0)
  }

  "Spaces" should "Skip some spaces or tabs" in {
    val state = State("\t\t \thello World")
    val spaces = skip(chIn(" \t"))
    spaces apply state

    state.status should be (4)
  }

  "Nothing" should "Skip nothing" in {
    val state = State("\nhello World")
    val spaces = skip(chIn(" \t"))
    spaces apply state
    state.status should be (0)
  }


}
