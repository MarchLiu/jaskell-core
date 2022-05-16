package jaskell.parsec


/**
 * Parsec Combinators
 *
 * @author mars
 * @version 1.0.0
 */
object Combinator {
  import jaskell.Monad.Implicits._
  import jaskell.parsec.Parsec.Implicits._

  def attempt[E, T](parser: Parsec[E, T]): Attempt[E, T] = {
    new Attempt[E, T](parser)
  }

  def ahead[E, T](parser: Parsec[E, T]): Ahead[E, T] = {
    new Ahead[E, T](parser)
  }

  def choice[E, T](parsers: Parsec[E, T]*): Choice[E, T] = {
    new Choice[E, T](parsers)
  }

  def many[E, T](parser: Parsec[E, T]): Parsec[E, Seq[T]] = {
    new Many[E, T](parser)
  }

  def many1[E, T](parser: Parsec[E, T]): Parsec[E, Seq[T]] = {
    new Many1[E, T](parser)
  }

  def manyTill[E, T, L](parser: Parsec[E, T], end: Parsec[E, L]): ManyTill[E, T, L] = {
    new ManyTill[E, T, L](parser, end)
  }

  def skip[E](parser: Parsec[E, _]): Parsec[E, Unit] = {
    new Skip[E](parser)
  }

  def skip1[E](parser: Parsec[E, _]): Parsec[E, Unit] = {
    new Skip1[E](parser)
  }

  def sepBy[T, Sep, E](parser: Parsec[E, T], by: Parsec[E, Sep]): Parsec[E, Seq[T]] = {
    new SepBy[E, T](parser, by)
  }

  def sepBy1[E, T](parser: Parsec[E, T], by: Parsec[E, _]): Parsec[E, Seq[T]] = {
    new SepBy1[E, T](parser, by)
  }

  def find[E, T](parser: Parsec[E, T]): Find[E, T] = {
    new Find[E, T](parser)
  }

  def between[E, T](open: Parsec[E, _], close: Parsec[E, _], parser: Parsec[E, T]): Parsec[E, T] = {
    new Between[E, T](open, close, parser)
  }

  def chars(str: String): Parsec[Char, String] = Chars(str)

  implicit class BuiltIn[E, T](p: Parsec[E, T]) {
    def attempt: Parsec[E, T] = Attempt(p)

    def ahead: Parsec[E, T] = Ahead(p)

    def or(other: Parsec[E, T]): Parsec[E, T] = Attempt(p) <|> other

    def many: Parsec[E, Seq[T]] = Many(p)

    def many1: Parsec[E, Seq[T]] = Many1(p)

    def manyTill(end: Parsec[E, _]): Parsec[E, Seq[T]] = ManyTill(p, end)

    def skip: Parsec[E, Unit] = Skip(p)

    def skip1: Parsec[E, Unit] = Skip1(p)

    def sepBy(by: Parsec[E, _]): Parsec[E, Seq[T]] = SepBy(p, by)

    def sepBy1(by: Parsec[E, _]): Parsec[E, Seq[T]] = SepBy1(p, by)

    def find: Parsec[E, T] = Find(p)

    def between(open: Parsec[E, _], close: Parsec[E, _]): Parsec[E, T] = Between(open, close, p)

    def opt: Parsec[E, Option[T]] = (p >>= {v => Return(Some(v))}) <|> Return(None)


  }
}
