package jaskell.croupier

import scala.collection.mutable
import scala.util.Random

/**
 * Rand select by scale weight. Faster if very large cards
 *
 * @param scale  the trait that get item's weight
 * @param random random object, auto new one if no given
 * @tparam T type of card
 */
class ZipScaled[T](scale: Scale[T], val random: Random = new Random()) extends Poker[T] {
  val scaled = new Scaled[(Int, Int)](_._2, random)
  val fair = new Fair[Int](random)

  override def select(cards: Seq[_ <: T]): Option[Int] = {
    if (cards == null || cards.isEmpty) {
      return None
    }
    if (cards.size == 1) {
      return Some(0)
    }

    val group = new mutable.TreeMap[Int, Seq[Int]]()
    val steps = cards.map(scale.weight)
    for (position <- steps.indices) {
      val w = steps(position)
      group.put(w, group.getOrElse(w, Seq()).appended(position))
    }
    val pairs: Seq[(Int, Int)] = (for ((weight, positions) <- group) yield (weight, weight * positions.size)).toSeq
    scaled.select(pairs)
      .map(pairs)
      .map({ weight =>
        group(weight._1)
      })
      .flatMap({ positions =>
        val result = fair.select(positions).map(positions)
        result
      })
  }
}
