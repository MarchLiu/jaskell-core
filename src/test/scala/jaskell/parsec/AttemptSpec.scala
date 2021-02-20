package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 22:33
 */
class AttemptSpec extends AnyFlatSpec with Matchers {
  "Simple" should "Run a simple test" in {
    val data = Seq("Hello", "World")

    val state = State[String](data)
    val idx = state.status
    val tryIt = new Attempt[String, String](Eq("Hello"))

    val re = tryIt ! state

    re should be("Hello")
    idx should not be (state.status)
  }

  "Fail" should "Run a failed test" in {
    val data = Seq("Hello", "World")
    val state = State[String](data)
    val idx = state.status
    val tryIt = new Attempt[String, String](new Eq[String]("hello"))

    a[ParsecException] should be thrownBy {
      tryIt ! state
    }
    idx should be(state.status)

    val tryIti = new Attempt[String, String](Parsec[String, String] { s =>
      s.next() flatMap { content =>
        if (content.toLowerCase == "hello") {
          Success(content)
        } else {
          state.trap(f"expect a word match [Hello] and case insensitive but get [${content}]")
        }
      }
    })

    val re = tryIti ! state
    re should be("Hello")
  }
}
