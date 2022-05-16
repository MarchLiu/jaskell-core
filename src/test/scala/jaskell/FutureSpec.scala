package jaskell

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Future

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2021/02/06 15:58
 */
class FutureSpec extends AsyncFlatSpec with Matchers {
  import jaskell.Monad.Implicits._

  val future: Future[Double] = Future("success") *> Future(3.14) <:> { value => value * 2*2} <* Future("Right")

  "Pi" should "success a area of r=2 circle" in {
    future.map(value => value should be (3.14 * 2 * 2))
  }
}
