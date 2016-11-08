package org.joda.money.format

import java.io.IOException
import java.io.Serializable
import java.util.Arrays
import org.joda.money.BigMoney
//remove if not needed
import scala.collection.JavaConversions._

/**
 * Prints and parses multiple printers/parsers.
 * <p>
 * This class is immutable and thread-safe.
 */
@SerialVersionUID(1L)
class MultiPrinterParser(val printers: Array[MoneyPrinter], val parsers: Array[MoneyParser])
    extends MoneyPrinter with MoneyParser with Serializable {

  def isPrinter(): Boolean = {
    Arrays.asList(printers:_*).contains(null) == false
  }

  def isParser(): Boolean = {
    Arrays.asList(parsers:_*).contains(null) == false
  }

  def appendTo(builder: MoneyFormatterBuilder) {
    for (i <- 0 until printers.length) {
      builder.append(printers(i), parsers(i))
    }
  }

  override def print(context: MoneyPrintContext, appendable: Appendable, money: BigMoney) {
    for (printer <- printers) {
      printer.print(context, appendable, money)
    }
  }

  override def parse(context: MoneyParseContext) {
    for (parser <- parsers) {
      parser.parse(context)
      if (context.isError) {
        //break
      }
    }
  }

  override def toString(): String = {
    val buf1 = new StringBuilder()
    if (isPrinter) {
      for (printer <- printers) {
        buf1.append(printer.toString)
      }
    }
    val buf2 = new StringBuilder()
    if (isParser) {
      for (parser <- parsers) {
        buf2.append(parser.toString)
      }
    }
    val str1 = buf1.toString
    val str2 = buf2.toString
    if (isPrinter && isParser == false) {
      str1
    } else if (isParser && isPrinter == false) {
      str2
    } else if (str1 == str2) {
      str1
    } else {
      str1 + ":" + str2
    }
  }
}
