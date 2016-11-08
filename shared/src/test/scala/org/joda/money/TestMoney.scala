package org.joda.money

import org.testng.Assert.assertEquals
import org.testng.Assert.assertSame
import org.testng.Assert.fail
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InvalidObjectException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Arrays
import java.util.Collections
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import TestMoney._
//remove if not needed
import scala.collection.JavaConversions._

object TestMoney {

  private val GBP = CurrencyUnit.of("GBP")

  private val EUR = CurrencyUnit.of("EUR")

  private val USD = CurrencyUnit.of("USD")

  private val JPY = CurrencyUnit.of("JPY")

  private val BIGDEC_2_3 = new BigDecimal("2.3")

  private val BIGDEC_2_34 = new BigDecimal("2.34")

  private val BIGDEC_2_345 = new BigDecimal("2.345")

  private val BIGDEC_M5_78 = new BigDecimal("-5.78")

  private val GBP_0_00 = Money.parse("GBP 0.00")

  private val GBP_1_23 = Money.parse("GBP 1.23")

  private val GBP_2_33 = Money.parse("GBP 2.33")

  private val GBP_2_34 = Money.parse("GBP 2.34")

  private val GBP_2_35 = Money.parse("GBP 2.35")

  private val GBP_2_36 = Money.parse("GBP 2.36")

  private val GBP_5_78 = Money.parse("GBP 5.78")

  private val GBP_M1_23 = Money.parse("GBP -1.23")

  private val GBP_M5_78 = Money.parse("GBP -5.78")

  private val GBP_INT_MAX_PLUS1 = Money.ofMinor(GBP, Integer.MAX_VALUE.toLong + 1)

  private val GBP_INT_MIN_MINUS1 = Money.ofMinor(GBP, Integer.MIN_VALUE.toLong - 1)

  private val GBP_INT_MAX_MAJOR_PLUS1 = Money.ofMinor(GBP, (Integer.MAX_VALUE.toLong + 1) * 100)

  private val GBP_INT_MIN_MAJOR_MINUS1 = Money.ofMinor(GBP, (Integer.MIN_VALUE.toLong - 1) * 100)

  private val GBP_LONG_MAX_PLUS1 = Money.of(GBP, BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE))

  private val GBP_LONG_MIN_MINUS1 = Money.of(GBP, BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE))

  private val GBP_LONG_MAX_MAJOR_PLUS1 = Money.of(GBP, BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE)
    .multiply(BigDecimal.valueOf(100)))

  private val GBP_LONG_MIN_MAJOR_MINUS1 = Money.of(GBP, BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE)
    .multiply(BigDecimal.valueOf(100)))

  private val JPY_423 = Money.parse("JPY 423")

  private val USD_1_23 = Money.parse("USD 1.23")

  private val USD_2_34 = Money.parse("USD 2.34")

  private val USD_2_35 = Money.parse("USD 2.35")
}

/**
 * Test Money.
 */
@Test
class TestMoney {

  def test_factory_of_Currency_BigDecimal() {
    val test = Money.of(GBP, BIGDEC_2_34)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 234)
    assertEquals(test.getAmount.scale(), 2)
  }

  def test_factory_of_Currency_BigDecimal_correctScale() {
    val test = Money.of(GBP, BIGDEC_2_3)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 230)
    assertEquals(test.getAmount.scale(), 2)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_factory_of_Currency_BigDecimal_invalidScaleGBP() {
    Money.of(GBP, BIGDEC_2_345)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_factory_of_Currency_BigDecimal_invalidScaleJPY() {
    Money.of(JPY, BIGDEC_2_3)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_BigDecimal_nullCurrency() {
    Money.of(null.asInstanceOf[CurrencyUnit], BIGDEC_2_34)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_BigDecimal_nullBigDecimal() {
    Money.of(GBP, null.asInstanceOf[BigDecimal])
  }

  def test_factory_of_Currency_BigDecimal_GBP_RoundingMode_DOWN() {
    val test = Money.of(GBP, BIGDEC_2_34, RoundingMode.DOWN)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 234)
    assertEquals(test.getAmount.scale(), 2)
  }

  def test_factory_of_Currency_BigDecimal_JPY_RoundingMode_DOWN() {
    val test = Money.of(JPY, BIGDEC_2_34, RoundingMode.DOWN)
    assertEquals(test.getCurrencyUnit, JPY)
    assertEquals(test.getAmountMinorInt, 2)
    assertEquals(test.getAmount.scale(), 0)
  }

  def test_factory_of_Currency_BigDecimal_JPY_RoundingMode_UP() {
    val test = Money.of(JPY, BIGDEC_2_34, RoundingMode.UP)
    assertEquals(test.getCurrencyUnit, JPY)
    assertEquals(test.getAmountMinorInt, 3)
    assertEquals(test.getAmount.scale(), 0)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_factory_of_Currency_BigDecimal_RoundingMode_UNNECESSARY() {
    Money.of(JPY, BIGDEC_2_34, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_BigDecimal_RoundingMode_nullCurrency() {
    Money.of(null.asInstanceOf[CurrencyUnit], BIGDEC_2_34, RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_BigDecimal_RoundingMode_nullBigDecimal() {
    Money.of(GBP, null.asInstanceOf[BigDecimal], RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_BigDecimal_RoundingMode_nullRoundingMode() {
    Money.of(GBP, BIGDEC_2_34, null.asInstanceOf[RoundingMode])
  }

  def test_factory_of_Currency_double() {
    val test = Money.of(GBP, 2.34d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 234)
    assertEquals(test.getScale, 2)
  }

  def test_factory_of_Currency_double_correctScale() {
    val test = Money.of(GBP, 2.3d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 230)
    assertEquals(test.getScale, 2)
  }

  def test_factory_of_Currency_double_trailingZero1() {
    val test = Money.of(GBP, 1.230d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(123L, 2))
    assertEquals(test.getScale, 2)
  }

  def test_factory_of_Currency_double_trailingZero2() {
    val test = Money.of(GBP, 1.20d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(120L, 2))
    assertEquals(test.getScale, 2)
  }

  def test_factory_of_Currency_double_medium() {
    val test = Money.of(GBP, 2000d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(200000L, 2))
    assertEquals(test.getScale, 2)
  }

  def test_factory_of_Currency_double_big() {
    val test = Money.of(GBP, 200000000d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(20000000000L, 2))
    assertEquals(test.getScale, 2)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_factory_of_Currency_double_invalidScaleGBP() {
    Money.of(GBP, 2.345d)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_factory_of_Currency_double_invalidScaleJPY() {
    Money.of(JPY, 2.3d)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_double_nullCurrency() {
    Money.of(null.asInstanceOf[CurrencyUnit], BIGDEC_2_34)
  }

  def test_factory_of_Currency_double_GBP_RoundingMode_DOWN() {
    val test = Money.of(GBP, 2.34d, RoundingMode.DOWN)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 234)
    assertEquals(test.getAmount.scale(), 2)
  }

  def test_factory_of_Currency_double_JPY_RoundingMode_DOWN() {
    val test = Money.of(JPY, 2.34d, RoundingMode.DOWN)
    assertEquals(test.getCurrencyUnit, JPY)
    assertEquals(test.getAmountMinorInt, 2)
    assertEquals(test.getAmount.scale(), 0)
  }

  def test_factory_of_Currency_double_JPY_RoundingMode_UP() {
    val test = Money.of(JPY, 2.34d, RoundingMode.UP)
    assertEquals(test.getCurrencyUnit, JPY)
    assertEquals(test.getAmountMinorInt, 3)
    assertEquals(test.getAmount.scale(), 0)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_factory_of_Currency_double_RoundingMode_UNNECESSARY() {
    Money.of(JPY, 2.34d, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_double_RoundingMode_nullCurrency() {
    Money.of(null.asInstanceOf[CurrencyUnit], 2.34d, RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_of_Currency_double_RoundingMode_nullRoundingMode() {
    Money.of(GBP, 2.34d, null.asInstanceOf[RoundingMode])
  }

  def test_factory_ofMajor_Currency_long() {
    val test = Money.ofMajor(GBP, 234)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 23400)
    assertEquals(test.getAmount.scale(), 2)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_ofMajor_Currency_long_nullCurrency() {
    Money.ofMajor(null.asInstanceOf[CurrencyUnit], 234)
  }

  def test_factory_ofMinor_Currency_long() {
    val test = Money.ofMinor(GBP, 234)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 234)
    assertEquals(test.getAmount.scale(), 2)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_ofMinor_Currency_long_nullCurrency() {
    Money.ofMinor(null.asInstanceOf[CurrencyUnit], 234)
  }

  def test_factory_zero_Currency() {
    val test = Money.zero(GBP)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 0)
    assertEquals(test.getAmount.scale(), 2)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_zero_Currency_nullCurrency() {
    Money.zero(null.asInstanceOf[CurrencyUnit])
  }

  def test_factory_from_BigMoneyProvider() {
    val test = Money.of(BigMoney.parse("GBP 104.23"))
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 10423)
    assertEquals(test.getAmount.scale(), 2)
  }

  def test_factory_from_BigMoneyProvider_fixScale() {
    val test = Money.of(BigMoney.parse("GBP 104.2"))
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 10420)
    assertEquals(test.getAmount.scale(), 2)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_factory_from_BigMoneyProvider_invalidCurrencyScale() {
    Money.of(BigMoney.parse("GBP 104.235"))
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_from_BigMoneyProvider_nullBigMoneyProvider() {
    Money.of(null.asInstanceOf[BigMoneyProvider])
  }

  def test_factory_from_BigMoneyProvider_RoundingMode() {
    val test = Money.of(BigMoney.parse("GBP 104.235"), RoundingMode.HALF_EVEN)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 10424)
    assertEquals(test.getAmount.scale(), 2)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_from_BigMoneyProvider_RoundingMode_nullBigMoneyProvider() {
    Money.of(null.asInstanceOf[BigMoneyProvider], RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_from_BigMoneyProvider_RoundingMode_nullRoundingMode() {
    Money.of(BigMoney.parse("GBP 104.235"), null.asInstanceOf[RoundingMode])
  }

  def test_factory_total_varargs_1() {
    val test = Money.total(GBP_1_23)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 123)
  }

  def test_factory_total_array_1() {
    val array = Array(GBP_1_23)
    val test = Money.total(array)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 123)
  }

  def test_factory_total_varargs_3() {
    val test = Money.total(GBP_1_23, GBP_2_33, GBP_2_36)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_array_3() {
    val array = Array(GBP_1_23, GBP_2_33, GBP_2_36)
    val test = Money.total(array)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_factory_total_varargs_empty() {
    Money.total()
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_factory_total_array_empty() {
    val array = Array.ofDim[Money](0)
    Money.total(array)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_varargs_currenciesDiffer() {
    try {
      Money.total(GBP_2_33, JPY_423)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_array_currenciesDiffer() {
    try {
      val array = Array(GBP_2_33, JPY_423)
      Money.total(array)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_varargs_nullFirst() {
    Money.total(null.asInstanceOf[Money], GBP_2_33, GBP_2_36)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_array_nullFirst() {
    val array = Array(null, GBP_2_33, GBP_2_36)
    Money.total(array)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_varargs_nullNotFirst() {
    Money.total(GBP_2_33, null, GBP_2_36)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_array_nullNotFirst() {
    val array = Array(GBP_2_33, null, GBP_2_36)
    Money.total(array)
  }

  def test_factory_total_Iterable() {
    val iterable = Arrays.asList(GBP_1_23, GBP_2_33, GBP_2_36)
    val test = Money.total(iterable)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_factory_total_Iterable_empty() {
    val iterable = Collections.emptyList()
    Money.total(iterable)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_Iterable_currenciesDiffer() {
    try {
      val iterable = Arrays.asList(GBP_2_33, JPY_423)
      Money.total(iterable)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_Iterable_nullFirst() {
    val iterable = Arrays.asList(null, GBP_2_33, GBP_2_36)
    Money.total(iterable)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_Iterable_nullNotFirst() {
    val iterable = Arrays.asList(GBP_2_33, null, GBP_2_36)
    Money.total(iterable)
  }

  def test_factory_total_CurrencyUnitVarargs_1() {
    val test = Money.total(GBP, GBP_1_23)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 123)
  }

  def test_factory_total_CurrencyUnitArray_1() {
    val array = Array(GBP_1_23)
    val test = Money.total(GBP, array)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 123)
  }

  def test_factory_total_CurrencyUnitVarargs_3() {
    val test = Money.total(GBP, GBP_1_23, GBP_2_33, GBP_2_36)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_CurrencyUnitArray_3() {
    val array = Array(GBP_1_23, GBP_2_33, GBP_2_36)
    val test = Money.total(GBP, array)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_CurrencyUnitVarargs_empty() {
    val test = Money.total(GBP)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 0)
  }

  def test_factory_total_CurrencyUnitArray_empty() {
    val array = Array.ofDim[Money](0)
    val test = Money.total(GBP, array)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 0)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_CurrencyUnitVarargs_currenciesDiffer() {
    try {
      Money.total(GBP, JPY_423)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_CurrencyUnitArray_currenciesDiffer() {
    try {
      val array = Array(JPY_423)
      Money.total(GBP, array)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_CurrencyUnitVarargs_currenciesDifferInArray() {
    try {
      Money.total(GBP, GBP_2_33, JPY_423)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_CurrencyUnitArray_currenciesDifferInArray() {
    try {
      val array = Array(GBP_2_33, JPY_423)
      Money.total(GBP, array)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_CurrencyUnitVarargs_nullFirst() {
    Money.total(GBP, null, GBP_2_33, GBP_2_36)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_CurrencyUnitArray_nullFirst() {
    val array = Array(null, GBP_2_33, GBP_2_36)
    Money.total(GBP, array)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_CurrencyUnitVarargs_nullNotFirst() {
    Money.total(GBP, GBP_2_33, null, GBP_2_36)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_CurrencyUnitArray_nullNotFirst() {
    val array = Array(GBP_2_33, null, GBP_2_36)
    Money.total(GBP, array)
  }

  def test_factory_total_CurrencyUnitIterable() {
    val iterable = Arrays.asList(GBP_1_23, GBP_2_33, GBP_2_36)
    val test = Money.total(GBP, iterable)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_CurrencyUnitIterable_empty() {
    val iterable = Collections.emptyList()
    val test = Money.total(GBP, iterable)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 0)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_CurrencyUnitIterable_currenciesDiffer() {
    try {
      val iterable = Arrays.asList(JPY_423:_*)
      Money.total(GBP, iterable)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_factory_total_CurrencyUnitIterable_currenciesDifferInIterable() {
    try {
      val iterable = Arrays.asList(GBP_2_33, JPY_423)
      Money.total(GBP, iterable)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_CurrencyUnitIterable_nullFirst() {
    val iterable = Arrays.asList(null, GBP_2_33, GBP_2_36)
    Money.total(GBP, iterable)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_total_CurrencyUnitIterable_nullNotFirst() {
    val iterable = Arrays.asList(GBP_2_33, null, GBP_2_36)
    Money.total(GBP, iterable)
  }

  @DataProvider(name = "parse")
  def data_parse(): Array[Array[Any]] = {
    Array(Array("GBP 2.43", GBP, 243), Array("GBP +12.57", GBP, 1257), Array("GBP -5.87", GBP, -587), Array("GBP 0.99", GBP, 99), Array("GBP .99", GBP, 99), Array("GBP +.99", GBP, 99), Array("GBP +0.99", GBP, 99), Array("GBP -.99", GBP, -99), Array("GBP -0.99", GBP, -99), Array("GBP 0", GBP, 0), Array("GBP 2", GBP, 200), Array("GBP 123.", GBP, 12300), Array("GBP3", GBP, 300), Array("GBP3.10", GBP, 310), Array("GBP  3.10", GBP, 310), Array("GBP   3.10", GBP, 310), Array("GBP                           3.10", GBP, 310))
  }

  @Test(dataProvider = "parse")
  def test_factory_parse(str: String, currency: CurrencyUnit, amount: Int) {
    val test = Money.parse(str)
    assertEquals(test.getCurrencyUnit, currency)
    assertEquals(test.getAmountMinorInt, amount)
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_factory_parse_String_tooShort() {
    Money.parse("GBP ")
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_factory_parse_String_badCurrency() {
    Money.parse("GBX 2.34")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_factory_parse_String_nullString() {
    Money.parse(null.asInstanceOf[String])
  }

  def test_nonNull_MoneyCurrencyUnit_nonNull() {
    val test = Money.nonNull(GBP_1_23, GBP)
    assertSame(test, GBP_1_23)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_nonNull_MoneyCurrencyUnit_nonNullCurrencyMismatch() {
    try {
      Money.nonNull(GBP_1_23, JPY)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_nonNull_MoneyCurrencyUnit_nonNull_nullCurrency() {
    Money.nonNull(GBP_1_23, null)
  }

  def test_nonNull_MoneyCurrencyUnit_null() {
    val test = Money.nonNull(null, GBP)
    assertEquals(test, GBP_0_00)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_nonNull_MoneyCurrencyUnit_null_nullCurrency() {
    Money.nonNull(null, null)
  }

  def test_constructor_null1() {
    val con = classOf[Money].getDeclaredConstructor(classOf[BigMoney])
    assertEquals(Modifier.isPublic(con.getModifiers), false)
    assertEquals(Modifier.isProtected(con.getModifiers), false)
    try {
      con.setAccessible(true)
      con.newInstance(Array(null))
      fail()
    } catch {
      case ex: InvocationTargetException => assertEquals(ex.getCause.getClass, classOf[AssertionError])
    }
  }

  def test_constructor_scale() {
    val con = classOf[Money].getDeclaredConstructor(classOf[BigMoney])
    try {
      con.setAccessible(true)
      con.newInstance(Array(BigMoney.of(GBP, BIGDEC_2_3)))
      fail()
    } catch {
      case ex: InvocationTargetException => assertEquals(ex.getCause.getClass, classOf[AssertionError])
    }
  }

  def test_serialization() {
    val a = GBP_2_34
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(a)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    val input = ois.readObject().asInstanceOf[Money]
    assertEquals(input, a)
  }

  @Test(expectedExceptions = classOf[InvalidObjectException])
  def test_serialization_invalidNumericCode() {
    val cu = new CurrencyUnit("GBP", 234.toShort, 2.toShort)
    val m = Money.of(cu, 123.43d)
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(m)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    ois.readObject()
  }

  @Test(expectedExceptions = classOf[InvalidObjectException])
  def test_serialization_invalidDecimalPlaces() {
    val cu = new CurrencyUnit("GBP", 826.toShort, 3.toShort)
    val m = Money.of(cu, 123.43d)
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(m)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    ois.readObject()
  }

  def test_getCurrencyUnit_GBP() {
    assertEquals(GBP_2_34.getCurrencyUnit, GBP)
  }

  def test_getCurrencyUnit_EUR() {
    assertEquals(Money.parse("EUR -5.78").getCurrencyUnit, EUR)
  }

  def test_withCurrencyUnit_Currency() {
    val test = GBP_2_34.withCurrencyUnit(USD)
    assertEquals(test.toString, "USD 2.34")
  }

  def test_withCurrencyUnit_Currency_same() {
    val test = GBP_2_34.withCurrencyUnit(GBP)
    assertSame(test, GBP_2_34)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_withCurrencyUnit_Currency_scaleProblem() {
    GBP_2_34.withCurrencyUnit(JPY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_withCurrencyUnit_Currency_nullCurrency() {
    GBP_2_34.withCurrencyUnit(null.asInstanceOf[CurrencyUnit])
  }

  def test_withCurrencyUnit_CurrencyRoundingMode_DOWN() {
    val test = GBP_2_34.withCurrencyUnit(JPY, RoundingMode.DOWN)
    assertEquals(test.toString, "JPY 2")
  }

  def test_withCurrencyUnit_CurrencyRoundingMode_UP() {
    val test = GBP_2_34.withCurrencyUnit(JPY, RoundingMode.UP)
    assertEquals(test.toString, "JPY 3")
  }

  def test_withCurrencyUnit_CurrencyRoundingMode_same() {
    val test = GBP_2_34.withCurrencyUnit(GBP, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_withCurrencyUnit_CurrencyRoundingMode_UNECESSARY() {
    GBP_2_34.withCurrencyUnit(JPY, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_withCurrencyUnit_CurrencyRoundingMode_nullCurrency() {
    GBP_2_34.withCurrencyUnit(null.asInstanceOf[CurrencyUnit], RoundingMode.UNNECESSARY)
  }

  def test_getScale_GBP() {
    assertEquals(GBP_2_34.getScale, 2)
  }

  def test_getScale_JPY() {
    assertEquals(JPY_423.getScale, 0)
  }

  def test_getAmount_positive() {
    assertEquals(GBP_2_34.getAmount, BIGDEC_2_34)
  }

  def test_getAmount_negative() {
    assertEquals(GBP_M5_78.getAmount, BIGDEC_M5_78)
  }

  def test_getAmountMajor_positive() {
    assertEquals(GBP_2_34.getAmountMajor, BigDecimal.valueOf(2))
  }

  def test_getAmountMajor_negative() {
    assertEquals(GBP_M5_78.getAmountMajor, BigDecimal.valueOf(-5))
  }

  def test_getAmountMajorLong_positive() {
    assertEquals(GBP_2_34.getAmountMajorLong, 2L)
  }

  def test_getAmountMajorLong_negative() {
    assertEquals(GBP_M5_78.getAmountMajorLong, -5L)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_getAmountMajorLong_tooBigPositive() {
    GBP_LONG_MAX_MAJOR_PLUS1.getAmountMajorLong
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_getAmountMajorLong_tooBigNegative() {
    GBP_LONG_MIN_MAJOR_MINUS1.getAmountMajorLong
  }

  def test_getAmountMajorInt_positive() {
    assertEquals(GBP_2_34.getAmountMajorInt, 2)
  }

  def test_getAmountMajorInt_negative() {
    assertEquals(GBP_M5_78.getAmountMajorInt, -5)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_getAmountMajorInt_tooBigPositive() {
    GBP_INT_MAX_MAJOR_PLUS1.getAmountMajorInt
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_getAmountMajorInt_tooBigNegative() {
    GBP_INT_MIN_MAJOR_MINUS1.getAmountMajorInt
  }

  def test_getAmountMinor_positive() {
    assertEquals(GBP_2_34.getAmountMinor, BigDecimal.valueOf(234))
  }

  def test_getAmountMinor_negative() {
    assertEquals(GBP_M5_78.getAmountMinor, BigDecimal.valueOf(-578))
  }

  def test_getAmountMinorLong_positive() {
    assertEquals(GBP_2_34.getAmountMinorLong, 234L)
  }

  def test_getAmountMinorLong_negative() {
    assertEquals(GBP_M5_78.getAmountMinorLong, -578L)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_getAmountMinorLong_tooBigPositive() {
    GBP_LONG_MAX_PLUS1.getAmountMinorLong
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_getAmountMinorLong_tooBigNegative() {
    GBP_LONG_MIN_MINUS1.getAmountMinorLong
  }

  def test_getAmountMinorInt_positive() {
    assertEquals(GBP_2_34.getAmountMinorInt, 234)
  }

  def test_getAmountMinorInt_negative() {
    assertEquals(GBP_M5_78.getAmountMinorInt, -578)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_getAmountMinorInt_tooBigPositive() {
    GBP_INT_MAX_PLUS1.getAmountMinorInt
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_getAmountMinorInt_tooBigNegative() {
    GBP_INT_MIN_MINUS1.getAmountMinorInt
  }

  def test_getMinorPart_positive() {
    assertEquals(GBP_2_34.getMinorPart, 34)
  }

  def test_getMinorPart_negative() {
    assertEquals(GBP_M5_78.getMinorPart, -78)
  }

  def test_isZero() {
    assertEquals(GBP_0_00.isZero, true)
    assertEquals(GBP_2_34.isZero, false)
    assertEquals(GBP_M5_78.isZero, false)
  }

  def test_isPositive() {
    assertEquals(GBP_0_00.isPositive, false)
    assertEquals(GBP_2_34.isPositive, true)
    assertEquals(GBP_M5_78.isPositive, false)
  }

  def test_isPositiveOrZero() {
    assertEquals(GBP_0_00.isPositiveOrZero, true)
    assertEquals(GBP_2_34.isPositiveOrZero, true)
    assertEquals(GBP_M5_78.isPositiveOrZero, false)
  }

  def test_isNegative() {
    assertEquals(GBP_0_00.isNegative, false)
    assertEquals(GBP_2_34.isNegative, false)
    assertEquals(GBP_M5_78.isNegative, true)
  }

  def test_isNegativeOrZero() {
    assertEquals(GBP_0_00.isNegativeOrZero, true)
    assertEquals(GBP_2_34.isNegativeOrZero, false)
    assertEquals(GBP_M5_78.isNegativeOrZero, true)
  }

  def test_withAmount_BigDecimal() {
    val test = GBP_2_34.withAmount(BIGDEC_M5_78)
    assertEquals(test.toString, "GBP -5.78")
  }

  def test_withAmount_BigDecimal_same() {
    val test = GBP_2_34.withAmount(BIGDEC_2_34)
    assertSame(test, GBP_2_34)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_withAmount_BigDecimal_invalidScale() {
    GBP_2_34.withAmount(new BigDecimal("2.345"))
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_withAmount_BigDecimal_nullBigDecimal() {
    GBP_2_34.withAmount(null.asInstanceOf[BigDecimal])
  }

  def test_withAmount_BigDecimalRoundingMode() {
    val test = GBP_2_34.withAmount(BIGDEC_M5_78, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP -5.78")
  }

  def test_withAmount_BigDecimalRoundingMode_same() {
    val test = GBP_2_34.withAmount(BIGDEC_2_34, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_withAmount_BigDecimalRoundingMode_roundDown() {
    val test = GBP_2_34.withAmount(new BigDecimal("2.355"), RoundingMode.DOWN)
    assertEquals(test, GBP_2_35)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_withAmount_BigDecimalRoundingMode_roundUnecessary() {
    GBP_2_34.withAmount(new BigDecimal("2.345"), RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_withAmount_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_2_34.withAmount(null.asInstanceOf[BigDecimal], RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_withAmount_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_2_34.withAmount(BIGDEC_2_34, null.asInstanceOf[RoundingMode])
  }

  def test_withAmount_double() {
    val test = GBP_2_34.withAmount(-5.78d)
    assertEquals(test.toString, "GBP -5.78")
  }

  def test_withAmount_double_same() {
    val test = GBP_2_34.withAmount(2.34d)
    assertSame(test, GBP_2_34)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_withAmount_double_invalidScale() {
    GBP_2_34.withAmount(2.345d)
  }

  def test_withAmount_doubleRoundingMode() {
    val test = GBP_2_34.withAmount(-5.78d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP -5.78")
  }

  def test_withAmount_doubleRoundingMode_same() {
    val test = GBP_2_34.withAmount(2.34d, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_withAmount_doubleRoundingMode_roundDown() {
    val test = GBP_2_34.withAmount(2.355d, RoundingMode.DOWN)
    assertEquals(test, GBP_2_35)
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_withAmount_doubleRoundingMode_roundUnecessary() {
    GBP_2_34.withAmount(2.345d, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_withAmount_doubleRoundingMode_nullRoundingMode() {
    GBP_2_34.withAmount(BIGDEC_2_34, null.asInstanceOf[RoundingMode])
  }

  def test_plus_Iterable() {
    val iterable = Arrays.asList(GBP_2_33, GBP_1_23)
    val test = GBP_2_34.plus(iterable)
    assertEquals(test.toString, "GBP 5.90")
  }

  def test_plus_Iterable_zero() {
    val iterable = Arrays.asList(GBP_0_00:_*)
    val test = GBP_2_34.plus(iterable)
    assertSame(test, GBP_2_34)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_plus_Iterable_currencyMismatch() {
    try {
      val iterable = Arrays.asList(GBP_2_33, JPY_423)
      GBP_M5_78.plus(iterable)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_plus_Iterable_nullEntry() {
    val iterable = Arrays.asList(GBP_2_33, null)
    GBP_M5_78.plus(iterable)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_plus_Iterable_nullIterable() {
    GBP_M5_78.plus(null.asInstanceOf[java.lang.Iterable[Money]])
  }

  def test_plus_Money_zero() {
    val test = GBP_2_34.plus(GBP_0_00)
    assertSame(test, GBP_2_34)
  }

  def test_plus_Money_positive() {
    val test = GBP_2_34.plus(GBP_1_23)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plus_Money_negative() {
    val test = GBP_2_34.plus(GBP_M1_23)
    assertEquals(test.toString, "GBP 1.11")
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_plus_Money_currencyMismatch() {
    try {
      GBP_M5_78.plus(USD_1_23)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, USD)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_plus_Money_nullMoney() {
    GBP_M5_78.plus(null.asInstanceOf[Money])
  }

  def test_plus_BigDecimal_zero() {
    val test = GBP_2_34.plus(BigDecimal.ZERO)
    assertSame(test, GBP_2_34)
  }

  def test_plus_BigDecimal_positive() {
    val test = GBP_2_34.plus(new BigDecimal("1.23"))
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plus_BigDecimal_negative() {
    val test = GBP_2_34.plus(new BigDecimal("-1.23"))
    assertEquals(test.toString, "GBP 1.11")
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_plus_BigDecimal_invalidScale() {
    GBP_2_34.plus(new BigDecimal("1.235"))
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_plus_BigDecimal_nullBigDecimal() {
    GBP_M5_78.plus(null.asInstanceOf[BigDecimal])
  }

  def test_plus_BigDecimalRoundingMode_zero() {
    val test = GBP_2_34.plus(BigDecimal.ZERO, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_plus_BigDecimalRoundingMode_positive() {
    val test = GBP_2_34.plus(new BigDecimal("1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plus_BigDecimalRoundingMode_negative() {
    val test = GBP_2_34.plus(new BigDecimal("-1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_plus_BigDecimalRoundingMode_roundDown() {
    val test = GBP_2_34.plus(new BigDecimal("1.235"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 3.57")
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_plus_BigDecimalRoundingMode_roundUnecessary() {
    GBP_2_34.plus(new BigDecimal("1.235"), RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_plus_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_M5_78.plus(null.asInstanceOf[BigDecimal], RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_plus_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_M5_78.plus(BIGDEC_2_34, null.asInstanceOf[RoundingMode])
  }

  def test_plus_double_zero() {
    val test = GBP_2_34.plus(0d)
    assertSame(test, GBP_2_34)
  }

  def test_plus_double_positive() {
    val test = GBP_2_34.plus(1.23d)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plus_double_negative() {
    val test = GBP_2_34.plus(-1.23d)
    assertEquals(test.toString, "GBP 1.11")
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_plus_double_invalidScale() {
    GBP_2_34.plus(1.235d)
  }

  def test_plus_doubleRoundingMode_zero() {
    val test = GBP_2_34.plus(0d, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_plus_doubleRoundingMode_positive() {
    val test = GBP_2_34.plus(1.23d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plus_doubleRoundingMode_negative() {
    val test = GBP_2_34.plus(-1.23d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_plus_doubleRoundingMode_roundDown() {
    val test = GBP_2_34.plus(1.235d, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 3.57")
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_plus_doubleRoundingMode_roundUnecessary() {
    GBP_2_34.plus(1.235d, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_plus_doubleRoundingMode_nullRoundingMode() {
    GBP_M5_78.plus(2.34d, null.asInstanceOf[RoundingMode])
  }

  def test_plusMajor_zero() {
    val test = GBP_2_34.plusMajor(0)
    assertSame(test, GBP_2_34)
  }

  def test_plusMajor_positive() {
    val test = GBP_2_34.plusMajor(123)
    assertEquals(test.toString, "GBP 125.34")
  }

  def test_plusMajor_negative() {
    val test = GBP_2_34.plusMajor(-123)
    assertEquals(test.toString, "GBP -120.66")
  }

  def test_plusMinor_zero() {
    val test = GBP_2_34.plusMinor(0)
    assertSame(test, GBP_2_34)
  }

  def test_plusMinor_positive() {
    val test = GBP_2_34.plusMinor(123)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plusMinor_negative() {
    val test = GBP_2_34.plusMinor(-123)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minus_Iterable() {
    val iterable = Arrays.asList(GBP_2_33, GBP_1_23)
    val test = GBP_2_34.minus(iterable)
    assertEquals(test.toString, "GBP -1.22")
  }

  def test_minus_Iterable_zero() {
    val iterable = Arrays.asList(GBP_0_00:_*)
    val test = GBP_2_34.minus(iterable)
    assertSame(test, GBP_2_34)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_minus_Iterable_currencyMismatch() {
    try {
      val iterable = Arrays.asList(GBP_2_33, JPY_423)
      GBP_M5_78.minus(iterable)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, JPY)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_minus_Iterable_nullEntry() {
    val iterable = Arrays.asList(GBP_2_33, null)
    GBP_M5_78.minus(iterable)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_minus_Iterable_nullIterable() {
    GBP_M5_78.minus(null.asInstanceOf[java.lang.Iterable[Money]])
  }

  def test_minus_Money_zero() {
    val test = GBP_2_34.minus(GBP_0_00)
    assertSame(test, GBP_2_34)
  }

  def test_minus_Money_positive() {
    val test = GBP_2_34.minus(GBP_1_23)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minus_Money_negative() {
    val test = GBP_2_34.minus(GBP_M1_23)
    assertEquals(test.toString, "GBP 3.57")
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_minus_Money_currencyMismatch() {
    try {
      GBP_M5_78.minus(USD_1_23)
    } catch {
      case ex: CurrencyMismatchException => {
        assertEquals(ex.getFirstCurrency, GBP)
        assertEquals(ex.getSecondCurrency, USD)
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_minus_Money_nullMoney() {
    GBP_M5_78.minus(null.asInstanceOf[Money])
  }

  def test_minus_BigDecimal_zero() {
    val test = GBP_2_34.minus(BigDecimal.ZERO)
    assertSame(test, GBP_2_34)
  }

  def test_minus_BigDecimal_positive() {
    val test = GBP_2_34.minus(new BigDecimal("1.23"))
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minus_BigDecimal_negative() {
    val test = GBP_2_34.minus(new BigDecimal("-1.23"))
    assertEquals(test.toString, "GBP 3.57")
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_minus_BigDecimal_invalidScale() {
    GBP_2_34.minus(new BigDecimal("1.235"))
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_minus_BigDecimal_nullBigDecimal() {
    GBP_M5_78.minus(null.asInstanceOf[BigDecimal])
  }

  def test_minus_BigDecimalRoundingMode_zero() {
    val test = GBP_2_34.minus(BigDecimal.ZERO, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_minus_BigDecimalRoundingMode_positive() {
    val test = GBP_2_34.minus(new BigDecimal("1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minus_BigDecimalRoundingMode_negative() {
    val test = GBP_2_34.minus(new BigDecimal("-1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_minus_BigDecimalRoundingMode_roundDown() {
    val test = GBP_2_34.minus(new BigDecimal("1.235"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 1.10")
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_minus_BigDecimalRoundingMode_roundUnecessary() {
    GBP_2_34.minus(new BigDecimal("1.235"), RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_minus_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_M5_78.minus(null.asInstanceOf[BigDecimal], RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_minus_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_M5_78.minus(BIGDEC_2_34, null.asInstanceOf[RoundingMode])
  }

  def test_minus_double_zero() {
    val test = GBP_2_34.minus(0d)
    assertSame(test, GBP_2_34)
  }

  def test_minus_double_positive() {
    val test = GBP_2_34.minus(1.23d)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minus_double_negative() {
    val test = GBP_2_34.minus(-1.23d)
    assertEquals(test.toString, "GBP 3.57")
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_minus_double_invalidScale() {
    GBP_2_34.minus(1.235d)
  }

  def test_minus_doubleRoundingMode_zero() {
    val test = GBP_2_34.minus(0d, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_minus_doubleRoundingMode_positive() {
    val test = GBP_2_34.minus(1.23d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minus_doubleRoundingMode_negative() {
    val test = GBP_2_34.minus(-1.23d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_minus_doubleRoundingMode_roundDown() {
    val test = GBP_2_34.minus(1.235d, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 1.10")
  }

  @Test(expectedExceptions = classOf[ArithmeticException])
  def test_minus_doubleRoundingMode_roundUnecessary() {
    GBP_2_34.minus(1.235d, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_minus_doubleRoundingMode_nullRoundingMode() {
    GBP_M5_78.minus(2.34d, null.asInstanceOf[RoundingMode])
  }

  def test_minusMajor_zero() {
    val test = GBP_2_34.minusMajor(0)
    assertSame(test, GBP_2_34)
  }

  def test_minusMajor_positive() {
    val test = GBP_2_34.minusMajor(123)
    assertEquals(test.toString, "GBP -120.66")
  }

  def test_minusMajor_negative() {
    val test = GBP_2_34.minusMajor(-123)
    assertEquals(test.toString, "GBP 125.34")
  }

  def test_minusMinor_zero() {
    val test = GBP_2_34.minusMinor(0)
    assertSame(test, GBP_2_34)
  }

  def test_minusMinor_positive() {
    val test = GBP_2_34.minusMinor(123)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minusMinor_negative() {
    val test = GBP_2_34.minusMinor(-123)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_multipliedBy_BigDecimalRoundingMode_one() {
    val test = GBP_2_34.multipliedBy(BigDecimal.ONE, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_multipliedBy_BigDecimalRoundingMode_positive() {
    val test = GBP_2_33.multipliedBy(new BigDecimal("2.5"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 5.82")
  }

  def test_multipliedBy_BigDecimalRoundingMode_positive_halfUp() {
    val test = GBP_2_33.multipliedBy(new BigDecimal("2.5"), RoundingMode.HALF_UP)
    assertEquals(test.toString, "GBP 5.83")
  }

  def test_multipliedBy_BigDecimalRoundingMode_negative() {
    val test = GBP_2_33.multipliedBy(new BigDecimal("-2.5"), RoundingMode.FLOOR)
    assertEquals(test.toString, "GBP -5.83")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_multipliedBy_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_5_78.multipliedBy(null.asInstanceOf[BigDecimal], RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_multipliedBy_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_5_78.multipliedBy(new BigDecimal("2.5"), null.asInstanceOf[RoundingMode])
  }

  def test_multipliedBy_doubleRoundingMode_one() {
    val test = GBP_2_34.multipliedBy(1d, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_multipliedBy_doubleRoundingMode_positive() {
    val test = GBP_2_33.multipliedBy(2.5d, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 5.82")
  }

  def test_multipliedBy_doubleRoundingMode_positive_halfUp() {
    val test = GBP_2_33.multipliedBy(2.5d, RoundingMode.HALF_UP)
    assertEquals(test.toString, "GBP 5.83")
  }

  def test_multipliedBy_doubleRoundingMode_negative() {
    val test = GBP_2_33.multipliedBy(-2.5d, RoundingMode.FLOOR)
    assertEquals(test.toString, "GBP -5.83")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_multipliedBy_doubleRoundingMode_nullRoundingMode() {
    GBP_5_78.multipliedBy(2.5d, null.asInstanceOf[RoundingMode])
  }

  def test_multipliedBy_long_one() {
    val test = GBP_2_34.multipliedBy(1)
    assertSame(test, GBP_2_34)
  }

  def test_multipliedBy_long_positive() {
    val test = GBP_2_34.multipliedBy(3)
    assertEquals(test.toString, "GBP 7.02")
  }

  def test_multipliedBy_long_negative() {
    val test = GBP_2_34.multipliedBy(-3)
    assertEquals(test.toString, "GBP -7.02")
  }

  def test_dividedBy_BigDecimalRoundingMode_one() {
    val test = GBP_2_34.dividedBy(BigDecimal.ONE, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_dividedBy_BigDecimalRoundingMode_positive() {
    val test = GBP_2_34.dividedBy(new BigDecimal("2.5"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 0.93")
  }

  def test_dividedBy_BigDecimalRoundingMode_positive_halfUp() {
    val test = GBP_2_34.dividedBy(new BigDecimal("2.5"), RoundingMode.HALF_UP)
    assertEquals(test.toString, "GBP 0.94")
  }

  def test_dividedBy_BigDecimalRoundingMode_negative() {
    val test = GBP_2_34.dividedBy(new BigDecimal("-2.5"), RoundingMode.FLOOR)
    assertEquals(test.toString, "GBP -0.94")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_dividedBy_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_5_78.dividedBy(null.asInstanceOf[BigDecimal], RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_dividedBy_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_5_78.dividedBy(new BigDecimal("2.5"), null.asInstanceOf[RoundingMode])
  }

  def test_dividedBy_doubleRoundingMode_one() {
    val test = GBP_2_34.dividedBy(1d, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_dividedBy_doubleRoundingMode_positive() {
    val test = GBP_2_34.dividedBy(2.5d, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 0.93")
  }

  def test_dividedBy_doubleRoundingMode_positive_halfUp() {
    val test = GBP_2_34.dividedBy(2.5d, RoundingMode.HALF_UP)
    assertEquals(test.toString, "GBP 0.94")
  }

  def test_dividedBy_doubleRoundingMode_negative() {
    val test = GBP_2_34.dividedBy(-2.5d, RoundingMode.FLOOR)
    assertEquals(test.toString, "GBP -0.94")
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_dividedBy_doubleRoundingMode_nullRoundingMode() {
    GBP_5_78.dividedBy(2.5d, null.asInstanceOf[RoundingMode])
  }

  def test_dividedBy_long_one() {
    val test = GBP_2_34.dividedBy(1, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_dividedBy_long_positive() {
    val test = GBP_2_34.dividedBy(3, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 0.78")
  }

  def test_dividedBy_long_positive_roundDown() {
    val test = GBP_2_35.dividedBy(3, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 0.78")
  }

  def test_dividedBy_long_positive_roundUp() {
    val test = GBP_2_35.dividedBy(3, RoundingMode.UP)
    assertEquals(test.toString, "GBP 0.79")
  }

  def test_dividedBy_long_negative() {
    val test = GBP_2_34.dividedBy(-3, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP -0.78")
  }

  def test_negated_positive() {
    val test = GBP_2_34.negated()
    assertEquals(test.toString, "GBP -2.34")
  }

  def test_negated_negative() {
    val test = Money.parse("GBP -2.34").negated()
    assertEquals(test.toString, "GBP 2.34")
  }

  def test_abs_positive() {
    val test = GBP_2_34.abs()
    assertSame(test, GBP_2_34)
  }

  def test_abs_negative() {
    val test = Money.parse("GBP -2.34").abs()
    assertEquals(test.toString, "GBP 2.34")
  }

  def test_round_2down() {
    val test = GBP_2_34.rounded(2, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_round_2up() {
    val test = GBP_2_34.rounded(2, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_round_1down() {
    val test = GBP_2_34.rounded(1, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 2.30")
  }

  def test_round_1up() {
    val test = GBP_2_34.rounded(1, RoundingMode.UP)
    assertEquals(test.toString, "GBP 2.40")
  }

  def test_round_0down() {
    val test = GBP_2_34.rounded(0, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 2.00")
  }

  def test_round_0up() {
    val test = GBP_2_34.rounded(0, RoundingMode.UP)
    assertEquals(test.toString, "GBP 3.00")
  }

  def test_round_M1down() {
    val test = Money.parse("GBP 432.34").rounded(-1, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 430.00")
  }

  def test_round_M1up() {
    val test = Money.parse("GBP 432.34").rounded(-1, RoundingMode.UP)
    assertEquals(test.toString, "GBP 440.00")
  }

  def test_round_3() {
    val test = GBP_2_34.rounded(3, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_convertedTo_BigDecimalRoundingMode_positive() {
    val test = GBP_2_33.convertedTo(EUR, new BigDecimal("2.5"), RoundingMode.DOWN)
    assertEquals(test.toString, "EUR 5.82")
  }

  def test_convertedTo_BigDecimalRoundingMode_positive_halfUp() {
    val test = GBP_2_33.convertedTo(EUR, new BigDecimal("2.5"), RoundingMode.HALF_UP)
    assertEquals(test.toString, "EUR 5.83")
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_convertedTo_BigDecimalRoundingMode_negative() {
    GBP_2_33.convertedTo(EUR, new BigDecimal("-2.5"), RoundingMode.FLOOR)
  }

  @Test(expectedExceptions = classOf[IllegalArgumentException])
  def test_convertedTo_BigDecimalRoundingMode_sameCurrency() {
    GBP_2_33.convertedTo(GBP, new BigDecimal("2.5"), RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_convertedTo_BigDecimalRoundingMode_nullCurrency() {
    GBP_5_78.convertedTo(null.asInstanceOf[CurrencyUnit], new BigDecimal("2"), RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_convertedTo_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_5_78.convertedTo(EUR, null.asInstanceOf[BigDecimal], RoundingMode.DOWN)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_convertedTo_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_5_78.convertedTo(EUR, new BigDecimal("2.5"), null.asInstanceOf[RoundingMode])
  }

  def test_toBigMoney() {
    assertEquals(GBP_2_34.toBigMoney(), BigMoney.ofMinor(GBP, 234))
  }

  def test_isSameCurrency_Money_same() {
    assertEquals(GBP_2_34.isSameCurrency(GBP_2_35), true)
  }

  def test_isSameCurrency_Money_different() {
    assertEquals(GBP_2_34.isSameCurrency(USD_2_34), false)
  }

  def test_isSameCurrency_BigMoney_same() {
    assertEquals(GBP_2_34.isSameCurrency(BigMoney.parse("GBP 2")), true)
  }

  def test_isSameCurrency_BigMoney_different() {
    assertEquals(GBP_2_34.isSameCurrency(BigMoney.parse("USD 2")), false)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_isSameCurrency_Money_nullMoney() {
    GBP_2_34.isSameCurrency(null.asInstanceOf[Money])
  }

  def test_compareTo_Money() {
    val a = GBP_2_34
    val b = GBP_2_35
    val c = GBP_2_36
    assertEquals(a.compareTo(a), 0)
    assertEquals(b.compareTo(b), 0)
    assertEquals(c.compareTo(c), 0)
    assertEquals(a.compareTo(b), -1)
    assertEquals(b.compareTo(a), 1)
    assertEquals(a.compareTo(c), -1)
    assertEquals(c.compareTo(a), 1)
    assertEquals(b.compareTo(c), -1)
    assertEquals(c.compareTo(b), 1)
  }

  def test_compareTo_BigMoney() {
    val t = GBP_2_35
    val a = BigMoney.ofMinor(GBP, 234)
    val b = BigMoney.ofMinor(GBP, 235)
    val c = BigMoney.ofMinor(GBP, 236)
    assertEquals(t.compareTo(a), 1)
    assertEquals(t.compareTo(b), 0)
    assertEquals(t.compareTo(c), -1)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_compareTo_currenciesDiffer() {
    val a = GBP_2_34
    val b = USD_2_35
    a.compareTo(b)
  }

  @Test(expectedExceptions = classOf[ClassCastException])
  def test_compareTo_wrongType() {
    val a = GBP_2_34
    a.compareTo("NotRightType")
  }

  def test_isEqual() {
    val a = GBP_2_34
    val b = GBP_2_35
    val c = GBP_2_36
    assertEquals(a.isEqual(a), true)
    assertEquals(b.isEqual(b), true)
    assertEquals(c.isEqual(c), true)
    assertEquals(a.isEqual(b), false)
    assertEquals(b.isEqual(a), false)
    assertEquals(a.isEqual(c), false)
    assertEquals(c.isEqual(a), false)
    assertEquals(b.isEqual(c), false)
    assertEquals(c.isEqual(b), false)
  }

  def test_isEqual_Money() {
    val a = GBP_2_34
    val b = BigMoney.ofMinor(GBP, 234)
    assertEquals(a.isEqual(b), true)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_isEqual_currenciesDiffer() {
    val a = GBP_2_34
    val b = USD_2_35
    a.isEqual(b)
  }

  def test_isGreaterThan() {
    val a = GBP_2_34
    val b = GBP_2_35
    val c = GBP_2_36
    assertEquals(a.isGreaterThan(a), false)
    assertEquals(b.isGreaterThan(b), false)
    assertEquals(c.isGreaterThan(c), false)
    assertEquals(a.isGreaterThan(b), false)
    assertEquals(b.isGreaterThan(a), true)
    assertEquals(a.isGreaterThan(c), false)
    assertEquals(c.isGreaterThan(a), true)
    assertEquals(b.isGreaterThan(c), false)
    assertEquals(c.isGreaterThan(b), true)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_isGreaterThan_currenciesDiffer() {
    val a = GBP_2_34
    val b = USD_2_35
    a.isGreaterThan(b)
  }

  def test_isLessThan() {
    val a = GBP_2_34
    val b = GBP_2_35
    val c = GBP_2_36
    assertEquals(a.isLessThan(a), false)
    assertEquals(b.isLessThan(b), false)
    assertEquals(c.isLessThan(c), false)
    assertEquals(a.isLessThan(b), true)
    assertEquals(b.isLessThan(a), false)
    assertEquals(a.isLessThan(c), true)
    assertEquals(c.isLessThan(a), false)
    assertEquals(b.isLessThan(c), true)
    assertEquals(c.isLessThan(b), false)
  }

  @Test(expectedExceptions = classOf[CurrencyMismatchException])
  def test_isLessThan_currenciesDiffer() {
    val a = GBP_2_34
    val b = USD_2_35
    a.isLessThan(b)
  }

  def test_equals_hashCode_positive() {
    val a = GBP_2_34
    val b = GBP_2_34
    val c = GBP_2_35
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
    val a = GBP_2_34
    assertEquals(a == null, false)
    assertEquals(a == "String", false)
    assertEquals(a == new AnyRef(), false)
  }

  def test_toString_positive() {
    val test = Money.of(GBP, BIGDEC_2_34)
    assertEquals(test.toString, "GBP 2.34")
  }

  def test_toString_negative() {
    val test = Money.of(EUR, BIGDEC_M5_78)
    assertEquals(test.toString, "EUR -5.78")
  }
}
