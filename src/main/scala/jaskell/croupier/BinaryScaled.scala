package jaskell.croupier

import scala.util.Random

/**
 * Rand select by scale weight.
 *
 * @param scale  the trait that get item's weight
 * @param random random object, auto new one if no given
 * @tparam T type of card
 */
class BinaryScaled[T](scale: Scale[T], val random: Random = new Random()) extends Poker[T] {
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
        result :+ (result.last + r)
      }
    val top = steps.last
    val score = random.nextInt(top)

    var idx: Int = steps.size / 2
    var low = 0
    var high = steps.size - 1
    while (true) {
      if (steps(idx) <= score && steps(idx + 1) > score) {
        return Some(idx)
      }
      if (score < steps(idx)) {
        high = idx
        idx -= math.max(idx / 2, 1)
      } else {
        low = idx
        idx += math.max((high - idx) / 2, 1)
      }
    }

    Some(cards.size - 1)
  }
}
