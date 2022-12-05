package test

sealed trait Cons[+T]

case class ConsT[T](head: T, tag: Int, var tail: Cons[T]) extends Cons[T]

case object ConsZ extends Cons[Nothing]

trait Queue[+T] {
  self =>

  val size: Int
  val cons: Cons[T]
  val last: Cons[T]

  def isEmpty: Boolean = size <= 0

  def insert[G >: T](g: G): Queue[G] = {
    val s:Int = size + 1
    self.cons match {
      case consT: ConsT[G] =>
        self.last match {
          case lastT: ConsT[G] =>
            val newTail = ConsT(g, s, tail = ConsZ)
            lastT.tail = newTail
            new Queue[G] {
              override val size: Int = size
              override val cons: Cons[G] = consT
              override val last: Cons[G] = newTail
            }
          case ConsZ =>
            val newTail = ConsT(g, s, tail = ConsZ)
            consT.tail = newTail
            new Queue[G] {
              override val size: Int = s
              override val cons: Cons[G] = consT
              override val last: Cons[G] = newTail
            }
        }
      case ConsZ =>
        val consT = ConsT(g, s, tail = ConsZ)
        new Queue[G] {
          override val size: Int = s
          override val cons: Cons[G] = consT
          override val last: Cons[G] = consT.tail
        }
    }
  }

  def remove: (Option[T], Queue[T]) = cons match {
    case ConsT(head, tag, tail) if self.size > 0 => (Option(head), new Queue[T] {
      override val size: Int = self.size - 1
      override val cons: Cons[T] = tail
      override val last: Cons[T] = tail match {
        case s: ConsT[T] => self.last
        case _ => ConsZ
      }
    })
    case _ => (Option.empty, self)
  }
}

object Queue {
  def empty[T]: Queue[T] = new Queue[T] {
    override val size: Int = 0
    override val cons: Cons[T] = ConsZ
    override val last: Cons[T] = ConsZ
  }
}

object QueueTest extends App {

  val ququeInt = Queue.empty[Int].insert(2).insert(5).insert(6).insert(8).insert(10)
  val q1 = ququeInt.remove
  val q2 = q1._2.remove
  val q3 = q2._2.remove
  val q4 = q3._2.remove
  val qq4 = q3._2.insert(85).insert(86).insert(22)
  val q5 = q4._2.remove
  val q6 = q5._2.remove
  val q7 = q6._2.remove
  assert(q1._1 == Option(2))
  assert(q2._1 == Option(5))
  assert(q3._1 == Option(6))
  assert(q4._1 == Option(8))
  assert(q5._1 == Option(10))
  assert(q6._1.isEmpty)
  assert(q7._1.isEmpty)
  assert(qq4.remove._1 == Option(8))
  assert(qq4.remove._2.remove._1 == Option(10))
  assert(qq4.remove._2.remove._2.remove._1 == Option(85))
  assert(qq4.remove._2.remove._2.remove._2.remove._1 == Option(86))
  assert(qq4.remove._2.remove._2.remove._2.remove._2.remove._1 == Option(22))
  assert(qq4.remove._2.remove._2.remove._2.remove._2.remove._2.remove._1.isEmpty)

  assert(!q1._2.isEmpty)
  assert(!q2._2.isEmpty)
  assert(!q3._2.isEmpty)
  assert(!q4._2.isEmpty)
  assert(q5._2.isEmpty)
  assert(q6._2.isEmpty)
  assert(q7._2.isEmpty)
  assert(!qq4.remove._2.isEmpty)
  assert(!qq4.remove._2.remove._2.isEmpty)
  assert(!qq4.remove._2.remove._2.remove._2.isEmpty)
  assert(!qq4.remove._2.remove._2.remove._2.remove._2.isEmpty)
  assert(qq4.remove._2.remove._2.remove._2.remove._2.remove._2.isEmpty)
  assert(qq4.remove._2.remove._2.remove._2.remove._2.remove._2.remove._2.isEmpty)

}
