package jaskell.parsec

class PIterator[E, T](val p: Parsec[E, T], val state: State[E]) extends scala.collection.Iterator[T] {
    val psc = p.attempt
    var item = psc ? state
    def hasNext = item.isSuccess
    def next(): T = {
        val re = item.get
        item = psc ? state
        return re
    }
}