package jaskell.parsec
import scala.language.postfixOps
import scala.util.{Try, Success, Failure}

/**
 * Skip p applies the parser p zero or more times, skipping its result.
 *
 * @author mars
 * @version 1.0.0
 */
class Skip[E](val psc: Parsec[E, _]) extends Parsec[E, Unit] {
  val p: Attempt[E, _] = Attempt(psc)

  override def ask(s: State[E]): Try[Unit] = {

    while (true){
      if(p ? s isFailure) {
        return Success()
      }
    }
    Success()
  }
}

object Skip {
  def apply[E](psc: Parsec[E, _]): Skip[E] = new Skip[E](psc)
}
