package jaskell.parsec
import scala.language.postfixOps

/**
 * Skip p applies the parser p zero or more times, skipping its result.
 *
 * @author mars
 * @version 1.0.0
 */
class Skip[E](val psc: Parsec[_, E]) extends Parsec[Unit, E] {
  val p = new Try(psc)

  override def ask(s: State[E]): Either[Exception, Unit] = {

    while (true){
      if(p ? s isLeft) {
        return Right()
      }
    }
    Right()
  }
}

object Skip {
  def apply[E](psc: Parsec[_, E]): Skip[E] = new Skip[E](psc)
}
