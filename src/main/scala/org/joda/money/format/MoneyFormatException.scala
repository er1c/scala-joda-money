package org.joda.money.format

import java.io.IOException
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Exception thrown during monetary formatting.
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 */
@SerialVersionUID(87533576L)
class MoneyFormatException(message: String) extends RuntimeException(message) {

  /**
   * Constructor taking a message and cause.
   *
   * @param message  the message
   * @param cause  the exception cause
   */
  def this(message: String, cause: Throwable) {
    super(message, cause)
  }

  /**
   * Checks if the cause of this exception was an IOException, and if so re-throws it
   * <p>
   * This method is useful if you call a printer with an open stream or
   * writer and want to ensure that IOExceptions are not lost.
   * <pre>
   * try {
   *   printer.print(writer, money);
   * } catch (CalendricalFormatException ex) {
   *   ex.rethrowIOException();
   *   // if code reaches here exception was caused by issues other than IO
   * }
   * </pre>
   * Note that calling this method will re-throw the original IOException,
   * causing this MoneyFormatException to be lost.
   *
   * @throws IOException if the cause of this exception is an IOException
   */
  def rethrowIOException() {
    if (getCause.isInstanceOf[IOException]) {
      throw getCause.asInstanceOf[IOException]
    }
  }
}
