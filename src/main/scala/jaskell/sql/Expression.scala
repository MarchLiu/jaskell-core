package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/18 15:50
 */
trait Expression extends Directive with CouldBeQuote with CouldIn with CouldBeColumn {
  def +(other: Expression): Expression = new Expression {
    override def script: String = s"${Expression.this.script} + ${other.script}"

    override def parameters: Seq[Parameter[_]] = Expression.this.parameters ++ other.parameters
  }

  def -(other: Expression): Expression = new Expression {
    override def script: String = s"${Expression.this.script} - ${other.script}"

    override def parameters: Seq[Parameter[_]] = Expression.this.parameters ++ other.parameters
  }

  def *(other: Expression): Expression = new Expression {
    override def script: String = s"${Expression.this.script} * ${other.script}"

    override def parameters: Seq[Parameter[_]] = Expression.this.parameters ++ other.parameters
  }

  def /(other: Expression): Expression = new Expression {
    override def script: String = s"${Expression.this.script} / ${other.script}"

    override def parameters: Seq[Parameter[_]] = Expression.this.parameters ++ other.parameters
  }
}
