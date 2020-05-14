package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 17:36
 */
object Txt {
  def ch(value: Char): Ch = Ch(value)
  def ch(value: Char, caseSensitive: Boolean): Ch = Ch(value, caseSensitive)

  def nch(value: Char): NCh = NCh(value)
  def nch(value: Char, caseSensitive: Boolean): NCh = NCh(value, caseSensitive)

  def chIn(data: String): ChIn = ChIn(data)
  def chIn(data: String, caseSensitive: Boolean): ChIn = ChIn(data, caseSensitive)

  def chNone(data: String): ChNone = ChNone(data)
  def chNone(data: String, caseSensitive: Boolean): ChNone = ChNone(data, caseSensitive)

  def crlf: Crlf = new Crlf

  def decsimal: Decimal = Decimal()

  def udecimal: UDecimal = new UDecimal

  def digit: Digit = new Digit

  def integer: Int = new Int

  def uInteger: UInt = new UInt

  def eol: EndOfLine = new EndOfLine

  def newline: Newline = new Newline

  def space: Space = new Space

  def whitespace: Whitespace = new Whitespace

  def noWhitespace: NoWhitespace = new NoWhitespace

  def skipSpaces: SkipSpaces = new SkipSpaces

  def skipWhiteSpaces: Whitespace = new Whitespace

  def text(value: String): Text = Text(value)
  def text(value: String, caseSensitive: Boolean): Text = Text(value, caseSensitive)

  implicit def stringToText(content: String) : Parsec[String, Char] = Text(content)
  implicit def charToCh(ch: Char) : Parsec[Char, Char] = Ch(ch)

}
