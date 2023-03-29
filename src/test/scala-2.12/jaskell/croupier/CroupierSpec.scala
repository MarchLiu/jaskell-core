package jaskell.croupier

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable
import scala.util.Random

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2023/03/27 12:40
 */
class CroupierSpec extends AnyFlatSpec with Matchers {
  val elements: Seq[Int] = 0 to 100

  "Play" should "select every element fair" in {
    val counter: mutable.Map[Int, Int] = new mutable.TreeMap[Int, Int]()
    val croupier = Croupier.fair[Int]
    for (_ <- 0 to 100000) {
      val element = croupier.randSelect(elements)
      element should not be None
      element.foreach { value =>
        counter.put(value, counter.getOrElse(value, 0) + 1)
      }
    }

    for ((key, value) <- counter) {
      info(s"fair select $key times ${value}")
    }
  }

  it should "select more elements while more nearer front" in {
    val counter: mutable.Map[Int, Int] = new mutable.TreeMap[Int, Int]()
    val croupier = Croupier.damping[Int]
    for (_ <- 0 to 100000) {
      val element = croupier.randSelect(elements)
      element should not be None
      element.foreach { value =>
        counter.put(value, counter.getOrElse(value, 0) + 1)
      }
    }
    for ((key, value) <- counter) {
      info(s"damping select $key times ${value}")
    }
  }

  it should "select more elements while more nearer back" in {
    val counter: mutable.Map[Int, Int] = new mutable.TreeMap[Int, Int]()
    val croupier = Croupier.invert[Int]
    for (_ <- 0 to 100000) {
      val element = croupier.randSelect(elements)
      element should not be None
      element.foreach { value =>
        counter.put(value, counter.getOrElse(value, 0) + 1)
      }
    }

    for ((key, value) <- counter) {
      info(s"invert select $key times ${value}")
    }
  }

  "Deal" should "draw more while more near front and left rest" in {
    val buffer:Seq[Int] = 0 until 1000
    val croupier = Croupier.damping[Int]
    val counter: mutable.Map[Int, Int] = new mutable.TreeMap[Int, Int]()
    for (_ <- 0 until 10) {
      val (items, rest) = croupier.randDeal(buffer, 10)
      items.size should be(10)
      rest.size should be(990)
      items foreach { item =>
        counter.put(item, counter.getOrElse(item, 0) + 1)
      }
    }

    for ((item, count) <- counter) {
      info(s"$item be draw $count times")
    }
  }

  "Rank or Weight" should "rand select items by weight" in {
    val random = new Random()
    val buffer: Seq[(Int, Int)] = (0 until 10).map(idx => (idx, random.nextInt(10)))

    val counter = new mutable.TreeMap[Int, (Int, Int)]()
    val croupier: Croupier[(Int, Int)] = Croupier.byWeight(_._2)
    for (_ <- 0 until 1000) {
      val item = croupier.randSelect(buffer)
      item should not be (None)
      item foreach { value =>
        val old = counter.getOrElse(value._1, (value._2, 0))
        counter.put(value._1, old.copy(_2 = old._2 + 1))
      }
    }
    for((idx, (weight, count)) <- counter) {
      println(s"weight croupier rand select item[$idx] weight $weight times $count")
    }
  }

  it should "rand select items by rank" in {
    val random = new Random()
    val buffer: Seq[(Int, Double)] = (0 until 10).map(idx => (idx, random.nextDouble() * 10))

    val counter = new mutable.TreeMap[Int, (Double, Int)]()
    val croupier: Croupier[(Int, Double)] = Croupier.byRank(_._2)
    for (_ <- 0 until 1000) {
      val item = croupier.randSelect(buffer)
      item should not be (None)
      item foreach { value =>
        val old = counter.getOrElse(value._1, (value._2, 0))
        counter.put(value._1, old.copy(_2 = old._2 + 1))
      }
    }
    for ((idx, (weight, count)) <- counter) {
      println(s"weight croupier rand select item[$idx] rank $weight times $count")
    }
  }

  it should "rand select items by zip scale" in {
    val random = new Random()
    val buffer: Seq[(Int, Int)] = (0 until 100).map(idx => (idx, random.nextInt(10)))

    val counter = new mutable.TreeMap[Int, (Int, Int)]()
    val croupier: Croupier[(Int, Int)] = Croupier.byZipScaler(_._2)
    for (_ <- 0 until 10000) {
      val item = croupier.randSelect(buffer)
      item should not be (None)
      item foreach { value =>
        val old = counter.getOrElse(value._1, (value._2, 0))
        counter.put(value._1, old.copy(_2 = old._2 + 1))
      }
    }
    for ((idx, (weight, count)) <- counter) {
      println(s"weight croupier rand select item[$idx] weight $weight times $count")
    }
  }
}
