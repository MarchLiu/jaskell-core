package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/12 18:57
 */
class LetterSpec extends AnyFlatSpec with Matchers {

  import Txt._

  "Letter" should "test a char is letter" in {
    val content: State[Char] = "a"
    val parser = letter

    parser ? content should be (Success('a'))
  }

  "no" should "test a char is letter" in {
    val content: State[Char] = "22"
    val parser = letter

    (parser ? content).isFailure should be (true)
  }

}
