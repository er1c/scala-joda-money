package org.joda.money

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.List
import java.util.regex.Matcher
import java.util.regex.Pattern
import DefaultCurrencyUnitDataProvider._
//remove if not needed
import scala.collection.JavaConversions._

object DefaultCurrencyUnitDataProvider {

  /**
   Regex format for the csv line.
   */
  private val REGEX_LINE = Pattern.compile("([A-Z]{3}),(-1|[0-9]{1,3}),(-1|[0-9]),([A-Z]*)#?.*")
}

/**
 * Provider for available currencies using a file.
 * <p>
 * This reads currencies from two files.
 * Firstly it reads the mandatory resource named {@code /org/joda/money/MoneyData.csv}.
 * Then it reads the optional resource named {@code /org/joda/money/MoneyDataExtension.csv}.
 * Both will be read as the first found on the classpath.
 * The second file may replace entries in the first file.
 */
class DefaultCurrencyUnitDataProvider extends CurrencyUnitDataProvider {

  /**
   * Registers all the currencies known by this provider.
   *
   * @throws Exception if an error occurs
   */
  def registerCurrencies() {
    loadCurrenciesFromFile("/org/joda/money/MoneyData.csv", true)
    loadCurrenciesFromFile("/org/joda/money/MoneyDataExtension.csv", false)
  }

  /**
   * Loads Currencies from a file
   *
   * @param fileName  the file to load, not null
   * @param isNecessary  whether or not the file is necessary
   * @throws Exception if a necessary file is not found
   */
  private def loadCurrenciesFromFile(fileName: String, isNecessary: Boolean) {
    var in: InputStream = null
    var resultEx: Exception = null
    try {
      in = getClass.getResourceAsStream(fileName)
      if (in == null && isNecessary) {
        throw new FileNotFoundException("Data file " + fileName + " not found")
      } else if (in == null && !isNecessary) {
        return
      }
      val reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))
      var line: String = reader.readLine()
      while (line != null) {
        val matcher = REGEX_LINE.matcher(line)
        if (matcher.matches()) {
          val countryCodes = new ArrayList[String]()
          val codeStr = matcher.group(4)
          val currencyCode = matcher.group(1)
          if (codeStr.length % 2 == 1) {
            //continue
          }
          var i = 0
          while (i < codeStr.length) {
            countryCodes.add(codeStr.substring(i, i + 2))
            i += 2
          }
          val numericCode = Integer.parseInt(matcher.group(2))
          val digits = Integer.parseInt(matcher.group(3))
          registerCurrency(currencyCode, numericCode, digits, countryCodes)
        }
        line = reader.readLine()
      }
    } catch {
      case ex: Exception => {
        resultEx = ex
        throw ex
      }
    } finally {
      if (in != null) {
        if (resultEx != null) {
          in.close()
        } else {
          in.close()
        }
      }
    }
  }
}
