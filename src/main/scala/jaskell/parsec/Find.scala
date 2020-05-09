package jaskell.parsec

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2020/05/09 16:22
 */
class Find[T, E](val psc: Parsec[T, E]) extends Parsec [T, E]{
  override def apply[S <: State[E]](s: S): T = {
    var error: ParsecException = null
    var re:Option[T] = Option.empty
    while(re.isEmpty) {
      psc.either(s) match {
        case Right(value) =>
          re = Some(value)
        case Left(_) => {
          case err: EofException =>
            if(error == null){
              throw err
            } else {
              throw err
            }
          case err: ParsecException =>
            if(error == null){
              error = err
            }
        }
      }
    }
    re.get
  }
}
