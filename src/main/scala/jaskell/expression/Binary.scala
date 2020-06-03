package jaskell.expression

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/02 21:33
 */
abstract class Binary(var left: Expression, var right: Expression) extends Expression {
  def priority: Int

  override def makeAst: Expression = {
    right match {
      case r: Binary =>
        if (this.priority >= r.priority) {
          val lx = r.left
          this.right = lx
          r.left = this
          r.right = r.right.makeAst
          r
        } else {
          this
        }
      case _ =>
        this.right = this.right.makeAst
        this
    }
  }
}
