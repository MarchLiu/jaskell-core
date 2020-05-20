package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/19 17:17
 */
class Empty extends Directive {
  override def script: String = ""

  override def parameters: Seq[Parameter[_]] = Seq.empty
}
