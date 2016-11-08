package org.joda.money

import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InvalidObjectException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Arrays
import java.util.Collections
import java.util.Currency
import java.util.List
import java.util.Locale
import java.util.Set
import org.testng.annotations.Test
import TestCurrencyUnit._
//remove if not needed
import scala.collection.JavaConversions._

object TestCurrencyUnit {

  private val JDK_GBP = Currency.getInstance("GBP")
}

/**
 * Test CurrencyUnit.
 */
@Test
class TestCurrencyUnit {

  def test_registeredCurrencies() {
    val curList = CurrencyUnit.registeredCurrencies()
    var found = false
    for (currencyUnit <- curList if currencyUnit.getCode == "GBP") {
      found = true
      //break
    }
    assertEquals(found, true)
  }

  def test_registeredCurrencies_sorted() {
    val curList1 = CurrencyUnit.registeredCurrencies()
    val curList2 = CurrencyUnit.registeredCurrencies()
    Collections.sort(curList2)
    assertEquals(curList1, curList2)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_registeredCurrency_nullCode() {
    CurrencyUnit.registerCurrency(null, 991, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidStringCode_empty() {
    CurrencyUnit.registerCurrency("", 991, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidStringCode_1letter() {
    CurrencyUnit.registerCurrency("A", 991, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidStringCode_2letters() {
    CurrencyUnit.registerCurrency("AB", 991, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidStringCode_4letters() {
    CurrencyUnit.registerCurrency("ABCD", 991, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidStringCode_lowerCase() {
    CurrencyUnit.registerCurrency("xxA", 991, 2, Arrays.asList("xx":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidStringCode_number() {
    CurrencyUnit.registerCurrency("123", 991, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidStringCode_dash() {
    CurrencyUnit.registerCurrency("A-", 991, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidNumericCode_small() {
    CurrencyUnit.registerCurrency("TST", -2, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidNumericCode_big() {
    CurrencyUnit.registerCurrency("TST", 1000, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidDP_small() {
    CurrencyUnit.registerCurrency("TST", 991, -2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_invalidDP_big() {
    CurrencyUnit.registerCurrency("TST", 991, 10, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_registeredCurrency_nullCountry() {
    CurrencyUnit.registerCurrency("TST", 991, 2, Arrays.asList(null.asInstanceOf[String]:_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_alreadyRegisteredCode() {
    CurrencyUnit.registerCurrency("GBP", 991, 2, Arrays.asList("GB":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_alreadyRegisteredNumericCode() {
    CurrencyUnit.registerCurrency("TST", 826, 2, Arrays.asList("TS":_*))
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_registeredCurrency_alreadyRegisteredCountry() {
    CurrencyUnit.registerCurrency("GBX", 991, 2, Arrays.asList("GB":_*))
  }

  def test_constants() {
    assertEquals(CurrencyUnit.USD, CurrencyUnit.of("USD"))
    assertEquals(CurrencyUnit.EUR, CurrencyUnit.of("EUR"))
    assertEquals(CurrencyUnit.JPY, CurrencyUnit.of("JPY"))
    assertEquals(CurrencyUnit.GBP, CurrencyUnit.of("GBP"))
    assertEquals(CurrencyUnit.CHF, CurrencyUnit.of("CHF"))
    assertEquals(CurrencyUnit.AUD, CurrencyUnit.of("AUD"))
    assertEquals(CurrencyUnit.CAD, CurrencyUnit.of("CAD"))
  }

  @Test(expectedExceptions = classOf[AssertionError])
  def test_constructor_nullCode() {
    new CurrencyUnit(null, 1.toShort, 2.toShort)
  }

  def test_factory_of_Currency() {
    val test = CurrencyUnit.of(JDK_GBP)
    assertEquals(test.getCode, "GBP")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_nullCurrency() {
    CurrencyUnit.of(null.asInstanceOf[Currency])
  }

  def test_factory_of_String() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.getCode, "GBP")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_String_nullString() {
    CurrencyUnit.of(null.asInstanceOf[String])
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_of_String_unknownCurrency() {
    try {
      CurrencyUnit.of("ABC")
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency 'ABC'")
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_of_String_empty() {
    CurrencyUnit.of("")
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_of_String_tooShort_unknown() {
    CurrencyUnit.of("AB")
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_of_String_tooLong_unknown() {
    CurrencyUnit.of("ABCD")
  }

  def test_factory_ofNumericCode_String() {
    val test = CurrencyUnit.ofNumericCode("826")
    assertEquals(test.getCode, "GBP")
  }

  def test_factory_ofNumericCode_String_2char() {
    val test = CurrencyUnit.ofNumericCode("051")
    assertEquals(test.getCode, "AMD")
  }

  def test_factory_ofNumericCode_String_2charNoPad() {
    val test = CurrencyUnit.ofNumericCode("51")
    assertEquals(test.getCode, "AMD")
  }

  def test_factory_ofNumericCode_String_1char() {
    val test = CurrencyUnit.ofNumericCode("008")
    assertEquals(test.getCode, "ALL")
  }

  def test_factory_ofNumericCode_String_1charNoPad() {
    val test = CurrencyUnit.ofNumericCode("8")
    assertEquals(test.getCode, "ALL")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_ofNumericCode_String_nullString() {
    CurrencyUnit.ofNumericCode(null.asInstanceOf[String])
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_ofNumericCode_String_unknownCurrency() {
    try {
      CurrencyUnit.ofNumericCode("111")
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency '111'")
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_ofNumericCode_String_negative() {
    CurrencyUnit.ofNumericCode("-1")
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_ofNumericCode_String_empty() {
    try {
      CurrencyUnit.ofNumericCode("")
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency ''")
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_ofNumericCode_String_tooLong() {
    try {
      CurrencyUnit.ofNumericCode("1234")
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency '1234'")
        throw ex
      }
    }
  }

  def test_factory_ofNumericCode_int() {
    val test = CurrencyUnit.ofNumericCode(826)
    assertEquals(test.getCode, "GBP")
  }

  def test_factory_ofNumericCode_int_2char() {
    val test = CurrencyUnit.ofNumericCode(51)
    assertEquals(test.getCode, "AMD")
  }

  def test_factory_ofNumericCode_int_1char() {
    val test = CurrencyUnit.ofNumericCode(8)
    assertEquals(test.getCode, "ALL")
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_ofNumericCode_int_unknownCurrency() {
    try {
      CurrencyUnit.ofNumericCode(111)
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency '111'")
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_ofNumericCode_int_negative() {
    try {
      CurrencyUnit.ofNumericCode(-1)
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency '-1'")
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_ofNumericCode_int_tooLong() {
    try {
      CurrencyUnit.ofNumericCode(1234)
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency '1234'")
        throw ex
      }
    }
  }

  def test_factory_of_Locale() {
    val test = CurrencyUnit.of(Locale.UK)
    assertEquals(test.getCode, "GBP")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Locale_nullLocale() {
    CurrencyUnit.of(null.asInstanceOf[Locale])
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_of_Locale_unknownCurrency() {
    try {
      CurrencyUnit.of(new Locale("en", "XY"))
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency for locale 'en_XY'")
        throw ex
      }
    }
  }

  def test_factory_ofCountry_String() {
    val test = CurrencyUnit.ofCountry("GB")
    assertEquals(test.getCode, "GBP")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_ofCountry_String_nullString() {
    CurrencyUnit.ofCountry(null.asInstanceOf[String])
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_ofCountry_String_unknownCurrency() {
    try {
      CurrencyUnit.ofCountry("gb")
    } catch {
      case ex: IllegalCurrencyException => {
        assertEquals(ex.getMessage, "Unknown currency for country 'gb'")
        throw ex
      }
    }
  }

  def test_factory_getInstance_String() {
    val test = CurrencyUnit.getInstance("GBP")
    assertEquals(test.getCode, "GBP")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_getInstance_String_nullString() {
    CurrencyUnit.getInstance(null.asInstanceOf[String])
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_getInstance_String_unknownCurrency() {
    CurrencyUnit.getInstance("ABC")
  }

  def test_factory_getInstance_Locale() {
    val test = CurrencyUnit.getInstance(Locale.UK)
    assertEquals(test.getCode, "GBP")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_getInstance_Locale_nullString() {
    CurrencyUnit.getInstance(null.asInstanceOf[Locale])
  }

  @Test(expectedExceptions = classOf[IllegalCurrencyException])
  def test_factory_getInstance_Locale_unknownCurrency() {
    CurrencyUnit.getInstance(new Locale("en", "XY"))
  }

  def test_serialization() {
    val cu = CurrencyUnit.of("GBP")
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(cu)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    val input = ois.readObject().asInstanceOf[CurrencyUnit]
    assertEquals(input, cu)
  }

  @Test(expectedExceptions = classOf[InvalidObjectException])
  def test_serialization_invalidNumericCode() {
    val cu = new CurrencyUnit("GBP", 234.toShort, 2.toShort)
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(cu)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    try {
      ois.readObject()
    } catch {
      case ex: InvalidObjectException => {
        assertTrue(ex.getMessage.contains("numeric code"))
        assertTrue(ex.getMessage.contains("currency GBP"))
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[InvalidObjectException])
  def test_serialization_invalidDecimalPlaces() {
    val cu = new CurrencyUnit("GBP", 826.toShort, 1.toShort)
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(cu)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    try {
      ois.readObject()
    } catch {
      case ex: InvalidObjectException => {
        assertTrue(ex.getMessage.contains("decimal places"))
        assertTrue(ex.getMessage.contains("currency GBP"))
        throw ex
      }
    }
  }

  def test_getCurrencyCode_GBP() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.getCode, "GBP")
    assertEquals(test.getCurrencyCode, "GBP")
  }

  def test_getNumeric3Code_GBP() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.getNumeric3Code, "826")
  }

  def test_getNumeric3Code_ALL() {
    val test = CurrencyUnit.of("ALL")
    assertEquals(test.getNumeric3Code, "008")
  }

  def test_getNumeric3Code_AMD() {
    val test = CurrencyUnit.of("AMD")
    assertEquals(test.getNumeric3Code, "051")
  }

  def test_getNumeric3Code_XFU() {
    val test = CurrencyUnit.of("XFU")
    assertEquals(test.getNumeric3Code, "")
  }

  def test_getNumericCode_GBP() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.getNumericCode, 826)
  }

  def test_getCurrencyCodes_GBP() {
    val test = CurrencyUnit.of("GBP").getCountryCodes
    assertEquals(test.contains("GB"), true)
    assertEquals(test.contains("IM"), true)
    assertEquals(test.contains("JE"), true)
    assertEquals(test.contains("GG"), true)
    assertEquals(test.contains("GS"), true)
    assertEquals(test.contains("IO"), true)
  }

  def test_getDecimalPlaces_GBP() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.getDecimalPlaces, 2)
  }

  def test_getDecimalPlaces_JPY() {
    val test = CurrencyUnit.of("JPY")
    assertEquals(test.getDecimalPlaces, 0)
  }

  def test_getDecimalPlaces_XXX() {
    val test = CurrencyUnit.of("XXX")
    assertEquals(test.getDecimalPlaces, 0)
  }

  def test_isPseudoCurrency_GBP() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.isPseudoCurrency, false)
  }

  def test_isPseudoCurrency_JPY() {
    val test = CurrencyUnit.of("JPY")
    assertEquals(test.isPseudoCurrency, false)
  }

  def test_isPseudoCurrency_XXX() {
    val test = CurrencyUnit.of("XXX")
    assertEquals(test.isPseudoCurrency, true)
  }

  def test_getDefaultFractionDigits_GBP() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.getDefaultFractionDigits, 2)
  }

  def test_getDefaultFractionDigits_JPY() {
    val test = CurrencyUnit.of("JPY")
    assertEquals(test.getDefaultFractionDigits, 0)
  }

  def test_getDefaultFractionDigits_XXX() {
    val test = CurrencyUnit.of("XXX")
    assertEquals(test.getDefaultFractionDigits, -1)
  }

  def test_getSymbol_GBP() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("GBP")
      assertEquals(test.getSymbol, "£")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_JPY() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("JPY")
      assertEquals(test.getSymbol, "JPY")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_TMT() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("TMT")
      assertEquals(test.getSymbol, "TMT")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_XXX() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("XXX")
      assertEquals(test.getSymbol, "XXX")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_Locale_GBP_UK() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("GBP")
      assertEquals(test.getSymbol(Locale.UK), "£")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_Locale_GBP_France() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("GBP")
      assertEquals(test.getSymbol(Locale.FRANCE), "GBP")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_Locale_USD_UK() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("USD")
      assertEquals(test.getSymbol(Locale.US), "$")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_Locale_USD_France() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("USD")
      assertEquals(test.getSymbol(Locale.FRANCE), "USD")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_Locale_JPY_Japan() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("JPY")
      assertEquals(test.getSymbol(Locale.JAPAN), "￥")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_TMT_UK() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("TMT")
      assertEquals(test.getSymbol(Locale.UK), "TMT")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_getSymbol_Locale_XXX() {
    val loc = Locale.getDefault
    try {
      Locale.setDefault(Locale.UK)
      val test = CurrencyUnit.of("XXX")
      assertEquals(test.getSymbol(Locale.FRANCE), "XXX")
    } finally {
      Locale.setDefault(loc)
    }
  }

  def test_toCurrency() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.toCurrency(), JDK_GBP)
  }

  def test_compareTo() {
    val a = CurrencyUnit.of("EUR")
    val b = CurrencyUnit.of("GBP")
    val c = CurrencyUnit.of("JPY")
    assertEquals(a.compareTo(a), 0)
    assertEquals(b.compareTo(b), 0)
    assertEquals(c.compareTo(c), 0)
    assertTrue(a.compareTo(b) < 0)
    assertTrue(b.compareTo(a) > 0)
    assertTrue(a.compareTo(c) < 0)
    assertTrue(c.compareTo(a) > 0)
    assertTrue(b.compareTo(c) < 0)
    assertTrue(c.compareTo(b) > 0)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_compareTo_null() {
    CurrencyUnit.of("EUR").compareTo(null)
  }

  def test_equals_hashCode() {
    val a = CurrencyUnit.of("GBP")
    val b = CurrencyUnit.of("GBP")
    val c = CurrencyUnit.of("EUR")
    assertEquals(a == a, true)
    assertEquals(b == b, true)
    assertEquals(c == c, true)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode == b.hashCode, true)
    assertEquals(a == c, false)
    assertEquals(b == c, false)
  }

  def test_equals_false() {
    val a = CurrencyUnit.of("GBP")
    assertEquals(a == null, false)
    assertEquals(a == "String", false)
    assertEquals(a == new AnyRef(), false)
  }

  def test_toString() {
    val test = CurrencyUnit.of("GBP")
    assertEquals(test.toString, "GBP")
  }
}
