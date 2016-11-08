package org.joda.money.format

import java.util.Locale
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Context used when printing money.
 * <p>
 * This class is mutable and intended for use by a single thread.
 * A new instance is created for each parse.
 */
class MoneyPrintContext(@BeanProperty var locale: Locale) {

  /**
   * Sets the locale.
   *
   * @param locale  the locale, not null
   */
  def setLocale(locale: Locale) {
    MoneyFormatter.checkNotNull(locale, "Locale must not be null")
    this.locale = locale
  }
}
