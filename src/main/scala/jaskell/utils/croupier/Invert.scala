package jaskell.utils.croupier

import scala.util.Random

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2023/03/28 19:02
 */
class Invert[T](val random: Random) extends Poker[T] {
  override def select(cards: Seq[T]): Option[Int] = {
    if (cards == null || cards.isEmpty) {
      return None
    }
    if (cards.size == 1) {
      return Some(0)
    }

    val range = Math.exp(cards.size)
    val value = Math.log(random.nextDouble() * range)
    Some(Math.floor(value).intValue())
  }
}
