package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 19:59
 */
trait CouldBeCondition extends Directive {

  def ==(that: CouldBeCondition): Condition = new Condition {

    override def script: String = {
      CouldBeCondition.this.script + " == " + that.script
    }

    override def parameters: Seq[Parameter[_]] = CouldBeCondition.this.parameters ++ that.parameters
  }

  def !=(that: CouldBeCondition): Condition = new Condition {

    override def script: String = {
      CouldBeCondition.this.script + " != " + that.script
    }

    override def parameters: Seq[Parameter[_]] = CouldBeCondition.this.parameters ++ that.parameters
  }

  def >(that: CouldBeCondition): Condition = new Condition {

    override def script: String = {
      CouldBeCondition.this.script + " > " + that.script
    }

    override def parameters: Seq[Parameter[_]] = CouldBeCondition.this.parameters ++ that.parameters
  }

  def <(that: CouldBeCondition): Condition = new Condition {

    override def script: String = {
      CouldBeCondition.this.script + " < " + that.script
    }

    override def parameters: Seq[Parameter[_]] = CouldBeCondition.this.parameters ++ that.parameters
  }

  def >=(that: CouldBeCondition): Condition = new Condition {

    override def script: String = {
      CouldBeCondition.this.script + " >= " + that.script
    }

    override def parameters: Seq[Parameter[_]] = CouldBeCondition.this.parameters ++ that.parameters
  }

  def <=(that: CouldBeCondition): Condition = new Condition {

    override def script: String = {
      CouldBeCondition.this.script + " <= " + that.script
    }

    override def parameters: Seq[Parameter[_]] = CouldBeCondition.this.parameters ++ that.parameters
  }
}
