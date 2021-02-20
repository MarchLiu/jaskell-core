package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.parsec.Txt._

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 19:52
 */
class NewLineSpec extends AnyFlatSpec with Matchers{
  "NewLine" should "Test new line" in {
    val state = State("\r\n");

    val enter = new Newline()

    val c = for{
      _ <- ch('\r') ask state
      re <- enter ask state
    } yield re

    c.isSuccess should be (true)
    c foreach(re => re should be ('\n'))
  }

  "CRLF" should "test \\r\\n match" in {
    val state = State("\r\n");

    val parser = crlf

    parser ! state should be ("\r\n")
  }
}
