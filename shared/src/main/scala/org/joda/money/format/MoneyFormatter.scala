package org.joda.money.format

import java.io.IOException
import java.io.Serializable
import java.util.Locale
import java.lang.StringBuilder
import org.joda.money.BigMoney
import org.joda.money.BigMoneyProvider
import org.joda.money.Money
import MoneyFormatter._
import scala.beans.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

object MoneyFormatter {

  /**
   * Validates that the object specified is not null
   *
   * @param object  the object to check, null throws exception
   * @param message  the message to use in the exception, not null
   * @throws NullPointerException if the input value is null
   */
  def checkNotNull(`object`: AnyRef, message: String) {
    if (`object` == null) {
      throw new NullPointerException(message)
    }
  }
}

/**
 * Formats instances of money to and from a String.
 * <p>
 * Instances of {@code MoneyFormatter} can be created by
 * {@code MoneyFormatterBuilder}.
 * <p>
 * This class is immutable and thread-safe.
 */
@SerialVersionUID(2385346258L)
class MoneyFormatter(@BeanProperty val locale: Locale, @BeanProperty val printerParser: MultiPrinterParser)
    extends Serializable {

  assert(locale != null)

  assert(printerParser != null)

  /**
   * Constructor, creating a new formatter.
   *
   * @param locale  the locale to use, not null
   * @param printers 
   * @param parsers 
   */
  def this(locale: Locale, printers: Array[MoneyPrinter], parsers: Array[MoneyParser]) {
    this(locale, new MultiPrinterParser(printers, parsers))
    
    assert(locale != null)
    assert(printers != null)
    assert(parsers != null)
    assert(printers.length == parsers.length)
  }

  /**
   * Returns a copy of this instance with the specified locale.
   * <p>
   * Changing the locale may change the style of output depending on how the
   * formatter has been configured.
   *
   * @param locale  the locale, not null
   * @return the new instance, never null
   */
  def withLocale(locale: Locale): MoneyFormatter = {
    checkNotNull(locale, "Locale must not be null")
    new MoneyFormatter(locale, printerParser)
  }

  /**
   * Checks whether this formatter can print.
   * <p>
   * If the formatter cannot print, an UnsupportedOperationException will
   * be thrown from the print methods.
   *
   * @return true if the formatter can print
   */
  def isPrinter(): Boolean = printerParser.isPrinter

  /**
   * Checks whether this formatter can parse.
   * <p>
   * If the formatter cannot parse, an UnsupportedOperationException will
   * be thrown from the print methods.
   *
   * @return true if the formatter can parse
   */
  def isParser(): Boolean = printerParser.isParser

  /**
   * Prints a monetary value to a {@code String}.
   *
   * @param moneyProvider  the money to print, not null
   * @return the string printed using the settings of this formatter
   * @throws UnsupportedOperationException if the formatter is unable to print
   * @throws MoneyFormatException if there is a problem while printing
   */
  def print(moneyProvider: BigMoneyProvider): String = {
    val buf = new StringBuilder()
    print(buf, moneyProvider)
    buf.toString
  }

  /**
   * Prints a monetary value to an {@code Appendable} converting
   * any {@code IOException} to a {@code MoneyFormatException}.
   * <p>
   * Example implementations of {@code Appendable} are {@code StringBuilder},
   * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
   * and {@code StringBuffer} never throw an {@code IOException}.
   *
   * @param appendable  the appendable to add to, not null
   * @param moneyProvider  the money to print, not null
   * @throws UnsupportedOperationException if the formatter is unable to print
   * @throws MoneyFormatException if there is a problem while printing
   */
  def print(appendable: Appendable, moneyProvider: BigMoneyProvider) {
    printIO(appendable, moneyProvider)
  }

  /**
   * Prints a monetary value to an {@code Appendable} potentially
   * throwing an {@code IOException}.
   * <p>
   * Example implementations of {@code Appendable} are {@code StringBuilder},
   * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
   * and {@code StringBuffer} never throw an {@code IOException}.
   *
   * @param appendable  the appendable to add to, not null
   * @param moneyProvider  the money to print, not null
   * @throws UnsupportedOperationException if the formatter is unable to print
   * @throws MoneyFormatException if there is a problem while printing
   * @throws IOException if an IO error occurs
   */
  def printIO(appendable: Appendable, moneyProvider: BigMoneyProvider) {
    checkNotNull(moneyProvider, "BigMoneyProvider must not be null")
    if (isPrinter == false) {
      throw new UnsupportedOperationException("MoneyFomatter has not been configured to be able to print")
    }
    val money = BigMoney.of(moneyProvider)
    val context = new MoneyPrintContext(locale)
    printerParser.print(context, appendable, money)
  }

  /**
   * Fully parses the text into a {@code BigMoney}.
   * <p>
   * The parse must complete normally and parse the entire text (currency and amount).
   * If the parse completes without reading the entire length of the text, an exception is thrown.
   * If any other problem occurs during parsing, an exception is thrown.
   *
   * @param text  the text to parse, not null
   * @return the parsed monetary value, never null
   * @throws UnsupportedOperationException if the formatter is unable to parse
   * @throws MoneyFormatException if there is a problem while parsing
   */
  def parseBigMoney(text: CharSequence): BigMoney = {
    checkNotNull(text, "Text must not be null")
    val result = parse(text, 0)
    if (result.isError || result.isFullyParsed == false || result.isComplete == false) {
      val str = (if (text.length > 64) text.subSequence(0, 64).toString + "..." else text.toString)
      if (result.isError) {
        throw new MoneyFormatException("Text could not be parsed at index " + result.getErrorIndex + 
          ": " + 
          str)
      } else if (result.isFullyParsed == false) {
        throw new MoneyFormatException("Unparsed text found at index " + result.getIndex + ": " + 
          str)
      } else {
        throw new MoneyFormatException("Parsing did not find both currency and amount: " + str)
      }
    }
    result.toBigMoney()
  }

  /**
   * Fully parses the text into a {@code Money} requiring that the parsed
   * amount has the correct number of decimal places.
   * <p>
   * The parse must complete normally and parse the entire text (currency and amount).
   * If the parse completes without reading the entire length of the text, an exception is thrown.
   * If any other problem occurs during parsing, an exception is thrown.
   *
   * @param text  the text to parse, not null
   * @return the parsed monetary value, never null
   * @throws UnsupportedOperationException if the formatter is unable to parse
   * @throws MoneyFormatException if there is a problem while parsing
   * @throws ArithmeticException if the scale of the parsed money exceeds the scale of the currency
   */
  def parseMoney(text: CharSequence): Money = parseBigMoney(text).toMoney()

  /**
   * Parses the text extracting monetary information.
   * <p>
   * This method parses the input providing low-level access to the parsing state.
   * The resulting context contains the parsed text, indicator of error, position
   * following the parse and the parsed currency and amount.
   * Together, these provide enough information for higher level APIs to use.
   *
   * @param text  the text to parse, not null
   * @param startIndex  the start index to parse from
   * @return the parsed monetary value, null only if the parse results in an error
   * @throws IndexOutOfBoundsException if the start index is invalid
   * @throws UnsupportedOperationException if this formatter cannot parse
   */
  def parse(text: CharSequence, startIndex: Int): MoneyParseContext = {
    checkNotNull(text, "Text must not be null")
    if (startIndex < 0 || startIndex > text.length) {
      throw new StringIndexOutOfBoundsException("Invalid start index: " + startIndex)
    }
    if (isParser == false) {
      throw new UnsupportedOperationException("MoneyFomatter has not been configured to be able to parse")
    }
    val context = new MoneyParseContext(locale, text, startIndex)
    printerParser.parse(context)
    context
  }

  /**
   * Gets a string summary of the formatter.
   *
   * @return a string summarising the formatter, never null
   */
  override def toString(): String = printerParser.toString
}
