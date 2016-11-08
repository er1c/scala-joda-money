package org.joda.money

//remove if not needed
import scala.collection.JavaConversions._

/**
 * Exception thrown when the requested currency is illegal.
 * <p>
 * For example, this exception would be thrown when trying to obtain a
 * currency using an unrecognised currency code or locale.
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 */
@SerialVersionUID(1L)
class IllegalCurrencyException(message: String) extends IllegalArgumentException(message)
