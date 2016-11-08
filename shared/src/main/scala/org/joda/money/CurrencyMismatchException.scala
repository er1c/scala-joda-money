package org.joda.money

import scala.beans.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Exception thrown when a monetary operation fails due to mismatched currencies.
 * <p>
 * For example, this exception would be thrown when trying to add a monetary
 * value in one currency to a monetary value in a different currency.
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 */
@SerialVersionUID(1L)
class CurrencyMismatchException(@BeanProperty val firstCurrency: CurrencyUnit, @BeanProperty val secondCurrency: CurrencyUnit)
    extends IllegalArgumentException("Currencies differ: " + 
  (if (firstCurrency != null) firstCurrency.getCode else "null") + 
  '/' + 
  (if (secondCurrency != null) secondCurrency.getCode else "null"))
