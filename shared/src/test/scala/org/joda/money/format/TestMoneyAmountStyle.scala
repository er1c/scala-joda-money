package org.joda.money.format

import org.testng.Assert.assertEquals
import org.testng.Assert.assertSame
import org.testng.Assert.assertTrue
import java.math.BigDecimal
import java.util.Locale
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import TestMoneyAmountStyle._
//remove if not needed
import scala.collection.JavaConversions._

object TestMoneyAmountStyle {

  private val cCachedLocale = Locale.getDefault

  private val TEST_GB_LOCALE = new Locale("en", "GB", "TEST")

  private val TEST_DE_LOCALE = new Locale("de", "DE", "TEST")

  private val MONEY = BigMoney.of(CurrencyUnit.GBP, new BigDecimal("87654321.12345678"))
}

/**
 * Test MoneyAmountStyle.
 */
@Test
class TestMoneyAmountStyle {

  @BeforeMethod
  def beforeMethod() {
    Locale.setDefault(TEST_GB_LOCALE)
  }

  @AfterMethod
  def afterMethod() {
    Locale.setDefault(cCachedLocale)
  }

  def test_ASCII_DECIMAL_POINT_GROUP3_COMMA() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_ASCII_DECIMAL_POINT_GROUP3_COMMA_print() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87,654,321.123,456,78")
  }

  def test_ASCII_DECIMAL_POINT_GROUP3_SPACE() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_SPACE
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, ' '.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_ASCII_ASCII_DECIMAL_POINT_GROUP3_SPACE_print() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_SPACE
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87 654 321.123 456 78")
  }

  def test_ASCII_DECIMAL_POINT_NO_GROUPING() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_POINT_NO_GROUPING
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.NONE)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_ASCII_DECIMAL_POINT_NO_GROUPING_print() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_POINT_NO_GROUPING
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87654321.12345678")
  }

  def test_ASCII_ASCII_DECIMAL_COMMA_GROUP3_DOT() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_COMMA_GROUP3_DOT
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_ASCII_DECIMAL_COMMA_GROUP3_DOT_print() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_COMMA_GROUP3_DOT
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87.654.321,123.456.78")
  }

  def test_ASCII_DECIMAL_COMMA_GROUP3_SPACE() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_COMMA_GROUP3_SPACE
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, ' '.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_ASCII_DECIMAL_COMMA_GROUP3_SPACE_print() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_COMMA_GROUP3_SPACE
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87 654 321,123 456 78")
  }

  def test_ASCII_DECIMAL_COMMA_NO_GROUPING() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_COMMA_NO_GROUPING
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.NONE)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_ASCII_DECIMAL_COMMA_NO_GROUPING_print() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_COMMA_NO_GROUPING
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87654321,12345678")
  }

  def test_LOCALIZED_GROUPING() {
    val style = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(style.getZeroCharacter, null)
    assertEquals(style.getPositiveSignCharacter, null)
    assertEquals(style.getNegativeSignCharacter, null)
    assertEquals(style.getDecimalPointCharacter, null)
    assertEquals(style.getGroupingCharacter, null)
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, null)
    assertEquals(style.getExtendedGroupingSize, null)
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_LOCALIZED_GROUPING_print() {
    val style = MoneyAmountStyle.LOCALIZED_GROUPING
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87,654,321.123,456,78")
  }

  def test_LOCALIZED_NO_GROUPING() {
    val style = MoneyAmountStyle.LOCALIZED_NO_GROUPING
    assertEquals(style.getZeroCharacter, null)
    assertEquals(style.getPositiveSignCharacter, null)
    assertEquals(style.getNegativeSignCharacter, null)
    assertEquals(style.getDecimalPointCharacter, null)
    assertEquals(style.getGroupingCharacter, null)
    assertEquals(style.getGroupingStyle, GroupingStyle.NONE)
    assertEquals(style.getGroupingSize, null)
    assertEquals(style.getExtendedGroupingSize, null)
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_LOCALIZED_NO_GROUPING_print() {
    val style = MoneyAmountStyle.LOCALIZED_NO_GROUPING
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87654321.12345678")
  }

  def test_print_groupBeforeDecimal() {
    val style = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingStyle(GroupingStyle.BEFORE_DECIMAL_POINT)
    val test = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
    assertEquals(test.print(MONEY), "87,654,321.12345678")
  }

  def test_of_Locale_GB() {
    val style = MoneyAmountStyle.of(TEST_GB_LOCALE)
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_of_Locale_DE() {
    val style = MoneyAmountStyle.of(TEST_DE_LOCALE)
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_localize_GB() {
    val style = MoneyAmountStyle.LOCALIZED_GROUPING.localize(TEST_GB_LOCALE)
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_localize_DE() {
    val style = MoneyAmountStyle.LOCALIZED_GROUPING.localize(TEST_DE_LOCALE)
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_localize_DE_fixedZero() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING.withZeroCharacter('_')
    val style = base.localize(TEST_DE_LOCALE)
    assertEquals(style.getZeroCharacter, '_'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_localize_DE_fixedPositive() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING.withPositiveSignCharacter('_')
    val style = base.localize(TEST_DE_LOCALE)
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '_'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_localize_DE_fixedNegative() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING.withNegativeSignCharacter('_')
    val style = base.localize(TEST_DE_LOCALE)
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '_'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_localize_DE_fixedDecimal() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING.withDecimalPointCharacter('_')
    val style = base.localize(TEST_DE_LOCALE)
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, '_'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_localize_DE_fixedGrouping() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingCharacter('_')
    val style = base.localize(TEST_DE_LOCALE)
    assertEquals(style.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, ','.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '_'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_localize_DE_fixedZeroAndDecimal() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING.withZeroCharacter('_')
      .withDecimalPointCharacter('-')
    val style = base.localize(TEST_DE_LOCALE)
    assertEquals(style.getZeroCharacter, '_'.asInstanceOf[java.lang.Character])
    assertEquals(style.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    assertEquals(style.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getDecimalPointCharacter, '-'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingCharacter, '.'.asInstanceOf[java.lang.Character])
    assertEquals(style.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(style.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    assertEquals(style.getExtendedGroupingSize, 0.asInstanceOf[java.lang.Integer])
    assertEquals(style.isForcedDecimalPoint, false)
  }

  def test_withZeroCharacter() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getZeroCharacter, null)
    val test = base.withZeroCharacter('_')
    assertEquals(base.getZeroCharacter, null)
    assertEquals(test.getZeroCharacter, '_'.asInstanceOf[java.lang.Character])
  }

  def test_withZeroCharacter_same() {
    val base = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    assertEquals(base.getZeroCharacter, '0'.asInstanceOf[java.lang.Character])
    val test = base.withZeroCharacter('0')
    assertSame(test, base)
  }

  def test_withZeroCharacter_sameNull() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getZeroCharacter, null)
    val test = base.withZeroCharacter(null)
    assertSame(test, base)
  }

  def test_withPositiveSignCharacter() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getPositiveSignCharacter, null)
    val test = base.withPositiveSignCharacter('_')
    assertEquals(base.getPositiveSignCharacter, null)
    assertEquals(test.getPositiveSignCharacter, '_'.asInstanceOf[java.lang.Character])
  }

  def test_withPositiveSignCharacter_same() {
    val base = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    assertEquals(base.getPositiveSignCharacter, '+'.asInstanceOf[java.lang.Character])
    val test = base.withPositiveSignCharacter('+')
    assertSame(test, base)
  }

  def test_withPositiveSignCharacter_sameNull() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getPositiveSignCharacter, null)
    val test = base.withPositiveSignCharacter(null)
    assertSame(test, base)
  }

  def test_withNegativeSignCharacter() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getNegativeSignCharacter, null)
    val test = base.withNegativeSignCharacter('_')
    assertEquals(base.getNegativeSignCharacter, null)
    assertEquals(test.getNegativeSignCharacter, '_'.asInstanceOf[java.lang.Character])
  }

  def test_withNegativeSignCharacter_same() {
    val base = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    assertEquals(base.getNegativeSignCharacter, '-'.asInstanceOf[java.lang.Character])
    val test = base.withNegativeSignCharacter('-')
    assertSame(test, base)
  }

  def test_withNegativeSignCharacter_sameNull() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getNegativeSignCharacter, null)
    val test = base.withNegativeSignCharacter(null)
    assertSame(test, base)
  }

  def test_withDecimalPointCharacter() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getDecimalPointCharacter, null)
    val test = base.withDecimalPointCharacter('_')
    assertEquals(base.getDecimalPointCharacter, null)
    assertEquals(test.getDecimalPointCharacter, '_'.asInstanceOf[java.lang.Character])
  }

  def test_withDecimalPointCharacter_same() {
    val base = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    assertEquals(base.getDecimalPointCharacter, '.'.asInstanceOf[java.lang.Character])
    val test = base.withDecimalPointCharacter('.')
    assertSame(test, base)
  }

  def test_withDecimalPointCharacter_sameNull() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getDecimalPointCharacter, null)
    val test = base.withDecimalPointCharacter(null)
    assertSame(test, base)
  }

  def test_withGroupingCharacter() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getGroupingCharacter, null)
    val test = base.withGroupingCharacter('_')
    assertEquals(base.getGroupingCharacter, null)
    assertEquals(test.getGroupingCharacter, '_'.asInstanceOf[java.lang.Character])
  }

  def test_withGroupingCharacter_same() {
    val base = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    assertEquals(base.getGroupingCharacter, ','.asInstanceOf[java.lang.Character])
    val test = base.withGroupingCharacter(',')
    assertSame(test, base)
  }

  def test_withGroupingCharacter_sameNull() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getGroupingCharacter, null)
    val test = base.withGroupingCharacter(null)
    assertSame(test, base)
  }

  def test_withGroupingStyle() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getGroupingStyle, GroupingStyle.FULL)
    val test = base.withGroupingStyle(GroupingStyle.BEFORE_DECIMAL_POINT)
    assertEquals(base.getGroupingStyle, GroupingStyle.FULL)
    assertEquals(test.getGroupingStyle, GroupingStyle.BEFORE_DECIMAL_POINT)
  }

  def test_withGroupingStyle_same() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getGroupingStyle, GroupingStyle.FULL)
    val test = base.withGroupingStyle(GroupingStyle.FULL)
    assertSame(test, base)
  }

  def test_withGroupingSize() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getGroupingSize, null)
    val test = base.withGroupingSize(6)
    assertEquals(base.getGroupingSize, null)
    assertEquals(test.getGroupingSize, 6.asInstanceOf[java.lang.Integer])
  }

  def test_withGroupingSize_same() {
    val base = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    assertEquals(base.getGroupingSize, 3.asInstanceOf[java.lang.Integer])
    val test = base.withGroupingSize(3)
    assertSame(test, base)
  }

  def test_withGroupingSize_sameNull() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.getGroupingSize, null)
    val test = base.withGroupingSize(null)
    assertSame(test, base)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_withGroupingSize_negative() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    base.withGroupingSize(-1)
  }

  def test_withForcedDecimalPoint() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.isForcedDecimalPoint, false)
    val test = base.withForcedDecimalPoint(true)
    assertEquals(base.isForcedDecimalPoint, false)
    assertEquals(test.isForcedDecimalPoint, true)
  }

  def test_withForcedDecimalPoint_same() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.isForcedDecimalPoint, false)
    val test = base.withForcedDecimalPoint(false)
    assertSame(test, base)
  }

  def test_withAbsValue() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.isAbsValue, false)
    val test = base.withAbsValue(true)
    assertEquals(base.isAbsValue, false)
    assertEquals(test.isAbsValue, true)
  }

  def test_withAbsValue_same() {
    val base = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(base.isAbsValue, false)
    val test = base.withAbsValue(false)
    assertSame(test, base)
  }

  def test_equals_same() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(a == a, true)
  }

  def test_equals_otherType() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(a == "", false)
  }

  def test_equals_null() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    assertEquals(a == null, false)
  }

  def test_equals_equal_zeroChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withZeroCharacter('_')
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withZeroCharacter('_')
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_zeroChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withZeroCharacter('_')
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_equals_equal_positiveChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withPositiveSignCharacter('_')
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withPositiveSignCharacter('_')
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_positiveChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withPositiveSignCharacter('_')
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_equals_equal_negativeChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withNegativeSignCharacter('_')
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withNegativeSignCharacter('_')
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_negativeChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withNegativeSignCharacter('_')
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_equals_equal_decimalPointChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withDecimalPointCharacter('_')
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withDecimalPointCharacter('_')
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_decimalPointChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withDecimalPointCharacter('_')
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_equals_equal_groupingChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingCharacter('_')
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingCharacter('_')
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_groupingChar() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingCharacter('_')
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_equals_equal_groupingStyle() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingStyle(GroupingStyle.BEFORE_DECIMAL_POINT)
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingStyle(GroupingStyle.BEFORE_DECIMAL_POINT)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_groupingStyle() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingStyle(GroupingStyle.BEFORE_DECIMAL_POINT)
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingStyle(GroupingStyle.NONE)
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_equals_equal_groupingSize() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingSize(4)
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingSize(4)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_groupingSize() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withGroupingSize(4)
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_equals_equal_forcedDecimalPoint_false() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withForcedDecimalPoint(true)
      .withForcedDecimalPoint(false)
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withForcedDecimalPoint(true)
      .withForcedDecimalPoint(false)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_equal_forcedDecimalPoint_true() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withForcedDecimalPoint(true)
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withForcedDecimalPoint(true)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_forcedDecimalPoint() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withForcedDecimalPoint(true)
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_equals_equal_absValue_false() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withAbsValue(true)
      .withAbsValue(false)
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withAbsValue(true)
      .withAbsValue(false)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_equal_absValue_true() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING.withAbsValue(true)
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withAbsValue(true)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode, b.hashCode)
  }

  def test_equals_notEqual_absValue() {
    val a = MoneyAmountStyle.LOCALIZED_GROUPING
    val b = MoneyAmountStyle.LOCALIZED_GROUPING.withAbsValue(true)
    assertEquals(a == b, false)
    assertEquals(b == a, false)
  }

  def test_toString() {
    val test = MoneyAmountStyle.LOCALIZED_GROUPING
    assertTrue(test.toString.startsWith("MoneyAmountStyle"))
  }
}
