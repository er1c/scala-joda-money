package org.joda.money.format

import java.math.BigDecimal
import java.text.ParsePosition
import java.util.Locale
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Context used when parsing money.
 * <p>
 * This class is mutable and intended for use by a single thread.
 * A new instance is created for each parse.
 */
class MoneyParseContext(@BeanProperty var locale: Locale, @BeanProperty var text: CharSequence, index: Int)
    {

  /**
   * The text index.
   */
  private var textIndex: Int = index

  /**
   * The text error index.
   */
  private var textErrorIndex: Int = -1

  /**
   * The parsed currency.
   */
  @BeanProperty
  var currency: CurrencyUnit = _

  /**
   * The parsed amount.
   */
  @BeanProperty
  var amount: BigDecimal = _

  /**
   * Constructor.
   *
   * @param locale  the locale, not null
   * @param text  the text to parse, not null
   * @param index  the current text index
   * @param errorIndex  the error index
   * @param currency  the currency
   * @param amount  the parsed amount
   */
  def this(locale: Locale, 
      text: CharSequence, 
      index: Int, 
      errorIndex: Int, 
      currency: CurrencyUnit, 
      amount: BigDecimal) {
    this()
    this.locale = locale
    this.text = text
    this.textIndex = index
    this.textErrorIndex = errorIndex
    this.currency = currency
    this.amount = amount
  }

  /**
   * Sets the locale.
   *
   * @param locale  the locale, not null
   */
  def setLocale(locale: Locale) {
    MoneyFormatter.checkNotNull(locale, "Locale must not be null")
    this.locale = locale
  }

  /**
   * Sets the text.
   *
   * @param text  the text being parsed, not null
   */
  def setText(text: CharSequence) {
    MoneyFormatter.checkNotNull(text, "Text must not be null")
    this.text = text
  }

  /**
   * Gets the length of the text being parsed.
   *
   * @return the length of the text being parsed
   */
  def getTextLength(): Int = text.length

  /**
   * Gets a substring of the text being parsed.
   *
   * @param start  the start index
   * @param end  the end index
   * @return the substring, not null
   */
  def getTextSubstring(start: Int, end: Int): String = text.subSequence(start, end).toString

  /**
   * Gets the current parse position index.
   *
   * @return the current parse position index
   */
  def getIndex(): Int = textIndex

  /**
   * Sets the current parse position index.
   *
   * @param index  the current parse position index
   */
  def setIndex(index: Int) {
    this.textIndex = index
  }

  /**
   * Gets the error index.
   *
   * @return the error index, negative if no error
   */
  def getErrorIndex(): Int = textErrorIndex

  /**
   * Sets the error index.
   *
   * @param index  the error index
   */
  def setErrorIndex(index: Int) {
    this.textErrorIndex = index
  }

  /**
   * Sets the error index from the current index.
   */
  def setError() {
    this.textErrorIndex = textIndex
  }

  /**
   * Checks if the parse has found an error.
   *
   * @return whether a parse error has occurred
   */
  def isError(): Boolean = textErrorIndex >= 0

  /**
   * Checks if the text has been fully parsed such that there is no more text to parse.
   *
   * @return true if fully parsed
   */
  def isFullyParsed(): Boolean = textIndex == getTextLength

  /**
   * Checks if the context contains a currency and amount suitable for creating
   * a monetary value.
   *
   * @return true if able to create a monetary value
   */
  def isComplete(): Boolean = currency != null && amount != null

  /**
   * Creates a child context.
   *
   * @return the child context, never null
   */
  def createChild(): MoneyParseContext = {
    new MoneyParseContext(locale, text, textIndex, textErrorIndex, currency, amount)
  }

  /**
   * Merges the child context back into this instance.
   *
   * @param child  the child context, not null
   */
  def mergeChild(child: MoneyParseContext) {
    setLocale(child.getLocale)
    setText(child.getText)
    setIndex(child.getIndex)
    setErrorIndex(child.getErrorIndex)
    setCurrency(child.getCurrency)
    setAmount(child.getAmount)
  }

  /**
   * Converts the indexes to a parse position.
   *
   * @return the parse position, never null
   */
  def toParsePosition(): ParsePosition = {
    val pp = new ParsePosition(textIndex)
    pp.setErrorIndex(textErrorIndex)
    pp
  }

  /**
   * Converts the context to a {@code BigMoney}.
   *
   * @return the monetary value, never null
   * @throws MoneyFormatException if either the currency or amount is missing
   */
  def toBigMoney(): BigMoney = {
    if (currency == null) {
      throw new MoneyFormatException("Cannot convert to BigMoney as no currency found")
    }
    if (amount == null) {
      throw new MoneyFormatException("Cannot convert to BigMoney as no amount found")
    }
    BigMoney.of(currency, amount)
  }
}
