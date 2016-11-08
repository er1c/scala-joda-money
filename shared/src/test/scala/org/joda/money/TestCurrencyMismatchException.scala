package org.joda.money

import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import TestCurrencyMismatchException._
//remove if not needed
import scala.collection.JavaConversions._

object TestCurrencyMismatchException {

  private val GBP = CurrencyUnit.of("GBP")

  private val EUR = CurrencyUnit.of("EUR")
}

/**
 * Test CurrencyMismatchException.
 */
@Test
class TestCurrencyMismatchException {

  def test_new_GBPEUR() {
    val test = new CurrencyMismatchException(GBP, EUR)
    assertEquals(test.getMessage, "Currencies differ: GBP/EUR")
    assertEquals(test.getCause, null)
    assertEquals(test.getFirstCurrency, GBP)
    assertEquals(test.getSecondCurrency, EUR)
  }

  def test_new_nullEUR() {
    val test = new CurrencyMismatchException(null, EUR)
    assertEquals(test.getMessage, "Currencies differ: null/EUR")
    assertEquals(test.getCause, null)
    assertEquals(test.getFirstCurrency, null)
    assertEquals(test.getSecondCurrency, EUR)
  }

  def test_new_GBPnull() {
    val test = new CurrencyMismatchException(GBP, null)
    assertEquals(test.getMessage, "Currencies differ: GBP/null")
    assertEquals(test.getCause, null)
    assertEquals(test.getFirstCurrency, GBP)
    assertEquals(test.getSecondCurrency, null)
  }

  def test_new_nullnull() {
    val test = new CurrencyMismatchException(null, null)
    assertEquals(test.getMessage, "Currencies differ: null/null")
    assertEquals(test.getCause, null)
    assertEquals(test.getFirstCurrency, null)
    assertEquals(test.getSecondCurrency, null)
  }
}
