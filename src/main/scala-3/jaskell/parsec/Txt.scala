package jaskell.parsec

import scala.util.{Try, Success}

/**
 * Functions Helper include parsers for Text
 *
 * @author mars
 * @version 1.0.0
 */
object Txt:
  def ch(value: Char): Ch = Ch(value)
  def ch(value: Char, caseSensitive: Boolean): Ch = Ch(value, caseSensitive)
  def nch(value: Char): NCh = NCh(value)
  def nch(value: Char, caseSensitive: Boolean): NCh = NCh(value, caseSensitive)

  def chIn(data: String): ChIn = ChIn(data)
  def chIn(data: String, caseSensitive: Boolean): ChIn = ChIn(data, caseSensitive)
  def chNone(data: String): ChNone = ChNone(data)
  def chNone(data: String, caseSensitive: Boolean): ChNone = ChNone(data, caseSensitive)

  def digit: Digit = new Digit
  def letter: Letter = Letter()

  def integer: Int = new Int
  def uInteger: UInt = new UInt

  def decimal: Decimal = Decimal()
  def udecimal: UDecimal = new UDecimal
  def scNumber: ScNumber = new ScNumber

  def eol: EndOfLine = new EndOfLine
  def newline: Newline = new Newline
  def crlf: Crlf = new Crlf
  def space: Space = new Space
  def whitespace: Whitespace = new Whitespace
  def noWhitespace: NoWhitespace = new NoWhitespace
  
  def skipSpaces: SkipSpaces = new SkipSpaces
  def skipWhiteSpaces: SkipWhitespaces = new SkipWhitespaces

  def text(value: String): Text = Text(value)
  def text(value: String, caseSensitive: Boolean): Text = Text(value, caseSensitive)

  def mkString: Seq[Char] => Parsec[Char, String] = data => state => Success(data.mkString)

