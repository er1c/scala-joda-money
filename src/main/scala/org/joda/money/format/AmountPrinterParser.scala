package org.joda.money.format

import java.io.IOException
import java.io.Serializable
import java.math.BigDecimal
import org.joda.money.BigMoney
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Prints and parses the amount part of the money.
 * <p>
 * This class is immutable and thread-safe.
 */
@SerialVersionUID(1L)
class AmountPrinterParser(val style: MoneyAmountStyle) extends MoneyPrinter with MoneyParser with Serializable {

  override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
    val activeStyle = style.localize(context.getLocale)
    if (money.isNegative) {
      money = money.negated()
      if (!activeStyle.isAbsValue) {
        appendable.append(activeStyle.getNegativeSignCharacter)
      }
    }
    var str = money.getAmount.toPlainString()
    val zeroChar = activeStyle.getZeroCharacter
    if (zeroChar != '0') {
      val diff = zeroChar - '0'
      val zeroConvert = new StringBuilder(str)
      for (i <- 0 until str.length) {
        val ch = str.charAt(i)
        if (ch >= '0' && ch <= '9') {
          zeroConvert.setCharAt(i, (ch + diff).toChar)
        }
      }
      str = zeroConvert.toString
    }
    val decPoint = str.indexOf('.')
    val afterDecPoint = decPoint + 1
    if (activeStyle.getGroupingStyle == GroupingStyle.NONE) {
      if (decPoint < 0) {
        appendable.append(str)
        if (activeStyle.isForcedDecimalPoint) {
          appendable.append(activeStyle.getDecimalPointCharacter)
        }
      } else {
        appendable.append(str.subSequence(0, decPoint)).append(activeStyle.getDecimalPointCharacter)
          .append(str.substring(afterDecPoint))
      }
    } else {
      val groupingSize = activeStyle.getGroupingSize
      var extendedGroupingSize = activeStyle.getExtendedGroupingSize
      extendedGroupingSize = if (extendedGroupingSize == 0) groupingSize else extendedGroupingSize
      val groupingChar = activeStyle.getGroupingCharacter
      val pre = (if (decPoint < 0) str.length else decPoint)
      val post = (if (decPoint < 0) 0 else str.length - decPoint - 1)
      appendable.append(str.charAt(0))
      for (i <- 1 until pre) {
        if (isPreGroupingPoint(pre - i, groupingSize, extendedGroupingSize)) {
          appendable.append(groupingChar)
        }
        appendable.append(str.charAt(i))
      }
      if (decPoint >= 0 || activeStyle.isForcedDecimalPoint) {
        appendable.append(activeStyle.getDecimalPointCharacter)
      }
      if (activeStyle.getGroupingStyle == GroupingStyle.BEFORE_DECIMAL_POINT) {
        if (decPoint >= 0) {
          appendable.append(str.substring(afterDecPoint))
        }
      } else {
        for (i <- 0 until post) {
          appendable.append(str.charAt(i + afterDecPoint))
          if (isPostGroupingPoint(i, post, groupingSize, extendedGroupingSize)) {
            appendable.append(groupingChar)
          }
        }
      }
    }
  }

  private def isPreGroupingPoint(remaining: Int, groupingSize: Int, extendedGroupingSize: Int): Boolean = {
    if (remaining >= groupingSize + extendedGroupingSize) {
      return (remaining - groupingSize) % extendedGroupingSize == 0
    }
    remaining % groupingSize == 0
  }

  private def isPostGroupingPoint(i: Int, 
      post: Int, 
      groupingSize: Int, 
      extendedGroupingSize: Int): Boolean = {
    val atEnd = (i + 1) >= post
    if (i > groupingSize) {
      return (i - groupingSize) % extendedGroupingSize == (extendedGroupingSize - 1) && 
        !atEnd
    }
    i % groupingSize == (groupingSize - 1) && !atEnd
  }

  override def parse(context: MoneyParseContext) {
    val len = context.getTextLength
    val activeStyle = style.localize(context.getLocale)
    val buf = Array.ofDim[Char](len - context.getIndex)
    val bufPos = 0
    var dpSeen = false
    var pos = context.getIndex
    if (pos < len) {
      val ch = context.getText.charAt(pos += 1)
      if (ch == activeStyle.getNegativeSignCharacter) {
        buf(bufPos += 1) = '-'
      } else if (ch == activeStyle.getPositiveSignCharacter) {
        buf(bufPos += 1) = '+'
      } else if (ch >= activeStyle.getZeroCharacter && ch < activeStyle.getZeroCharacter + 10) {
        buf(bufPos += 1) = ('0' + ch - activeStyle.getZeroCharacter).toChar
      } else if (ch == activeStyle.getDecimalPointCharacter) {
        buf(bufPos += 1) = '.'
        dpSeen = true
      } else {
        context.setError()
        return
      }
    }
    var lastWasGroup = false
    while (pos < len) {
      val ch = context.getText.charAt(pos)
      if (ch >= activeStyle.getZeroCharacter && ch < activeStyle.getZeroCharacter + 10) {
        buf(bufPos += 1) = ('0' + ch - activeStyle.getZeroCharacter).toChar
        lastWasGroup = false
      } else if (ch == activeStyle.getDecimalPointCharacter && dpSeen == false) {
        buf(bufPos += 1) = '.'
        dpSeen = true
        lastWasGroup = false
      } else if (ch == activeStyle.getGroupingCharacter && lastWasGroup == false) {
        lastWasGroup = true
      } else {
        //break
      }
      pos += 1
    }
    if (lastWasGroup) {
      pos -= 1
    }
    try {
      context.setAmount(new BigDecimal(buf, 0, bufPos))
      context.setIndex(pos)
    } catch {
      case ex: NumberFormatException => context.setError()
    }
  }

  override def toString(): String = "${amount}"
}
