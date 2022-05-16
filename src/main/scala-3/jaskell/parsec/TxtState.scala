package jaskell.parsec

import scala.util.{Try, Failure}
import scala.collection.{SortedMap, mutable}

/**
 * Txt State extends Common State trait. It special for text content analyst.
 * Txt state could mark a status point in which line.
 *
 * @author Mars Liu
 * @version 1.0.0
 */
class TxtState(val txt: String, val newLine:Char = '\n') extends CommonState[Char](content=txt.toCharArray.toSeq):
  val lines: SortedMap[scala.Int, scala.Int] = 
    val result = new mutable.TreeMap[scala.Int, scala.Int]();
    result.put(0, 0);
    for(index <- Range(0, txt.length)){
      val c = txt.charAt(index)
      if(c == newLine) {
        val lastIndex = result.lastKey
        result.put(lastIndex, index);
        if(index < txt.length - 1) {
          result.put(index+1, index+1)
        }
      }
    }
    result

  def lineByIndex(index: scala.Int): scala.Int = 
    var i = 0
    for(idx <- lines.keys){
      if(idx <= index && index <= lines(idx)) {
        return i
      }
      i += 1
    }
    -1

object TxtState:
  def parse(txt: String, newLine: Char='\n'): TxtState = new TxtState(txt, newLine)
