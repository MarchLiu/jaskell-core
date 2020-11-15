package jaskell.parsec

/**
 * Atom parsers, like one, eof, return and failed, equals and not equals, etc.
 *
 * @author mars
 * @version 1.0.0
 */
object Atom {
  def one[E]: One[E] = new One[E]

  def eof[E]: Eof[E] = new Eof

  def pack[E, T](value: T): Return[E, T] = new Return[E, T](value)

  def fail[E](message: String, objects: Any*): Fail[E] = new Fail[E](message, objects)

  def eq[E](item: E): Eq[E] = new Eq[E](item)

  def ne[E](item: E): Ne[E] = new Ne[E](item)

  def oneOf[E](data: Set[E]): OneOf[E] = new OneOf[E](data)

  def noneOf[E](data: Set[E]): NoneOf[E] = new NoneOf[E](data)

  def is[E](predicate: Function[E, Boolean]): Is[E] = new Is(predicate)
}
