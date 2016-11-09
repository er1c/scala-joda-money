package org.joda.money

import org.scalatest.testng.TestNGSuite
import org.testng.Assert._
import org.testng.annotations.{AfterMethod, BeforeMethod, DataProvider, Test}
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Test IllegalCurrencyException.
 */
@Test
class TestIllegalCurrencyException extends TestNGSuite {

  def test_String() {
    val test = new IllegalCurrencyException("PROBLEM")
    assertEquals(test.getMessage, "PROBLEM")
    assertEquals(test.getCause, null)
  }

  def test_String_nullString() {
    val test = new IllegalCurrencyException(null)
    assertEquals(test.getMessage, null)
    assertEquals(test.getCause, null)
  }
}
