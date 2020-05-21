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

  override def script: String = s"WITH"

  override def parameters: Seq[Parameter[_]] = name.parameters
}

object With {

  def recursive: Recursive = new Recursive

  def apply(n: Name): With = new With(name = n)

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

  class TableExpression(val prefix: Directive = SQL.empty, val name: Name, val quote: Quote, val sep:String = " ") extends Directive {
    override def script: String = s" ${prefix.script}$sep${name.script} AS ${quote.script}".trim

    override def parameters: Seq[Parameter[_]] = prefix.parameters ++ name.parameters ++ quote.parameters

    def and(name: Name, quote: Quote): TableExpression = new TableExpression(this, name, quote, ", ")

    def select = new Select(this)

    def update(name: Name) = new Update(this, name)

    def delete = new Delete(this)
  }

  class Recursive extends Directive {

    override def script: String = s"WITH RECURSIVE"

    override def parameters: Seq[Parameter[_]] = Seq.empty

    def as(name: Name, quote: Quote): TableExpression = new TableExpression(this, name, quote)

    def as(name: Name, query: Query): TableExpression = new TableExpression(this, name, Quote(query))
  }

}
