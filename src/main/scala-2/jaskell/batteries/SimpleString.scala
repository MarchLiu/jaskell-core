package jaskell.batteries

/**
 * TODO
 *
 * @author mars
 * @version 1.0.0
 * @since 2022/05/06 00:44
 */
case class SimpleString(prefix: String,
                        begin: String,
                        content: String,
                        end: String) extends Token {
  override def parse(env: Env): String = f"$prefix$begin$content$end"
}
