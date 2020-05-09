package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 15:17
 */
class UInt extends Parsec[String, Char]{
  val psc = new Many1[Char, Char](new Digit())
  override def apply[S <: State[Char]](s: S): String = {
    val cs: Seq[Char] = psc(s)
    cs.mkString
  }
}
