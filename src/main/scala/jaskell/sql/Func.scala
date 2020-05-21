package jaskell.sql

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/21 11:26
 */
class Func(val name:String, val args:Seq[Directive]) extends Directive with Expression {

  override def script: String = {
    val arguments = args.map(_.script).mkString(", ")
    s"$name($arguments)"
  }

  override def parameters: Seq[Parameter[_]] = args.flatMap(_.parameters)

}

object Func {
  def apply(name: String, parameters: Directive*): Func = {
    new Func(name, parameters)
  }

}
