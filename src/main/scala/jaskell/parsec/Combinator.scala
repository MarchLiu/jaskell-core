package jaskell.parsec

/**
 * Parsec Combinators
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:35
 */
object Combinator {
  def attempt[T, E](parser: Parsec[T, E]): Try[T, E] = {
    new Try[T, E](parser)
  }

  def ahead[T, E](parser: Parsec[T, E]): Ahead[T, E] = {
    new Ahead[T, E](parser)
  }

  def choice[T, E](parsers: Parsec[T, E]*): Choice[T, E] = {
    new Choice[T, E](parsers)
  }

  def many[T, E](parser: Parsec[T, E]): Many[T, E] = {
    new Many[T, E](parser)
  }

  def many1[T, E](parser: Parsec[T, E]): Many1[T, E] = {
    new Many1[T, E](parser)
  }

  def manyTill[T, L, E](parser: Parsec[T, E], end: Parsec[L, E]): ManyTill[T, L, E] = {
    new ManyTill[T, L, E](parser, end)
  }

  def skip[E](parser: Parsec[_, E]): Skip[E] = {
    new Skip[E](parser)
  }

  def skip1[E](parser: Parsec[_, E]): Skip1[E] = {
    new Skip1[E](parser)
  }

  def sepBy[T, Sep, E](parser: Parsec[T, E], by: Parsec[Sep, E]): SepBy[T, E] = {
    new SepBy[T, E](parser, by)
  }

  def sepBy1[T, E](parser: Parsec[T, E], by: Parsec[_, E]): SepBy1[T, E] = {
    new SepBy1[T, E](parser, by)
  }

  def find[T, E](parser: Parsec[T, E]): Find[T, E] = {
    new Find[T, E](parser)
  }

  def between[T, E](open: Parsec[_, E], close: Parsec[_, E], parser: Parsec[T, E]): Parsec[T, E] = {
    new Between[T, E](open, close, parser)
  }

}
