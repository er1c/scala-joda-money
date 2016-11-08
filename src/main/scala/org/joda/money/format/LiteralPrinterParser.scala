package org.joda.money.format

import java.io.IOException
import java.io.Serializable
import org.joda.money.BigMoney
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Prints and parses a literal.
 * <p>
 * This class is immutable and thread-safe.
 */
@SerialVersionUID(1L)
class LiteralPrinterParser(val literal: String) extends MoneyPrinter with MoneyParser with Serializable {

  override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
    appendable.append(literal)
  }

  override def parse(context: MoneyParseContext) {
    val endPos = context.getIndex + literal.length
    if (endPos <= context.getTextLength && 
      context.getTextSubstring(context.getIndex, endPos) == literal) {
      context.setIndex(endPos)
    } else {
      context.setError()
    }
  }

  override def toString(): String = "'" + literal + "'"
}
