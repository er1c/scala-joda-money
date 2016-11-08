package org.joda.money

import org.testng.Assert.assertEquals
import java.util.List
import org.testng.annotations.Test
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Test CurrencyUnit.
 */
@Test
class TestCurrencyUnitExtension {

  def test_CurrencyFromMoneyData() {
    val curList = CurrencyUnit.registeredCurrencies()
    var found = false
    for (currencyUnit <- curList if currencyUnit.getCode == "GBP") {
      found = true
      //break
    }
    assertEquals(found, true)
  }

  def test_CurrencyFromMoneyDataExtension() {
    val curList = CurrencyUnit.registeredCurrencies()
    var found = false
    for (currencyUnit <- curList if currencyUnit.getCode == "BTC") {
      found = true
      //break
    }
    assertEquals(found, true)
  }

  def test_CurrencyMissing() {
    val curList = CurrencyUnit.registeredCurrencies()
    var found = false
    for (currencyUnit <- curList if currencyUnit.getCode == "NMC") {
      found = true
      //break
    }
    assertEquals(found, false)
  }

  def test_CurrencyEURChanged() {
    val currency = CurrencyUnit.ofCountry("HU")
    assertEquals(currency, CurrencyUnit.EUR)
    assertEquals(CurrencyUnit.EUR.getCountryCodes.contains("HU"), true)
    assertEquals(CurrencyUnit.of("HUF").getCountryCodes.isEmpty, true)
  }
}
