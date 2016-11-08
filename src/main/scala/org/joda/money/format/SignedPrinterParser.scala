package org.joda.money.format

import java.io.IOException
import java.io.Serializable
import java.math.BigDecimal
import org.joda.money.BigMoney
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Prints and parses using delegated formatters, one for positive and one for megative.
 * <p>
 * This class is immutable and thread-safe.
 */
@SerialVersionUID(1L)
class SignedPrinterParser(val whenPositive: MoneyFormatter, val whenZero: MoneyFormatter, val whenNegative: MoneyFormatter)
    extends MoneyPrinter with MoneyParser with Serializable {

  override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
    val fmt = (if (money.isZero) whenZero else if (money.isPositive) whenPositive else whenNegative)
    fmt.getPrinterParser.print(context, appendable, money)
  }

  override def parse(context: MoneyParseContext) {
    val positiveContext = context.createChild()
    whenPositive.getPrinterParser.parse(positiveContext)
    val zeroContext = context.createChild()
    whenZero.getPrinterParser.parse(zeroContext)
    val negativeContext = context.createChild()
    whenNegative.getPrinterParser.parse(negativeContext)
    var best: MoneyParseContext = null
    if (!positiveContext.isError) {
      best = positiveContext
    }
    if (!zeroContext.isError) {
      if (best == null || zeroContext.getIndex > best.getIndex) {
        best = zeroContext
      }
    }
    if (!negativeContext.isError) {
      if (best == null || negativeContext.getIndex > best.getIndex) {
        best = negativeContext
      }
    }
    if (best == null) {
      context.setError()
    } else {
      context.mergeChild(best)
      if (best == zeroContext) {
        if (context.getAmount == null || context.getAmount.compareTo(BigDecimal.ZERO) != 0) {
          context.setAmount(BigDecimal.ZERO)
        }
      } else if (best == negativeContext && context.getAmount.compareTo(BigDecimal.ZERO) > 0) {
        context.setAmount(context.getAmount.negate())
      }
    }
  }

  override def toString(): String = {
    "PositiveZeroNegative(" + whenPositive + "," + whenZero + 
      "," + 
      whenNegative + 
      ")"
  }
}
