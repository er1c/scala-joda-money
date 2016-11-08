package org.joda.money

import java.io.InvalidObjectException
import java.io.ObjectInputStream
import java.io.Serializable
import java.util.ArrayList
import java.util.Collections
import java.util.Currency
import java.util.HashSet
import java.util.List
import java.util.Locale
import java.util.Map.Entry
import java.util.Set
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.regex.Pattern
import org.joda.convert.FromString
import org.joda.convert.ToString
import CurrencyUnit._
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

object CurrencyUnit {

  /**
   * The currency code pattern.
   */
  private val CODE = Pattern.compile("[A-Z][A-Z][A-Z]")

  /**
   * Map of registered currencies by text code.
   */
  private val currenciesByCode = new ConcurrentHashMap[String, CurrencyUnit]()

  /**
   * Map of registered currencies by numeric code.
   */
  private val currenciesByNumericCode = new ConcurrentHashMap[Integer, CurrencyUnit]()

  /**
   * Map of registered currencies by country.
   */
  private val currenciesByCountry = new ConcurrentHashMap[String, CurrencyUnit]()

  try {
    val clsName = System.getProperty("org.joda.money.CurrencyUnitDataProvider", "org.joda.money.DefaultCurrencyUnitDataProvider")
    val cls = classOf[CurrencyUnit].getClassLoader.loadClass(clsName)
      .asSubclass(classOf[CurrencyUnitDataProvider])
    cls.newInstance().registerCurrencies()
  } catch {
    case ex: SecurityException => new DefaultCurrencyUnitDataProvider().registerCurrencies()
  }

  /**
   * The currency 'USD' - United States Dollar.
   */
  val USD = of("USD")

  /**
   * The currency 'EUR' - Euro.
   */
  val EUR = of("EUR")

  /**
   * The currency 'JPY' - Japanese Yen.
   */
  val JPY = of("JPY")

  /**
   * The currency 'GBP' - British pound.
   */
  val GBP = of("GBP")

  /**
   * The currency 'CHF' - Swiss Franc.
   */
  val CHF = of("CHF")

  /**
   * The currency 'AUD' - Australian Dollar.
   */
  val AUD = of("AUD")

  /**
   * The currency 'CAD' - Canadian Dollar.
   */
  val CAD = of("CAD")

  /**
   * Registers a currency allowing it to be used.
   * <p>
   * This class only permits known currencies to be returned.
   * To achieve this, all currencies have to be registered in advance.
   * <p>
   * Since this method is public, it is possible to add currencies in
   * application code. It is recommended to do this only at startup, however
   * it is safe to do so later as the internal implementation is thread-safe.
   * <p>
   * The currency code must be three upper-case ASCII letters, based on ISO-4217.
   * The numeric code must be from 0 to 999, or -1 if not applicable.
   *
   * @param currencyCode  the three-letter upper-case currency code, not null
   * @param numericCurrencyCode  the numeric currency code, from 0 to 999, -1 if none
   * @param decimalPlaces  the number of decimal places that the currency
   *  normally has, from 0 to 9 (normally 0, 2 or 3), or -1 for a pseudo-currency
   * @param countryCodes  the country codes to register the currency under, not null
   * @return the new instance, never null
   * @throws IllegalArgumentException if the code is already registered, or the
   *  specified data is invalid
   */
  def registerCurrency(currencyCode: String, 
      numericCurrencyCode: Int, 
      decimalPlaces: Int, 
      countryCodes: List[String]): CurrencyUnit = {
    synchronized {
      registerCurrency(currencyCode, numericCurrencyCode, decimalPlaces, countryCodes, false)
    }
  }

  /**
   * Registers a currency allowing it to be used, allowing replacement.
   * <p>
   * This class only permits known currencies to be returned.
   * To achieve this, all currencies have to be registered in advance.
   * <p>
   * Since this method is public, it is possible to add currencies in
   * application code. It is recommended to do this only at startup, however
   * it is safe to do so later as the internal implementation is thread-safe.
   * <p>
   * This method uses a flag to determine whether the registered currency
   * must be new, or can replace an existing currency.
   * <p>
   * The currency code must be three upper-case ASCII letters, based on ISO-4217.
   * The numeric code must be from 0 to 999, or -1 if not applicable.
   *
   * @param currencyCode  the three-letter upper-case currency code, not null
   * @param numericCurrencyCode  the numeric currency code, from 0 to 999, -1 if none
   * @param decimalPlaces  the number of decimal places that the currency
   *  normally has, from 0 to 9 (normally 0, 2 or 3), or -1 for a pseudo-currency
   * @param countryCodes  the country codes to register the currency under,
   *  use of ISO-3166 is recommended, not null
   * @param force  true to register forcefully, replacing any existing matching currency,
   *  false to validate that there is no existing matching currency
   * @return the new instance, never null
   * @throws IllegalArgumentException if the code is already registered and {@code force} is false;
   *  or if the specified data is invalid
   */
  def registerCurrency(currencyCode: String, 
      numericCurrencyCode: Int, 
      decimalPlaces: Int, 
      countryCodes: List[String], 
      force: Boolean): CurrencyUnit = {
    synchronized {
      MoneyUtils.checkNotNull(currencyCode, "Currency code must not be null")
      if (currencyCode.length != 3) {
        throw new IllegalArgumentException("Invalid string code, must be length 3")
      }
      if (CODE.matcher(currencyCode).matches() == false) {
        throw new IllegalArgumentException("Invalid string code, must be ASCII upper-case letters")
      }
      if (numericCurrencyCode < -1 || numericCurrencyCode > 999) {
        throw new IllegalArgumentException("Invalid numeric code")
      }
      if (decimalPlaces < -1 || decimalPlaces > 9) {
        throw new IllegalArgumentException("Invalid number of decimal places")
      }
      MoneyUtils.checkNotNull(countryCodes, "Country codes must not be null")
      val currency = new CurrencyUnit(currencyCode, numericCurrencyCode.toShort, decimalPlaces.toShort)
      if (force) {
        currenciesByCode.remove(currencyCode)
        currenciesByNumericCode.remove(numericCurrencyCode)
        for (countryCode <- countryCodes) {
          currenciesByCountry.remove(countryCode)
        }
      } else {
        if (currenciesByCode.containsKey(currencyCode) || 
          currenciesByNumericCode.containsKey(numericCurrencyCode)) {
          throw new IllegalArgumentException("Currency already registered: " + currencyCode)
        }
        for (countryCode <- countryCodes if currenciesByCountry.containsKey(countryCode)) {
          throw new IllegalArgumentException("Currency already registered for country: " + countryCode)
        }
      }
      currenciesByCode.putIfAbsent(currencyCode, currency)
      if (numericCurrencyCode >= 0) {
        currenciesByNumericCode.putIfAbsent(numericCurrencyCode, currency)
      }
      for (countryCode <- countryCodes) {
        currenciesByCountry.put(countryCode, currency)
      }
      currenciesByCode.get(currencyCode)
    }
  }

  /**
   * Gets the list of all registered currencies.
   * <p>
   * This class only permits known currencies to be returned, thus this list is
   * the complete list of valid singleton currencies. The list may change after
   * application startup, however this isn't recommended.
   *
   * @return the sorted, independent, list of all registered currencies, never null
   */
  def registeredCurrencies(): List[CurrencyUnit] = {
    val list = new ArrayList[CurrencyUnit](currenciesByCode.values)
    Collections.sort(list)
    list
  }

  /**
   * Obtains an instance of {@code CurrencyUnit} matching the specified JDK currency.
   * <p>
   * This converts the JDK currency instance to a currency unit using the code.
   *
   * @param currency  the currency, not null
   * @return the singleton instance, never null
   * @throws IllegalCurrencyException if the currency is unknown
   */
  def of(currency: Currency): CurrencyUnit = {
    MoneyUtils.checkNotNull(currency, "Currency must not be null")
    of(currency.getCurrencyCode)
  }

  /**
   * Obtains an instance of {@code CurrencyUnit} for the specified three letter currency code.
   * <p>
   * A currency is uniquely identified by a three letter code, based on ISO-4217.
   * Valid currency codes are three upper-case ASCII letters.
   *
   * @param currencyCode  the three-letter currency code, not null
   * @return the singleton instance, never null
   * @throws IllegalCurrencyException if the currency is unknown
   */
  @FromString
  def of(currencyCode: String): CurrencyUnit = {
    MoneyUtils.checkNotNull(currencyCode, "Currency code must not be null")
    val currency = currenciesByCode.get(currencyCode)
    if (currency == null) {
      throw new IllegalCurrencyException("Unknown currency '" + currencyCode + '\'')
    }
    currency
  }

  /**
   * Obtains an instance of {@code CurrencyUnit} for the specified ISO-4217 numeric currency code.
   * <p>
   * The numeric code is an alternative to the three letter code.
   * This method is lenient and does not require the string to be left padded with zeroes.
   *
   * @param numericCurrencyCode  the currency code, not null
   * @return the singleton instance, never null
   * @throws IllegalCurrencyException if the currency is unknown
   */
  def ofNumericCode(numericCurrencyCode: String): CurrencyUnit = {
    MoneyUtils.checkNotNull(numericCurrencyCode, "Currency code must not be null")
    numericCurrencyCode.length match {
      case 1 => ofNumericCode(numericCurrencyCode.charAt(0) - '0')
      case 2 => ofNumericCode((numericCurrencyCode.charAt(0) - '0') * 10 + numericCurrencyCode.charAt(1) - 
        '0')
      case 3 => ofNumericCode((numericCurrencyCode.charAt(0) - '0') * 100 + (numericCurrencyCode.charAt(1) - '0') * 10 + 
        numericCurrencyCode.charAt(2) - 
        '0')
      case _ => throw new IllegalCurrencyException("Unknown currency '" + numericCurrencyCode + '\'')
    }
  }

  /**
   * Obtains an instance of {@code CurrencyUnit} for the specified ISO-4217 numeric currency code.
   * <p>
   * The numeric code is an alternative to the three letter code.
   *
   * @param numericCurrencyCode  the numeric currency code, not null
   * @return the singleton instance, never null
   * @throws IllegalCurrencyException if the currency is unknown
   */
  def ofNumericCode(numericCurrencyCode: Int): CurrencyUnit = {
    val currency = currenciesByNumericCode.get(numericCurrencyCode)
    if (currency == null) {
      throw new IllegalCurrencyException("Unknown currency '" + numericCurrencyCode + '\'')
    }
    currency
  }

  /**
   * Obtains an instance of {@code CurrencyUnit} for the specified locale.
   * <p>
   * Only the country is used from the locale.
   *
   * @param locale  the locale, not null
   * @return the singleton instance, never null
   * @throws IllegalCurrencyException if the currency is unknown
   */
  def of(locale: Locale): CurrencyUnit = {
    MoneyUtils.checkNotNull(locale, "Locale must not be null")
    val currency = currenciesByCountry.get(locale.getCountry)
    if (currency == null) {
      throw new IllegalCurrencyException("Unknown currency for locale '" + locale + '\'')
    }
    currency
  }

  /**
   * Obtains an instance of {@code CurrencyUnit} for the specified ISO-3166 country code.
   * <p>
   * Country codes should generally be in upper case.
   * This method is case sensitive.
   *
   * @param countryCode  the country code, typically ISO-3166, not null
   * @return the singleton instance, never null
   * @throws IllegalCurrencyException if the currency is unknown
   */
  def ofCountry(countryCode: String): CurrencyUnit = {
    MoneyUtils.checkNotNull(countryCode, "Country code must not be null")
    val currency = currenciesByCountry.get(countryCode)
    if (currency == null) {
      throw new IllegalCurrencyException("Unknown currency for country '" + countryCode + '\'')
    }
    currency
  }

  /**
   * Obtains an instance of {@code CurrencyUnit} for the specified three-letter currency code.
   * <p>
   * This method exists to match the API of {@link Currency}.
   *
   * @param currencyCode  the three-letter currency code, not null
   * @return the singleton instance, never null
   * @throws IllegalCurrencyException if the currency is unknown
   */
  def getInstance(currencyCode: String): CurrencyUnit = CurrencyUnit.of(currencyCode)

  /**
   * Obtains an instance of {@code CurrencyUnit} for the specified locale.
   * <p>
   * This method exists to match the API of {@link Currency}.
   *
   * @param locale  the locale, not null
   * @return the singleton instance, never null
   * @throws IllegalCurrencyException if the currency is unknown
   */
  def getInstance(locale: Locale): CurrencyUnit = CurrencyUnit.of(locale)
}

/**
 * A unit of currency.
 * <p>
 * This class represents a unit of currency such as the British Pound, Euro
 * or US Dollar.
 * <p>
 * The set of loaded currencies is provided by an instance of {@link CurrencyUnitDataProvider}.
 * The provider used is determined by the system property {@code org.joda.money.CurrencyUnitDataProvider}
 * which should be the fully qualified class name of the provider. The default provider loads the first
 * resource named {@code /org/joda/money/MoneyData.csv} on the classpath.
 * <p>
 * This class is immutable and thread-safe.
 */
@SerialVersionUID(327835287287L)
class CurrencyUnit(@BeanProperty val code: String, @BeanProperty val numericCode: Short, val decimalPlaces: Short)
    extends Comparable[CurrencyUnit] with Serializable {

  assert(code != null) : "Joda-Money bug: Currency code must not be null"

  /**
   * Block malicious data streams.
   *
   * @param ois  the input stream, not null
   * @throws InvalidObjectException
   */
  private def readObject(ois: ObjectInputStream) {
    throw new InvalidObjectException("Serialization delegate required")
  }

  /**
   * Uses a serialization delegate.
   *
   * @return the replacing object, never null
   */
  private def writeReplace(): AnyRef = new Ser(Ser.CURRENCY_UNIT, this)

  /**
   * Gets the ISO-4217 numeric currency code as a three digit string.
   * <p>
   * This formats the numeric code as a three digit string prefixed by zeroes if necessary.
   * If there is no valid code, then an empty string is returned.
   *
   * @return the three digit numeric currency code, empty is no code, never null
   */
  def getNumeric3Code(): String = {
    if (numericCode < 0) {
      return ""
    }
    val str = Integer toString numericCode
    if (str.length == 1) {
      return "00" + str
    }
    if (str.length == 2) {
      return "0" + str
    }
    str
  }

  /**
   * Gets the country codes applicable to this currency.
   * <p>
   * A currency is typically valid in one or more countries.
   * The codes are typically defined by ISO-3166.
   * An empty set indicates that no the currency is not associated with a country code.
   *
   * @return the country codes, may be empty, not null
   */
  def getCountryCodes(): Set[String] = {
    val countryCodes = new HashSet[String]()
    for ((key, value) <- currenciesByCountry if this == value) {
      countryCodes.add(key)
    }
    countryCodes
  }

  /**
   * Gets the number of decimal places typically used by this currency.
   * <p>
   * Different currencies have different numbers of decimal places by default.
   * For example, 'GBP' has 2 decimal places, but 'JPY' has zero.
   * Pseudo-currencies will return zero.
   * <p>
   * See also {@link #getDefaultFractionDigits()}.
   *
   * @return the decimal places, from 0 to 9 (normally 0, 2 or 3)
   */
  def getDecimalPlaces(): Int = {
    if (decimalPlaces < 0) 0 else decimalPlaces
  }

  /**
   * Checks if this is a pseudo-currency.
   *
   * @return true if this is a pseudo-currency
   */
  def isPseudoCurrency(): Boolean = decimalPlaces < 0

  /**
   * Gets the ISO-4217 three-letter currency code.
   * <p>
   * This method matches the API of {@link Currency}.
   *
   * @return the currency code, never null
   */
  def getCurrencyCode(): String = code

  /**
   * Gets the number of fractional digits typically used by this currency.
   * <p>
   * Different currencies have different numbers of fractional digits by default.
   * For example, 'GBP' has 2 fractional digits, but 'JPY' has zero.
   * Pseudo-currencies are indicated by -1.
   * <p>
   * This method matches the API of {@link Currency}.
   * The alternative method {@link #getDecimalPlaces()} may be more useful.
   *
   * @return the fractional digits, from 0 to 9 (normally 0, 2 or 3), or -1 for pseudo-currencies
   */
  def getDefaultFractionDigits(): Int = decimalPlaces

  /**
   * Gets the symbol for this locale from the JDK.
   * <p>
   * If this currency doesn't have a JDK equivalent, then the currency code
   * is returned.
   * <p>
   * This method matches the API of {@link Currency}.
   *
   * @return the JDK currency instance, never null
   */
  def getSymbol(): String = {
    try {
      Currency.getInstance(code).getSymbol
    } catch {
      case ex: IllegalArgumentException => code
    }
  }

  /**
   * Gets the symbol for this locale from the JDK.
   * <p>
   * If this currency doesn't have a JDK equivalent, then the currency code
   * is returned.
   * <p>
   * This method matches the API of {@link Currency}.
   *
   * @param locale  the locale to get the symbol for, not null
   * @return the JDK currency instance, never null
   */
  def getSymbol(locale: Locale): String = {
    MoneyUtils.checkNotNull(locale, "Locale must not be null")
    try {
      Currency.getInstance(code).getSymbol(locale)
    } catch {
      case ex: IllegalArgumentException => code
    }
  }

  /**
   * Gets the JDK currency instance equivalent to this currency.
   * <p>
   * This attempts to convert a {@code CurrencyUnit} to a JDK {@code Currency}.
   *
   * @return the JDK currency instance, never null
   * @throws IllegalArgumentException if no matching currency exists in the JDK
   */
  def toCurrency(): Currency = Currency.getInstance(code)

  /**
   * Compares this currency to another by alphabetical comparison of the code.
   *
   * @param other  the other currency, not null
   * @return negative if earlier alphabetically, 0 if equal, positive if greater alphabetically
   */
  override def compareTo(other: CurrencyUnit): Int = code.compareTo(other.code)

  /**
   * Checks if this currency equals another currency.
   * <p>
   * The comparison checks the 3 letter currency code.
   *
   * @param obj  the other currency, null returns false
   * @return true if equal
   */
  override def equals(obj: Any): Boolean = {
    if (obj == this) {
      return true
    }
    if (obj.isInstanceOf[CurrencyUnit]) {
      return code == obj.asInstanceOf[CurrencyUnit].code
    }
    false
  }

  /**
   * Returns a suitable hash code for the currency.
   *
   * @return the hash code
   */
  override def hashCode(): Int = code.hashCode

  /**
   * Gets the currency code as a string.
   *
   * @return the currency code, never null
   */
  @ToString
  override def toString(): String = code
}
