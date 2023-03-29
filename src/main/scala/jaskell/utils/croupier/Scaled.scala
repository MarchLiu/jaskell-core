package jaskell.utils.croupier

import scala.util.Random

/**
 * Rand select by scale weight.
 *
 * @param scale  the trait that get item's weight
 * @param random random object, auto new one if no given
 * @tparam T type of card
 */
class Scaled[T](scale: Scale[T], val random: Random = new Random()) extends Poker[T] {
  override def select(cards: Seq[T]): Option[Int] = {
    if (cards == null || cards.isEmpty) {
      return None
    }
    if (cards.size == 1) {
      return Some(0)
    }

    val steps: Seq[Int] = cards
      .map(scale.weight)
      .foldLeft(Seq[Int](0)) { (result: Seq[Int], r: Int) =>
        result.appended(result.last + r)
      }
    val top = steps.last
    val score = random.nextInt(top)
    for (idx <- cards.indices) {
      if (steps(idx) <= score && steps(idx + 1) > score) {
        return Some(idx)
      }
    }
    Some(cards.size - 1)
  }
}
