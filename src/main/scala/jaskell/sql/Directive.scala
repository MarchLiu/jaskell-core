package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 15:14
 */
trait Directive {
  def script: String

  def parameters: Seq[Parameter[_]]
}
