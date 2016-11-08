package org.joda.money

import java.io.InvalidObjectException
import java.io.ObjectInputStream
import java.io.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Arrays
import java.util.Iterator
import org.joda.convert.FromString
import org.joda.convert.ToString
import Money._
//remove if not needed
import scala.collection.JavaConversions._

object Money {

  /**
   * Obtains an instance of {@code Money} from a {@code BigDecimal}.
   * <p>
   * This allows you to create an instance with a specific currency and amount.
   * No rounding is performed on the amount, so it must have a scale compatible
   * with the currency.
   *
   * @param currency  the currency, not null
   * @param amount  the amount of money, not null
   * @return the new instance, never null
   * @throws ArithmeticException if the scale exceeds the currency scale
   */
  def of(currency: CurrencyUnit, amount: BigDecimal): Money = {
    MoneyUtils.checkNotNull(currency, "Currency must not be null")
    MoneyUtils.checkNotNull(amount, "Amount must not be null")
    if (amount.scale() > currency.getDecimalPlaces) {
      throw new ArithmeticException("Scale of amount " + amount + " is greater than the scale of the currency " + 
        currency)
    }
    Money.of(currency, amount, RoundingMode.UNNECESSARY)
  }

  /**
   * Obtains an instance of {@code Money} from a {@code BigDecimal}, rounding as necessary.
   * <p>
   * This allows you to create an instance with a specific currency and amount.
   * If the amount has a scale in excess of the scale of the currency then the excess
   * fractional digits are rounded using the rounding mode.
   *
   * @param currency  the currency, not null
   * @param amount  the amount of money, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new instance, never null
   * @throws ArithmeticException if the rounding fails
   */
  def of(currency: CurrencyUnit, amount: BigDecimal, roundingMode: RoundingMode): Money = {
    MoneyUtils.checkNotNull(currency, "CurrencyUnit must not be null")
    MoneyUtils.checkNotNull(amount, "Amount must not be null")
    MoneyUtils.checkNotNull(roundingMode, "RoundingMode must not be null")
    amount = amount.setScale(currency.getDecimalPlaces, roundingMode)
    new Money(BigMoney.of(currency, amount))
  }

  /**
   * Obtains an instance of {@code Money} from a {@code double} using a
   * well-defined conversion.
   * <p>
   * This allows you to create an instance with a specific currency and amount.
   * No rounding is performed on the amount, so it must have a scale compatible
   * with the currency.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   *
   * @param currency  the currency, not null
   * @param amount  the amount of money, not null
   * @return the new instance, never null
   * @throws ArithmeticException if the scale exceeds the currency scale
   */
  def of(currency: CurrencyUnit, amount: Double): Money = {
    Money.of(currency, BigDecimal.valueOf(amount))
  }

  /**
   * Obtains an instance of {@code Money} from a {@code double} using a
   * well-defined conversion, rounding as necessary.
   * <p>
   * This allows you to create an instance with a specific currency and amount.
   * If the amount has a scale in excess of the scale of the currency then the excess
   * fractional digits are rounded using the rounding mode.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   *
   * @param currency  the currency, not null
   * @param amount  the amount of money, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new instance, never null
   * @throws ArithmeticException if the rounding fails
   */
  def of(currency: CurrencyUnit, amount: Double, roundingMode: RoundingMode): Money = {
    Money.of(currency, BigDecimal.valueOf(amount), roundingMode)
  }

  /**
   * Obtains an instance of {@code Money} from an amount in major units.
   * <p>
   * This allows you to create an instance with a specific currency and amount.
   * The amount is a whole number only. Thus you can initialise the value
   * 'USD 20', but not the value 'USD 20.32'.
   * For example, {@code ofMajor(USD, 25)} creates the instance {@code USD 25.00}.
   *
   * @param currency  the currency, not null
   * @param amountMajor  the amount of money in the major division of the currency
   * @return the new instance, never null
   */
  def ofMajor(currency: CurrencyUnit, amountMajor: Long): Money = {
    Money.of(currency, BigDecimal.valueOf(amountMajor), RoundingMode.UNNECESSARY)
  }

  /**
   * Obtains an instance of {@code Money} from an amount in minor units.
   * <p>
   * This allows you to create an instance with a specific currency and amount
   * expressed in terms of the minor unit.
   * For example, if constructing US Dollars, the input to this method represents cents.
   * Note that when a currency has zero decimal places, the major and minor units are the same.
   * For example, {@code ofMinor(USD, 2595)} creates the instance {@code USD 25.95}.
   *
   * @param currency  the currency, not null
   * @param amountMinor  the amount of money in the minor division of the currency
   * @return the new instance, never null
   */
  def ofMinor(currency: CurrencyUnit, amountMinor: Long): Money = {
    new Money(BigMoney.ofMinor(currency, amountMinor))
  }

  /**
   * Obtains an instance of {@code Money} representing zero.
   * <p>
   * For example, {@code zero(USD)} creates the instance {@code USD 0.00}.
   *
   * @param currency  the currency, not null
   * @return the instance representing zero, never null
   */
  def zero(currency: CurrencyUnit): Money = {
    MoneyUtils.checkNotNull(currency, "Currency must not be null")
    val bd = BigDecimal.valueOf(0, currency.getDecimalPlaces)
    new Money(BigMoney.of(currency, bd))
  }

  /**
   * Obtains an instance of {@code Money} from a provider.
   * <p>
   * This allows you to create an instance from any class that implements the
   * provider, such as {@code BigMoney}.
   * No rounding is performed on the amount, so it must have a scale compatible
   * with the currency.
   *
   * @param moneyProvider  the money to convert, not null
   * @return the new instance, never null
   * @throws ArithmeticException if the scale exceeds the currency scale
   */
  def of(moneyProvider: BigMoneyProvider): Money = {
    Money.of(moneyProvider, RoundingMode.UNNECESSARY)
  }

  /**
   * Obtains an instance of {@code Money} from a provider, rounding as necessary.
   * <p>
   * This allows you to create an instance from any class that implements the
   * provider, such as {@code BigMoney}.
   * The rounding mode is used to adjust the scale to the scale of the currency.
   *
   * @param moneyProvider  the money to convert, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new instance, never null
   * @throws ArithmeticException if the rounding fails
   */
  def of(moneyProvider: BigMoneyProvider, roundingMode: RoundingMode): Money = {
    MoneyUtils.checkNotNull(moneyProvider, "BigMoneyProvider must not be null")
    MoneyUtils.checkNotNull(roundingMode, "RoundingMode must not be null")
    new Money(BigMoney.of(moneyProvider).withCurrencyScale(roundingMode))
  }

  /**
   * Obtains an instance of {@code Money} as the total value of an array.
   * <p>
   * The array must contain at least one monetary value.
   * Subsequent amounts are added as though using {@link #plus(Money)}.
   * All amounts must be in the same currency.
   *
   * @param monies  the monetary values to total, not empty, no null elements, not null
   * @return the total, never null
   * @throws IllegalArgumentException if the array is empty
   * @throws CurrencyMismatchException if the currencies differ
   */
  def total(monies: Money*): Money = {
    MoneyUtils.checkNotNull(monies, "Money array must not be null")
    if (monies.length == 0) {
      throw new IllegalArgumentException("Money array must not be empty")
    }
    var total = monies(0)
    MoneyUtils.checkNotNull(total, "Money arary must not contain null entries")
    for (i <- 1 until monies.length) {
      total = total.plus(monies(i))
    }
    total
  }

  /**
   * Obtains an instance of {@code Money} as the total value of a collection.
   * <p>
   * The iterable must provide at least one monetary value.
   * Subsequent amounts are added as though using {@link #plus(Money)}.
   * All amounts must be in the same currency.
   *
   * @param monies  the monetary values to total, not empty, no null elements, not null
   * @return the total, never null
   * @throws IllegalArgumentException if the iterable is empty
   * @throws CurrencyMismatchException if the currencies differ
   */
  def total(monies: java.lang.Iterable[Money]): Money = {
    MoneyUtils.checkNotNull(monies, "Money iterator must not be null")
    val it = monies.iterator()
    if (it.hasNext == false) {
      throw new IllegalArgumentException("Money iterator must not be empty")
    }
    var total = it.next()
    MoneyUtils.checkNotNull(total, "Money iterator must not contain null entries")
    while (it.hasNext) {
      total = total.plus(it.next())
    }
    total
  }

  /**
   * Obtains an instance of {@code Money} as the total value of
   * a possibly empty array.
   * <p>
   * The amounts are added as though using {@link #plus(Money)} starting
   * from zero in the specified currency.
   * All amounts must be in the same currency.
   *
   * @param currency  the currency to total in, not null
   * @param monies  the monetary values to total, no null elements, not null
   * @return the total, never null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def total(currency: CurrencyUnit, monies: Money*): Money = {
    Money.zero(currency).plus(Arrays.asList(monies:_*))
  }

  /**
   * Obtains an instance of {@code Money} as the total value of
   * a possibly empty collection.
   * <p>
   * The amounts are added as though using {@link #plus(Money)} starting
   * from zero in the specified currency.
   * All amounts must be in the same currency.
   *
   * @param currency  the currency to total in, not null
   * @param monies  the monetary values to total, no null elements, not null
   * @return the total, never null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def total(currency: CurrencyUnit, monies: java.lang.Iterable[Money]): Money = Money.zero(currency).plus(monies)

  /**
   * Parses an instance of {@code Money} from a string.
   * <p>
   * The string format is '$currencyCode $amount' where there may be
   * zero to many spaces between the two parts.
   * The currency code must be a valid three letter currency.
   * The amount must match the regular expression {@code [+-]?[0-9]*[.]?[0-9]*}.
   * The spaces and numbers must be ASCII characters.
   * This matches the output from {@link #toString()}.
   * <p>
   * For example, {@code parse("USD 25")} creates the instance {@code USD 25.00}
   * while {@code parse("USD 25.95")} creates the instance {@code USD 25.95}.
   *
   * @param moneyStr  the money string to parse, not null
   * @return the parsed instance, never null
   * @throws IllegalArgumentException if the string is malformed
   * @throws ArithmeticException if the amount is too large
   */
  @FromString
  def parse(moneyStr: String): Money = Money.of(BigMoney.parse(moneyStr))

  /**
   * Ensures that a {@code Money} is not {@code null}.
   * <p>
   * If the input money is not {@code null}, then it is returned, providing
   * that the currency matches the specified currency.
   * If the input money is {@code null}, then zero money in the currency is returned.
   *
   * @param money  the monetary value to check, may be null
   * @param currency  the currency to use, not null
   * @return the input money or zero in the specified currency, never null
   * @throws CurrencyMismatchException if the input money is non-null and the currencies differ
   */
  def nonNull(money: Money, currency: CurrencyUnit): Money = {
    if (money == null) {
      return zero(currency)
    }
    if (money.getCurrencyUnit == currency == false) {
      MoneyUtils.checkNotNull(currency, "Currency must not be null")
      throw new CurrencyMismatchException(money.getCurrencyUnit, currency)
    }
    money
  }
}

/**
 * An amount of money with the standard decimal places defined by the currency.
 * <p>
 * This class represents a quantity of money, stored as a {@code BigDecimal} amount
 * in a single {@link CurrencyUnit currency}.
 * <p>
 * Every currency has a certain standard number of decimal places.
 * This is typically 2 (Euro, British Pound, US Dollar) but might be
 * 0 (Japanese Yen), 1 (Vietnamese Dong) or 3 (Bahrain Dinar).
 * The {@code Money} class is fixed to this number of decimal places.
 * <p>
 * For example, US dollars has a standard number of decimal places of 2.
 * The major units are dollars. The minor units are cents, 100 to the dollar.
 * This class does not allow calculations on fractions of a cent.
 * <p>
 * This class is immutable and thread-safe.
 */
@SerialVersionUID(1L)
class Money private () extends BigMoneyProvider with Comparable[BigMoneyProvider] with Serializable {

  /**
   * The money, not null.
   */
  private val money = null

  /**
   * Constructor, creating a new monetary instance.
   *
   * @param money  the underlying money, not null
   */
  def this(money: BigMoney) {
    this()
    assert(money != null) : "Joda-Money bug: BigMoney must not be null"
    assert(money.isCurrencyScale) : "Joda-Money bug: Only currency scale is valid for Money"
    this.money = money
  }

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
  private def writeReplace(): AnyRef = new Ser(Ser.MONEY, this)

  /**
   * Returns a new {@code Money}, returning {@code this} if possible.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param newInstance  the new money to use, not null
   * @return the new instance, never null
   */
  private def with(newInstance: BigMoney): Money = {
    if (money == newInstance) {
      return this
    }
    new Money(newInstance)
  }

  /**
   * Gets the currency.
   *
   * @return the currency, never null
   */
  def getCurrencyUnit(): CurrencyUnit = money.getCurrencyUnit

  /**
   * Returns a copy of this monetary value with the specified currency.
   * <p>
   * The returned instance will have the specified currency and the amount
   * from this instance. If the scale differs between the currencies such
   * that rounding would be required, then an exception is thrown.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param currency  the currency to use, not null
   * @return the new instance with the input currency set, never null
   * @throws ArithmeticException if the scale of the new currency is less than
   *  the scale of this currency
   */
  def withCurrencyUnit(currency: CurrencyUnit): Money = {
    withCurrencyUnit(currency, RoundingMode.UNNECESSARY)
  }

  /**
   * Returns a copy of this monetary value with the specified currency.
   * <p>
   * The returned instance will have the specified currency and the amount
   * from this instance. If the number of decimal places differs between the
   * currencies, then the amount may be rounded.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param currency  the currency to use, not null
   * @param roundingMode  the rounding mode to use to bring the decimal places back in line, not null
   * @return the new instance with the input currency set, never null
   * @throws ArithmeticException if the rounding fails
   */
  def withCurrencyUnit(currency: CurrencyUnit, roundingMode: RoundingMode): Money = {
    with(money.withCurrencyUnit(currency).withCurrencyScale(roundingMode))
  }

  /**
   * Gets the scale of the {@code BigDecimal} amount.
   * <p>
   * The scale has the same meaning as in {@link BigDecimal}.
   * Positive values represent the number of decimal places in use.
   * For example, a scale of 2 means that the money will have two decimal places
   * such as 'USD 43.25'.
   * <p>
   * For {@code Money}, the scale is fixed and always matches that of the currency.
   *
   * @return the scale in use, typically 2 but could be 0, 1 and 3
   */
  def getScale(): Int = money.getScale

  /**
   * Gets the amount.
   * <p>
   * This returns the value of the money as a {@code BigDecimal}.
   * The scale will be the scale of this money.
   *
   * @return the amount, never null
   */
  def getAmount(): BigDecimal = money.getAmount

  /**
   * Gets the amount in major units as a {@code BigDecimal} with scale 0.
   * <p>
   * This returns the monetary amount in terms of the major units of the currency,
   * truncating the amount if necessary.
   * For example, 'EUR 2.35' will return 2, and 'BHD -1.345' will return -1.
   * <p>
   * This is returned as a {@code BigDecimal} rather than a {@code BigInteger}.
   * This is to allow further calculations to be performed on the result.
   * Should you need a {@code BigInteger}, simply call {@link BigDecimal#toBigInteger()}.
   *
   * @return the major units part of the amount, never null
   */
  def getAmountMajor(): BigDecimal = money.getAmountMajor

  /**
   * Gets the amount in major units as a {@code long}.
   * <p>
   * This returns the monetary amount in terms of the major units of the currency,
   * truncating the amount if necessary.
   * For example, 'EUR 2.35' will return 2, and 'BHD -1.345' will return -1.
   *
   * @return the major units part of the amount
   * @throws ArithmeticException if the amount is too large for a {@code long}
   */
  def getAmountMajorLong(): Long = money.getAmountMajorLong

  /**
   * Gets the amount in major units as an {@code int}.
   * <p>
   * This returns the monetary amount in terms of the major units of the currency,
   * truncating the amount if necessary.
   * For example, 'EUR 2.35' will return 2, and 'BHD -1.345' will return -1.
   *
   * @return the major units part of the amount
   * @throws ArithmeticException if the amount is too large for an {@code int}
   */
  def getAmountMajorInt(): Int = money.getAmountMajorInt

  /**
   * Gets the amount in minor units as a {@code BigDecimal} with scale 0.
   * <p>
   * This returns the monetary amount in terms of the minor units of the currency,
   * truncating the amount if necessary.
   * For example, 'EUR 2.35' will return 235, and 'BHD -1.345' will return -1345.
   * <p>
   * This is returned as a {@code BigDecimal} rather than a {@code BigInteger}.
   * This is to allow further calculations to be performed on the result.
   * Should you need a {@code BigInteger}, simply call {@link BigDecimal#toBigInteger()}.
   *
   * @return the minor units part of the amount, never null
   */
  def getAmountMinor(): BigDecimal = money.getAmountMinor

  /**
   * Gets the amount in minor units as a {@code long}.
   * <p>
   * This returns the monetary amount in terms of the minor units of the currency,
   * truncating the amount if necessary.
   * For example, 'EUR 2.35' will return 235, and 'BHD -1.345' will return -1345.
   *
   * @return the minor units part of the amount
   * @throws ArithmeticException if the amount is too large for a {@code long}
   */
  def getAmountMinorLong(): Long = money.getAmountMinorLong

  /**
   * Gets the amount in minor units as an {@code int}.
   * <p>
   * This returns the monetary amount in terms of the minor units of the currency,
   * truncating the amount if necessary.
   * For example, 'EUR 2.35' will return 235, and 'BHD -1.345' will return -1345.
   *
   * @return the minor units part of the amount
   * @throws ArithmeticException if the amount is too large for an {@code int}
   */
  def getAmountMinorInt(): Int = money.getAmountMinorInt

  /**
   * Gets the minor part of the amount.
   * <p>
   * This return the minor unit part of the monetary amount.
   * This is defined as the amount in minor units excluding major units.
   * <p>
   * For example, EUR has a scale of 2, so the minor part is always between 0 and 99
   * for positive amounts, and 0 and -99 for negative amounts.
   * Thus 'EUR 2.35' will return 35, and 'EUR -1.34' will return -34.
   *
   * @return the minor part of the amount, negative if the amount is negative
   */
  def getMinorPart(): Int = money.getMinorPart

  /**
   * Checks if the amount is zero.
   *
   * @return true if the amount is zero
   */
  def isZero(): Boolean = money.isZero

  /**
   * Checks if the amount is greater than zero.
   *
   * @return true if the amount is greater than zero
   */
  def isPositive(): Boolean = money.isPositive

  /**
   * Checks if the amount is zero or greater.
   *
   * @return true if the amount is zero or greater
   */
  def isPositiveOrZero(): Boolean = money.isPositiveOrZero

  /**
   * Checks if the amount is less than zero.
   *
   * @return true if the amount is less than zero
   */
  def isNegative(): Boolean = money.isNegative

  /**
   * Checks if the amount is zero or less.
   *
   * @return true if the amount is zero or less
   */
  def isNegativeOrZero(): Boolean = money.isNegativeOrZero

  /**
   * Returns a copy of this monetary value with the specified amount.
   * <p>
   * The returned instance will have this currency and the new amount.
   * No rounding is performed on the amount to be added, so it must have a
   * scale compatible with the currency.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amount  the monetary amount to set in the returned instance, not null
   * @return the new instance with the input amount set, never null
   * @throws ArithmeticException if the scale of the amount is too large
   */
  def withAmount(amount: BigDecimal): Money = {
    withAmount(amount, RoundingMode.UNNECESSARY)
  }

  /**
   * Returns a copy of this monetary value with the specified amount.
   * <p>
   * The returned instance will have this currency and the new amount.
   * If the scale of the {@code BigDecimal} needs to be adjusted, then
   * it will be rounded using the specified mode.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amount  the monetary amount to set in the returned instance, not null
   * @param roundingMode  the rounding mode to adjust the scale, not null
   * @return the new instance with the input amount set, never null
   */
  def withAmount(amount: BigDecimal, roundingMode: RoundingMode): Money = {
    with(money.withAmount(amount).withCurrencyScale(roundingMode))
  }

  /**
   * Returns a copy of this monetary value with the specified amount using a well-defined
   * conversion from a {@code double}.
   * <p>
   * The returned instance will have this currency and the new amount.
   * No rounding is performed on the amount to be added, so it must have a
   * scale compatible with the currency.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amount  the monetary amount to set in the returned instance, not null
   * @return the new instance with the input amount set, never null
   * @throws ArithmeticException if the scale of the amount is too large
   */
  def withAmount(amount: Double): Money = {
    withAmount(amount, RoundingMode.UNNECESSARY)
  }

  /**
   * Returns a copy of this monetary value with the specified amount using a well-defined
   * conversion from a {@code double}.
   * <p>
   * The returned instance will have this currency and the new amount.
   * If the scale of the {@code BigDecimal} needs to be adjusted, then
   * it will be rounded using the specified mode.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amount  the monetary amount to set in the returned instance, not null
   * @param roundingMode  the rounding mode to adjust the scale, not null
   * @return the new instance with the input amount set, never null
   */
  def withAmount(amount: Double, roundingMode: RoundingMode): Money = {
    with(money.withAmount(amount).withCurrencyScale(roundingMode))
  }

  /**
   * Returns a copy of this monetary value with a collection of monetary amounts added.
   * <p>
   * This adds the specified amounts to this monetary amount, returning a new object.
   * The amounts must be in the same currency.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param moniesToAdd  the monetary values to add, no null elements, not null
   * @return the new instance with the input amounts added, never null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def plus(moniesToAdd: java.lang.Iterable[Money]): Money = with(money.plus(moniesToAdd))

  /**
   * Returns a copy of this monetary value with the amount added.
   * <p>
   * This adds the specified amount to this monetary amount, returning a new object.
   * The amount added must be in the same currency.
   * <p>
   * The addition has no rounding issues and is always accurate.
   * For example,'USD 25.95' plus 'USD 3.02' will 'USD 28.97'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param moneyToAdd  the monetary value to add, not null
   * @return the new instance with the input amount added, never null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def plus(moneyToAdd: Money): Money = with(money.plus(moneyToAdd))

  /**
   * Returns a copy of this monetary value with the amount added.
   * <p>
   * This adds the specified amount to this monetary amount, returning a new object.
   * No rounding is performed on the amount to be added, so it must have a
   * scale compatible with the currency.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToAdd  the monetary value to add, not null
   * @return the new instance with the input amount added, never null
   * @throws ArithmeticException if the scale of the amount is too large
   */
  def plus(amountToAdd: BigDecimal): Money = {
    plus(amountToAdd, RoundingMode.UNNECESSARY)
  }

  /**
   * Returns a copy of this monetary value with the amount added.
   * <p>
   * This adds the specified amount to this monetary amount, returning a new object.
   * If the amount to add exceeds the scale of the currency, then the
   * rounding mode will be used to adjust the result.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToAdd  the monetary value to add, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new instance with the input amount added, never null
   */
  def plus(amountToAdd: BigDecimal, roundingMode: RoundingMode): Money = {
    with(money.plusRetainScale(amountToAdd, roundingMode))
  }

  /**
   * Returns a copy of this monetary value with the amount added.
   * <p>
   * This adds the specified amount to this monetary amount, returning a new object.
   * No rounding is performed on the amount to be added, so it must have a
   * scale compatible with the currency.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToAdd  the monetary value to add, not null
   * @return the new instance with the input amount added, never null
   * @throws ArithmeticException if the scale of the amount is too large
   */
  def plus(amountToAdd: Double): Money = {
    plus(amountToAdd, RoundingMode.UNNECESSARY)
  }

  /**
   * Returns a copy of this monetary value with the amount added.
   * <p>
   * This adds the specified amount to this monetary amount, returning a new object.
   * If the amount to add exceeds the scale of the currency, then the
   * rounding mode will be used to adjust the result.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToAdd  the monetary value to add, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new instance with the input amount added, never null
   */
  def plus(amountToAdd: Double, roundingMode: RoundingMode): Money = {
    with(money.plusRetainScale(amountToAdd, roundingMode))
  }

  /**
   * Returns a copy of this monetary value with the amount in major units added.
   * <p>
   * This adds an amount in major units, leaving the minor units untouched.
   * For example, USD 23.45 plus 138 gives USD 161.45.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToAdd  the monetary value to add, not null
   * @return the new instance with the input amount added, never null
   */
  def plusMajor(amountToAdd: Long): Money = with(money.plusMajor(amountToAdd))

  /**
   * Returns a copy of this monetary value with the amount in minor units added.
   * <p>
   * This adds an amount in minor units.
   * For example, USD 23.45 plus 138 gives USD 24.83.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToAdd  the monetary value to add, not null
   * @return the new instance with the input amount added, never null
   */
  def plusMinor(amountToAdd: Long): Money = with(money.plusMinor(amountToAdd))

  /**
   * Returns a copy of this monetary value with a collection of monetary amounts subtracted.
   * <p>
   * This subtracts the specified amounts from this monetary amount, returning a new object.
   * The amounts must be in the same currency.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param moniesToSubtract  the monetary values to subtract, no null elements, not null
   * @return the new instance with the input amounts subtracted, never null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def minus(moniesToSubtract: java.lang.Iterable[Money]): Money = with(money.minus(moniesToSubtract))

  /**
   * Returns a copy of this monetary value with the amount subtracted.
   * <p>
   * This subtracts the specified amount from this monetary amount, returning a new object.
   * The amount subtracted must be in the same currency.
   * <p>
   * The subtraction has no rounding issues and is always accurate.
   * For example,'USD 25.95' minus 'USD 3.02' will 'USD 22.93'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param moneyToSubtract  the monetary value to subtract, not null
   * @return the new instance with the input amount subtracted, never null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def minus(moneyToSubtract: Money): Money = with(money.minus(moneyToSubtract))

  /**
   * Returns a copy of this monetary value with the amount subtracted.
   * <p>
   * This subtracts the specified amount from this monetary amount, returning a new object.
   * No rounding is performed on the amount to be subtracted, so it must have a
   * scale compatible with the currency.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToSubtract  the monetary value to subtract, not null
   * @return the new instance with the input amount subtracted, never null
   * @throws ArithmeticException if the scale of the amount is too large
   */
  def minus(amountToSubtract: BigDecimal): Money = {
    minus(amountToSubtract, RoundingMode.UNNECESSARY)
  }

  /**
   * Returns a copy of this monetary value with the amount subtracted.
   * <p>
   * This subtracts the specified amount from this monetary amount, returning a new object.
   * If the amount to subtract exceeds the scale of the currency, then the
   * rounding mode will be used to adjust the result.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToSubtract  the monetary value to subtract, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new instance with the input amount subtracted, never null
   */
  def minus(amountToSubtract: BigDecimal, roundingMode: RoundingMode): Money = {
    with(money.minusRetainScale(amountToSubtract, roundingMode))
  }

  /**
   * Returns a copy of this monetary value with the amount subtracted.
   * <p>
   * This subtracts the specified amount from this monetary amount, returning a new object.
   * No rounding is performed on the amount to be subtracted, so it must have a
   * scale compatible with the currency.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToSubtract  the monetary value to subtract, not null
   * @return the new instance with the input amount subtracted, never null
   * @throws ArithmeticException if the scale of the amount is too large
   */
  def minus(amountToSubtract: Double): Money = {
    minus(amountToSubtract, RoundingMode.UNNECESSARY)
  }

  /**
   * Returns a copy of this monetary value with the amount subtracted.
   * <p>
   * This subtracts the specified amount from this monetary amount, returning a new object.
   * If the amount to subtract exceeds the scale of the currency, then the
   * rounding mode will be used to adjust the result.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToSubtract  the monetary value to subtract, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new instance with the input amount subtracted, never null
   */
  def minus(amountToSubtract: Double, roundingMode: RoundingMode): Money = {
    with(money.minusRetainScale(amountToSubtract, roundingMode))
  }

  /**
   * Returns a copy of this monetary value with the amount in major units subtracted.
   * <p>
   * This subtracts an amount in major units, leaving the minor units untouched.
   * For example, USD 23.45 minus 138 gives USD -114.55.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToSubtract  the monetary value to subtract, not null
   * @return the new instance with the input amount subtracted, never null
   */
  def minusMajor(amountToSubtract: Long): Money = {
    with(money.minusMajor(amountToSubtract))
  }

  /**
   * Returns a copy of this monetary value with the amount in minor units subtracted.
   * <p>
   * This subtracts an amount in minor units.
   * For example, USD 23.45 minus 138 gives USD 22.07.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param amountToSubtract  the monetary value to subtract, not null
   * @return the new instance with the input amount subtracted, never null
   */
  def minusMinor(amountToSubtract: Long): Money = {
    with(money.minusMinor(amountToSubtract))
  }

  /**
   * Returns a copy of this monetary value multiplied by the specified value.
   * <p>
   * This takes this amount and multiplies it by the specified value, rounding
   * the result is rounded as specified.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param valueToMultiplyBy  the scalar value to multiply by, not null
   * @param roundingMode  the rounding mode to use to bring the decimal places back in line, not null
   * @return the new multiplied instance, never null
   * @throws ArithmeticException if the rounding fails
   */
  def multipliedBy(valueToMultiplyBy: BigDecimal, roundingMode: RoundingMode): Money = {
    with(money.multiplyRetainScale(valueToMultiplyBy, roundingMode))
  }

  /**
   * Returns a copy of this monetary value multiplied by the specified value.
   * <p>
   * This takes this amount and multiplies it by the specified value, rounding
   * the result is rounded as specified.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param valueToMultiplyBy  the scalar value to multiply by, not null
   * @param roundingMode  the rounding mode to use to bring the decimal places back in line, not null
   * @return the new multiplied instance, never null
   * @throws ArithmeticException if the rounding fails
   */
  def multipliedBy(valueToMultiplyBy: Double, roundingMode: RoundingMode): Money = {
    with(money.multiplyRetainScale(valueToMultiplyBy, roundingMode))
  }

  /**
   * Returns a copy of this monetary value multiplied by the specified value.
   * <p>
   * This takes this amount and multiplies it by the specified value.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param valueToMultiplyBy  the scalar value to multiply by, not null
   * @return the new multiplied instance, never null
   */
  def multipliedBy(valueToMultiplyBy: Long): Money = {
    with(money.multipliedBy(valueToMultiplyBy))
  }

  /**
   * Returns a copy of this monetary value divided by the specified value.
   * <p>
   * This takes this amount and divides it by the specified value, rounding
   * the result is rounded as specified.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param valueToDivideBy  the scalar value to divide by, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new divided instance, never null
   * @throws ArithmeticException if dividing by zero
   * @throws ArithmeticException if the rounding fails
   */
  def dividedBy(valueToDivideBy: BigDecimal, roundingMode: RoundingMode): Money = {
    with(money.dividedBy(valueToDivideBy, roundingMode))
  }

  /**
   * Returns a copy of this monetary value divided by the specified value.
   * <p>
   * This takes this amount and divides it by the specified value, rounding
   * the result is rounded as specified.
   * <p>
   * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
   * the most expected answer for most programming scenarios.
   * Any {@code double} literal in code will be converted to
   * exactly the same BigDecimal with the same scale.
   * For example, the literal '1.45d' will be converted to '1.45'.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param valueToDivideBy  the scalar value to divide by, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new divided instance, never null
   * @throws ArithmeticException if dividing by zero
   * @throws ArithmeticException if the rounding fails
   */
  def dividedBy(valueToDivideBy: Double, roundingMode: RoundingMode): Money = {
    with(money.dividedBy(valueToDivideBy, roundingMode))
  }

  /**
   * Returns a copy of this monetary value divided by the specified value.
   * <p>
   * This takes this amount and divides it by the specified value, rounding
   * the result is rounded as specified.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param valueToDivideBy  the scalar value to divide by, not null
   * @param roundingMode  the rounding mode to use, not null
   * @return the new divided instance, never null
   * @throws ArithmeticException if dividing by zero
   * @throws ArithmeticException if the rounding fails
   */
  def dividedBy(valueToDivideBy: Long, roundingMode: RoundingMode): Money = {
    with(money.dividedBy(valueToDivideBy, roundingMode))
  }

  /**
   * Returns a copy of this monetary value with the amount negated.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @return the new instance with the amount negated, never null
   */
  def negated(): Money = with(money.negated())

  /**
   * Returns a copy of this monetary value with a positive amount.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @return the new instance with the amount converted to be positive, never null
   */
  def abs(): Money = (if (isNegative) negated() else this)

  /**
   * Returns a copy of this monetary value rounded to the specified scale without
   * changing the current scale.
   * <p>
   * Scale has the same meaning as in {@link BigDecimal}.
   * A scale of 2 means round to 2 decimal places.
   * <ul>
   * <li>Rounding 'EUR 45.23' to a scale of -1 returns 40.00 or 50.00 depending on the rounding mode.
   * <li>Rounding 'EUR 45.23' to a scale of 0 returns 45.00 or 46.00 depending on the rounding mode.
   * <li>Rounding 'EUR 45.23' to a scale of 1 returns 45.20 or 45.30 depending on the rounding mode.
   * <li>Rounding 'EUR 45.23' to a scale of 2 has no effect (it already has that scale).
   * <li>Rounding 'EUR 45.23' to a scale of 3 has no effect (the scale is not increased).
   * </ul>
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param scale  the new scale
   * @param roundingMode  the rounding mode to use, not null
   * @return the new instance with the amount converted to be positive, never null
   * @throws ArithmeticException if the rounding fails
   */
  def rounded(scale: Int, roundingMode: RoundingMode): Money = {
    with(money.rounded(scale, roundingMode))
  }

  /**
   * Returns a copy of this monetary value converted into another currency
   * using the specified conversion rate, with a rounding mode used to adjust
   * the decimal places in the result.
   * <p>
   * This instance is immutable and unaffected by this method.
   *
   * @param currency  the new currency, not null
   * @param conversionMultipler  the conversion factor between the currencies, not null
   * @param roundingMode  the rounding mode to use to bring the decimal places back in line, not null
   * @return the new multiplied instance, never null
   * @throws IllegalArgumentException if the currency is the same as this currency
   * @throws IllegalArgumentException if the conversion multiplier is negative
   * @throws ArithmeticException if the rounding fails
   */
  def convertedTo(currency: CurrencyUnit, conversionMultipler: BigDecimal, roundingMode: RoundingMode): Money = {
    with(money.convertedTo(currency, conversionMultipler).withCurrencyScale(roundingMode))
  }

  /**
   * Implements the {@code BigMoneyProvider} interface, returning a
   * {@code BigMoney} instance with the same currency, amount and scale.
   *
   * @return the money instance, never null
   */
  override def toBigMoney(): BigMoney = money

  /**
   * Checks if this instance and the specified instance have the same currency.
   *
   * @param other  the money to check, not null
   * @return true if they have the same currency
   */
  def isSameCurrency(other: BigMoneyProvider): Boolean = money.isSameCurrency(other)

  /**
   * Compares this monetary value to another.
   * <p>
   * This allows {@code Money} to be compared to any {@code BigMoneyProvider}.
   * Scale is ignored in the comparison.
   * The compared values must be in the same currency.
   *
   * @param other  the other monetary value, not null
   * @return -1 if this is less than , 0 if equal, 1 if greater than
   * @throws CurrencyMismatchException if the currencies differ
   */
  override def compareTo(other: BigMoneyProvider): Int = money.compareTo(other)

  /**
   * Checks if this monetary value is equal to another.
   * <p>
   * This allows {@code Money} to be compared to any {@code BigMoneyProvider}.
   * Scale is ignored, so 'USD 30.00' and 'USD 30' are equal.
   * The compared values must be in the same currency.
   *
   * @param other  the other monetary value, not null
   * @return true is this is greater than the specified monetary value
   * @throws CurrencyMismatchException if the currencies differ
   * @see #equals(Object)
   */
  def isEqual(other: BigMoneyProvider): Boolean = money.isEqual(other)

  /**
   * Checks if this monetary value is greater than another.
   * <p>
   * This allows {@code Money} to be compared to any {@code BigMoneyProvider}.
   * Scale is ignored in the comparison.
   * The compared values must be in the same currency.
   *
   * @param other  the other monetary value, not null
   * @return true is this is greater than the specified monetary value
   * @throws CurrencyMismatchException if the currencies differ
   */
  def isGreaterThan(other: BigMoneyProvider): Boolean = money.isGreaterThan(other)

  /**
   * Checks if this monetary value is less than another.
   * <p>
   * This allows {@code Money} to be compared to any {@code BigMoneyProvider}.
   * Scale is ignored in the comparison.
   * The compared values must be in the same currency.
   *
   * @param other  the other monetary value, not null
   * @return true is this is less than the specified monetary value
   * @throws CurrencyMismatchException if the currencies differ
   */
  def isLessThan(other: BigMoneyProvider): Boolean = money.isLessThan(other)

  /**
   * Checks if this monetary value equals another.
   * <p>
   * The comparison takes into account the scale.
   * The compared values must be in the same currency.
   *
   * @param other  the other object to compare to, not null
   * @return true if this instance equals the other instance
   */
  override def equals(other: Any): Boolean = {
    if (this == other) {
      return true
    }
    if (other.isInstanceOf[Money]) {
      val otherMoney = other.asInstanceOf[Money]
      return money == otherMoney.money
    }
    false
  }

  /**
   * Returns a hash code for this monetary value.
   *
   * @return a suitable hash code
   */
  override def hashCode(): Int = money.hashCode + 3

  /**
   * Gets the monetary value as a string.
   * <p>
   * The format is the 3 letter ISO currency code, followed by a space,
   * followed by the amount as per {@link BigDecimal#toPlainString()}.
   *
   * @return the string representation of this monetary value, never null
   */
  @ToString
  override def toString(): String = money.toString
}
