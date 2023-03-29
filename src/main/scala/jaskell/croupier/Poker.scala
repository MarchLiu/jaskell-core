package jaskell.croupier

import scala.util.Random

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2023/03/28 19:04
 */
trait Poker[T] {
  def select(cards: Seq[T]): Option[Int];
}

object Poker {
  def fair = new Fair(new Random())

  def fair(random: Random) = new Fair(random)

  def fair(seed: Long) = new Fair(new Random(seed))

  def damping = new Damping(new Random())

  def damping(random: Random) = new Damping(random)

  def damping(seed: Long) = new Damping(new Random(seed))

  def invert = new Invert(new Random())

  def invert(random: Random) = new Invert(random)

  def invert(seed: Long) = new Invert(new Random(seed))
}