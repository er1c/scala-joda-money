package org.joda.money.format

import org.testng.Assert.assertEquals
import java.math.BigDecimal
import java.text.ParsePosition
import java.util.Locale
import org.joda.money.CurrencyUnit
import org.testng.annotations.Test
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Test MoneyParseContext.
 */
@Test
class TestMoneyParseContext {

  def test_initialState() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    assertEquals(test.getAmount, null)
    assertEquals(test.getCurrency, null)
    assertEquals(test.getIndex, 0)
    assertEquals(test.getErrorIndex, -1)
    assertEquals(test.getText.toString, "GBP 123")
    assertEquals(test.getTextLength, 7)
    assertEquals(test.isError, false)
    assertEquals(test.isFullyParsed, false)
    assertEquals(test.isComplete, false)
    val pp = new ParsePosition(0)
    pp.setErrorIndex(-1)
    assertEquals(test.toParsePosition(), pp)
  }

  def test_setIndex() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    assertEquals(test.getIndex, 0)
    test.setIndex(2)
    assertEquals(test.getIndex, 2)
  }

  def test_setErrorIndex() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    assertEquals(test.getErrorIndex, -1)
    test.setErrorIndex(3)
    assertEquals(test.getErrorIndex, 3)
  }

  def test_setError() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    assertEquals(test.getIndex, 0)
    assertEquals(test.getErrorIndex, -1)
    test.setError()
    assertEquals(test.getIndex, 0)
    assertEquals(test.getErrorIndex, 0)
  }

  def test_setError_withIndex() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    assertEquals(test.getIndex, 0)
    assertEquals(test.getErrorIndex, -1)
    test.setIndex(2)
    test.setError()
    assertEquals(test.getIndex, 2)
    assertEquals(test.getErrorIndex, 2)
  }

  def test_isComplete_noCurrency() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    test.setAmount(BigDecimal.TEN)
    assertEquals(test.isComplete, false)
  }

  def test_isComplete_noAmount() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    test.setCurrency(CurrencyUnit.GBP)
    assertEquals(test.isComplete, false)
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_toBigMoney_noCurrency() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    test.setAmount(BigDecimal.TEN)
    test.toBigMoney()
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_toBigMoney_noAmount() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    test.setCurrency(CurrencyUnit.GBP)
    test.toBigMoney()
  }

  def test_getTextSubstring_ok() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    assertEquals(test.getTextSubstring(0, 2), "GB")
    assertEquals(test.getTextSubstring(5, 7), "23")
  }

  @Test(expectedExceptions = classOf[IndexOutOfBoundsException])
  def test_getTextSubstring_beforeStart() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    test.getTextSubstring(-1, 2)
  }

  @Test(expectedExceptions = classOf[IndexOutOfBoundsException])
  def test_getTextSubstring_afterEnd() {
    val test = new MoneyParseContext(Locale.FRANCE, "GBP 123", 0)
    test.getTextSubstring(0, 8)
  }
}
