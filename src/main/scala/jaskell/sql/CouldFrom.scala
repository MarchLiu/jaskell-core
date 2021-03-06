package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 16:03
 */
trait CouldFrom extends Directive {

  def from(f: CouldBeFrom): From = new From {
    override def script: String = CouldFrom.this.script + " FROM " + f.script

    override def parameters: Seq[Parameter[_]] = CouldFrom.this.parameters ++ f.parameters
  }

  def from(f: String): From = new From {
    override def script: String = CouldFrom.this.script + " FROM " + f

    override def parameters: Seq[Parameter[_]] = CouldFrom.this.parameters
  }

}
