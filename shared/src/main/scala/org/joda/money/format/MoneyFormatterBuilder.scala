package org.joda.money.format

import java.io.IOException
import java.util.ArrayList
import java.util.List
import java.util.Locale
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.joda.money.IllegalCurrencyException
import MoneyFormatterBuilder._
//remove if not needed
import scala.collection.JavaConversions._

object MoneyFormatterBuilder {

  sealed abstract class Singletons(var s: String) extends MoneyPrinter with MoneyParser with Serializable {
    override def toString(): String = s
  }

  object Singletons {
    case object CODE extends Singletons("${code}") {
      override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
         appendable.append(money.getCurrencyUnit().getCode())
       }
       
       override def parse(context: MoneyParseContext) {
         val endPos: Int = context.getIndex() + 3
         if (endPos > context.getTextLength()) {
           context.setError()
         } else {
           val code: String = context.getTextSubstring(context.getIndex(), endPos)
           try {
             context.setCurrency(CurrencyUnit.of(code))
             context.setIndex(endPos);
           } catch {
             case ex: IllegalCurrencyException => context.setError()
           }
         }
       }
    }
    
    case object NUMERIC_3_CODE extends Singletons("${numeric3Code}") {
      override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
        appendable.append(money.getCurrencyUnit().getNumeric3Code())
      }

      override def parse(context: MoneyParseContext) {
        val endPos: Int = context.getIndex() + 3
        if (endPos > context.getTextLength()) {
          context.setError()
        } else {
          val code: String = context.getTextSubstring(context.getIndex(), endPos)
          try {
            context.setCurrency(CurrencyUnit.ofNumericCode(code))
            context.setIndex(endPos)
          } catch {
            case ex: IllegalCurrencyException => context.setError()
          }
        }
      }
    }
    
    case object NUMERIC_CODE extends Singletons("${numericCode}") {
      override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
        appendable.append(Integer.toString(money.getCurrencyUnit().getNumericCode()))
      }
      
      override def parse(context: MoneyParseContext) {
        val code: String = context.getText().toString().substring(context.getIndex()).takeWhile(Character.isDigit)
        try {
           context.setCurrency(CurrencyUnit.ofNumericCode(code))
           context.setIndex(context.getIndex()+code.size)
        } catch {
          case ex: IllegalCurrencyException => context.setError()
        }
      }
    }
  }

  sealed abstract class SingletonPrinters extends MoneyPrinter {
    override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
      appendable.append(money.getCurrencyUnit.getSymbol(context.getLocale))
    }

    override def toString(): String = "${symbolLocalized}"
  }

  object SingletonPrinters extends Enumeration {
    case object LOCALIZED_SYMBOL extends SingletonPrinters
  }
}

/**
 * Provides the ability to build a formatter for monetary values.
 * <p>
 * This class is mutable and intended for use by a single thread.
 * A new instance should be created for each use.
 * The formatters produced by the builder are immutable and thread-safe.
 */
class MoneyFormatterBuilder {

  /**
   * The printers.
   */
  private val printers = new ArrayList[MoneyPrinter]()

  /**
   * The parsers.
   */
  private val parsers = new ArrayList[MoneyParser]()

  /**
   * Appends the amount to the builder using a standard format.
   * <p>
   * The format used is {@link MoneyAmountStyle#ASCII_DECIMAL_POINT_GROUP3_COMMA}.
   * The amount is the value itself, such as '12.34'.
   *
   * @return this, for chaining, never null
   */
  def appendAmount(): MoneyFormatterBuilder = {
    val pp = new AmountPrinterParser(MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA)
    appendInternal(pp, pp)
  }

  /**
   * Appends the amount to the builder using a grouped localized format.
   * <p>
   * The format used is {@link MoneyAmountStyle#LOCALIZED_GROUPING}.
   * The amount is the value itself, such as '12.34'.
   *
   * @return this, for chaining, never null
   */
  def appendAmountLocalized(): MoneyFormatterBuilder = {
    val pp = new AmountPrinterParser(MoneyAmountStyle.LOCALIZED_GROUPING)
    appendInternal(pp, pp)
  }

  /**
   * Appends the amount to the builder using the specified amount style.
   * <p>
   * The amount is the value itself, such as '12.34'.
   * <p>
   * The amount style allows the formatting of the number to be controlled in detail.
   * This includes the characters for positive, negative, decimal, grouping and whether
   * to output the absolute or signed amount.
   * See {@link MoneyAmountStyle} for more details.
   *
   * @param style  the style to use, not null
   * @return this, for chaining, never null
   */
  def appendAmount(style: MoneyAmountStyle): MoneyFormatterBuilder = {
    MoneyFormatter.checkNotNull(style, "MoneyAmountStyle must not be null")
    val pp = new AmountPrinterParser(style)
    appendInternal(pp, pp)
  }

  /**
   * Appends the currency code to the builder.
   * <p>
   * The currency code is the three letter ISO code, such as 'GBP'.
   *
   * @return this, for chaining, never null
   */
  def appendCurrencyCode(): MoneyFormatterBuilder = {
    appendInternal(Singletons.CODE, Singletons.CODE)
  }

  /**
   * Appends the currency code to the builder.
   * <p>
   * The numeric code is the ISO numeric code, such as '826' and is
   * zero padded to three digits.
   *
   * @return this, for chaining, never null
   */
  def appendCurrencyNumeric3Code(): MoneyFormatterBuilder = {
    appendInternal(Singletons.NUMERIC_3_CODE, Singletons.NUMERIC_3_CODE)
  }

  /**
   * Appends the currency code to the builder.
   * <p>
   * The numeric code is the ISO numeric code, such as '826'.
   *
   * @return this, for chaining, never null
   */
  def appendCurrencyNumericCode(): MoneyFormatterBuilder = {
    appendInternal(Singletons.NUMERIC_CODE, Singletons.NUMERIC_CODE)
  }

  /**
   * Appends the localized currency symbol to the builder.
   * <p>
   * The localized currency symbol is the symbol as chosen by the locale
   * of the formatter.
   * <p>
   * Symbols cannot be parsed.
   *
   * @return this, for chaining, never null
   */
  def appendCurrencySymbolLocalized(): MoneyFormatterBuilder = {
    appendInternal(SingletonPrinters.LOCALIZED_SYMBOL, null)
  }

  /**
   * Appends a literal to the builder.
   * <p>
   * The localized currency symbol is the symbol as chosen by the locale
   * of the formatter.
   *
   * @param literal  the literal to append, null or empty ignored
   * @return this, for chaining, never null
   */
  def appendLiteral(literal: CharSequence): MoneyFormatterBuilder = {
    if (literal == null || literal.length == 0) {
      return this
    }
    val pp = new LiteralPrinterParser(literal.toString)
    appendInternal(pp, pp)
  }

  /**
   * Appends the printers and parsers from the specified formatter to this builder.
   * <p>
   * If the specified formatter cannot print, then the the output of this
   * builder will be unable to print. If the specified formatter cannot parse,
   * then the output of this builder will be unable to parse.
   *
   * @param formatter  the formatter to append, not null
   * @return this for chaining, never null
   */
  def append(formatter: MoneyFormatter): MoneyFormatterBuilder = {
    MoneyFormatter.checkNotNull(formatter, "MoneyFormatter must not be null")
    formatter.getPrinterParser.appendTo(this)
    this
  }

  /**
   * Appends the specified printer and parser to this builder.
   * <p>
   * If null is specified then the formatter will be unable to print/parse.
   *
   * @param printer  the printer to append, null makes the formatter unable to print
   * @param parser  the parser to append, null makes the formatter unable to parse
   * @return this for chaining, never null
   */
  def append(printer: MoneyPrinter, parser: MoneyParser): MoneyFormatterBuilder = appendInternal(printer, parser)

  /**
   * Appends the specified formatters, one used when the amount is positive,
   * and one when the amount is negative.
   * <p>
   * When printing, the amount is queried and the appropriate formatter is used.
   * <p>
   * When parsing, each formatter is tried, with the longest successful match,
   * or the first match if multiple are successful. If the negative parser is
   * matched, the amount returned will be negative no matter what amount is parsed.
   * <p>
   * A typical use case for this would be to produce a format like
   * '{@code ($123)}' for negative amounts and '{@code $123}' for positive amounts.
   * <p>
   * In order to use this method, it may be necessary to output an unsigned amount.
   * This can be achieved using {@link #appendAmount(MoneyAmountStyle)} and
   * {@link MoneyAmountStyle#withAbsValue(boolean)}.
   *
   * @param whenPositiveOrZero  the formatter to use when the amount is positive or zero
   * @param whenNegative  the formatter to use when the amount is negative
   * @return this for chaining, never null
   */
  def appendSigned(whenPositiveOrZero: MoneyFormatter, whenNegative: MoneyFormatter): MoneyFormatterBuilder = {
    appendSigned(whenPositiveOrZero, whenPositiveOrZero, whenNegative)
  }

  /**
   * Appends the specified formatters, one used when the amount is positive,
   * one when the amount is zero and one when the amount is negative.
   * <p>
   * When printing, the amount is queried and the appropriate formatter is used.
   * <p>
   * When parsing, each formatter is tried, with the longest successful match,
   * or the first match if multiple are successful. If the zero parser is matched,
   * the amount returned will be zero no matter what amount is parsed. If the negative
   * parser is matched, the amount returned will be negative no matter what amount is parsed.
   * <p>
   * A typical use case for this would be to produce a format like
   * '{@code ($123)}' for negative amounts and '{@code $123}' for positive amounts.
   * <p>
   * In order to use this method, it may be necessary to output an unsigned amount.
   * This can be achieved using {@link #appendAmount(MoneyAmountStyle)} and
   * {@link MoneyAmountStyle#withAbsValue(boolean)}.
   *
   * @param whenPositive  the formatter to use when the amount is positive
   * @param whenZero  the formatter to use when the amount is zero
   * @param whenNegative  the formatter to use when the amount is negative
   * @return this for chaining, never null
   */
  def appendSigned(whenPositive: MoneyFormatter, whenZero: MoneyFormatter, whenNegative: MoneyFormatter): MoneyFormatterBuilder = {
    MoneyFormatter.checkNotNull(whenPositive, "MoneyFormatter whenPositive must not be null")
    MoneyFormatter.checkNotNull(whenZero, "MoneyFormatter whenZero must not be null")
    MoneyFormatter.checkNotNull(whenNegative, "MoneyFormatter whenNegative must not be null")
    val pp = new SignedPrinterParser(whenPositive, whenZero, whenNegative)
    appendInternal(pp, pp)
  }

  /**
   * Appends the specified printer and parser to this builder.
   * <p>
   * Either the printer or parser must be non-null.
   *
   * @param printer  the printer to append, null makes the formatter unable to print
   * @param parser  the parser to append, null makes the formatter unable to parse
   * @return this for chaining, never null
   */
  private def appendInternal(printer: MoneyPrinter, parser: MoneyParser): MoneyFormatterBuilder = {
    printers.add(printer)
    parsers.add(parser)
    this
  }

  /**
   * Builds the formatter from the builder using the default locale.
   * <p>
   * Once the builder is in the correct state it must be converted to a
   * {@code MoneyFormatter} to be used. Calling this method does not
   * change the state of this instance, so it can still be used.
   * <p>
   * This method uses the default locale within the returned formatter.
   * It can be changed by calling {@link MoneyFormatter#withLocale(Locale)}.
   *
   * @return the formatter built from this builder, never null
   */
  def toFormatter(): MoneyFormatter = toFormatter(Locale.getDefault)

  /**
   * Builds the formatter from the builder setting the locale.
   * <p>
   * Once the builder is in the correct state it must be converted to a
   * {@code MoneyFormatter} to be used. Calling this method does not
   * change the state of this instance, so it can still be used.
   * <p>
   * This method uses the specified locale within the returned formatter.
   * It can be changed by calling {@link MoneyFormatter#withLocale(Locale)}.
   *
   * @param locale  the initial locale for the formatter, not null
   * @return the formatter built from this builder, never null
   */
  def toFormatter(locale: Locale): MoneyFormatter = {
    MoneyFormatter.checkNotNull(locale, "Locale must not be null")
    val printersCopy = printers.toArray(Array.ofDim[MoneyPrinter](printers.size)).asInstanceOf[Array[MoneyPrinter]]
    val parsersCopy = parsers.toArray(Array.ofDim[MoneyParser](parsers.size)).asInstanceOf[Array[MoneyParser]]
    new MoneyFormatter(locale, printersCopy, parsersCopy)
  }
}
