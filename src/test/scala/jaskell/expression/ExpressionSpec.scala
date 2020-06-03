package jaskell.expression

import jaskell.parsec.{Decimal, State, TxtState, UDecimal}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import jaskell.expression.parsers.{Num, Parser}

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/06/03 15:09
 */
class ExpressionSpec extends AnyFlatSpec with Matchers {

  "Number" should "match a number" in {
    val st: TxtState = State("3.14")
    val p = new Num
    val exp = p(st)
    exp.eval should be(3.14)
  }

  "Basic" should "match a number" in {
    val st: TxtState = State("3.14")
    val p = new Parser
    val exp = p(st)
    exp.eval should be(3.14)
  }

  "Add" should "match a add expression" in {
    val st: TxtState = State("3.14+2.53")
    val p = new Parser
    val exp = p(st)
    exp.eval should be(5.67)
  }

  "Sub" should "match a sub expression" in {
    val st: TxtState = State("179- 8")
    val p = new Parser
    val exp = p(st)
    exp.eval should be(171)
  }

  "Product" should "match a product expression" in {
    val st: TxtState = State("8 * -8")
    val p = new Parser
    val exp = p(st)
    exp.eval should be(-64)
  }

  "Divide" should "match a divide expression" in {
    val st: TxtState = State("128/8")
    val p = new Parser
    val exp = p(st)
    exp.eval should be(16)
  }

  "Quote" should "match a quoted expression" in {
    val st: TxtState = State("(128/8)")
    val p = new Parser
    val exp = p(st)
    exp.eval should be(16)
  }

  "Priorities" should "compute a ploy expressio right to left" in {
    val st = State("7 + 15 * 3")
    val p = new Parser
    val re = p(st)
    val exp = re.makeAst
    exp.eval should be(52)
  }

  "Priorities flow" should "compute a ploy expression left to right" in {
    val st = State("5 * 3 + 7")
    val p = new Parser
    val re = p(st)
    val exp = re.makeAst
    exp.eval should be(22)
  }

  "Ploy Quote" should "compute a ploy expression include quote" in {
    val st = State("5 * (3 + 7)")
    val p = new Parser
    val re = p(st)
    val exp = re.makeAst
    exp.eval should be(50)
  }

  "Ploy Complex" should "compute a complex ploy expression include quote" in {
    val st = State("5 * (3 + 7) - 22.5")
    val p = new Parser
    val re = p(st)
    val exp = re.makeAst
    exp.eval should be(27.5)
  }

  "More Complex" should "compute a complex ploy expression has double sub" in {
    val st = State("5 * (3 + 7) - -22.5")
    val p = new Parser
    val re = p(st)
    val exp = re.makeAst
    exp.eval should be(72.5)
  }
}
