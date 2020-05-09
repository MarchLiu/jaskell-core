package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:36
 */
object Txt {
  def ch(value: Char) = new Ch(value)

  def chIn(data: String) = new ChIn(data)

  def chNone(data: String) = new ChNone(data)

  def crlf = new Crlf

  def decimal = new Decimal

  def udecimal = new UDecimal

  def digit = new Digit

  def integer = new Int

  def uinteger = new UInt

  def eol = new EndOfLine

  def newline = new Newline

  def space = new Space

  def whitespace = new Whitespace

  def skipSpaces = new SkipSpaces

  def skipWhiteSpaces = new Whitespace

  def text(value: String) = new Text(value)

  def joining = new JoinText

  def joinChars = new JoinCharacters

}
