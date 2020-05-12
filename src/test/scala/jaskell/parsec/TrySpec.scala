package jaskell.parsec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 22:33
 */
class TrySpec extends AnyFlatSpec with Matchers {
  "Simple" should "Run a simple test" in {
    val data = Seq("Hello", "World")

    val state = State[String](data)
    val idx = state.status
    val tryIt = new Try[String, String](Eq("Hello"))

    val re = tryIt apply state

    re should be ("Hello")
    idx should not be (state.status)
  }

  "Fail" should "Run a failed test" in {
    val data = Seq("Hello", "World")
    val state = State[String](data)
    val idx = state.status
    val tryIt = new Try[String, String](new Eq[String]("hello"))

    a[ParsecException] should be thrownBy {
      tryIt apply state
    }
    idx should be (state.status)

    val tryIti = new Try[String, String](Parsec[String, String] { s =>
      val content = s.next()
      if(content.toLowerCase == "hello") {
        content
      } else {
        throw new ParsecException(s.status,
          s"expect a word match [Hello] and case insensitive but get [${content}]")
      }
    })

    val re = tryIti apply state
    re should be ("Hello")
  }
}
