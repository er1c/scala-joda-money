package org.joda.money

import org.testng.Assert.assertEquals
import org.testng.annotations.Test
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Test IllegalCurrencyException.
 */
@Test
class TestIllegalCurrencyException {

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
