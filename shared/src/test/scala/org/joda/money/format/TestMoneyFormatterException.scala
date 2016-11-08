package org.joda.money.format

import java.io.IOException
import org.testng.annotations.Test
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Test MoneyFormatterException.
 */
@Test
class TestMoneyFormatterException {

  @Test(expectedExceptions = classOf[IOException])
  def test_MoneyFormatException_IOException_notRethrown() {
    val test = new MoneyFormatException("Error", new IOException("Inner"))
    test.rethrowIOException()
  }

  def test_MoneyFormatException_nonIOException_notRethrown() {
    val test = new MoneyFormatException("Error", new IllegalStateException("Inner"))
    test.rethrowIOException()
  }
}
