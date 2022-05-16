package jaskell.batteries

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/05/06 00:41
 */
trait Literal {
  val prefix: String
  val begin: String
  val content: String
  val end: String
  def token: Token
  def literal: String = f"$prefix$begin$content$end"
}
