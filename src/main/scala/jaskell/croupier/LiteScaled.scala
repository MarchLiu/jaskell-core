package jaskell.croupier

import scala.util.Random

/**
 * Rand select by scale weight.
 * Lite scale generate a index list size is sum(cards weight).
 * Lite scale is simple, but maybe use too large buffer.
 *
 * @param scale  the trait that get item's weight
 * @param random random object, auto new one if no given
 * @tparam T type of card
 */
class LiteScaled[T](scale: Scale[T], val random: Random = new Random()) extends Poker[T] {
  override def select(cards: Seq[T]): Option[Int] = {
    if (cards == null || cards.isEmpty) {
      return None
    }
    if (cards.size == 1) {
      return Some(0)
    }

    val steps: Seq[Int] = for {
      idx <- cards.indices
      w = scale.weight(cards(idx))
      value <- Seq.fill(w)(idx)
    } yield value

    val top = steps.size
    val score = random.nextInt(top)
    Some(steps(score))
  }
}
