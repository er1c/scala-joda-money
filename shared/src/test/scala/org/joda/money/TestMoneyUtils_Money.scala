package org.joda.money

import org.testng.Assert.assertEquals
import org.testng.Assert.assertSame
import org.testng.annotations.Test
import TestMoneyUtils_Money._
//remove if not needed
import scala.collection.JavaConversions._

object TestMoneyUtils_Money {

  private val GBP_0 = Money.parse("GBP 0")

  private val GBP_20 = Money.parse("GBP 20")

  private val GBP_30 = Money.parse("GBP 30")

  private val GBP_50 = Money.parse("GBP 50")

  private val GBP_M10 = Money.parse("GBP -10")

  private val GBP_M30 = Money.parse("GBP -30")

  private val EUR_30 = Money.parse("EUR 30")
}

/**
 * Test MoneyUtils.
 */
@Test
class TestMoneyUtils_Money {

  def test_checkNotNull_notNull() {
    MoneyUtils.checkNotNull(new AnyRef(), "")
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_checkNotNull_null() {
    try {
      MoneyUtils.checkNotNull(null, "Hello")
    } catch {
      case ex: NullPointerException => {
        assertEquals(ex.getMessage, "Hello")
        throw ex
      }
    }
  }

  def test_isZero() {
    assertSame(MoneyUtils.isZero(null), true)
    assertSame(MoneyUtils.isZero(GBP_0), true)
    assertSame(MoneyUtils.isZero(GBP_30), false)
    assertSame(MoneyUtils.isZero(GBP_M30), false)
  }

  def test_isPositive() {
    assertSame(MoneyUtils.isPositive(null), false)
    assertSame(MoneyUtils.isPositive(GBP_0), false)
    assertSame(MoneyUtils.isPositive(GBP_30), true)
    assertSame(MoneyUtils.isPositive(GBP_M30), false)
  }

  def test_isPositiveOrZero() {
    assertSame(MoneyUtils.isPositiveOrZero(null), true)
    assertSame(MoneyUtils.isPositiveOrZero(GBP_0), true)
    assertSame(MoneyUtils.isPositiveOrZero(GBP_30), true)
    assertSame(MoneyUtils.isPositiveOrZero(GBP_M30), false)
  }

  def test_isNegative() {
    assertSame(MoneyUtils.isNegative(null), false)
    assertSame(MoneyUtils.isNegative(GBP_0), false)
    assertSame(MoneyUtils.isNegative(GBP_30), false)
    assertSame(MoneyUtils.isNegative(GBP_M30), true)
  }

  def test_isNegativeOrZero() {
    assertSame(MoneyUtils.isNegativeOrZero(null), true)
    assertSame(MoneyUtils.isNegativeOrZero(GBP_0), true)
    assertSame(MoneyUtils.isNegativeOrZero(GBP_30), false)
    assertSame(MoneyUtils.isNegativeOrZero(GBP_M30), true)
  }

  def test_max1() {
    assertSame(MoneyUtils.max(GBP_20, GBP_30), GBP_30)
  }

  def test_max2() {
    assertSame(MoneyUtils.max(GBP_30, GBP_20), GBP_30)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_max_differentCurrencies() {
    MoneyUtils.max(GBP_20, EUR_30)
  }

  def test_max_null1() {
    assertSame(MoneyUtils.max(null.asInstanceOf[Money], GBP_30), GBP_30)
  }

  def test_max_null2() {
    assertSame(MoneyUtils.max(GBP_20, null.asInstanceOf[Money]), GBP_20)
  }

  def test_max_nullBoth() {
    assertEquals(MoneyUtils.max(null.asInstanceOf[Money], null.asInstanceOf[Money]), null)
  }

  def test_min1() {
    assertSame(MoneyUtils.min(GBP_20, GBP_30), GBP_20)
  }

  def test_min2() {
    assertSame(MoneyUtils.min(GBP_30, GBP_20), GBP_20)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_min_differentCurrencies() {
    MoneyUtils.min(GBP_20, EUR_30)
  }

  def test_min_null1() {
    assertSame(MoneyUtils.min(null.asInstanceOf[Money], GBP_30), GBP_30)
  }

  def test_min_null2() {
    assertSame(MoneyUtils.min(GBP_20, null.asInstanceOf[Money]), GBP_20)
  }

  def test_min_nullBoth() {
    assertEquals(MoneyUtils.min(null.asInstanceOf[Money], null.asInstanceOf[Money]), null)
  }

  def test_add() {
    assertEquals(MoneyUtils.add(GBP_20, GBP_30), GBP_50)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_add_differentCurrencies() {
    MoneyUtils.add(GBP_20, EUR_30)
  }

  def test_add_null1() {
    assertSame(MoneyUtils.add(null.asInstanceOf[Money], GBP_30), GBP_30)
  }

  def test_add_null2() {
    assertSame(MoneyUtils.add(GBP_20, null.asInstanceOf[Money]), GBP_20)
  }

  def test_add_nullBoth() {
    assertEquals(MoneyUtils.add(null.asInstanceOf[Money], null.asInstanceOf[Money]), null)
  }

  def test_subtract() {
    assertEquals(MoneyUtils.subtract(GBP_20, GBP_30), GBP_M10)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_subtract_differentCurrencies() {
    MoneyUtils.subtract(GBP_20, EUR_30)
  }

  def test_subtract_null1() {
    assertEquals(MoneyUtils.subtract(null.asInstanceOf[Money], GBP_30), GBP_M30)
  }

  def test_subtract_null2() {
    assertSame(MoneyUtils.subtract(GBP_20, null.asInstanceOf[Money]), GBP_20)
  }

  def test_subtract_nullBoth() {
    assertEquals(MoneyUtils.subtract(null.asInstanceOf[Money], null.asInstanceOf[Money]), null)
  }
}
