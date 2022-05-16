package jaskell.parsec

import scala.util.{Try}

class Binder[A, B, C](val parsec: Parsec[A, B], val f: B => Parsec[A, C]) extends Parsec[A, C] {
    def apply(state: State[A]): Try[C] = for {
            a <- parsec(state)
            b <- f(a)(state)
        } yield b
} 