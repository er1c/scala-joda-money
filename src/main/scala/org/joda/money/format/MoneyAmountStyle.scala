package org.joda.money.format

import java.io.Serializable
import java.lang.reflect.Method
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import MoneyAmountStyle._
import scala.reflect.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

object MoneyAmountStyle {

  /**
   * A style that uses ASCII digits/negative sign, the decimal point
   * and groups large amounts in 3's using a comma.
   * Forced decimal point is disabled.
   */
  val ASCII_DECIMAL_POINT_GROUP3_COMMA = new MoneyAmountStyle('0', '+', '-', '.', GroupingStyle.FULL, 
    ',', 3, 0, false, false)

  /**
   * A style that uses ASCII digits/negative sign, the decimal point
   * and groups large amounts in 3's using a space.
   * Forced decimal point is disabled.
   */
  val ASCII_DECIMAL_POINT_GROUP3_SPACE = new MoneyAmountStyle('0', '+', '-', '.', GroupingStyle.FULL, 
    ' ', 3, 0, false, false)

  /**
   * A style that uses ASCII digits/negative sign, the decimal point
   * and no grouping of large amounts.
   * Forced decimal point is disabled.
   */
  val ASCII_DECIMAL_POINT_NO_GROUPING = new MoneyAmountStyle('0', '+', '-', '.', GroupingStyle.NONE, 
    ',', 3, 0, false, false)

  /**
   * A style that uses ASCII digits/negative sign, the decimal comma
   * and groups large amounts in 3's using a dot.
   * Forced decimal point is disabled.
   */
  val ASCII_DECIMAL_COMMA_GROUP3_DOT = new MoneyAmountStyle('0', '+', '-', ',', GroupingStyle.FULL, '.', 
    3, 0, false, false)

  /**
   * A style that uses ASCII digits/negative sign, the decimal comma
   * and groups large amounts in 3's using a space.
   * Forced decimal point is disabled.
   */
  val ASCII_DECIMAL_COMMA_GROUP3_SPACE = new MoneyAmountStyle('0', '+', '-', ',', GroupingStyle.FULL, 
    ' ', 3, 0, false, false)

  /**
   * A style that uses ASCII digits/negative sign, the decimal point
   * and no grouping of large amounts.
   * Forced decimal point is disabled.
   */
  val ASCII_DECIMAL_COMMA_NO_GROUPING = new MoneyAmountStyle('0', '+', '-', ',', GroupingStyle.NONE, 
    '.', 3, 0, false, false)

  /**
   * A style that will be filled in with localized values using the locale of the formatter.
   * Grouping is enabled. Forced decimal point is disabled.
   */
  val LOCALIZED_GROUPING = new MoneyAmountStyle(-1, -1, -1, -1, GroupingStyle.FULL, -1, -1, -1, false, 
    false)

  /**
   * A style that will be filled in with localized values using the locale of the formatter.
   * Grouping is disabled. Forced decimal point is disabled.
   */
  val LOCALIZED_NO_GROUPING = new MoneyAmountStyle(-1, -1, -1, -1, GroupingStyle.NONE, -1, -1, -1, false, 
    false)

  /**
   * Cache of localized styles.
   */
  private val LOCALIZED_CACHE = new ConcurrentHashMap[Locale, MoneyAmountStyle]()

  /**
   * Gets a localized style.
   * <p>
   * This creates a localized style for the specified locale.
   * Grouping will be enabled, forced decimal point will be disabled,
   * absolute values will be disabled.
   *
   * @param locale  the locale to use, not null
   * @return the new instance, never null
   */
  def of(locale: Locale): MoneyAmountStyle = getLocalizedStyle(locale)

  /**
   * Gets the prototype localized style for the given locale.
   * <p>
   * This uses {@link DecimalFormatSymbols} and {@link NumberFormat}.
   * <p>
   * If JDK 6 or newer is being used, {@code DecimalFormatSymbols.getInstance(locale)}
   * will be used in order to allow the use of locales defined as extensions.
   * Otherwise, {@code new DecimalFormatSymbols(locale)} will be used.
   *
   * @param locale  the {@link Locale} used to get the correct {@link DecimalFormatSymbols}
   * @return the symbols, never null
   */
  private def getLocalizedStyle(locale: Locale): MoneyAmountStyle = {
    var protoStyle = LOCALIZED_CACHE.get(locale)
    if (protoStyle == null) {
      var symbols: DecimalFormatSymbols = null
      try {
        val method = classOf[DecimalFormatSymbols].getMethod("getInstance", Array(classOf[Locale]))
        symbols = method.invoke(null, Array(locale)).asInstanceOf[DecimalFormatSymbols]
      } catch {
        case ex: Exception => symbols = new DecimalFormatSymbols(locale)
      }
      val format = NumberFormat.getCurrencyInstance(locale)
      val size = (if (format.isInstanceOf[DecimalFormat]) format.asInstanceOf[DecimalFormat].getGroupingSize else 3)
      protoStyle = new MoneyAmountStyle(symbols.getZeroDigit, '+', symbols.getMinusSign, symbols.getMonetaryDecimalSeparator, 
        GroupingStyle.FULL, symbols.getGroupingSeparator, size, 0, false, false)
      LOCALIZED_CACHE.putIfAbsent(locale, protoStyle)
    }
    protoStyle
  }
}

/**
 * Defines the style that the amount of a monetary value will be formatted with.
 * <p>
 * The style contains a number of fields that may be configured based on the locale:
 * <ul>
 * <li>character used for zero, which defined all the numbers from zero to nine
 * <li>character used for positive and negative symbols
 * <li>character used for the decimal point
 * <li>whether and how to group the amount
 * <li>character used for grouping, such as grouping thousands
 * <li>size for each group, such as 3 for thousands
 * <li>whether to always use a decimal point
 * </ul>
 * <p>
 * The style can be used in three basic ways.
 * <ul>
 * <li>set all the fields manually, resulting in the same amount style for all locales
 * <li>call {@link #localize} manually and optionally adjust to set as required
 * <li>leave the localized fields as {@code null} and let the locale in the
 *  formatter to determine the style
 * </ul>
 * <p>
 * This class is immutable and thread-safe.
 */
@SerialVersionUID(1L)
class MoneyAmountStyle private (val zeroCharacter: Int, 
    val positiveCharacter: Int, 
    val negativeCharacter: Int, 
    val decimalPointCharacter: Int, 
    @BeanProperty val groupingStyle: GroupingStyle, 
    val groupingCharacter: Int, 
    val groupingSize: Int, 
    val extendedGroupingSize: Int, 
    val forceDecimalPoint: Boolean, 
    @BooleanBeanProperty val absValue: Boolean) extends Serializable {

  /**
   * Returns a {@code MoneyAmountStyle} instance configured for the specified locale.
   * <p>
   * This method will return a new instance where each field that was defined
   * to be localized (by being set to {@code null}) is filled in.
   * If this instance is fully defined (with all fields non-null), then this
   * method has no effect. Once this method is called, no method will return null.
   * <p>
   * The settings for the locale are pulled from {@link DecimalFormatSymbols} and
   * {@link DecimalFormat}.
   *
   * @param locale  the locale to use, not null
   * @return the new instance for chaining, never null
   */
  def localize(locale: Locale): MoneyAmountStyle = {
    MoneyFormatter.checkNotNull(locale, "Locale must not be null")
    var result = this
    var protoStyle: MoneyAmountStyle = null
    if (zeroCharacter < 0) {
      protoStyle = getLocalizedStyle(locale)
      result = result.withZeroCharacter(protoStyle.getZeroCharacter)
    }
    if (positiveCharacter < 0) {
      protoStyle = getLocalizedStyle(locale)
      result = result.withPositiveSignCharacter(protoStyle.getPositiveSignCharacter)
    }
    if (negativeCharacter < 0) {
      protoStyle = getLocalizedStyle(locale)
      result = result.withNegativeSignCharacter(protoStyle.getNegativeSignCharacter)
    }
    if (decimalPointCharacter < 0) {
      protoStyle = (if (protoStyle == null) getLocalizedStyle(locale) else protoStyle)
      result = result.withDecimalPointCharacter(protoStyle.getDecimalPointCharacter)
    }
    if (groupingCharacter < 0) {
      protoStyle = (if (protoStyle == null) getLocalizedStyle(locale) else protoStyle)
      result = result.withGroupingCharacter(protoStyle.getGroupingCharacter)
    }
    if (groupingSize < 0) {
      protoStyle = (if (protoStyle == null) getLocalizedStyle(locale) else protoStyle)
      result = result.withGroupingSize(protoStyle.getGroupingSize)
    }
    if (extendedGroupingSize < 0) {
      protoStyle = (if (protoStyle == null) getLocalizedStyle(locale) else protoStyle)
      result = result.withExtendedGroupingSize(protoStyle.getExtendedGroupingSize)
    }
    result
  }

  /**
   * Gets the character used for zero, and defining the characters zero to nine.
   * <p>
   * The UTF-8 standard supports a number of different numeric scripts.
   * Each script has the characters in order from zero to nine.
   * This method returns the zero character, which therefore also defines one to nine.
   *
   * @return the zero character, null if to be determined by locale
   */
  def getZeroCharacter(): java.lang.Character = {
    if (zeroCharacter < 0) null else zeroCharacter.toChar
  }

  /**
   * Returns a copy of this style with the specified zero character.
   * <p>
   * The UTF-8 standard supports a number of different numeric scripts.
   * Each script has the characters in order from zero to nine.
   * This method sets the zero character, which therefore also defines one to nine.
   * <p>
   * For English, this is a '0'. Some other scripts use different characters
   * for the numbers zero to nine.
   *
   * @param zeroCharacter  the zero character, null if to be determined by locale
   * @return the new instance for chaining, never null
   */
  def withZeroCharacter(zeroCharacter: java.lang.Character): MoneyAmountStyle = {
    val zeroVal = (if (zeroCharacter == null) -1 else zeroCharacter)
    if (zeroVal == this.zeroCharacter) {
      return this
    }
    new MoneyAmountStyle(zeroVal, positiveCharacter, negativeCharacter, decimalPointCharacter, groupingStyle, 
      groupingCharacter, groupingSize, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Gets the character used for the positive sign character.
   * <p>
   * The standard ASCII symbol is '+'.
   *
   * @return the format for positive amounts, null if to be determined by locale
   */
  def getPositiveSignCharacter(): java.lang.Character = {
    if (positiveCharacter < 0) null else positiveCharacter.toChar
  }

  /**
   * Returns a copy of this style with the specified positive sign character.
   * <p>
   * The standard ASCII symbol is '+'.
   *
   * @param positiveCharacter  the positive character, null if to be determined by locale
   * @return the new instance for chaining, never null
   */
  def withPositiveSignCharacter(positiveCharacter: java.lang.Character): MoneyAmountStyle = {
    val positiveVal = (if (positiveCharacter == null) -1 else positiveCharacter)
    if (positiveVal == this.positiveCharacter) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveVal, negativeCharacter, decimalPointCharacter, groupingStyle, 
      groupingCharacter, groupingSize, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Gets the character used for the negative sign character.
   * <p>
   * The standard ASCII symbol is '-'.
   *
   * @return the format for negative amounts, null if to be determined by locale
   */
  def getNegativeSignCharacter(): java.lang.Character = {
    if (negativeCharacter < 0) null else negativeCharacter.toChar
  }

  /**
   * Returns a copy of this style with the specified negative sign character.
   * <p>
   * The standard ASCII symbol is '-'.
   *
   * @param negativeCharacter  the negative character, null if to be determined by locale
   * @return the new instance for chaining, never null
   */
  def withNegativeSignCharacter(negativeCharacter: java.lang.Character): MoneyAmountStyle = {
    val negativeVal = (if (negativeCharacter == null) -1 else negativeCharacter)
    if (negativeVal == this.negativeCharacter) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveCharacter, negativeVal, decimalPointCharacter, groupingStyle, 
      groupingCharacter, groupingSize, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Gets the character used for the decimal point.
   *
   * @return the decimal point character, null if to be determined by locale
   */
  def getDecimalPointCharacter(): java.lang.Character = {
    if (decimalPointCharacter < 0) null else decimalPointCharacter.toChar
  }

  /**
   * Returns a copy of this style with the specified decimal point character.
   * <p>
   * For English, this is a dot.
   *
   * @param decimalPointCharacter  the decimal point character, null if to be determined by locale
   * @return the new instance for chaining, never null
   */
  def withDecimalPointCharacter(decimalPointCharacter: java.lang.Character): MoneyAmountStyle = {
    val dpVal = (if (decimalPointCharacter == null) -1 else decimalPointCharacter)
    if (dpVal == this.decimalPointCharacter) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveCharacter, negativeCharacter, dpVal, groupingStyle, groupingCharacter, 
      groupingSize, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Gets the character used to separate groups, typically thousands.
   *
   * @return the grouping character, null if to be determined by locale
   */
  def getGroupingCharacter(): java.lang.Character = {
    if (groupingCharacter < 0) null else groupingCharacter.toChar
  }

  /**
   * Returns a copy of this style with the specified grouping character.
   * <p>
   * For English, this is a comma.
   *
   * @param groupingCharacter  the grouping character, null if to be determined by locale
   * @return the new instance for chaining, never null
   */
  def withGroupingCharacter(groupingCharacter: java.lang.Character): MoneyAmountStyle = {
    val groupingVal = (if (groupingCharacter == null) -1 else groupingCharacter)
    if (groupingVal == this.groupingCharacter) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveCharacter, negativeCharacter, decimalPointCharacter, 
      groupingStyle, groupingVal, groupingSize, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Gets the size of each group, typically 3 for thousands.
   *
   * @return the size of each group, null if to be determined by locale
   */
  def getGroupingSize(): java.lang.Integer = {
    if (groupingSize < 0) null else groupingSize
  }

  /**
   * Returns a copy of this style with the specified grouping size.
   *
   * @param groupingSize  the size of each group, such as 3 for thousands,
   *          not zero or negative, null if to be determined by locale
   * @return the new instance for chaining, never null
   * @throws IllegalArgumentException if the grouping size is zero or less
   */
  def withGroupingSize(groupingSize: java.lang.Integer): MoneyAmountStyle = {
    val sizeVal = (if (groupingSize == null) -1 else groupingSize)
    if (groupingSize != null && sizeVal <= 0) {
      throw new IllegalArgumentException("Grouping size must be greater than zero")
    }
    if (sizeVal == this.groupingSize) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveCharacter, negativeCharacter, decimalPointCharacter, 
      groupingStyle, groupingCharacter, sizeVal, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Gets the size of each group, not typically used.
   * <p>
   * This is primarily used to enable the Indian Number System, where the group
   * closest to the decimal point is of size 3 and other groups are of size 2.
   * The extended grouping size is used for groups that are not next to the decimal point.
   * The value zero is used to indicate that extended grouping is not needed.
   *
   * @return the size of each group, null if to be determined by locale
   */
  def getExtendedGroupingSize(): java.lang.Integer = {
    if (extendedGroupingSize < 0) null else extendedGroupingSize
  }

  /**
   * Returns a copy of this style with the specified extended grouping size.
   *
   * @param extendedGroupingSize  the size of each group, such as 3 for thousands,
   *          not zero or negative, null if to be determined by locale
   * @return the new instance for chaining, never null
   * @throws IllegalArgumentException if the grouping size is zero or less
   */
  def withExtendedGroupingSize(extendedGroupingSize: java.lang.Integer): MoneyAmountStyle = {
    val sizeVal = (if (extendedGroupingSize == null) -1 else extendedGroupingSize)
    if (extendedGroupingSize != null && sizeVal < 0) {
      throw new IllegalArgumentException("Extended grouping size must not be negative")
    }
    if (sizeVal == this.extendedGroupingSize) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveCharacter, negativeCharacter, decimalPointCharacter, 
      groupingStyle, groupingCharacter, groupingSize, sizeVal, forceDecimalPoint, absValue)
  }

  /**
   * Returns a copy of this style with the specified grouping setting.
   *
   * @param groupingStyle  the grouping style, not null
   * @return the new instance for chaining, never null
   */
  def withGroupingStyle(groupingStyle: GroupingStyle): MoneyAmountStyle = {
    MoneyFormatter.checkNotNull(groupingStyle, "groupingStyle")
    if (this.groupingStyle == groupingStyle) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveCharacter, negativeCharacter, decimalPointCharacter, 
      groupingStyle, groupingCharacter, groupingSize, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Gets whether to always use the decimal point, even if there is no fraction.
   *
   * @return whether to force the decimal point on output
   */
  def isForcedDecimalPoint(): Boolean = forceDecimalPoint

  /**
   * Returns a copy of this style with the specified decimal point setting.
   *
   * @param forceDecimalPoint  true to force the use of the decimal point, false to use it if required
   * @return the new instance for chaining, never null
   */
  def withForcedDecimalPoint(forceDecimalPoint: Boolean): MoneyAmountStyle = {
    if (this.forceDecimalPoint == forceDecimalPoint) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveCharacter, negativeCharacter, decimalPointCharacter, 
      groupingStyle, groupingCharacter, groupingSize, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Returns a copy of this style with the specified absolute value setting.
   * <p>
   * If this is set to true, the absolute (unsigned) value will be output.
   * If this is set to false, the signed value will be output.
   * Note that when parsing, signs are accepted.
   *
   * @param absValue  true to output the absolute value, false for the signed value
   * @return the new instance for chaining, never null
   */
  def withAbsValue(absValue: Boolean): MoneyAmountStyle = {
    if (this.absValue == absValue) {
      return this
    }
    new MoneyAmountStyle(zeroCharacter, positiveCharacter, negativeCharacter, decimalPointCharacter, 
      groupingStyle, groupingCharacter, groupingSize, extendedGroupingSize, forceDecimalPoint, absValue)
  }

  /**
   * Compares this style with another.
   *
   * @param other  the other style, null returns false
   * @return true if equal
   */
  override def equals(other: Any): Boolean = {
    if (other == this) {
      return true
    }
    if (other.isInstanceOf[MoneyAmountStyle] == false) {
      return false
    }
    val otherStyle = other.asInstanceOf[MoneyAmountStyle]
    (zeroCharacter == otherStyle.zeroCharacter) && 
      (positiveCharacter == otherStyle.positiveCharacter) && 
      (negativeCharacter == otherStyle.negativeCharacter) && 
      (decimalPointCharacter == otherStyle.decimalPointCharacter) && 
      (groupingStyle == otherStyle.groupingStyle) && 
      (groupingCharacter == otherStyle.groupingCharacter) && 
      (groupingSize == otherStyle.groupingSize) && 
      (forceDecimalPoint == otherStyle.forceDecimalPoint) && 
      (absValue == otherStyle.absValue)
  }

  /**
   * A suitable hash code.
   *
   * @return a hash code
   */
  override def hashCode(): Int = {
    var hash = 13
    hash += zeroCharacter * 17
    hash += positiveCharacter * 17
    hash += negativeCharacter * 17
    hash += decimalPointCharacter * 17
    hash += groupingStyle.hashCode * 17
    hash += groupingCharacter * 17
    hash += groupingSize * 17
    hash += (if (forceDecimalPoint) 2 else 4)
    hash += (if (absValue) 3 else 9)
    hash
  }

  /**
   * Gets a string summary of the style.
   *
   * @return a string summarising the style, never null
   */
  override def toString(): String = {
    "MoneyAmountStyle['" + getZeroCharacter + "','" + getPositiveSignCharacter + 
      "','" + 
      getNegativeSignCharacter + 
      "','" + 
      getDecimalPointCharacter + 
      "','" + 
      getGroupingStyle + 
      "," + 
      getGroupingCharacter + 
      "','" + 
      getGroupingSize + 
      "'," + 
      isForcedDecimalPoint + 
      "'," + 
      isAbsValue + 
      "]"
  }
}
