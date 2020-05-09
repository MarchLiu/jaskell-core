package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:35
 */
object Atom {
  def one[E]: One[E] = {
    new One[E]
  }

  def eof[E, S <: State[E]]: Eof = {
    new Eof
  }

  def pack[T, E](value: T): Return[T, E] = {
    new Return[T, E](value)
  }

  def fail[E](message: String, objects: Any*): Fail[E] = {
    new Fail[E](message, objects)
  }

  def eq[E](item: E): Eq[E] = {
    new Eq[E](item)
  }

  def ne[E](item: E): Ne[E] = {
    new Ne[E](item)
  }

  def oneOf[E](data: Set[E]): OneOf[E] = {
    new OneOf[E](data)
  }

  def noneOf[E](data: Set[E]): NoneOf[E] = {
    new NoneOf[E](data)
  }
}
