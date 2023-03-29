package jaskell.croupier

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Random

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2023/03/26 23:55
 */
class Croupier[T](val poker: Poker[T]) {
  def randIndex(seq: Seq[T]): Option[Int] = poker.select(seq)

  def randDraw(seq: Seq[T]): (Option[T], Seq[T]) =
    poker.select(seq) match {
      case Some(idx) =>
        (Some(seq(idx)), seq.take(idx) ++ seq.drop(idx + 1))
      case None =>
        (None, seq)
    }

  def randDraw(list: mutable.ListBuffer[T]): Option[T] =
    poker.select(list.toSeq)
      .map(idx => list.remove(idx))

  def randDraw(data: mutable.ListBuffer[T], size: Int): Seq[T] = {
    if (data == null) {
      Seq()
    }

    for {
      _ <- 0 until Math.min(data.size, size)
      element <- randDraw(data)
    } yield element
  }

  def randSelect(seq: Seq[T], size: Int): Seq[T] = {
    randDeal(seq, size)._1
  }

  def randSelect(seq: Seq[T]): Option[T] = randIndex(seq).map(seq)

  def randDeal(seq: Seq[T], size: Int): (Seq[T], Seq[T]) = {
    @tailrec
    def helper(data: Seq[T], seq: Seq[T], size: Int): (Seq[T], Seq[T]) = {
      if (size == 0 || seq.isEmpty) {
        return (data, seq)
      }
      randDraw(seq) match {
        case (Some(element), rest: Seq[T]) =>
          helper(data :+ element, rest, size - 1)
        case (None, _) =>
          (data, seq)
      }
    }

    helper(Seq(), seq, size)
  }
}

object Croupier {

  def fair[T]: Croupier[T] = new Croupier(new Fair(new Random()))

  def fair[T](random: Random): Croupier[T] = new Croupier[T](new Fair(random))

  def fair[T](seed: Long): Croupier[T] = new Croupier[T](new Fair(new Random(seed)))

  def damping[T]: Croupier[T] = new Croupier(new Damping(new Random()))

  def damping[T](random: Random): Croupier[T] = new Croupier(new Damping(random))

  def damping[T](seed: Long): Croupier[T] = new Croupier(new Damping(new Random(seed)))

  def invert[T]: Croupier[T] = new Croupier(new Invert(new Random()))

  def invert[T](random: Random): Croupier[T] = new Croupier[T](new Invert(random))

  def invert[T](seed: Long): Croupier[T] = new Croupier[T](new Invert(new Random(seed)))

  def byWeight[T](scale: Scale[T]) = new Croupier[T](new Scaled[T](scale, new Random()))

  def byWeight[T](scale: Scale[T], random: Random) = new Croupier[T](new Scaled[T](scale, random))

  def byWeight[T](scale: Scale[T], seed: Long) = new Croupier[T](new Scaled[T](scale, new Random(seed)))

  def byRank[T](ranker: Ranker[T]) = new Croupier[T](new Ranked[T](ranker, new Random()))

  def byRank[T](ranker: Ranker[T], random: Random) = new Croupier[T](new Ranked[T](ranker, random))

  def byRank[T](ranker: Ranker[T], seed: Long) = new Croupier[T](new Ranked[T](ranker, new Random(seed)))

  def byZipScaler[T](scale: Scale[T]) = new Croupier[T](new ZipScaled[T](scale, new Random()))

  def byZipScaler[T](scale: Scale[T], random: Random) = new Croupier[T](new ZipScaled[T](scale, random))

  def byZipScaler[T](scale: Scale[T], seed: Long) = new Croupier[T](new ZipScaled[T](scale, new Random(seed)))
}



















