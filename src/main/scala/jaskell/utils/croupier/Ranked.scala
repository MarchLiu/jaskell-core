package jaskell.utils.croupier

import scala.util.Random

/**
 * Rand select by rank score.
 *
 * @param ranker the trait that get item's rank
 * @param random random object, auto new one if no given
 * @tparam T type of card
 */
class Ranked[T](ranker: Ranker[T], val random: Random = new Random()) extends Poker[T] {
  override def select(cards: Seq[T]): Option[Int] = {
    if (cards == null || cards.isEmpty) {
      return None
    }
    if (cards.size == 1) {
      return Some(0)
    }

    val steps: Seq[Double] = cards
      .map(ranker.rank)
      .foldLeft(Seq[Double](0)) { (result: Seq[Double], r: Double) =>
        result.appended(r + result.last)
      }
    val top = steps.last
    val score = random.nextDouble() * top
    for (idx <- cards.indices) {
      if (steps(idx) <= score && steps(idx + 1) > score) {
        return Some(idx)
      }
    }
    Some(cards.size - 1)
  }
}
