package org.joda.money

//remove if not needed
import scala.collection.JavaConversions._

object MoneyUtils {

  /**
   * Validates that the object specified is not null.
   *
   * @param object  the object to check, not null
   * @throws NullPointerException if the input value is null
   */
  def checkNotNull(`object`: AnyRef, message: String) {
    if (`object` == null) {
      throw new NullPointerException(message)
    }
  }

  /**
   * Checks if the monetary value is zero, treating null as zero.
   * <p>
   * This method accepts any implementation of {@code BigMoneyProvider}.
   *
   * @param moneyProvider  the money to check, null returns zero
   * @return true if the money is null or zero
   */
  def isZero(moneyProvider: BigMoneyProvider): Boolean = {
    (moneyProvider == null || moneyProvider.toBigMoney().isZero)
  }

  /**
   * Checks if the monetary value is positive and non-zero, treating null as zero.
   * <p>
   * This method accepts any implementation of {@code BigMoneyProvider}.
   *
   * @param moneyProvider  the money to check, null returns false
   * @return true if the money is non-null and positive
   */
  def isPositive(moneyProvider: BigMoneyProvider): Boolean = {
    (moneyProvider != null && moneyProvider.toBigMoney().isPositive)
  }

  /**
   * Checks if the monetary value is positive or zero, treating null as zero.
   * <p>
   * This method accepts any implementation of {@code BigMoneyProvider}.
   *
   * @param moneyProvider  the money to check, null returns true
   * @return true if the money is null, zero or positive
   */
  def isPositiveOrZero(moneyProvider: BigMoneyProvider): Boolean = {
    (moneyProvider == null || moneyProvider.toBigMoney().isPositiveOrZero)
  }

  /**
   * Checks if the monetary value is negative and non-zero, treating null as zero.
   * <p>
   * This method accepts any implementation of {@code BigMoneyProvider}.
   *
   * @param moneyProvider  the money to check, null returns false
   * @return true if the money is non-null and negative
   */
  def isNegative(moneyProvider: BigMoneyProvider): Boolean = {
    (moneyProvider != null && moneyProvider.toBigMoney().isNegative)
  }

  /**
   * Checks if the monetary value is negative or zero, treating null as zero.
   * <p>
   * This method accepts any implementation of {@code BigMoneyProvider}.
   *
   * @param moneyProvider  the money to check, null returns true
   * @return true if the money is null, zero or negative
   */
  def isNegativeOrZero(moneyProvider: BigMoneyProvider): Boolean = {
    (moneyProvider == null || moneyProvider.toBigMoney().isNegativeOrZero)
  }

  /**
   * Finds the maximum {@code Money} value, handing null.
   * <p>
   * This returns the greater of money1 or money2 where null is ignored.
   * If both input values are null, then null is returned.
   *
   * @param money1  the first money instance, null returns money2
   * @param money2  the first money instance, null returns money1
   * @return the maximum value, null if both inputs are null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def max(money1: Money, money2: Money): Money = {
    if (money1 == null) {
      return money2
    }
    if (money2 == null) {
      return money1
    }
    if (money1.compareTo(money2) > 0) money1 else money2
  }

  /**
   * Finds the minimum {@code Money} value, handing null.
   * <p>
   * This returns the greater of money1 or money2 where null is ignored.
   * If both input values are null, then null is returned.
   *
   * @param money1  the first money instance, null returns money2
   * @param money2  the first money instance, null returns money1
   * @return the minimum value, null if both inputs are null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def min(money1: Money, money2: Money): Money = {
    if (money1 == null) {
      return money2
    }
    if (money2 == null) {
      return money1
    }
    if (money1.compareTo(money2) < 0) money1 else money2
  }

  /**
   * Adds two {@code Money} objects, handling null.
   * <p>
   * This returns {@code money1 + money2} where null is ignored.
   * If both input values are null, then null is returned.
   *
   * @param money1  the first money instance, null returns money2
   * @param money2  the first money instance, null returns money1
   * @return the total, where null is ignored, null if both inputs are null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def add(money1: Money, money2: Money): Money = {
    if (money1 == null) {
      return money2
    }
    if (money2 == null) {
      return money1
    }
    money1.plus(money2)
  }

  /**
   * Subtracts the second {@code Money} from the first, handling null.
   * <p>
   * This returns {@code money1 - money2} where null is ignored.
   * If both input values are null, then null is returned.
   *
   * @param money1  the first money instance, null treated as zero
   * @param money2  the first money instance, null returns money1
   * @return the total, where null is ignored, null if both inputs are null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def subtract(money1: Money, money2: Money): Money = {
    if (money2 == null) {
      return money1
    }
    if (money1 == null) {
      return money2.negated()
    }
    money1.minus(money2)
  }

  /**
   * Finds the maximum {@code BigMoney} value, handing null.
   * <p>
   * This returns the greater of money1 or money2 where null is ignored.
   * If both input values are null, then null is returned.
   *
   * @param money1  the first money instance, null returns money2
   * @param money2  the first money instance, null returns money1
   * @return the maximum value, null if both inputs are null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def max(money1: BigMoney, money2: BigMoney): BigMoney = {
    if (money1 == null) {
      return money2
    }
    if (money2 == null) {
      return money1
    }
    if (money1.compareTo(money2) > 0) money1 else money2
  }

  /**
   * Finds the minimum {@code BigMoney} value, handing null.
   * <p>
   * This returns the greater of money1 or money2 where null is ignored.
   * If both input values are null, then null is returned.
   *
   * @param money1  the first money instance, null returns money2
   * @param money2  the first money instance, null returns money1
   * @return the minimum value, null if both inputs are null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def min(money1: BigMoney, money2: BigMoney): BigMoney = {
    if (money1 == null) {
      return money2
    }
    if (money2 == null) {
      return money1
    }
    if (money1.compareTo(money2) < 0) money1 else money2
  }

  /**
   * Adds two {@code BigMoney} objects, handling null.
   * <p>
   * This returns {@code money1 + money2} where null is ignored.
   * If both input values are null, then null is returned.
   *
   * @param money1  the first money instance, null returns money2
   * @param money2  the first money instance, null returns money1
   * @return the total, where null is ignored, null if both inputs are null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def add(money1: BigMoney, money2: BigMoney): BigMoney = {
    if (money1 == null) {
      return money2
    }
    if (money2 == null) {
      return money1
    }
    money1.plus(money2)
  }

  /**
   * Subtracts the second {@code BigMoney} from the first, handling null.
   * <p>
   * This returns {@code money1 - money2} where null is ignored.
   * If both input values are null, then null is returned.
   *
   * @param money1  the first money instance, null treated as zero
   * @param money2  the first money instance, null returns money1
   * @return the total, where null is ignored, null if both inputs are null
   * @throws CurrencyMismatchException if the currencies differ
   */
  def subtract(money1: BigMoney, money2: BigMoney): BigMoney = {
    if (money2 == null) {
      return money1
    }
    if (money1 == null) {
      return money2.negated()
    }
    money1.minus(money2)
  }
}
