package org.joda.money

import java.io.Externalizable
import java.io.IOException
import java.io.InvalidClassException
import java.io.InvalidObjectException
import java.io.ObjectInput
import java.io.ObjectOutput
import java.io.StreamCorruptedException
import java.math.BigDecimal
import java.math.BigInteger
import Ser._
//remove if not needed
import scala.collection.JavaConversions._

object Ser {

  /**
   Type for BigMoney.
   */
  val BIG_MONEY: Byte = 'B'

  /**
   Type for Money.
   */
  val MONEY: Byte = 'M'

  /**
   Type for CurrencyUnit.
   */
  val CURRENCY_UNIT: Byte = 'C'
}

/**
 * A package scoped class used to manage serialization efficiently.
 * <p>
 * This class is mutable and intended for use by a single thread.
 */
class Ser extends Externalizable {

  /**
   The type.
   */
  private var `type`: Byte = _

  /**
   The data object.
   */
  private var `object`: AnyRef = _

  /**
   * Constructor for package.
   *
   * @param type  the type
   * @param object  the object
   */
  def this(`type`: Byte, `object`: AnyRef) {
    this()
    this.`type` = `type`
    this.`object` = `object`
  }

  /**
   * Outputs the data.
   *
   * @serialData One byte type code, then data specific to the type.
   * @param out  the output stream
   * @throws IOException if an error occurs
   */
  override def writeExternal(out: ObjectOutput) {
    out.writeByte(`type`)
    `type` match {
      case BIG_MONEY => {
        val obj = `object`.asInstanceOf[BigMoney]
        writeBigMoney(out, obj)
        return
      }
      case MONEY => {
        val obj = `object`.asInstanceOf[Money]
        writeBigMoney(out, obj.toBigMoney())
        return
      }
      case CURRENCY_UNIT => {
        val obj = `object`.asInstanceOf[CurrencyUnit]
        writeCurrency(out, obj)
        return
      }
    }
    throw new InvalidClassException("Joda-Money bug: Serialization broken")
  }

  private def writeBigMoney(out: ObjectOutput, obj: BigMoney) {
    writeCurrency(out, obj.getCurrencyUnit)
    val bytes = obj.getAmount.unscaledValue().toByteArray()
    out.writeInt(bytes.length)
    out.write(bytes)
    out.writeInt(obj.getScale)
  }

  private def writeCurrency(out: ObjectOutput, obj: CurrencyUnit) {
    out.writeUTF(obj.getCode)
    out.writeShort(obj.getNumericCode)
    out.writeShort(obj.getDefaultFractionDigits)
  }

  /**
   * Outputs the data.
   *
   * @param in  the input stream
   * @throws IOException if an error occurs
   */
  override def readExternal(in: ObjectInput) {
    `type` = in.readByte()
    `type` match {
      case BIG_MONEY => {
        `object` = readBigMoney(in)
        return
      }
      case MONEY => {
        `object` = new Money(readBigMoney(in))
        return
      }
      case CURRENCY_UNIT => {
        `object` = readCurrency(in)
        return
      }
    }
    throw new StreamCorruptedException("Serialization input has invalid type")
  }

  private def readBigMoney(in: ObjectInput): BigMoney = {
    val currency = readCurrency(in)
    val bytes = Array.ofDim[Byte](in.readInt())
    in.readFully(bytes)
    val bd = new BigDecimal(new BigInteger(bytes), in.readInt())
    val bigMoney = new BigMoney(currency, bd)
    bigMoney
  }

  private def readCurrency(in: ObjectInput): CurrencyUnit = {
    val code = in.readUTF()
    val singletonCurrency = CurrencyUnit.of(code)
    if (singletonCurrency.getNumericCode != in.readShort()) {
      throw new InvalidObjectException("Deserialization found a mismatch in the numeric code for currency " + 
        code)
    }
    if (singletonCurrency.getDefaultFractionDigits != in.readShort()) {
      throw new InvalidObjectException("Deserialization found a mismatch in the decimal places for currency " + 
        code)
    }
    singletonCurrency
  }

  /**
   * Returns the object that will replace this one.
   *
   * @return the read object, should never be null
   */
  private def readResolve(): AnyRef = `object`
}
