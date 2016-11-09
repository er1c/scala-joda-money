package org.joda.money


import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InvalidObjectException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.Arrays
import java.util.Collections
import org.scalatest.testng.TestNGSuite
import org.testng.Assert._
import org.testng.annotations.{AfterMethod, BeforeMethod, DataProvider, Test}
import TestBigMoney._
import collection.JavaConverters._

object TestBigMoney {

  private val GBP = CurrencyUnit.of("GBP")

  private val EUR = CurrencyUnit.of("EUR")

  private val USD = CurrencyUnit.of("USD")

  private val JPY = CurrencyUnit.of("JPY")

  private val BIGDEC_2_34 = new BigDecimal("2.34")

  private val BIGDEC_2_345 = new BigDecimal("2.345")

  private val BIGDEC_M5_78 = new BigDecimal("-5.78")

  private val GBP_0_00 = BigMoney.parse("GBP 0.00")

  private val GBP_1_23 = BigMoney.parse("GBP 1.23")

  private val GBP_2_33 = BigMoney.parse("GBP 2.33")

  private val GBP_2_34 = BigMoney.parse("GBP 2.34")

  private val GBP_2_35 = BigMoney.parse("GBP 2.35")

  private val GBP_2_36 = BigMoney.parse("GBP 2.36")

  private val GBP_5_78 = BigMoney.parse("GBP 5.78")

  private val GBP_M1_23 = BigMoney.parse("GBP -1.23")

  private val GBP_M5_78 = BigMoney.parse("GBP -5.78")

  private val GBP_INT_MAX_PLUS1 = BigMoney.ofMinor(GBP, Int.MaxValue.toLong + 1)

  private val GBP_INT_MIN_MINUS1 = BigMoney.ofMinor(GBP, Int.MinValue.toLong - 1)

  private val GBP_INT_MAX_MAJOR_PLUS1 = BigMoney.ofMinor(GBP, (Int.MaxValue.toLong + 1) * 100)

  private val GBP_INT_MIN_MAJOR_MINUS1 = BigMoney.ofMinor(GBP, (Int.MinValue.toLong - 1) * 100)

  private val GBP_LONG_MAX_PLUS1 = BigMoney.of(GBP, BigDecimal.valueOf(Long.MaxValue).add(BigDecimal.ONE))

  private val GBP_LONG_MIN_MINUS1 = BigMoney.of(GBP, BigDecimal.valueOf(Long.MaxValue).subtract(BigDecimal.ONE))

  private val GBP_LONG_MAX_MAJOR_PLUS1 = BigMoney.of(GBP, BigDecimal.valueOf(Long.MaxValue).add(BigDecimal.ONE)
    .multiply(BigDecimal.valueOf(100)))

  private val GBP_LONG_MIN_MAJOR_MINUS1 = BigMoney.of(GBP, BigDecimal.valueOf(Long.MinValue).subtract(BigDecimal.ONE)
    .multiply(BigDecimal.valueOf(100)))

  private val JPY_423 = BigMoney.parse("JPY 423")

  private val USD_1_23 = BigMoney.parse("USD 1.23")

  private val USD_2_34 = BigMoney.parse("USD 2.34")

  private val USD_2_35 = BigMoney.parse("USD 2.35")

  private val BAD_PROVIDER = new BigMoneyProvider() {

    override def toBigMoney(): BigMoney = null
  }

  private def bd(str: String): BigDecimal = new BigDecimal(str)
}

/**
 * Test BigMoney.
 */
@Test
class TestBigMoney extends TestNGSuite {

  def test_factory_of_Currency_BigDecimal() {
    val test = BigMoney.of(GBP, BIGDEC_2_345)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BIGDEC_2_345)
    assertEquals(test.getScale, 3)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_of_Currency_BigDecimal_nullCurrency() {
    BigMoney.of(null.asInstanceOf[CurrencyUnit], BIGDEC_2_345)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_of_Currency_BigDecimal_nullBigDecimal() {
    BigMoney.of(GBP, null.asInstanceOf[BigDecimal])
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_factory_of_Currency_subClass1() {
    @SerialVersionUID(1L)
    class BadDecimal() extends BigDecimal(432) {

      override def unscaledValue(): BigInteger = return null

      override def scale(): Int = return 1
    }
    val sub = new BadDecimal()
    BigMoney.of(GBP, sub)
  }

  def test_factory_of_Currency_subClass2() {
    @SerialVersionUID(1L)
    class BadInteger extends BigInteger("123")
    @SerialVersionUID(1L)
    class BadDecimal() extends BigDecimal(432) {

      override def unscaledValue(): BigInteger = return new BadInteger()

      override def scale(): Int = return 1
    }
    val sub = new BadDecimal()
    val test = BigMoney.of(GBP, sub)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, bd("12.3"))
    assertEquals(test.getScale, 1)
    assertEquals(test.getAmount.getClass == classOf[BigDecimal], true)
  }

  def test_factory_of_Currency_double() {
    val test = BigMoney.of(GBP, 2.345d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BIGDEC_2_345)
    assertEquals(test.getScale, 3)
  }

  def test_factory_of_Currency_double_trailingZero1() {
    val test = BigMoney.of(GBP, 1.230d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(123L, 2))
    assertEquals(test.getScale, 2)
  }

  def test_factory_of_Currency_double_trailingZero2() {
    val test = BigMoney.of(GBP, 1.20d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(12L, 1))
    assertEquals(test.getScale, 1)
  }

  def test_factory_of_Currency_double_medium() {
    val test = BigMoney.of(GBP, 2000d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(2000L, 0))
    assertEquals(test.getScale, 0)
  }

  def test_factory_of_Currency_double_big() {
    val test = BigMoney.of(GBP, 200000000d)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(200000000L, 0))
    assertEquals(test.getScale, 0)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_of_Currency_double_nullCurrency() {
    BigMoney.of(null.asInstanceOf[CurrencyUnit], 2.345d)
  }

  def test_factory_ofScale_Currency_BigDecimal_int() {
    val test = BigMoney.ofScale(GBP, BIGDEC_2_34, 4)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(23400, 4))
  }

  def test_factory_ofScale_Currency_BigDecimal_negativeScale() {
    val test = BigMoney.ofScale(GBP, BigDecimal.valueOf(23400), -2)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(23400L, 0))
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_factory_ofScale_Currency_BigDecimal_invalidScale() {
    BigMoney.ofScale(GBP, BIGDEC_2_345, 2)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_ofScale_Currency_BigDecimal_nullCurrency() {
    BigMoney.ofScale(null.asInstanceOf[CurrencyUnit], BIGDEC_2_34, 2)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_ofScale_Currency_BigDecimal_nullBigDecimal() {
    BigMoney.ofScale(GBP, null.asInstanceOf[BigDecimal], 2)
  }

  def test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_DOWN() {
    val test = BigMoney.ofScale(GBP, BIGDEC_2_34, 1, RoundingMode.DOWN)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(23, 1))
  }

  def test_factory_ofScale_Currency_BigDecimal_int_JPY_RoundingMode_UP() {
    val test = BigMoney.ofScale(JPY, BIGDEC_2_34, 0, RoundingMode.UP)
    assertEquals(test.getCurrencyUnit, JPY)
    assertEquals(test.getAmount, BigDecimal.valueOf(3, 0))
  }

  def test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_negativeScale() {
    val test = BigMoney.ofScale(GBP, BigDecimal.valueOf(23400), -2, RoundingMode.DOWN)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(23400L, 0))
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_UNNECESSARY() {
    BigMoney.ofScale(JPY, BIGDEC_2_34, 1, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_nullCurrency() {
    BigMoney.ofScale(null.asInstanceOf[CurrencyUnit], BIGDEC_2_34, 2, RoundingMode.DOWN)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_nullBigDecimal() {
    BigMoney.ofScale(GBP, null.asInstanceOf[BigDecimal], 2, RoundingMode.DOWN)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_nullRoundingMode() {
    BigMoney.ofScale(GBP, BIGDEC_2_34, 2, null.asInstanceOf[RoundingMode])
  }

  def test_factory_ofScale_Currency_long_int() {
    val test = BigMoney.ofScale(GBP, 234, 4)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(234, 4))
  }

  def test_factory_ofScale_Currency_long_int_negativeScale() {
    val test = BigMoney.ofScale(GBP, 234, -4)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(2340000, 0))
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_ofScale_Currency_long_int_nullCurrency() {
    BigMoney.ofScale(null.asInstanceOf[CurrencyUnit], 234, 2)
  }

  def test_factory_ofMajor_Currency_long() {
    val test = BigMoney.ofMajor(GBP, 234)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, bd("234"))
    assertEquals(test.getScale, 0)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_ofMajor_Currency_long_nullCurrency() {
    BigMoney.ofMajor(null.asInstanceOf[CurrencyUnit], 234)
  }

  def test_factory_ofMinor_Currency_long() {
    val test = BigMoney.ofMinor(GBP, 234)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, bd("2.34"))
    assertEquals(test.getScale, 2)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_ofMinor_Currency_long_nullCurrency() {
    BigMoney.ofMinor(null.asInstanceOf[CurrencyUnit], 234)
  }

  def test_factory_zero_Currency() {
    val test = BigMoney.zero(GBP)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.ZERO)
    assertEquals(test.getScale, 0)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_zero_Currency_nullCurrency() {
    BigMoney.zero(null.asInstanceOf[CurrencyUnit])
  }

  def test_factory_zero_Currency_int() {
    val test = BigMoney.zero(GBP, 3)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(0, 3))
  }

  def test_factory_zero_Currency_int_negativeScale() {
    val test = BigMoney.zero(GBP, -3)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(0, 0))
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_zero_Currency_int_nullCurrency() {
    BigMoney.zero(null.asInstanceOf[CurrencyUnit], 3)
  }

  def test_factory_from_BigMoneyProvider() {
    val test = BigMoney.of(BigMoney.parse("GBP 104.23"))
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 10423)
    assertEquals(test.getScale, 2)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_from_BigMoneyProvider_nullBigMoneyProvider() {
    BigMoney.of(null.asInstanceOf[BigMoneyProvider])
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_from_BigMoneyProvider_badProvider() {
    BigMoney.of(BAD_PROVIDER)
  }

  def test_factory_total_varargs_1BigMoney() {
    val test = BigMoney.total(GBP_1_23)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 123)
  }

  def test_factory_total_array_1BigMoney() {
    val array = Seq(GBP_1_23)
    val test = BigMoney.total(array.asJava)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 123)
  }

  def test_factory_total_varargs_3Mixed() {
    val test = BigMoney.total(GBP_1_23, GBP_2_33.toMoney(), GBP_2_36)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_array_3Mixed() {
    val array = Seq(GBP_1_23, GBP_2_33.toMoney(), GBP_2_36)
    val test = BigMoney.total(array.asJava)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_array_3Money() {
    val array = Seq(GBP_1_23.toMoney(), GBP_2_33.toMoney(), GBP_2_36.toMoney())
    val test = BigMoney.total(array.asJava)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_factory_total_varargs_empty() {
    BigMoney.total()
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_factory_total_array_empty() {
    val array = Array.ofDim[BigMoneyProvider](0)
    BigMoney.total(array.toSeq.asJava)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_varargs_currenciesDiffer() {
    BigMoney.total(GBP_2_33, JPY_423)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_array_currenciesDiffer() {
    val array = Seq(GBP_2_33, JPY_423)
    BigMoney.total(array.asJava)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_varargs_nullFirst() {
    BigMoney.total(null.asInstanceOf[BigMoney], GBP_2_33, GBP_2_36)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_array_nullFirst() {
    val array = Seq(null, GBP_2_33, GBP_2_36)
    BigMoney.total(array.asJava)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_varargs_nullNotFirst() {
    BigMoney.total(GBP_2_33, null, GBP_2_36)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_array_nullNotFirst() {
    val array = Seq(GBP_2_33, null, GBP_2_36)
    BigMoney.total(array.asJava)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_varargs_badProvider() {
    BigMoney.total(BAD_PROVIDER)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_array_badProvider() {
    val array = Seq(BAD_PROVIDER)
    BigMoney.total(array.asJava)
  }

  def test_factory_total_Iterable() {
    val iterable = Arrays.asList(GBP_1_23, GBP_2_33, BigMoney.of(GBP, 2.361d))
    val test = BigMoney.total(iterable)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(5921, 3))
  }

  def test_factory_total_Iterable_Mixed() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_1_23.toMoney(), GBP_2_33)
    val test = BigMoney.total(iterable)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(356, 2))
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_factory_total_Iterable_empty() {
    val iterable = Collections.emptyList()
    BigMoney.total(iterable)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_Iterable_currenciesDiffer() {
    val iterable = Arrays.asList(GBP_2_33, JPY_423)
    BigMoney.total(iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_Iterable_nullFirst() {
    val iterable = Arrays.asList(null, GBP_2_33, GBP_2_36)
    BigMoney.total(iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_Iterable_nullNotFirst() {
    val iterable = Arrays.asList(GBP_2_33, null, GBP_2_36)
    BigMoney.total(iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_Iterable_badProvider() {
    val iterable = Arrays.asList[BigMoneyProvider](BAD_PROVIDER)
    BigMoney.total(iterable)
  }

  def test_factory_total_CurrencyUnitVarargs_1() {
    val test = BigMoney.total(GBP, GBP_1_23)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 123)
  }

  def test_factory_total_CurrencyUnitArray_1() {
    val array = Seq(GBP_1_23)
    val test = BigMoney.total(GBP, array.asJava)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 123)
  }

  def test_factory_total_CurrencyUnitVarargs_3() {
    val test = BigMoney.total(GBP, GBP_1_23, GBP_2_33, GBP_2_36)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_CurrencyUnitArray_3() {
    val array = Seq(GBP_1_23, GBP_2_33, GBP_2_36)
    val test = BigMoney.total(GBP, array.asJava)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_CurrencyUnitVarargs_3Mixed() {
    val test = BigMoney.total(GBP, GBP_1_23, GBP_2_33.toMoney(), GBP_2_36)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_CurrencyUnitArray_3Mixed() {
    val array = Seq(GBP_1_23, GBP_2_33.toMoney(), GBP_2_36)
    val test = BigMoney.total(GBP, array.asJava)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_CurrencyUnitArray_3Money() {
    val array = Seq(GBP_1_23.toMoney(), GBP_2_33.toMoney(), GBP_2_36.toMoney())
    val test = BigMoney.total(GBP, array.asJava)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 592)
  }

  def test_factory_total_CurrencyUnitVarargs_empty() {
    val test = BigMoney.total(GBP)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 0)
  }

  def test_factory_total_CurrencyUnitArray_empty() {
    val array = Array.ofDim[BigMoney](0).toSeq
    val test = BigMoney.total(GBP, array.asJava)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 0)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_CurrencyUnitVarargs_currenciesDiffer() {
    BigMoney.total(GBP, JPY_423)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_CurrencyUnitArray_currenciesDiffer() {
    val array = Seq(JPY_423)
    BigMoney.total(GBP, array.asJava)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_CurrencyUnitVarargs_currenciesDifferInArray() {
    BigMoney.total(GBP, GBP_2_33, JPY_423)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_CurrencyUnitArray_currenciesDifferInArray() {
    val array = Seq(GBP_2_33, JPY_423)
    BigMoney.total(GBP, array.asJava)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitVarargs_nullFirst() {
    BigMoney.total(GBP, null, GBP_2_33, GBP_2_36)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitArray_nullFirst() {
    val array = Seq(null, GBP_2_33, GBP_2_36)
    BigMoney.total(GBP, array.asJava)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitVarargs_nullNotFirst() {
    BigMoney.total(GBP, GBP_2_33, null, GBP_2_36)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitArray_nullNotFirst() {
    val array = Seq(GBP_2_33, null, GBP_2_36)
    BigMoney.total(GBP, array.asJava)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitVarargs_badProvider() {
    BigMoney.total(GBP, BAD_PROVIDER)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitArray_badProvider() {
    val array = Seq(BAD_PROVIDER)
    BigMoney.total(GBP, array.asJava)
  }

  def test_factory_total_CurrencyUnitIterable() {
    val iterable = Arrays.asList(GBP_1_23, GBP_2_33, BigMoney.of(GBP, 2.361d))
    val test = BigMoney.total(GBP, iterable)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(5921, 3))
  }

  def test_factory_total_CurrencyUnitIterable_Mixed() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_1_23.toMoney(), GBP_2_33)
    val test = BigMoney.total(GBP, iterable)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmount, BigDecimal.valueOf(356, 2))
  }

  def test_factory_total_CurrencyUnitIterable_empty() {
    val iterable = Collections.emptyList()
    val test = BigMoney.total(GBP, iterable)
    assertEquals(test.getCurrencyUnit, GBP)
    assertEquals(test.getAmountMinorInt, 0)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_CurrencyUnitIterable_currenciesDiffer() {
    val iterable = Arrays.asList(JPY_423)
    BigMoney.total(GBP, iterable)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_factory_total_CurrencyUnitIterable_currenciesDifferInIterable() {
    val iterable = Arrays.asList(GBP_2_33, JPY_423)
    BigMoney.total(GBP, iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitIterable_nullFirst() {
    val iterable = Arrays.asList(null, GBP_2_33, GBP_2_36)
    BigMoney.total(GBP, iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitIterable_nullNotFirst() {
    val iterable = Arrays.asList(GBP_2_33, null, GBP_2_36)
    BigMoney.total(GBP, iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_total_CurrencyUnitIterable_badProvider() {
    val iterable = Arrays.asList[BigMoneyProvider](BAD_PROVIDER)
    BigMoney.total(GBP, iterable)
  }

  @DataProvider(name = "parse")
  def data_parse(): Array[Array[Any]] = {
    Array(Array("GBP 2.43", GBP, "2.43", 2), Array("GBP +12.57", GBP, "12.57", 2), Array("GBP -5.87", GBP, "-5.87", 2), Array("GBP 0.99", GBP, "0.99", 2), Array("GBP .99", GBP, "0.99", 2), Array("GBP +.99", GBP, "0.99", 2), Array("GBP +0.99", GBP, "0.99", 2), Array("GBP -.99", GBP, "-0.99", 2), Array("GBP -0.99", GBP, "-0.99", 2), Array("GBP 0", GBP, "0", 0), Array("GBP 2", GBP, "2", 0), Array("GBP 123.", GBP, "123", 0), Array("GBP3", GBP, "3", 0), Array("GBP3.10", GBP, "3.10", 2), Array("GBP  3.10", GBP, "3.10", 2), Array("GBP   3.10", GBP, "3.10", 2), Array("GBP                           3.10", GBP, "3.10", 2), Array("GBP 123.456789", GBP, "123.456789", 6))
  }

  @Test(dataProvider = "parse")
  def test_factory_parse(str: String,
                         currency: CurrencyUnit,
                         amountStr: String,
                         scale: Int) {
    val test = BigMoney.parse(str)
    assertEquals(test.getCurrencyUnit, currency)
    assertEquals(test.getAmount, bd(amountStr))
    assertEquals(test.getScale, scale)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_factory_parse_String_tooShort() {
    BigMoney.parse("GBP")
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_factory_parse_String_exponent() {
    BigMoney.parse("GBP 234E2")
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_factory_parse_String_badCurrency() {
    BigMoney.parse("GBX 2.34")
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_factory_parse_String_nullString() {
    BigMoney.parse(null.asInstanceOf[String])
  }

  def test_nonNull_BigMoneyCurrencyUnit_nonNull() {
    val test = BigMoney.nonNull(GBP_1_23, GBP)
    assertSame(test, GBP_1_23)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_nonNull_BigMoneyCurrencyUnit_nonNullCurrencyMismatch() {
    BigMoney.nonNull(GBP_1_23, JPY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_nonNull_BigMoneyCurrencyUnit_nonNull_nullCurrency() {
    BigMoney.nonNull(GBP_1_23, null)
  }

  def test_nonNull_BigMoneyCurrencyUnit_null() {
    val test = BigMoney.nonNull(null, GBP)
    assertEquals(test, BigMoney.ofMajor(GBP, 0))
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_nonNull_BigMoneyCurrencyUnit_null_nullCurrency() {
    BigMoney.nonNull(null, null)
  }

  def test_constructor_null1() {
    val con = classOf[BigMoney].getDeclaredConstructor(classOf[CurrencyUnit], classOf[BigDecimal])
    assertEquals(Modifier.isPublic(con.getModifiers), true) // Making this public in scala version to still allow "new BigMoney(...)" vs using companion object apply
    assertEquals(Modifier.isProtected(con.getModifiers), false)
    try {
      con.setAccessible(true)
      con.newInstance(Array(null, BIGDEC_2_34))
      fail()
    } catch {
      case ex: IllegalArgumentException => //ex: InvocationTargetException => assertEquals(ex.getCause.getClass, classOf[AssertionError])
    }
  }

  def test_constructor_null2() {
    val con = classOf[BigMoney].getDeclaredConstructor(classOf[CurrencyUnit], classOf[BigDecimal])
    try {
      con.setAccessible(true)
      con.newInstance(Array(GBP, null))
      fail()
    } catch {
      case ex: IllegalArgumentException => //ex: InvocationTargetException => assertEquals(ex.getCause.getClass, classOf[AssertionError])
    }
  }

  def test_scaleNormalization1() {
    val a = BigMoney.ofScale(GBP, 100, 0)
    val b = BigMoney.ofScale(GBP, 1, -2)
    assertEquals(a.toString, "GBP 100")
    assertEquals(b.toString, "GBP 100")
    assertEquals(a == a, true)
    assertEquals(b == b, true)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode == b.hashCode, true)
  }

  def test_scaleNormalization2() {
    val a = BigMoney.ofScale(GBP, 1, 1)
    val b = BigMoney.ofScale(GBP, 10, 2)
    assertEquals(a.toString, "GBP 0.1")
    assertEquals(b.toString, "GBP 0.10")
    assertEquals(a == a, true)
    assertEquals(b == b, true)
    assertEquals(a == b, false)
    assertEquals(b == a, false)
    assertEquals(a.hashCode == b.hashCode, false)
  }

  def test_scaleNormalization3() {
    val a = BigMoney.of(GBP, new BigDecimal("100"))
    val b = BigMoney.of(GBP, new BigDecimal("1E+2"))
    assertEquals(a.toString, "GBP 100")
    assertEquals(b.toString, "GBP 100")
    assertEquals(a == a, true)
    assertEquals(b == b, true)
    assertEquals(a == b, true)
    assertEquals(b == a, true)
    assertEquals(a.hashCode == b.hashCode, true)
  }

  def test_serialization() {
    val a = BigMoney.parse("GBP 2.34")
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(a)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    val input = ois.readObject().asInstanceOf[BigMoney]
    assertEquals(input, a)
  }

  @Test(expectedExceptions = Array(classOf[InvalidObjectException]))
  def test_serialization_invalidNumericCode() {
    val cu = new CurrencyUnit("GBP", 234.toShort, 2.toShort)
    val m = BigMoney.of(cu, 123.43d)
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(m)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    ois.readObject()
  }

  @Test(expectedExceptions = Array(classOf[InvalidObjectException]))
  def test_serialization_invalidDecimalPlaces() {
    val cu = new CurrencyUnit("GBP", 826.toShort, 1.toShort)
    val m = BigMoney.of(cu, 123.43d)
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
    assertEquals(BigMoney.parse("EUR -5.78").getCurrencyUnit, EUR)
  }

  def test_withCurrencyUnit_Currency() {
    val test = GBP_2_34.withCurrencyUnit(USD)
    assertEquals(test.toString, "USD 2.34")
  }

  def test_withCurrencyUnit_Currency_same() {
    val test = GBP_2_34.withCurrencyUnit(GBP)
    assertSame(test, GBP_2_34)
  }

  def test_withCurrencyUnit_Currency_differentCurrencyScale() {
    val test = GBP_2_34.withCurrencyUnit(JPY)
    assertEquals(test.toString, "JPY 2.34")
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_withCurrencyUnit_Currency_nullCurrency() {
    GBP_2_34.withCurrencyUnit(null.asInstanceOf[CurrencyUnit])
  }

  def test_getScale_GBP() {
    assertEquals(GBP_2_34.getScale, 2)
  }

  def test_getScale_JPY() {
    assertEquals(JPY_423.getScale, 0)
  }

  def test_isCurrencyScale_GBP() {
    assertEquals(BigMoney.parse("GBP 2").isCurrencyScale, false)
    assertEquals(BigMoney.parse("GBP 2.3").isCurrencyScale, false)
    assertEquals(BigMoney.parse("GBP 2.34").isCurrencyScale, true)
    assertEquals(BigMoney.parse("GBP 2.345").isCurrencyScale, false)
  }

  def test_isCurrencyScale_JPY() {
    assertEquals(BigMoney.parse("JPY 2").isCurrencyScale, true)
    assertEquals(BigMoney.parse("JPY 2.3").isCurrencyScale, false)
    assertEquals(BigMoney.parse("JPY 2.34").isCurrencyScale, false)
    assertEquals(BigMoney.parse("JPY 2.345").isCurrencyScale, false)
  }

  def test_withScale_int_same() {
    val test = GBP_2_34.withScale(2)
    assertSame(test, GBP_2_34)
  }

  def test_withScale_int_more() {
    val test = GBP_2_34.withScale(3)
    assertEquals(test.getAmount, bd("2.340"))
    assertEquals(test.getScale, 3)
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_withScale_int_less() {
    BigMoney.parse("GBP 2.345").withScale(2)
  }

  def test_withScale_intRoundingMode_less() {
    val test = GBP_2_34.withScale(1, RoundingMode.UP)
    assertEquals(test.getAmount, bd("2.4"))
    assertEquals(test.getScale, 1)
  }

  def test_withScale_intRoundingMode_more() {
    val test = GBP_2_34.withScale(3, RoundingMode.UP)
    assertEquals(test.getAmount, bd("2.340"))
    assertEquals(test.getScale, 3)
  }

  def test_withCurrencyScale_int_same() {
    val test = GBP_2_34.withCurrencyScale()
    assertSame(test, GBP_2_34)
  }

  def test_withCurrencyScale_int_more() {
    val test = BigMoney.parse("GBP 2.3").withCurrencyScale()
    assertEquals(test.getAmount, bd("2.30"))
    assertEquals(test.getScale, 2)
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_withCurrencyScale_int_less() {
    BigMoney.parse("GBP 2.345").withCurrencyScale()
  }

  def test_withCurrencyScale_intRoundingMode_less() {
    val test = BigMoney.parse("GBP 2.345").withCurrencyScale(RoundingMode.UP)
    assertEquals(test.getAmount, bd("2.35"))
    assertEquals(test.getScale, 2)
  }

  def test_withCurrencyScale_intRoundingMode_more() {
    val test = BigMoney.parse("GBP 2.3").withCurrencyScale(RoundingMode.UP)
    assertEquals(test.getAmount, bd("2.30"))
    assertEquals(test.getScale, 2)
  }

  def test_withCurrencyScale_intRoundingMode_lessJPY() {
    val test = BigMoney.parse("JPY 2.345").withCurrencyScale(RoundingMode.UP)
    assertEquals(test.getAmount, bd("3"))
    assertEquals(test.getScale, 0)
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

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_getAmountMajorLong_tooBigPositive() {
    GBP_LONG_MAX_MAJOR_PLUS1.getAmountMajorLong
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_getAmountMajorLong_tooBigNegative() {
    GBP_LONG_MIN_MAJOR_MINUS1.getAmountMajorLong
  }

  def test_getAmountMajorInt_positive() {
    assertEquals(GBP_2_34.getAmountMajorInt, 2)
  }

  def test_getAmountMajorInt_negative() {
    assertEquals(GBP_M5_78.getAmountMajorInt, -5)
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_getAmountMajorInt_tooBigPositive() {
    GBP_INT_MAX_MAJOR_PLUS1.getAmountMajorInt
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
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

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_getAmountMinorLong_tooBigPositive() {
    GBP_LONG_MAX_PLUS1.getAmountMinorLong
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_getAmountMinorLong_tooBigNegative() {
    GBP_LONG_MIN_MINUS1.getAmountMinorLong
  }

  def test_getAmountMinorInt_positive() {
    assertEquals(GBP_2_34.getAmountMinorInt, 234)
  }

  def test_getAmountMinorInt_negative() {
    assertEquals(GBP_M5_78.getAmountMinorInt, -578)
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_getAmountMinorInt_tooBigPositive() {
    GBP_INT_MAX_PLUS1.getAmountMinorInt
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
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
    val test = GBP_2_34.withAmount(BIGDEC_2_345)
    assertEquals(test.getAmount, bd("2.345"))
    assertEquals(test.getScale, 3)
  }

  def test_withAmount_BigDecimal_same() {
    val test = GBP_2_34.withAmount(BIGDEC_2_34)
    assertSame(test, GBP_2_34)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_withAmount_BigDecimal_nullBigDecimal() {
    GBP_2_34.withAmount(null.asInstanceOf[BigDecimal])
  }

  def test_withAmount_double() {
    val test = GBP_2_34.withAmount(2.345d)
    assertEquals(test.getAmount, bd("2.345"))
    assertEquals(test.getScale, 3)
  }

  def test_withAmount_double_same() {
    val test = GBP_2_34.withAmount(2.34d)
    assertSame(test, GBP_2_34)
  }

  def test_plus_Iterable_BigMoneyProvider() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_2_33, GBP_1_23)
    val test = GBP_2_34.plus(iterable)
    assertEquals(test.toString, "GBP 5.90")
  }

  def test_plus_Iterable_BigMoney() {
    val iterable = Arrays.asList[BigMoney](GBP_2_33, GBP_1_23)
    val test = GBP_2_34.plus(iterable)
    assertEquals(test.toString, "GBP 5.90")
  }

  def test_plus_Iterable_Money() {
    val iterable = Arrays.asList[Money](GBP_2_33.toMoney(), GBP_1_23.toMoney())
    val test = GBP_2_34.plus(iterable)
    assertEquals(test.toString, "GBP 5.90")
  }

  def test_plus_Iterable_Mixed() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_2_33.toMoney(), new BigMoneyProvider() {

      override def toBigMoney(): BigMoney = return GBP_1_23
    })
    val test = GBP_2_34.plus(iterable)
    assertEquals(test.toString, "GBP 5.90")
  }

  def test_plus_Iterable_zero() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_0_00)
    val test = GBP_2_34.plus(iterable)
    assertEquals(test, GBP_2_34)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_plus_Iterable_currencyMismatch() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_2_33, JPY_423)
    GBP_M5_78.plus(iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plus_Iterable_nullEntry() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_2_33, null)
    GBP_M5_78.plus(iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plus_Iterable_nullIterable() {
    GBP_M5_78.plus(null.asInstanceOf[java.lang.Iterable[BigMoneyProvider]])
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plus_Iterable_badProvider() {
    val iterable = Arrays.asList[BigMoneyProvider](new BigMoneyProvider {
      override def toBigMoney(): BigMoney = return null
    })
    GBP_M5_78.plus(iterable)
  }

  def test_plus_BigMoneyProvider_zero() {
    val test = GBP_2_34.plus(GBP_0_00)
    assertSame(test, GBP_2_34)
  }

  def test_plus_BigMoneyProvider_positive() {
    val test = GBP_2_34.plus(GBP_1_23)
    assertEquals(test.toString, "GBP 3.57")
    assertEquals(test.getScale, 2)
  }

  def test_plus_BigMoneyProvider_negative() {
    val test = GBP_2_34.plus(GBP_M1_23)
    assertEquals(test.toString, "GBP 1.11")
    assertEquals(test.getScale, 2)
  }

  def test_plus_BigMoneyProvider_scale() {
    val test = GBP_2_34.plus(BigMoney.parse("GBP 1.111"))
    assertEquals(test.toString, "GBP 3.451")
    assertEquals(test.getScale, 3)
  }

  def test_plus_BigMoneyProvider_Money() {
    val test = GBP_2_34.plus(BigMoney.ofMinor(GBP, 1))
    assertEquals(test.toString, "GBP 2.35")
    assertEquals(test.getScale, 2)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_plus_BigMoneyProvider_currencyMismatch() {
    GBP_M5_78.plus(USD_1_23)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plus_BigMoneyProvider_nullBigMoneyProvider() {
    GBP_M5_78.plus(null.asInstanceOf[BigMoneyProvider])
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plus_BigMoneyProvider_badProvider() {
    GBP_M5_78.plus(new BigMoneyProvider() {

      override def toBigMoney(): BigMoney = null
    })
  }

  def test_plus_BigDecimal_zero() {
    val test = GBP_2_34.plus(BigDecimal.ZERO)
    assertSame(test, GBP_2_34)
  }

  def test_plus_BigDecimal_positive() {
    val test = GBP_2_34.plus(bd("1.23"))
    assertEquals(test.toString, "GBP 3.57")
    assertEquals(test.getScale, 2)
  }

  def test_plus_BigDecimal_negative() {
    val test = GBP_2_34.plus(bd("-1.23"))
    assertEquals(test.toString, "GBP 1.11")
    assertEquals(test.getScale, 2)
  }

  def test_plus_BigDecimal_scale() {
    val test = GBP_2_34.plus(bd("1.235"))
    assertEquals(test.toString, "GBP 3.575")
    assertEquals(test.getScale, 3)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plus_BigDecimal_nullBigDecimal() {
    GBP_M5_78.plus(null.asInstanceOf[BigDecimal])
  }

  def test_plus_double_zero() {
    val test = GBP_2_34.plus(0d)
    assertSame(test, GBP_2_34)
  }

  def test_plus_double_positive() {
    val test = GBP_2_34.plus(1.23d)
    assertEquals(test.toString, "GBP 3.57")
    assertEquals(test.getScale, 2)
  }

  def test_plus_double_negative() {
    val test = GBP_2_34.plus(-1.23d)
    assertEquals(test.toString, "GBP 1.11")
    assertEquals(test.getScale, 2)
  }

  def test_plus_double_scale() {
    val test = GBP_2_34.plus(1.234d)
    assertEquals(test.toString, "GBP 3.574")
    assertEquals(test.getScale, 3)
  }

  def test_plusMajor_zero() {
    val test = GBP_2_34.plusMajor(0)
    assertSame(test, GBP_2_34)
  }

  def test_plusMajor_positive() {
    val test = GBP_2_34.plusMajor(123)
    assertEquals(test.toString, "GBP 125.34")
    assertEquals(test.getScale, 2)
  }

  def test_plusMajor_negative() {
    val test = GBP_2_34.plusMajor(-123)
    assertEquals(test.toString, "GBP -120.66")
    assertEquals(test.getScale, 2)
  }

  def test_plusMinor_zero() {
    val test = GBP_2_34.plusMinor(0)
    assertSame(test, GBP_2_34)
  }

  def test_plusMinor_positive() {
    val test = GBP_2_34.plusMinor(123)
    assertEquals(test.toString, "GBP 3.57")
    assertEquals(test.getScale, 2)
  }

  def test_plusMinor_negative() {
    val test = GBP_2_34.plusMinor(-123)
    assertEquals(test.toString, "GBP 1.11")
    assertEquals(test.getScale, 2)
  }

  def test_plusMinor_scale() {
    val test = BigMoney.parse("GBP 12").plusMinor(123)
    assertEquals(test.toString, "GBP 13.23")
    assertEquals(test.getScale, 2)
  }

  def test_plusRetainScale_BigMoneyProviderRoundingMode_zero() {
    val test = GBP_2_34.plusRetainScale(BigMoney.zero(GBP), RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_plusRetainScale_BigMoneyProviderRoundingMode_positive() {
    val test = GBP_2_34.plusRetainScale(BigMoney.parse("GBP 1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plusRetainScale_BigMoneyProviderRoundingMode_negative() {
    val test = GBP_2_34.plusRetainScale(BigMoney.parse("GBP -1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_plusRetainScale_BigMoneyProviderRoundingMode_roundDown() {
    val test = GBP_2_34.plusRetainScale(BigMoney.parse("GBP 1.235"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 3.57")
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_plusRetainScale_BigMoneyProviderRoundingMode_roundUnecessary() {
    GBP_2_34.plusRetainScale(BigMoney.parse("GBP 1.235"), RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plusRetainScale_BigMoneyProviderRoundingMode_nullBigDecimal() {
    GBP_M5_78.plusRetainScale(null.asInstanceOf[BigDecimal], RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plusRetainScale_BigMoneyProviderRoundingMode_nullRoundingMode() {
    GBP_M5_78.plusRetainScale(BigMoney.parse("GBP 1.23"), null.asInstanceOf[RoundingMode])
  }

  def test_plusRetainScale_BigDecimalRoundingMode_zero() {
    val test = GBP_2_34.plusRetainScale(BigDecimal.ZERO, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_plusRetainScale_BigDecimalRoundingMode_positive() {
    val test = GBP_2_34.plusRetainScale(bd("1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plusRetainScale_BigDecimalRoundingMode_negative() {
    val test = GBP_2_34.plusRetainScale(bd("-1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_plusRetainScale_BigDecimalRoundingMode_roundDown() {
    val test = GBP_2_34.plusRetainScale(bd("1.235"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 3.57")
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_plusRetainScale_BigDecimalRoundingMode_roundUnecessary() {
    GBP_2_34.plusRetainScale(bd("1.235"), RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plusRetainScale_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_M5_78.plusRetainScale(null.asInstanceOf[BigDecimal], RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plusRetainScale_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_M5_78.plusRetainScale(BIGDEC_2_34, null.asInstanceOf[RoundingMode])
  }

  def test_plusRetainScale_doubleRoundingMode_zero() {
    val test = GBP_2_34.plusRetainScale(0d, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_plusRetainScale_doubleRoundingMode_positive() {
    val test = GBP_2_34.plusRetainScale(1.23d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_plusRetainScale_doubleRoundingMode_negative() {
    val test = GBP_2_34.plusRetainScale(-1.23d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_plusRetainScale_doubleRoundingMode_roundDown() {
    val test = GBP_2_34.plusRetainScale(1.235d, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 3.57")
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_plusRetainScale_doubleRoundingMode_roundUnecessary() {
    GBP_2_34.plusRetainScale(1.235d, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_plusRetainScale_doubleRoundingMode_nullRoundingMode() {
    GBP_M5_78.plusRetainScale(2.34d, null.asInstanceOf[RoundingMode])
  }

  def test_minus_Iterable_BigMoneyProvider() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_2_33, GBP_1_23)
    val test = GBP_2_34.minus(iterable)
    assertEquals(test.toString, "GBP -1.22")
  }

  def test_minus_Iterable_BigMoney() {
    val iterable = Arrays.asList[BigMoney](GBP_2_33, GBP_1_23)
    val test = GBP_2_34.minus(iterable)
    assertEquals(test.toString, "GBP -1.22")
  }

  def test_minus_Iterable_Money() {
    val iterable = Arrays.asList[Money](GBP_2_33.toMoney(), GBP_1_23.toMoney())
    val test = GBP_2_34.minus(iterable)
    assertEquals(test.toString, "GBP -1.22")
  }

  def test_minus_Iterable_Mixed() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_2_33.toMoney(), new BigMoneyProvider() {

      override def toBigMoney(): BigMoney = return GBP_1_23
    })
    val test = GBP_2_34.minus(iterable)
    assertEquals(test.toString, "GBP -1.22")
  }

  def test_minus_Iterable_zero() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_0_00)
    val test = GBP_2_34.minus(iterable)
    assertEquals(test, GBP_2_34)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_minus_Iterable_currencyMismatch() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_2_33, JPY_423)
    GBP_M5_78.minus(iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minus_Iterable_nullEntry() {
    val iterable = Arrays.asList[BigMoneyProvider](GBP_2_33, null)
    GBP_M5_78.minus(iterable)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minus_Iterable_nullIterable() {
    GBP_M5_78.minus(null.asInstanceOf[java.lang.Iterable[BigMoneyProvider]])
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minus_Iterable_badProvider() {
    val iterable = Arrays.asList[BigMoneyProvider](new BigMoneyProvider {
      override def toBigMoney(): BigMoney = return null
    })
    GBP_M5_78.minus(iterable)
  }

  def test_minus_BigMoneyProvider_zero() {
    val test = GBP_2_34.minus(GBP_0_00)
    assertSame(test, GBP_2_34)
  }

  def test_minus_BigMoneyProvider_positive() {
    val test = GBP_2_34.minus(GBP_1_23)
    assertEquals(test.toString, "GBP 1.11")
    assertEquals(test.getScale, 2)
  }

  def test_minus_BigMoneyProvider_negative() {
    val test = GBP_2_34.minus(GBP_M1_23)
    assertEquals(test.toString, "GBP 3.57")
    assertEquals(test.getScale, 2)
  }

  def test_minus_BigMoneyProvider_scale() {
    val test = GBP_2_34.minus(BigMoney.parse("GBP 1.111"))
    assertEquals(test.toString, "GBP 1.229")
    assertEquals(test.getScale, 3)
  }

  def test_minus_BigMoneyProvider_Money() {
    val test = GBP_2_34.minus(BigMoney.ofMinor(GBP, 1))
    assertEquals(test.toString, "GBP 2.33")
    assertEquals(test.getScale, 2)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_minus_BigMoneyProvider_currencyMismatch() {
    GBP_M5_78.minus(USD_1_23)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minus_BigMoneyProvider_nullBigMoneyProvider() {
    GBP_M5_78.minus(null.asInstanceOf[BigMoneyProvider])
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minus_BigMoneyProvider_badProvider() {
    GBP_M5_78.minus(new BigMoneyProvider() {

      override def toBigMoney(): BigMoney = null
    })
  }

  def test_minus_BigDecimal_zero() {
    val test = GBP_2_34.minus(BigDecimal.ZERO)
    assertSame(test, GBP_2_34)
  }

  def test_minus_BigDecimal_positive() {
    val test = GBP_2_34.minus(bd("1.23"))
    assertEquals(test.toString, "GBP 1.11")
    assertEquals(test.getScale, 2)
  }

  def test_minus_BigDecimal_negative() {
    val test = GBP_2_34.minus(bd("-1.23"))
    assertEquals(test.toString, "GBP 3.57")
    assertEquals(test.getScale, 2)
  }

  def test_minus_BigDecimal_scale() {
    val test = GBP_2_34.minus(bd("1.235"))
    assertEquals(test.toString, "GBP 1.105")
    assertEquals(test.getScale, 3)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minus_BigDecimal_nullBigDecimal() {
    GBP_M5_78.minus(null.asInstanceOf[BigDecimal])
  }

  def test_minus_double_zero() {
    val test = GBP_2_34.minus(0d)
    assertSame(test, GBP_2_34)
  }

  def test_minus_double_positive() {
    val test = GBP_2_34.minus(1.23d)
    assertEquals(test.toString, "GBP 1.11")
    assertEquals(test.getScale, 2)
  }

  def test_minus_double_negative() {
    val test = GBP_2_34.minus(-1.23d)
    assertEquals(test.toString, "GBP 3.57")
    assertEquals(test.getScale, 2)
  }

  def test_minus_double_scale() {
    val test = GBP_2_34.minus(1.235d)
    assertEquals(test.toString, "GBP 1.105")
    assertEquals(test.getScale, 3)
  }

  def test_minusMajor_zero() {
    val test = GBP_2_34.minusMajor(0)
    assertSame(test, GBP_2_34)
  }

  def test_minusMajor_positive() {
    val test = GBP_2_34.minusMajor(123)
    assertEquals(test.toString, "GBP -120.66")
    assertEquals(test.getScale, 2)
  }

  def test_minusMajor_negative() {
    val test = GBP_2_34.minusMajor(-123)
    assertEquals(test.toString, "GBP 125.34")
    assertEquals(test.getScale, 2)
  }

  def test_minusMinor_zero() {
    val test = GBP_2_34.minusMinor(0)
    assertSame(test, GBP_2_34)
  }

  def test_minusMinor_positive() {
    val test = GBP_2_34.minusMinor(123)
    assertEquals(test.toString, "GBP 1.11")
    assertEquals(test.getScale, 2)
  }

  def test_minusMinor_negative() {
    val test = GBP_2_34.minusMinor(-123)
    assertEquals(test.toString, "GBP 3.57")
    assertEquals(test.getScale, 2)
  }

  def test_minusMinor_scale() {
    val test = BigMoney.parse("GBP 12").minusMinor(123)
    assertEquals(test.toString, "GBP 10.77")
    assertEquals(test.getScale, 2)
  }

  def test_minusRetainScale_BigMoneyProviderRoundingMode_zero() {
    val test = GBP_2_34.minusRetainScale(BigMoney.zero(GBP), RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_minusRetainScale_BigMoneyProviderRoundingMode_positive() {
    val test = GBP_2_34.minusRetainScale(BigMoney.parse("GBP 1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minusRetainScale_BigMoneyProviderRoundingMode_negative() {
    val test = GBP_2_34.minusRetainScale(BigMoney.parse("GBP -1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_minusRetainScale_BigMoneyProviderRoundingMode_roundDown() {
    val test = GBP_2_34.minusRetainScale(BigMoney.parse("GBP 1.235"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 1.10")
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_minusRetainScale_BigMoneyProviderRoundingMode_roundUnecessary() {
    GBP_2_34.minusRetainScale(BigMoney.parse("GBP 1.235"), RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minusRetainScale_BigMoneyProviderRoundingMode_nullBigMoneyProvider() {
    GBP_M5_78.minusRetainScale(null.asInstanceOf[BigMoneyProvider], RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minusRetainScale_BigMoneyProviderRoundingMode_nullRoundingMode() {
    GBP_M5_78.minusRetainScale(BigMoney.parse("GBP 123"), null.asInstanceOf[RoundingMode])
  }

  def test_minusRetainScale_BigDecimalRoundingMode_zero() {
    val test = GBP_2_34.minusRetainScale(BigDecimal.ZERO, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_minusRetainScale_BigDecimalRoundingMode_positive() {
    val test = GBP_2_34.minusRetainScale(bd("1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minusRetainScale_BigDecimalRoundingMode_negative() {
    val test = GBP_2_34.minusRetainScale(bd("-1.23"), RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_minusRetainScale_BigDecimalRoundingMode_roundDown() {
    val test = GBP_2_34.minusRetainScale(bd("1.235"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 1.10")
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_minusRetainScale_BigDecimalRoundingMode_roundUnecessary() {
    GBP_2_34.minusRetainScale(bd("1.235"), RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minusRetainScale_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_M5_78.minusRetainScale(null.asInstanceOf[BigDecimal], RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minusRetainScale_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_M5_78.minusRetainScale(BIGDEC_2_34, null.asInstanceOf[RoundingMode])
  }

  def test_minusRetainScale_doubleRoundingMode_zero() {
    val test = GBP_2_34.minusRetainScale(0d, RoundingMode.UNNECESSARY)
    assertSame(test, GBP_2_34)
  }

  def test_minusRetainScale_doubleRoundingMode_positive() {
    val test = GBP_2_34.minusRetainScale(1.23d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 1.11")
  }

  def test_minusRetainScale_doubleRoundingMode_negative() {
    val test = GBP_2_34.minusRetainScale(-1.23d, RoundingMode.UNNECESSARY)
    assertEquals(test.toString, "GBP 3.57")
  }

  def test_minusRetainScale_doubleRoundingMode_roundDown() {
    val test = GBP_2_34.minusRetainScale(1.235d, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 1.10")
  }

  @Test(expectedExceptions = Array(classOf[ArithmeticException]))
  def test_minusRetainScale_doubleRoundingMode_roundUnecessary() {
    GBP_2_34.minusRetainScale(1.235d, RoundingMode.UNNECESSARY)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_minusRetainScale_doubleRoundingMode_nullRoundingMode() {
    GBP_M5_78.minusRetainScale(2.34d, null.asInstanceOf[RoundingMode])
  }

  def test_multipliedBy_BigDecimal_one() {
    val test = GBP_2_34.multipliedBy(BigDecimal.ONE)
    assertSame(test, GBP_2_34)
  }

  def test_multipliedBy_BigDecimal_positive() {
    val test = GBP_2_33.multipliedBy(bd("2.5"))
    assertEquals(test.toString, "GBP 5.825")
    assertEquals(test.getScale, 3)
  }

  def test_multipliedBy_BigDecimal_negative() {
    val test = GBP_2_33.multipliedBy(bd("-2.5"))
    assertEquals(test.toString, "GBP -5.825")
    assertEquals(test.getScale, 3)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_multipliedBy_BigDecimal_nullBigDecimal() {
    GBP_5_78.multipliedBy(null.asInstanceOf[BigDecimal])
  }

  def test_multipliedBy_doubleRoundingMode_one() {
    val test = GBP_2_34.multipliedBy(1d)
    assertSame(test, GBP_2_34)
  }

  def test_multipliedBy_doubleRoundingMode_positive() {
    val test = GBP_2_33.multipliedBy(2.5d)
    assertEquals(test.toString, "GBP 5.825")
    assertEquals(test.getScale, 3)
  }

  def test_multipliedBy_doubleRoundingMode_negative() {
    val test = GBP_2_33.multipliedBy(-2.5d)
    assertEquals(test.toString, "GBP -5.825")
    assertEquals(test.getScale, 3)
  }

  def test_multipliedBy_long_one() {
    val test = GBP_2_34.multipliedBy(1)
    assertSame(test, GBP_2_34)
  }

  def test_multipliedBy_long_positive() {
    val test = GBP_2_34.multipliedBy(3)
    assertEquals(test.toString, "GBP 7.02")
    assertEquals(test.getScale, 2)
  }

  def test_multipliedBy_long_negative() {
    val test = GBP_2_34.multipliedBy(-3)
    assertEquals(test.toString, "GBP -7.02")
    assertEquals(test.getScale, 2)
  }

  def test_multiplyRetainScale_BigDecimalRoundingMode_one() {
    val test = GBP_2_34.multiplyRetainScale(BigDecimal.ONE, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_multiplyRetainScale_BigDecimalRoundingMode_positive() {
    val test = GBP_2_33.multiplyRetainScale(bd("2.5"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 5.82")
  }

  def test_multiplyRetainScale_BigDecimalRoundingMode_positive_halfUp() {
    val test = GBP_2_33.multiplyRetainScale(bd("2.5"), RoundingMode.HALF_UP)
    assertEquals(test.toString, "GBP 5.83")
  }

  def test_multiplyRetainScale_BigDecimalRoundingMode_negative() {
    val test = GBP_2_33.multiplyRetainScale(bd("-2.5"), RoundingMode.FLOOR)
    assertEquals(test.toString, "GBP -5.83")
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_multiplyRetainScale_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_5_78.multiplyRetainScale(null.asInstanceOf[BigDecimal], RoundingMode.DOWN)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_multiplyRetainScale_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_5_78.multiplyRetainScale(bd("2.5"), null.asInstanceOf[RoundingMode])
  }

  def test_multiplyRetainScale_doubleRoundingMode_one() {
    val test = GBP_2_34.multiplyRetainScale(1d, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_multiplyRetainScale_doubleRoundingMode_positive() {
    val test = GBP_2_33.multiplyRetainScale(2.5d, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 5.82")
  }

  def test_multiplyRetainScale_doubleRoundingMode_positive_halfUp() {
    val test = GBP_2_33.multiplyRetainScale(2.5d, RoundingMode.HALF_UP)
    assertEquals(test.toString, "GBP 5.83")
  }

  def test_multiplyRetainScale_doubleRoundingMode_negative() {
    val test = GBP_2_33.multiplyRetainScale(-2.5d, RoundingMode.FLOOR)
    assertEquals(test.toString, "GBP -5.83")
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_multiplyRetainScale_doubleRoundingMode_nullRoundingMode() {
    GBP_5_78.multiplyRetainScale(2.5d, null.asInstanceOf[RoundingMode])
  }

  def test_dividedBy_BigDecimalRoundingMode_one() {
    val test = GBP_2_34.dividedBy(BigDecimal.ONE, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_dividedBy_BigDecimalRoundingMode_positive() {
    val test = GBP_2_34.dividedBy(bd("2.5"), RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 0.93")
  }

  def test_dividedBy_BigDecimalRoundingMode_positive_halfUp() {
    val test = GBP_2_34.dividedBy(bd("2.5"), RoundingMode.HALF_UP)
    assertEquals(test.toString, "GBP 0.94")
  }

  def test_dividedBy_BigDecimalRoundingMode_negative() {
    val test = GBP_2_34.dividedBy(bd("-2.5"), RoundingMode.FLOOR)
    assertEquals(test.toString, "GBP -0.94")
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_dividedBy_BigDecimalRoundingMode_nullBigDecimal() {
    GBP_5_78.dividedBy(null.asInstanceOf[BigDecimal], RoundingMode.DOWN)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_dividedBy_BigDecimalRoundingMode_nullRoundingMode() {
    GBP_5_78.dividedBy(bd("2.5"), null.asInstanceOf[RoundingMode])
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

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
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

  def test_negated_zero() {
    val test = GBP_0_00.negated()
    assertSame(test, GBP_0_00)
  }

  def test_negated_positive() {
    val test = GBP_2_34.negated()
    assertEquals(test.toString, "GBP -2.34")
  }

  def test_negated_negative() {
    val test = BigMoney.parse("GBP -2.34").negated()
    assertEquals(test.toString, "GBP 2.34")
  }

  def test_abs_positive() {
    val test = GBP_2_34.abs()
    assertSame(test, GBP_2_34)
  }

  def test_abs_negative() {
    val test = BigMoney.parse("GBP -2.34").abs()
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
    val test = BigMoney.parse("GBP 432.34").rounded(-1, RoundingMode.DOWN)
    assertEquals(test.toString, "GBP 430.00")
  }

  def test_round_M1up() {
    val test = BigMoney.parse("GBP 432.34").rounded(-1, RoundingMode.UP)
    assertEquals(test.toString, "GBP 440.00")
  }

  def test_round_3() {
    val test = GBP_2_34.rounded(3, RoundingMode.DOWN)
    assertSame(test, GBP_2_34)
  }

  def test_convertedTo_CurrencyUnit_BigDecimal_positive() {
    val test = GBP_2_33.convertedTo(EUR, bd("2.5"))
    assertEquals(test.toString, "EUR 5.825")
  }

  def test_convertedTo_CurrencyUnit_BigDecimal_sameCurrencyCorrectFactor() {
    val test = GBP_2_33.convertedTo(GBP, bd("1.00000"))
    assertEquals(test, GBP_2_33)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_convertedTo_CurrencyUnit_BigDecimal_negative() {
    GBP_2_33.convertedTo(EUR, bd("-2.5"))
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_convertedTo_CurrencyUnit_BigDecimal_sameCurrencyWrongFactor() {
    GBP_2_33.convertedTo(GBP, bd("2.5"))
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_convertedTo_CurrencyUnit_BigDecimal_nullCurrency() {
    GBP_5_78.convertedTo(null.asInstanceOf[CurrencyUnit], bd("2"))
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_convertedTo_CurrencyUnit_BigDecimal_nullBigDecimal() {
    GBP_5_78.convertedTo(EUR, null.asInstanceOf[BigDecimal])
  }

  def test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_positive() {
    val test = BigMoney.parse("GBP 2.2").convertRetainScale(EUR, bd("2.5"), RoundingMode.DOWN)
    assertEquals(test.toString, "EUR 5.5")
  }

  def test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_roundHalfUp() {
    val test = BigMoney.parse("GBP 2.21").convertRetainScale(EUR, bd("2.5"), RoundingMode.HALF_UP)
    assertEquals(test.toString, "EUR 5.53")
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_negative() {
    GBP_2_33.convertRetainScale(EUR, bd("-2.5"), RoundingMode.DOWN)
  }

  @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))
  def test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_sameCurrency() {
    GBP_2_33.convertRetainScale(GBP, bd("2.5"), RoundingMode.DOWN)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_nullCurrency() {
    GBP_5_78.convertRetainScale(null.asInstanceOf[CurrencyUnit], bd("2"), RoundingMode.DOWN)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_nullBigDecimal() {
    GBP_5_78.convertRetainScale(EUR, null.asInstanceOf[BigDecimal], RoundingMode.DOWN)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_nullRoundingMode() {
    GBP_5_78.convertRetainScale(EUR, bd("2"), null.asInstanceOf[RoundingMode])
  }

  def test_toBigMoney() {
    assertSame(GBP_2_34.toBigMoney(), GBP_2_34)
  }

  def test_toMoney() {
    assertEquals(GBP_2_34.toMoney(), Money.of(GBP, BIGDEC_2_34))
  }

  def test_toMoney_RoundingMode() {
    assertEquals(GBP_2_34.toMoney(RoundingMode.HALF_EVEN), Money.parse("GBP 2.34"))
  }

  def test_toMoney_RoundingMode_round() {
    val money = BigMoney.parse("GBP 2.355")
    assertEquals(money.toMoney(RoundingMode.HALF_EVEN), Money.parse("GBP 2.36"))
  }

  def test_isSameCurrency_BigMoney_same() {
    assertEquals(GBP_2_34.isSameCurrency(GBP_2_35), true)
  }

  def test_isSameCurrency_BigMoney_different() {
    assertEquals(GBP_2_34.isSameCurrency(USD_2_34), false)
  }

  def test_isSameCurrency_Money_same() {
    assertEquals(GBP_2_34.isSameCurrency(Money.parse("GBP 2")), true)
  }

  def test_isSameCurrency_Money_different() {
    assertEquals(GBP_2_34.isSameCurrency(Money.parse("USD 2")), false)
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_isSameCurrency_Money_nullMoney() {
    GBP_2_34.isSameCurrency(null.asInstanceOf[BigMoney])
  }

  def test_compareTo_BigMoney() {
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

  def test_compareTo_Money() {
    val t = GBP_2_35
    val a = Money.ofMinor(GBP, 234)
    val b = Money.ofMinor(GBP, 235)
    val c = Money.ofMinor(GBP, 236)
    assertEquals(t.compareTo(a), 1)
    assertEquals(t.compareTo(b), 0)
    assertEquals(t.compareTo(c), -1)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
  def test_compareTo_currenciesDiffer() {
    val a = GBP_2_34
    val b = USD_2_35
    a.compareTo(b)
  }

  @Test(expectedExceptions = Array(classOf[ClassCastException]))
  def test_compareTo_wrongType() {
    val a = GBP_2_34
    a.compareTo("NotRightType".asInstanceOf[BigMoney])
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
    val b = Money.ofMinor(GBP, 234)
    assertEquals(a.isEqual(b), true)
  }

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
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

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
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

  @Test(expectedExceptions = Array(classOf[CurrencyMismatchException]))
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
    val test = BigMoney.of(GBP, BIGDEC_2_34)
    assertEquals(test.toString, "GBP 2.34")
  }

  def test_toString_negative() {
    val test = BigMoney.of(EUR, BIGDEC_M5_78)
    assertEquals(test.toString, "EUR -5.78")
  }
}
