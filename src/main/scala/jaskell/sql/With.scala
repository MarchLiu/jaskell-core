package jaskell.sql

import jaskell.sql.With.TableExpression

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/20 18:41
 */
class With(val prefix: Directive = SQL.empty, val name: Name) extends Directive {

  def as(quote: Quote): TableExpression = new TableExpression(prefix = With.this, name = name, quote = quote)

  override def script: String = s"WITH ${name.script} "

  override def parameters: Seq[Parameter[_]] = name.parameters
}

object With {

  class Select(val prefix: Directive) extends jaskell.sql.Select {
    override def script: String = prefix.script + " " + super.script

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ super.parameters
  }

  class Insert(val prefix: Directive) extends jaskell.sql.Insert {
    override def script: String = prefix.script + " " + super.script

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ super.parameters
  }

  class Update(val prefix: Directive, name: Name) extends jaskell.sql.Update(name) {
    override def script: String = prefix.script + " " + super.script

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ super.parameters
  }

  class Delete(val prefix: Directive) extends jaskell.sql.Delete {
    override def script: String = prefix.script + " " + super.script

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ super.parameters
  }

  class TableExpression(val prefix: Directive = SQL.empty, val name: Name, val quote: Quote) extends Directive {
    override def script: String = s"${name.script} AS ${quote.script}"

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ name.parameters ++ quote.parameters

    def and(name: Name, quote: Quote): TableExpression = new TableExpression(this, name, quote)

    def select = new Select(this)

    def update(name: Name) = new Update(this, name)

    def delete = new Delete(this)
  }

}
