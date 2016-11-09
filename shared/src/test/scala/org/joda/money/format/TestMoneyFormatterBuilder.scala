package org.joda.money.format

import org.testng.Assert.assertEquals
import java.io.IOException
import java.math.BigDecimal
import java.text.DecimalFormatSymbols
import java.util.Locale
import org.joda.money.BigMoney
import org.joda.money.BigMoneyProvider
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import TestMoneyFormatterBuilder._
//remove if not needed
import scala.collection.JavaConversions._

object TestMoneyFormatterBuilder {

  private val GBP = CurrencyUnit.GBP

  private val JPY = CurrencyUnit.JPY

  private val BHD = CurrencyUnit.of("BHD")

  private val GBP_2_34 = Money.parse("GBP 2.34")

  private val GBP_23_45 = Money.parse("GBP 23.45")

  private val GBP_234_56 = Money.parse("GBP 234.56")

  private val GBP_MINUS_234_56 = Money.parse("GBP -234.56")

  private val GBP_2345_67 = Money.parse("GBP 2345.67")

  private val GBP_1234567_89 = Money.parse("GBP 1234567.89")

  private val GBP_1234_56789 = BigMoney.parse("GBP 1234.56789")

  private val GBP_1234567891234_1234567891 = BigMoney.parse("GBP 1234567891234.1234567891")

  private val JPY_2345 = Money.parse("JPY 2345")

  private val cCachedLocale = Locale.getDefault

  private val TEST_GB_LOCALE = new Locale("en", "GB", "TEST")

  private val TEST_FR_LOCALE = new Locale("fr", "FR", "TEST")

  private val FR_SYMBOLS = new DecimalFormatSymbols(Locale.FRANCE)

  private val FR_DECIMAL = FR_SYMBOLS.getMonetaryDecimalSeparator

  private val FR_GROUP = FR_SYMBOLS.getGroupingSeparator
}

/**
 * Test MoneyFormatterBuilder.
 */
@Test
class TestMoneyFormatterBuilder {

  private var iBuilder: MoneyFormatterBuilder = _

  @BeforeMethod
  def beforeMethod() {
    Locale.setDefault(TEST_GB_LOCALE)
    iBuilder = new MoneyFormatterBuilder()
  }

  @AfterMethod
  def afterMethod() {
    Locale.setDefault(cCachedLocale)
    iBuilder = null
  }

  def test_empty() {
    val test = iBuilder.toFormatter()
    assertEquals(test.print(GBP_2_34), "")
    assertEquals(test.toString, "")
  }

  def test_appendCurrencyCode_print() {
    iBuilder.appendCurrencyCode()
    val test = iBuilder.toFormatter()
    assertEquals(test.print(GBP_2_34), "GBP")
    assertEquals(test.toString, "${code}")
  }

  def test_appendCurrencyCode_parse_ok() {
    iBuilder.appendCurrencyCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("GBP", 0)
    assertEquals(parsed.isError, false)
    assertEquals(parsed.getIndex, 3)
    assertEquals(parsed.getErrorIndex, -1)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, CurrencyUnit.GBP)
  }

  def test_appendCurrencyCode_parse_tooShort() {
    iBuilder.appendCurrencyCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("GB", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendCurrencyCode_parse_empty() {
    iBuilder.appendCurrencyCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendCurrencyNumeric3Code_print() {
    iBuilder.appendCurrencyNumeric3Code()
    val test = iBuilder.toFormatter()
    assertEquals(test.print(GBP_2_34), "826")
    assertEquals(test.toString, "${numeric3Code}")
  }

  def test_appendCurrencyNumeric3Code_parse_ok() {
    iBuilder.appendCurrencyNumeric3Code()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("826A", 0)
    assertEquals(parsed.isError, false)
    assertEquals(parsed.getIndex, 3)
    assertEquals(parsed.getErrorIndex, -1)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, CurrencyUnit.GBP)
  }

  def test_appendCurrencyNumeric3Code_parse_tooShort() {
    iBuilder.appendCurrencyNumeric3Code()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("82", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendCurrencyNumeric3Code_parse_badCurrency() {
    iBuilder.appendCurrencyNumeric3Code()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("991A", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendCurrencyNumeric3Code_parse_empty() {
    iBuilder.appendCurrencyNumeric3Code()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendCurrencyNumericCode_print() {
    iBuilder.appendCurrencyNumericCode()
    val test = iBuilder.toFormatter()
    assertEquals(test.print(GBP_2_34), "826")
    assertEquals(test.toString, "${numericCode}")
  }

  def test_appendCurrencyNumericCode_parse_ok() {
    iBuilder.appendCurrencyNumericCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("826A", 0)
    assertEquals(parsed.isError, false)
    assertEquals(parsed.getIndex, 3)
    assertEquals(parsed.getErrorIndex, -1)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, CurrencyUnit.GBP)
  }

  def test_appendCurrencyNumericCode_parse_ok_padded() {
    iBuilder.appendCurrencyNumericCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("008A", 0)
    assertEquals(parsed.isError, false)
    assertEquals(parsed.getIndex, 3)
    assertEquals(parsed.getErrorIndex, -1)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency.getCode, "ALL")
  }

  def test_appendCurrencyNumericCode_parse_ok_notPadded1() {
    iBuilder.appendCurrencyNumericCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("8A", 0)
    assertEquals(parsed.isError, false)
    assertEquals(parsed.getIndex, 1)
    assertEquals(parsed.getErrorIndex, -1)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency.getCode, "ALL")
  }

  def test_appendCurrencyNumericCode_parse_ok_notPadded2() {
    iBuilder.appendCurrencyNumericCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("51 ", 0)
    assertEquals(parsed.isError, false)
    assertEquals(parsed.getIndex, 2)
    assertEquals(parsed.getErrorIndex, -1)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency.getCode, "AMD")
  }

  def test_appendCurrencyNumericCode_parse_tooShort() {
    iBuilder.appendCurrencyNumericCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendCurrencyNumericCode_parse_badCurrency() {
    iBuilder.appendCurrencyNumericCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("991A", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendCurrencyNumericCode_parse_empty() {
    iBuilder.appendCurrencyNumericCode()
    val test = iBuilder.toFormatter()
    val parsed = test.parse("", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendCurrencySymbolLocalized_print() {
    iBuilder.appendCurrencySymbolLocalized()
    val test = iBuilder.toFormatter()
    assertEquals(test.print(GBP_2_34), "£")
    assertEquals(test.toString, "${symbolLocalized}")
  }

  def test_appendCurrencySymbolLocalized_parse() {
    iBuilder.appendCurrencySymbolLocalized()
    val test = iBuilder.toFormatter()
    assertEquals(test.isParser, false)
  }

  def test_appendLiteral_print() {
    iBuilder.appendLiteral("Hello")
    val test = iBuilder.toFormatter()
    assertEquals(test.print(GBP_2_34), "Hello")
    assertEquals(test.toString, "'Hello'")
  }

  def test_appendLiteral_print_empty() {
    iBuilder.appendLiteral("")
    val test = iBuilder.toFormatter()
    assertEquals(test.print(GBP_2_34), "")
    assertEquals(test.toString, "")
  }

  def test_appendLiteral_print_null() {
    iBuilder.appendLiteral(null.asInstanceOf[CharSequence])
    val test = iBuilder.toFormatter()
    assertEquals(test.print(GBP_2_34), "")
    assertEquals(test.toString, "")
  }

  def test_appendLiteral_parse_ok() {
    iBuilder.appendLiteral("Hello")
    val test = iBuilder.toFormatter()
    val parsed = test.parse("HelloWorld", 0)
    assertEquals(parsed.isError, false)
    assertEquals(parsed.getIndex, 5)
    assertEquals(parsed.getErrorIndex, -1)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendLiteral_parse_tooShort() {
    iBuilder.appendLiteral("Hello")
    val test = iBuilder.toFormatter()
    val parsed = test.parse("Hell", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  def test_appendLiteral_parse_noMatch() {
    iBuilder.appendLiteral("Hello")
    val test = iBuilder.toFormatter()
    val parsed = test.parse("Helol", 0)
    assertEquals(parsed.isError, true)
    assertEquals(parsed.getIndex, 0)
    assertEquals(parsed.getErrorIndex, 0)
    assertEquals(parsed.getAmount, null)
    assertEquals(parsed.getCurrency, null)
  }

  @DataProvider(name = "appendAmount")
  def data_appendAmount(): Array[Array[Any]] = {
    Array(Array(GBP_2_34, "2.34"), Array(GBP_23_45, "23.45"), Array(GBP_234_56, "234.56"), Array(GBP_2345_67, "2,345.67"), Array(GBP_1234567_89, "1,234,567.89"), Array(GBP_1234_56789, "1,234.567,89"), Array(GBP_1234567891234_1234567891, "1,234,567,891,234.123,456,789,1"), Array(GBP_MINUS_234_56, "-234.56"))
  }

  @Test(dataProvider = "appendAmount")
  def test_appendAmount(money: BigMoneyProvider, expected: String) {
    iBuilder.appendAmount()
    val test = iBuilder.toFormatter()
    assertEquals(test.print(money), expected)
    assertEquals(test.toString, "${amount}")
  }

  def test_appendAmount_GBP_1234_56789_France() {
    iBuilder.appendAmount()
    val test = iBuilder.toFormatter(Locale.FRANCE)
    assertEquals(test.print(GBP_1234_56789), "1,234.567,89")
    assertEquals(test.toString, "${amount}")
  }

  def test_appendAmount_JPY_2345() {
    iBuilder.appendAmount()
    val test = iBuilder.toFormatter()
    assertEquals(test.print(JPY_2345), "2,345")
    assertEquals(test.toString, "${amount}")
  }

  def test_appendAmount_3dp_BHD() {
    iBuilder.appendAmount()
    val test = iBuilder.toFormatter()
    val money = Money.of(CurrencyUnit.getInstance("BHD"), 6345345.735d)
    assertEquals(test.print(money), "6,345,345.735")
  }

  @DataProvider(name = "appendAmountLocalized")
  def data_appendAmountLocalized(): Array[Array[Any]] = {
    Array(Array(GBP_2_34, "2" + FR_DECIMAL + "34"), Array(GBP_23_45, "23" + FR_DECIMAL + "45"), Array(GBP_234_56, "234" + FR_DECIMAL + "56"), Array(GBP_2345_67, "2" + FR_GROUP + "345" + FR_DECIMAL + "67"), Array(GBP_1234567_89, "1" + FR_GROUP + "234" + FR_GROUP + "567" + FR_DECIMAL + 
      "89"), Array(GBP_1234_56789, "1" + FR_GROUP + "234" + FR_DECIMAL + "567" + FR_GROUP + 
      "89"), Array(GBP_MINUS_234_56, "-234" + FR_DECIMAL + "56"))
  }

  @Test(dataProvider = "appendAmountLocalized")
  def test_appendAmountLocalized(money: BigMoneyProvider, expected: String) {
    iBuilder.appendAmountLocalized()
    val test = iBuilder.toFormatter(Locale.FRANCE)
    assertEquals(test.print(money), expected)
    assertEquals(test.toString, "${amount}")
  }

  def test_appendAmountLocalized_GBP_1234_56789_US() {
    iBuilder.appendAmountLocalized()
    val test = iBuilder.toFormatter(Locale.US)
    assertEquals(test.print(GBP_1234_56789), "1,234.567,89")
    assertEquals(test.toString, "${amount}")
  }

  def test_appendAmountLocalized_JPY_2345() {
    iBuilder.appendAmountLocalized()
    val test = iBuilder.toFormatter(Locale.FRANCE)
    assertEquals(test.print(JPY_2345), "2" + FR_GROUP + "345")
    assertEquals(test.toString, "${amount}")
  }

  @Test(expectedExceptions = Array(classOf[NullPointerException]))
  def test_appendAmount_MoneyAmountStyle_null() {
    iBuilder.appendAmount(null.asInstanceOf[MoneyAmountStyle])
  }

  @DataProvider(name = "appendAmount_MoneyAmountStyle")
  def data_appendAmount_MoneyAmountStyle(): Array[Array[Any]] = {
    val noGrouping = MoneyAmountStyle.ASCII_DECIMAL_POINT_NO_GROUPING
    val group3Comma = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA
    val group3Space = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_SPACE
    val group3BeforeDp = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withGroupingStyle(GroupingStyle.BEFORE_DECIMAL_POINT)
    val group3CommaForceDp = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withForcedDecimalPoint(true)
    val group3CommaAbs = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withAbsValue(true)
    val group1Dash = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withGroupingSize(1)
      .withGroupingCharacter('-')
    val group2Dash = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withGroupingSize(2)
      .withGroupingCharacter('-')
    val group4CommaAt = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withGroupingSize(4)
      .withDecimalPointCharacter('@')
      .withForcedDecimalPoint(true)
    val letters = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withZeroCharacter('A')
    Array(Array(noGrouping, "2", "2"), Array(noGrouping, "2123456", "2123456"), Array(noGrouping, "2.34", "2.34"), Array(noGrouping, "23.34", "23.34"), Array(noGrouping, "234.34", "234.34"), Array(noGrouping, "2345.34", "2345.34"), Array(noGrouping, "23456.34", "23456.34"), Array(noGrouping, "234567.34", "234567.34"), Array(noGrouping, "2345678.34", "2345678.34"), Array(noGrouping, "2.345", "2.345"), Array(noGrouping, "2.3456", "2.3456"), Array(noGrouping, "2.34567", "2.34567"), Array(noGrouping, "2.345678", "2.345678"), Array(noGrouping, "2.3456789", "2.3456789"), Array(group3Comma, "2", "2"), Array(group3Comma, "2123456", "2,123,456"), Array(group3Comma, "2.34", "2.34"), Array(group3Comma, "23.34", "23.34"), Array(group3Comma, "234.34", "234.34"), Array(group3Comma, "2345.34", "2,345.34"), Array(group3Comma, "23456.34", "23,456.34"), Array(group3Comma, "234567.34", "234,567.34"), Array(group3Comma, "2345678.34", "2,345,678.34"), Array(group3Comma, "2.345", "2.345"), Array(group3Comma, "2.3456", "2.345,6"), Array(group3Comma, "2.34567", "2.345,67"), Array(group3Comma, "2.345678", "2.345,678"), Array(group3Comma, "2.3456789", "2.345,678,9"), Array(group3Space, "2", "2"), Array(group3Space, "2123456", "2 123 456"), Array(group3Space, "2.34", "2.34"), Array(group3Space, "23.34", "23.34"), Array(group3Space, "234.34", "234.34"), Array(group3Space, "2345.34", "2 345.34"), Array(group3Space, "23456.34", "23 456.34"), Array(group3Space, "234567.34", "234 567.34"), Array(group3Space, "2345678.34", "2 345 678.34"), Array(group3Space, "2.345", "2.345"), Array(group3Space, "2.3456", "2.345 6"), Array(group3Space, "2.34567", "2.345 67"), Array(group3Space, "2.345678", "2.345 678"), Array(group3Space, "2.3456789", "2.345 678 9"), Array(group3BeforeDp, "2", "2"), Array(group3BeforeDp, "2123456", "2,123,456"), Array(group3BeforeDp, "2.34", "2.34"), Array(group3BeforeDp, "23.34", "23.34"), Array(group3BeforeDp, "234.34", "234.34"), Array(group3BeforeDp, "2345.34", "2,345.34"), Array(group3BeforeDp, "23456.34", "23,456.34"), Array(group3BeforeDp, "234567.34", "234,567.34"), Array(group3BeforeDp, "2345678.34", "2,345,678.34"), Array(group3BeforeDp, "2.345", "2.345"), Array(group3BeforeDp, "2.3456", "2.3456"), Array(group3BeforeDp, "2.34567", "2.34567"), Array(group3BeforeDp, "2.345678", "2.345678"), Array(group3BeforeDp, "2.3456789", "2.3456789"), Array(group3CommaForceDp, "2", "2."), Array(group3CommaForceDp, "2123456", "2,123,456."), Array(group3CommaForceDp, "2.34", "2.34"), Array(group3CommaForceDp, "23.34", "23.34"), Array(group3CommaForceDp, "234.34", "234.34"), Array(group3CommaForceDp, "2345.34", "2,345.34"), Array(group3CommaForceDp, "23456.34", "23,456.34"), Array(group3CommaForceDp, "234567.34", "234,567.34"), Array(group3CommaForceDp, "2345678.34", "2,345,678.34"), Array(group3CommaForceDp, "2.345", "2.345"), Array(group3CommaForceDp, "2.3456", "2.345,6"), Array(group3CommaForceDp, "2.34567", "2.345,67"), Array(group3CommaForceDp, "2.345678", "2.345,678"), Array(group3CommaForceDp, "2.3456789", "2.345,678,9"), Array(group3CommaAbs, "2", "2"), Array(group3CommaAbs, "-2", "2"), Array(group3CommaAbs, "2123456", "2,123,456"), Array(group3CommaAbs, "-2123456", "2,123,456"), Array(group3CommaAbs, "-2.34", "2.34"), Array(group3CommaAbs, "-23.34", "23.34"), Array(group3CommaAbs, "-234.34", "234.34"), Array(group3CommaAbs, "-2345.34", "2,345.34"), Array(group3CommaAbs, "-23456.34", "23,456.34"), Array(group3CommaAbs, "-234567.34", "234,567.34"), Array(group3CommaAbs, "-2345678.34", "2,345,678.34"), Array(group3CommaAbs, "-2.345", "2.345"), Array(group3CommaAbs, "-2.3456", "2.345,6"), Array(group3CommaAbs, "-2.34567", "2.345,67"), Array(group3CommaAbs, "-2.345678", "2.345,678"), Array(group3CommaAbs, "-2.3456789", "2.345,678,9"), Array(group1Dash, "2", "2"), Array(group1Dash, "2123456", "2-1-2-3-4-5-6"), Array(group1Dash, "2.34", "2.3-4"), Array(group1Dash, "23.34", "2-3.3-4"), Array(group1Dash, "234.34", "2-3-4.3-4"), Array(group1Dash, "2345.34", "2-3-4-5.3-4"), Array(group1Dash, "23456.34", "2-3-4-5-6.3-4"), Array(group1Dash, "234567.34", "2-3-4-5-6-7.3-4"), Array(group1Dash, "2345678.34", "2-3-4-5-6-7-8.3-4"), Array(group1Dash, "2.345", "2.3-4-5"), Array(group1Dash, "2.3456", "2.3-4-5-6"), Array(group1Dash, "2.34567", "2.3-4-5-6-7"), Array(group1Dash, "2.345678", "2.3-4-5-6-7-8"), Array(group1Dash, "2.3456789", "2.3-4-5-6-7-8-9"), Array(group2Dash, "2", "2"), Array(group2Dash, "2123456", "2-12-34-56"), Array(group2Dash, "2.34", "2.34"), Array(group2Dash, "23.34", "23.34"), Array(group2Dash, "234.34", "2-34.34"), Array(group2Dash, "2345.34", "23-45.34"), Array(group2Dash, "23456.34", "2-34-56.34"), Array(group2Dash, "234567.34", "23-45-67.34"), Array(group2Dash, "2345678.34", "2-34-56-78.34"), Array(group2Dash, "2.345", "2.34-5"), Array(group2Dash, "2.3456", "2.34-56"), Array(group2Dash, "2.34567", "2.34-56-7"), Array(group2Dash, "2.345678", "2.34-56-78"), Array(group2Dash, "2.3456789", "2.34-56-78-9"), Array(group4CommaAt, "2", "2@"), Array(group4CommaAt, "2123456", "212,3456@"), Array(group4CommaAt, "2.34", "2@34"), Array(group4CommaAt, "23.34", "23@34"), Array(group4CommaAt, "234.34", "234@34"), Array(group4CommaAt, "2345.34", "2345@34"), Array(group4CommaAt, "23456.34", "2,3456@34"), Array(group4CommaAt, "234567.34", "23,4567@34"), Array(group4CommaAt, "2345678.34", "234,5678@34"), Array(group4CommaAt, "2.345", "2@345"), Array(group4CommaAt, "2.3456", "2@3456"), Array(group4CommaAt, "2.34567", "2@3456,7"), Array(group4CommaAt, "2.345678", "2@3456,78"), Array(group4CommaAt, "2.3456789", "2@3456,789"), Array(letters, "2", "C"), Array(letters, "2123456", "C,BCD,EFG"), Array(letters, "2.34", "C.DE"), Array(letters, "23.34", "CD.DE"), Array(letters, "234.34", "CDE.DE"), Array(letters, "2345.34", "C,DEF.DE"), Array(letters, "23456.34", "CD,EFG.DE"), Array(letters, "234567.34", "CDE,FGH.DE"), Array(letters, "2345678.34", "C,DEF,GHI.DE"), Array(letters, "2.345", "C.DEF"), Array(letters, "2.3456", "C.DEF,G"), Array(letters, "2.34567", "C.DEF,GH"), Array(letters, "2.345678", "C.DEF,GHI"), Array(letters, "2.3456789", "C.DEF,GHI,J"))
  }

  @Test(dataProvider = "appendAmount_MoneyAmountStyle")
  def test_appendAmount_MoneyAmountStyle_GBP(style: MoneyAmountStyle, amount: String, expected: String) {
    iBuilder.appendAmount(style)
    val test = iBuilder.toFormatter()
    val money = BigMoney.of(GBP, new BigDecimal(amount))
    assertEquals(test.print(money), expected)
    if (!style.isAbsValue) {
      assertEquals(test.parse(expected, 0).getAmount, money.getAmount)
    } else {
      assertEquals(test.parse(expected, 0).getAmount, money.getAmount.abs())
    }
  }

  @Test(dataProvider = "appendAmount_MoneyAmountStyle")
  def test_appendAmount_MoneyAmountStyle_JPY(style: MoneyAmountStyle, amount: String, expected: String) {
    iBuilder.appendAmount(style)
    val test = iBuilder.toFormatter()
    val money = BigMoney.of(JPY, new BigDecimal(amount))
    assertEquals(test.print(money), expected)
    if (!style.isAbsValue) {
      assertEquals(test.parse(expected, 0).getAmount, money.getAmount)
    } else {
      assertEquals(test.parse(expected, 0).getAmount, money.getAmount.abs())
    }
  }

  @Test(dataProvider = "appendAmount_MoneyAmountStyle")
  def test_appendAmount_MoneyAmountStyle_BHD(style: MoneyAmountStyle, amount: String, expected: String) {
    iBuilder.appendAmount(style)
    val test = iBuilder.toFormatter()
    val money = BigMoney.of(BHD, new BigDecimal(amount))
    assertEquals(test.print(money), expected)
    if (!style.isAbsValue) {
      assertEquals(test.parse(expected, 0).getAmount, money.getAmount)
    } else {
      assertEquals(test.parse(expected, 0).getAmount, money.getAmount.abs())
    }
  }

  @Test
  def test_appendAmount_MoneyAmountStyle_JPY_issue49() {
    val money = Money.parse("JPY 12")
    val style = MoneyAmountStyle.LOCALIZED_GROUPING
    val formatter = new MoneyFormatterBuilder().appendAmount(style).toFormatter()
      .withLocale(Locale.JAPAN)
    assertEquals(formatter.print(money), "12")
  }

  @DataProvider(name = "appendAmountExtendedGrouping")
  def data_appendAmountExtendedGrouping(): Array[Array[Any]] = {
    Array(Array(GBP_2_34, "2.34"), Array(GBP_23_45, "23.45"), Array(GBP_234_56, "234.56"), Array(GBP_2345_67, "2,345.67"), Array(GBP_1234567_89, "12,34,567.89"), Array(GBP_1234_56789, "1,234.567,89"), Array(GBP_1234567891234_1234567891, "12,34,56,78,91,234.123,45,67,89,1"), Array(GBP_MINUS_234_56, "-234.56"))
  }

  @Test(dataProvider = "appendAmountExtendedGrouping")
  def test_appendAmount_parseExtendedGroupingSize(money: BigMoneyProvider, expected: String) {
    iBuilder.appendAmount()
    val test = new MoneyFormatterBuilder().appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withExtendedGroupingSize(2))
      .toFormatter()
    assertEquals(test.print(money), expected)
    assertEquals(test.toString, "${amount}")
  }

  @Test
  def test_appendAmount_parseExcessGrouping() {
    val expected = BigMoney.parse("GBP 12123.4567")
    val f = new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" ")
      .appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA)
      .toFormatter()
    val money = f.parseBigMoney("GBP 12,1,2,3,.,45,6,7")
    assertEquals(money, expected)
  }

  def test_append_MoneyPrinterMoneyParser_printer() {
    val printer = new MoneyPrinter() {

      override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
        appendable.append("HELLO")
      }
    }
    iBuilder.append(printer, null)
    val test = iBuilder.toFormatter()
    assertEquals(test.isPrinter, true)
    assertEquals(test.isParser, false)
    assertEquals(test.print(JPY_2345), "HELLO")
    assertEquals(test.toString.startsWith("org.joda.money.format.TestMoneyFormatterBuilder$"), true)
  }

  def test_append_MoneyPrinterMoneyParser_parser() {
    val parser = new MoneyParser() {

      override def parse(context: MoneyParseContext) {
        context.setAmount(JPY_2345.getAmount)
        context.setCurrency(JPY_2345.getCurrencyUnit)
      }
    }
    iBuilder.append(null, parser)
    val test = iBuilder.toFormatter()
    assertEquals(test.isPrinter, false)
    assertEquals(test.isParser, true)
    assertEquals(test.parseMoney(""), JPY_2345)
    assertEquals(test.toString.startsWith("org.joda.money.format.TestMoneyFormatterBuilder$"), true)
  }

  def test_append_MoneyPrinter_nullMoneyPrinter_nullMoneyParser() {
    iBuilder.append(null.asInstanceOf[MoneyPrinter], null.asInstanceOf[MoneyParser])
    val test = iBuilder.toFormatter()
    assertEquals(test.isPrinter, false)
    assertEquals(test.isParser, false)
    assertEquals(test.toString, "")
  }

  def test_append_MoneyFormatter() {
    val f1 = new MoneyFormatterBuilder().appendAmount().toFormatter()
    val f2 = new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" ")
      .append(f1)
      .toFormatter()
    assertEquals(f2.print(GBP_2345_67), "GBP 2,345.67")
  }

  def test_appendSigned_PN() {
    val pos = new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" ")
      .appendAmount()
      .toFormatter()
    val neg = new MoneyFormatterBuilder().appendLiteral("(").appendCurrencyCode()
      .appendLiteral(" ")
      .appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withAbsValue(true))
      .appendLiteral(")")
      .toFormatter()
    val f = new MoneyFormatterBuilder().appendSigned(pos, neg).toFormatter()
    assertEquals(f.toString, "PositiveZeroNegative(${code}' '${amount},${code}' '${amount},'('${code}' '${amount}')')")
    assertEquals(f.print(GBP_234_56), "GBP 234.56")
    assertEquals(f.print(Money.zero(GBP)), "GBP 0.00")
    assertEquals(f.print(GBP_MINUS_234_56), "(GBP 234.56)")
    assertEquals(f.parseMoney("GBP 234.56"), GBP_234_56)
    assertEquals(f.parseMoney("GBP 0"), Money.zero(GBP))
    assertEquals(f.parseMoney("(GBP 234.56)"), GBP_MINUS_234_56)
    val context = f.parse("X", 0)
    assertEquals(context.getIndex, 0)
    assertEquals(context.getErrorIndex, 0)
  }

  def test_appendSigned_PZN() {
    val pos = new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" ")
      .appendAmount()
      .toFormatter()
    val zro = new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" -")
      .toFormatter()
    val neg = new MoneyFormatterBuilder().appendLiteral("(").appendCurrencyCode()
      .appendLiteral(" ")
      .appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withAbsValue(true))
      .appendLiteral(")")
      .toFormatter()
    val f = new MoneyFormatterBuilder().appendSigned(pos, zro, neg)
      .toFormatter()
    assertEquals(f.print(GBP_234_56), "GBP 234.56")
    assertEquals(f.print(Money.zero(GBP)), "GBP -")
    assertEquals(f.print(GBP_MINUS_234_56), "(GBP 234.56)")
    assertEquals(f.parseMoney("GBP 234.56"), GBP_234_56)
    assertEquals(f.parseMoney("GBP -234.56"), GBP_MINUS_234_56)
    assertEquals(f.parseMoney("GBP -"), Money.zero(GBP))
    assertEquals(f.parseMoney("(GBP 234.56)"), GBP_MINUS_234_56)
    assertEquals(f.parseMoney("(GBP -234.56)"), GBP_MINUS_234_56)
  }

  def test_appendSigned_PZN_edgeCases() {
    val pos = new MoneyFormatterBuilder().appendAmount().toFormatter()
    val zro = new MoneyFormatterBuilder().appendAmount().appendLiteral(" (zero)")
      .toFormatter()
    val neg = new MoneyFormatterBuilder().appendLiteral("(").appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withAbsValue(true))
      .appendLiteral(")")
      .toFormatter()
    val f = new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" ")
      .appendSigned(pos, zro, neg)
      .toFormatter()
    assertEquals(f.print(GBP_234_56), "GBP 234.56")
    assertEquals(f.print(BigMoney.zero(GBP).withScale(2)), "GBP 0.00 (zero)")
    assertEquals(f.print(GBP_MINUS_234_56), "GBP (234.56)")
    assertEquals(f.parseBigMoney("GBP 234.56"), GBP_234_56.toBigMoney())
    assertEquals(f.parseBigMoney("GBP 0.00 (zero)"), BigMoney.zero(GBP).withScale(2))
    assertEquals(f.parseBigMoney("GBP 1.23 (zero)"), BigMoney.zero(GBP))
    assertEquals(f.parseBigMoney("GBP (234.56)"), GBP_MINUS_234_56.toBigMoney())
  }

  def test_toFormatter_defaultLocale() {
    val test = iBuilder.toFormatter()
    assertEquals(test.getLocale, TEST_GB_LOCALE)
  }

  def test_toFormatter_Locale() {
    val test = iBuilder.toFormatter(TEST_FR_LOCALE)
    assertEquals(test.getLocale, TEST_FR_LOCALE)
  }
}
