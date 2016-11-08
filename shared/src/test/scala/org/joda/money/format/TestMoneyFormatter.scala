package org.joda.money.format

import org.testng.Assert.assertEquals
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.math.BigDecimal
import java.text.ParsePosition
import java.util.Locale
import org.joda.money.BigMoney
import org.joda.money.BigMoneyProvider
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import TestMoneyFormatter._
//remove if not needed
import scala.collection.JavaConversions._

object TestMoneyFormatter {

  private val cCachedLocale = Locale.getDefault

  private val TEST_GB_LOCALE = new Locale("en", "GB", "TEST")

  private val TEST_FR_LOCALE = new Locale("fr", "FR", "TEST")

  private val MONEY_GBP_12_34 = Money.parse("GBP 12.34")

  private class IOAppendable extends Appendable {

    override def append(csq: CharSequence, start: Int, end: Int): Appendable = throw new IOException()

    override def append(c: Char): Appendable = throw new IOException()

    override def append(csq: CharSequence): Appendable = throw new IOException()
  }
}

/**
 * Test MoneyFormatter.
 */
@Test
class TestMoneyFormatter {

  private var iPrintTest: MoneyFormatter = _

  private var iCannotPrint: MoneyFormatter = _

  private var iParseTest: MoneyFormatter = _

  private var iCannotParse: MoneyFormatter = _

  @BeforeMethod
  def beforeMethod() {
    Locale.setDefault(TEST_GB_LOCALE)
    iPrintTest = new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" hello")
      .toFormatter()
    iCannotPrint = new MoneyFormatterBuilder().append(null, new MoneyParser() {

      override def parse(context: MoneyParseContext) {
      }
    })
      .toFormatter()
    iParseTest = new MoneyFormatterBuilder().appendAmountLocalized()
      .appendLiteral(" ")
      .appendCurrencyCode()
      .toFormatter()
    iCannotParse = new MoneyFormatterBuilder().append(new MoneyPrinter() {

      override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
      }
    }, null)
      .toFormatter()
  }

  @AfterMethod
  def afterMethod() {
    Locale.setDefault(cCachedLocale)
    iPrintTest = null
  }

  def test_serialization() {
    val a = iPrintTest
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(a)
    oos.close()
    val ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))
    val input = ois.readObject().asInstanceOf[MoneyFormatter]
    val value = MONEY_GBP_12_34
    assertEquals(input.print(value), a.print(value))
  }

  def test_getLocale() {
    assertEquals(iPrintTest.getLocale, TEST_GB_LOCALE)
  }

  def test_withLocale() {
    val test = iPrintTest.withLocale(TEST_FR_LOCALE)
    assertEquals(iPrintTest.getLocale, TEST_GB_LOCALE)
    assertEquals(test.getLocale, TEST_FR_LOCALE)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_withLocale_nullLocale() {
    iPrintTest.withLocale(null.asInstanceOf[Locale])
  }

  def test_print_BigMoneyProvider() {
    assertEquals(iPrintTest.print(MONEY_GBP_12_34), "GBP hello")
  }

  @Test(expectedExceptions = classOf[UnsupportedOperationException])
  def test_print_BigMoneyProvider_cannotPrint() {
    iCannotPrint.print(MONEY_GBP_12_34)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_print_BigMoneyProvider_nullBigMoneyProvider() {
    iPrintTest.print(null.asInstanceOf[BigMoneyProvider])
  }

  def test_print_AppendableBigMoneyProvider() {
    val buf = new StringBuilder()
    iPrintTest.print(buf, MONEY_GBP_12_34)
    assertEquals(buf.toString, "GBP hello")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_print_AppendableBigMoneyProvider_IOException() {
    val appendable = new IOAppendable()
    try {
      iPrintTest.print(appendable, MONEY_GBP_12_34)
    } catch {
      case ex: MoneyFormatException => {
        assertEquals(ex.getCause.getClass, classOf[IOException])
        throw ex
      }
    }
  }

  @Test(expectedExceptions = classOf[UnsupportedOperationException])
  def test_print_AppendableBigMoneyProvider_cannotPrint() {
    iCannotPrint.print(new StringBuilder(), MONEY_GBP_12_34)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_print_AppendableBigMoneyProvider_nullAppendable() {
    iPrintTest.print(null.asInstanceOf[Appendable], MONEY_GBP_12_34)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_print_AppendableBigMoneyProvider_nullBigMoneyProvider() {
    iPrintTest.print(new StringBuilder(), null.asInstanceOf[BigMoneyProvider])
  }

  def test_printIO_AppendableBigMoneyProvider() {
    val buf = new StringBuilder()
    iPrintTest.printIO(buf, MONEY_GBP_12_34)
    assertEquals(buf.toString, "GBP hello")
  }

  @Test(expectedExceptions = classOf[IOException])
  def test_printIO_AppendableBigMoneyProvider_IOException() {
    val appendable = new IOAppendable()
    iPrintTest.printIO(appendable, MONEY_GBP_12_34)
  }

  @Test(expectedExceptions = classOf[UnsupportedOperationException])
  def test_printIO_AppendableBigMoneyProvider_cannotPrint() {
    iCannotPrint.printIO(new StringBuilder(), MONEY_GBP_12_34)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_printIO_AppendableBigMoneyProvider_nullAppendable() {
    iPrintTest.printIO(null.asInstanceOf[Appendable], MONEY_GBP_12_34)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_printIO_AppendableBigMoneyProvider_nullBigMoneyProvider() {
    iPrintTest.printIO(new StringBuilder(), null.asInstanceOf[BigMoneyProvider])
  }

  def test_parseBigMoney_CharSequence() {
    val input = new StringBuilder("12.34 GBP")
    val test = iParseTest.parseBigMoney(input)
    assertEquals(test, MONEY_GBP_12_34.toBigMoney())
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseBigMoney_CharSequence_invalidCurrency() {
    iParseTest.parseBigMoney("12.34 GBX")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseBigMoney_CharSequence_notFullyParsed() {
    iParseTest.parseBigMoney("12.34 GBP X")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseBigMoney_CharSequence_incomplete() {
    iParseTest.parseBigMoney("12.34 GBP ")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseBigMoney_CharSequence_incompleteLongText() {
    iParseTest.parseBigMoney("12.34 GBP ABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABABAB")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseBigMoney_CharSequence_incompleteEmptyParser() {
    iCannotPrint.parseBigMoney("12.34 GBP")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseBigMoney_CharSequence_missingCurrency() {
    val f = new MoneyFormatterBuilder().appendAmount().toFormatter()
    f.parseBigMoney("12.34")
  }

  @Test(expectedExceptions = classOf[UnsupportedOperationException])
  def test_parseBigMoney_CharSequence_cannotParse() {
    iCannotParse.parseBigMoney(new StringBuilder())
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_parseBigMoney_CharSequence_nullCharSequence() {
    iParseTest.parseBigMoney(null.asInstanceOf[CharSequence])
  }

  def test_parseMoney_CharSequence() {
    val input = new StringBuilder("12.34 GBP")
    val test = iParseTest.parseMoney(input)
    assertEquals(test, MONEY_GBP_12_34)
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseMoney_CharSequence_invalidCurrency() {
    iParseTest.parseMoney("12.34 GBX")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseMoney_CharSequence_notFullyParsed() {
    iParseTest.parseMoney("12.34 GBP X")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseMoney_CharSequence_incomplete() {
    iCannotPrint.parseMoney("12.34 GBP")
  }

  @Test(expectedExceptions = classOf[UnsupportedOperationException])
  def test_parseMoney_CharSequence_cannotParse() {
    iCannotParse.parseMoney(new StringBuilder())
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_parseMoney_CharSequence_nullCharSequence() {
    iParseTest.parseMoney(null.asInstanceOf[CharSequence])
  }

  @DataProvider(name = "parse")
  def data_parse(): Array[Array[Any]] = {
    Array(Array("12.34 GBP", MONEY_GBP_12_34.getAmount, MONEY_GBP_12_34.getCurrencyUnit, 9, -1, false, true, true), Array("1,2.34 GBP", MONEY_GBP_12_34.getAmount, MONEY_GBP_12_34.getCurrencyUnit, 10, -1, false, true, true), Array("12,.34 GBP", MONEY_GBP_12_34.getAmount, MONEY_GBP_12_34.getCurrencyUnit, 10, -1, false, true, true), Array("12.,34 GBP", MONEY_GBP_12_34.getAmount, MONEY_GBP_12_34.getCurrencyUnit, 10, -1, false, true, true), Array("12.3,4 GBP", MONEY_GBP_12_34.getAmount, MONEY_GBP_12_34.getCurrencyUnit, 10, -1, false, true, true), Array(".12 GBP", BigDecimal.valueOf(12, 
      2), MONEY_GBP_12_34.getCurrencyUnit, 7, -1, false, true, true), Array("12. GBP", BigDecimal.valueOf(12), MONEY_GBP_12_34.getCurrencyUnit, 7, -1, false, true, true), Array("12,34 GBP", BigDecimal.valueOf(1234), MONEY_GBP_12_34.getCurrencyUnit, 9, -1, false, true, true), Array("-12.34 GBP", BigDecimal.valueOf(-1234, 
      2), CurrencyUnit.GBP, 10, -1, false, true, true), Array("+12.34 GBP", BigDecimal.valueOf(1234, 
      2), CurrencyUnit.GBP, 10, -1, false, true, true), Array("12.34 GB", BigDecimal.valueOf(1234, 2), null, 6, 6, true, false, false), Array(",12.34 GBP", null, null, 0, 0, true, false, false), Array("12..34 GBP", BigDecimal.valueOf(12), null, 3, 3, true, false, false), Array("12,,34 GBP", BigDecimal.valueOf(12), null, 2, 2, true, false, false), Array("12.34 GBX", MONEY_GBP_12_34.getAmount, null, 6, 6, true, false, false), Array("12.34 GBPX", MONEY_GBP_12_34.getAmount, MONEY_GBP_12_34.getCurrencyUnit, 9, -1, false, false, true))
  }

  @Test(dataProvider = "parse")
  def test_parse_CharSequenceInt(str: String, 
      amount: BigDecimal, 
      currency: CurrencyUnit, 
      index: Int, 
      errorIndex: Int, 
      error: Boolean, 
      fullyParsed: Boolean, 
      complete: Boolean) {
    val input = new StringBuilder(str)
    val test = iParseTest.parse(input, 0)
    assertEquals(test.getAmount, amount)
    assertEquals(test.getCurrency, currency)
    assertEquals(test.getIndex, index)
    assertEquals(test.getErrorIndex, errorIndex)
    assertEquals(test.getText.toString, str)
    assertEquals(test.getTextLength, str.length)
    assertEquals(test.isError, error)
    assertEquals(test.isFullyParsed, fullyParsed)
    assertEquals(test.isComplete, complete)
    val pp = new ParsePosition(index)
    pp.setErrorIndex(errorIndex)
    assertEquals(test.toParsePosition(), pp)
  }

  def test_parse_CharSequenceInt_incomplete() {
    val test = iCannotPrint.parse("12.34 GBP", 0)
    assertEquals(test.getAmount, null)
    assertEquals(test.getCurrency, null)
    assertEquals(test.getIndex, 0)
    assertEquals(test.getErrorIndex, -1)
    assertEquals(test.getText.toString, "12.34 GBP")
    assertEquals(test.getTextLength, 9)
    assertEquals(test.isError, false)
    assertEquals(test.isFullyParsed, false)
    assertEquals(test.isComplete, false)
  }

  def test_parse_CharSequenceInt_continueAfterDoubleDecimal() {
    val f = new MoneyFormatterBuilder().appendAmountLocalized()
      .appendLiteral(".")
      .appendCurrencyCode()
      .toFormatter()
    val test = f.parse("12..GBP", 0)
    assertEquals(test.getAmount, BigDecimal.valueOf(12))
    assertEquals(test.getCurrency, CurrencyUnit.of("GBP"))
    assertEquals(test.getIndex, 7)
    assertEquals(test.getErrorIndex, -1)
    assertEquals(test.getText.toString, "12..GBP")
    assertEquals(test.getTextLength, 7)
    assertEquals(test.isError, false)
    assertEquals(test.isFullyParsed, true)
    assertEquals(test.isComplete, true)
  }

  def test_parse_CharSequenceInt_continueAfterSingleComma() {
    val f = new MoneyFormatterBuilder().appendAmountLocalized()
      .appendLiteral(",")
      .appendCurrencyCode()
      .toFormatter()
    val test = f.parse("12,GBP", 0)
    assertEquals(test.getAmount, BigDecimal.valueOf(12))
    assertEquals(test.getCurrency, CurrencyUnit.of("GBP"))
    assertEquals(test.getIndex, 6)
    assertEquals(test.getErrorIndex, -1)
    assertEquals(test.getText.toString, "12,GBP")
    assertEquals(test.getTextLength, 6)
    assertEquals(test.isError, false)
    assertEquals(test.isFullyParsed, true)
    assertEquals(test.isComplete, true)
  }

  def test_parse_CharSequenceInt_continueAfterDoubleComma() {
    val f = new MoneyFormatterBuilder().appendAmountLocalized()
      .appendLiteral(",,")
      .appendCurrencyCode()
      .toFormatter()
    val test = f.parse("12,,GBP", 0)
    assertEquals(test.getAmount, BigDecimal.valueOf(12))
    assertEquals(test.getCurrency, CurrencyUnit.of("GBP"))
    assertEquals(test.getIndex, 7)
    assertEquals(test.getErrorIndex, -1)
    assertEquals(test.getText.toString, "12,,GBP")
    assertEquals(test.getTextLength, 7)
    assertEquals(test.isError, false)
    assertEquals(test.isFullyParsed, true)
    assertEquals(test.isComplete, true)
  }

  @Test(expectedExceptions = classOf[UnsupportedOperationException])
  def test_parse_CharSequenceInt_cannotParse() {
    iCannotParse.parse(new StringBuilder(), 0)
  }

  @Test(expectedExceptions = classOf[NullPointerException])
  def test_parse_CharSequenceInt_nullCharSequence() {
    iParseTest.parse(null.asInstanceOf[CharSequence], 0)
  }

  @Test(expectedExceptions = classOf[IndexOutOfBoundsException])
  def test_parse_CharSequenceInt_startIndexTooSmall() {
    iParseTest.parse("", -1)
  }

  @Test(expectedExceptions = classOf[IndexOutOfBoundsException])
  def test_parse_CharSequenceInt_startIndexTooBig() {
    iParseTest.parse("", 1)
  }

  def test_printParse_zeroChar() {
    val style = MoneyAmountStyle.ASCII_DECIMAL_POINT_GROUP3_COMMA.withZeroCharacter('A')
    val f = new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" ")
      .appendAmount(style)
      .toFormatter()
    assertEquals(f.print(MONEY_GBP_12_34), "GBP BC.DE")
    assertEquals(f.parseMoney("GBP BC.DE"), MONEY_GBP_12_34)
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseMoney_notFullyParsed() {
    iParseTest.parseMoney("GBP hello notfullyparsed")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseMoney_noAmount() {
    iParseTest.parseMoney("GBP hello")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseBigMoney_notFullyParsed() {
    iParseTest.parseBigMoney("GBP hello notfullyparsed")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parseBigMoney_noAmount() {
    iParseTest.parseBigMoney("GBP hello")
  }

  @Test(expectedExceptions = classOf[MoneyFormatException])
  def test_parse_notFullyParsed() {
    val context = iParseTest.parse("GBP hello notfullyparsed", 1)
    context.toBigMoney()
  }

  def test_toString() {
    assertEquals(iPrintTest.toString, "${code}' hello'")
  }

  def test_toString_differentPrinterParser() {
    val printer = new MoneyPrinter() {

      override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
      }

      override def toString(): String = return "A"
    }
    val parser = new MoneyParser() {

      override def parse(context: MoneyParseContext) {
      }

      override def toString(): String = return "B"
    }
    val f = new MoneyFormatterBuilder().append(printer, parser)
      .toFormatter()
    assertEquals(f.toString, "A:B")
  }
}
