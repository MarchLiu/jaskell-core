package jaskell.parsec

import java.io.EOFException

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/12 21:52
 */
class CommonStateSpec extends AnyFlatSpec with Matchers{
  "Index" should "Test index status" in {
    val data = "It is a \"string\" for this unit test";
    val state = State(data);
    while (state.status < data.length()){
      val index = state.status
      val c = state.next();
      val chr = data.charAt(index);
      c should be (Right(chr))
    }
    state.next().isLeft should be (true)
  }

  "Begin" should "Test begin tran and rollback then next" in {
    val state = State("hello")

    val c = state.next()


    c should be (Right('h'))

    val a = state.begin()

    state.next()
    state.next()
    state.next()

    state rollback a

    val d = state.next()

    d should be (Right('e'))
  }

  "Commit" should "Test begin a transaction and commit" in {
    val state = State("hello")
    val tran = state.begin()
    val c = state.next()


    c should be (Right('h'))
    state.next()

    state.commit(tran);

    val d = state.next();

    d should be (Right('l'))
  }

  "Rollback" should "Test rollback" in {
    val state = State("hello");

    val tran = state.begin();
    val c = state.next();


    c should be (Right('h'))

    state rollback tran

    val d = state.next();

    d should be (Right('h'))
  }

  "Next" should "Test state next method" in {
    val state = State("hello")


    val c = state.next()

    c should be (Right('h'))

    val d = state.next()

    d should be (Right('e'))

    val e = state.next()

    e should be (Right('l'))

    val f = state.next()

    f should be (Right('l'))
    val g = state.next()

    g should be (Right('o'))
  }


}
