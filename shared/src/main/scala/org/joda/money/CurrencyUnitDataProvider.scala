package org.joda.money

import java.util.List
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Provider for available currencies.
 */
trait CurrencyUnitDataProvider {

  /**
   * Registers all the currencies known by this provider.
   *
   * @throws Exception if an error occurs
   */
  def registerCurrencies(): Unit

  /**
   * Registers a currency allowing it to be used.
   * <p>
   * This method is called by {@link #registerCurrencies()} to perform the
   * actual creation of a currency.
   *
   * @param currencyCode  the currency code, not null
   * @param numericCurrencyCode  the numeric currency code, -1 if none
   * @param decimalPlaces  the number of decimal places that the currency
   *  normally has, from 0 to 3, or -1 for a pseudo-currency
   * @param countryCodes  the country codes to register the currency under, not null
   */
  protected def registerCurrency(currencyCode: String, 
      numericCurrencyCode: Int, 
      decimalPlaces: Int, 
      countryCodes: List[String]) {
    CurrencyUnit.registerCurrency(currencyCode, numericCurrencyCode, decimalPlaces, countryCodes, true)
  }
}
