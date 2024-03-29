package jaskell.parsec

import scala.language.implicitConversions

/**
 * Functions Helper include parsers for Text
 *
 * @author mars
 * @version 1.0.0
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

  def decimal: Decimal = Decimal()

  def udecimal: UDecimal = new UDecimal

  def scNumber: ScNumber = new ScNumber

  def digit: Digit = new Digit

  def letter: Letter = new Letter

  def integer: Int = new Int

  def uInteger: UInt = new UInt

  def eol: EndOfLine = new EndOfLine

  def newline: Newline = new Newline

  def space: Space = new Space

  def whitespace: Whitespace = new Whitespace

  def noWhitespace: NoWhitespace = new NoWhitespace

  def skipSpaces: SkipSpaces = new SkipSpaces

  def skipWhiteSpaces: SkipWhitespaces = new SkipWhitespaces

  def text(value: String): Text = Text(value)

  def text(value: String, caseSensitive: Boolean): Text = Text(value, caseSensitive)

  def chars(chs: String, caseSensitive: Boolean = true): CharsIn = CharsIn(chs, caseSensitive)

  def enumerate(chars: String): Enumerate[Char, Char] = {
    val enumerates = chars.toCharArray.map(c => Ch(c))
    new Enumerate(enumerates)
  }

  def enumerate(chars: String, by: Char): Enumerate[Char, Char] = {
    val enumerates = chars.toCharArray.map(c => Ch(c))
    new Enumerate(enumerates, Ch(by))
  }

  def enumerate(chars: String, by: String): Enumerate[Char, Char] = {
    val enumerates = chars.toCharArray.map(c => Ch(c))
    new Enumerate(enumerates, Text(by))
  }

  def enumerate(chars: String, by: Parsec[Char, _]): Enumerate[Char, Char] = {
    val enumerates = chars.toCharArray.map(c => Ch(c))
    new Enumerate(enumerates, by)
  }

  def mkString: Binder[Char, Seq[Char], String] = new MkString

  implicit def stringToText(content: String): Parsec[Char, String] = Text(content)

  implicit def charToCh(ch: Char): Parsec[Char, Char] = Ch(ch)

  implicit def stringToState(content: String): TxtState = TxtState(content)

}
