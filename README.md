NOTE: This is 90% done, most of the tests work & the scalajs code compiles/etc, but I haven't had time to finish it

-----

Based upon https://github.com/JodaOrg/joda-money using https://github.com/timowest/scalagen

Simply added the plugin to the joda-money pom.xml:

<plugin>
  <groupId>com.mysema.scalagen</groupId>
  <artifactId>scalagen-maven-plugin</artifactId>
  <version>0.2.2</version>
</plugin>

Then, executed mvn scalagen:main to generate src/main/scala/
That directory tree is copied, as well as joda-money/src/main/resources/org/joda/money/MoneyData.csv

Then, executed mvn scalagen:test to generate src/test/scala


-----

```

eric@Erics-MacBook-Pro:~/Work/scala-joda-money$ sbt test
[info] Loading project definition from /Users/eric/Work/scala-joda-money/project
[info] Set current project to scalajs root project (in build file:/Users/eric/Work/scala-joda-money/)
[info] Compiling 2 Scala sources to /Users/eric/Work/scala-joda-money/jvm/target/scala-2.11/classes...
[info] Compiling 2 Scala sources to /Users/eric/Work/scala-joda-money/js/target/scala-2.11/classes...
[TestNG] Running:
  Command line suite

[TestNG] Running:
  Command line suite

[TestNG] Running:
  Command line suite

[TestNG] Running:
  Command line suite

[TestNG] Running:
  Command line suite

[TestNG] Running:
  Command line suite


===============================================
Command line suite
Total tests run: 12, Failures: 0, Skips: 0
===============================================


===============================================
Command line suite
Total tests run: 28, Failures: 0, Skips: 0
===============================================

[TestNG] Running:
  Command line suite

[TestNG] Running:
  Command line suite


===============================================
Command line suite
Total tests run: 4, Failures: 0, Skips: 0
===============================================

[TestNG] Running:
  Command line suite

[TestNG] Running:
  Command line suite


===============================================
Command line suite
Total tests run: 2, Failures: 0, Skips: 0
===============================================


===============================================
Command line suite
Total tests run: 76, Failures: 0, Skips: 0
===============================================


===============================================
Command line suite
Total tests run: 29, Failures: 0, Skips: 0
===============================================


===============================================
Command line suite
Total tests run: 92, Failures: 0, Skips: 0
===============================================


===============================================
Command line suite
Total tests run: 64, Failures: 2, Skips: 0
===============================================

[TestNG] Running:
  Command line suite

[TestNG] Reporter org.testng.reporters.JUnitReportReporter@7c17bc85 failed
java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:901)
	at java.util.ArrayList$Itr.next(ArrayList.java:851)
	at org.testng.reporters.JUnitReportReporter.getNextConfiguration(JUnitReportReporter.java:220)
	at org.testng.reporters.JUnitReportReporter.generateReport(JUnitReportReporter.java:105)
	at org.testng.TestNG.generateReports(TestNG.java:1175)
	at org.testng.TestNG.run(TestNG.java:1102)
	at org.scalatest.testng.TestNGSuiteLike$class.run(TestNGSuiteLike.scala:261)
	at org.scalatest.testng.TestNGSuite.run(TestNGSuite.scala:67)
	at org.scalatest.testng.TestNGSuiteLike$class.runTestNG(TestNGSuiteLike.scala:248)
	at org.scalatest.testng.TestNGSuite.runTestNG(TestNGSuite.scala:67)
	at org.scalatest.testng.TestNGSuiteLike$class.run(TestNGSuiteLike.scala:149)
	at org.scalatest.testng.TestNGSuite.run(TestNGSuite.scala:67)
	at org.scalatest.tools.Framework.org$scalatest$tools$Framework$$runSuite(Framework.scala:314)
	at org.scalatest.tools.Framework$ScalaTestTask.execute(Framework.scala:472)
	at sbt.TestRunner.runTest$1(TestFramework.scala:76)
	at sbt.TestRunner.run(TestFramework.scala:85)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1$$anonfun$apply$8.apply(TestFramework.scala:202)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1$$anonfun$apply$8.apply(TestFramework.scala:202)
	at sbt.TestFramework$.sbt$TestFramework$$withContextLoader(TestFramework.scala:185)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1.apply(TestFramework.scala:202)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1.apply(TestFramework.scala:202)
	at sbt.TestFunction.apply(TestFramework.scala:207)
	at sbt.Tests$$anonfun$9.apply(Tests.scala:216)
	at sbt.Tests$$anonfun$9.apply(Tests.scala:216)
	at sbt.std.Transform$$anon$3$$anonfun$apply$2.apply(System.scala:44)
	at sbt.std.Transform$$anon$3$$anonfun$apply$2.apply(System.scala:44)
	at sbt.std.Transform$$anon$4.work(System.scala:63)
	at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:226)
	at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:226)
	at sbt.ErrorHandling$.wideConvert(ErrorHandling.scala:17)
	at sbt.Execute.work(Execute.scala:235)
	at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:226)
	at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:226)
	at sbt.ConcurrentRestrictions$$anon$4$$anonfun$1.apply(ConcurrentRestrictions.scala:159)
	at sbt.CompletionService$$anon$2.call(CompletionService.scala:28)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
[info] TestMoneyAmountStyle:
[info] - test_ASCII_ASCII_DECIMAL_COMMA_GROUP3_DOT
[info] - test_ASCII_ASCII_DECIMAL_POINT_GROUP3_SPACE_print
[info] - test_ASCII_DECIMAL_COMMA_GROUP3_DOT_print
[info] - test_ASCII_DECIMAL_COMMA_GROUP3_SPACE
[info] - test_ASCII_DECIMAL_COMMA_GROUP3_SPACE_print
[info] - test_ASCII_DECIMAL_COMMA_NO_GROUPING
[info] - test_ASCII_DECIMAL_COMMA_NO_GROUPING_print
[info] - test_ASCII_DECIMAL_POINT_GROUP3_COMMA
[info] - test_ASCII_DECIMAL_POINT_GROUP3_COMMA_print
[info] - test_ASCII_DECIMAL_POINT_GROUP3_SPACE
[info] - test_ASCII_DECIMAL_POINT_NO_GROUPING
[info] - test_ASCII_DECIMAL_POINT_NO_GROUPING_print
[info] - test_LOCALIZED_GROUPING
[info] - test_LOCALIZED_GROUPING_print
[info] - test_LOCALIZED_NO_GROUPING
[info] - test_LOCALIZED_NO_GROUPING_print
[info] - test_equals_equal_absValue_false
[info] - test_equals_equal_absValue_true
[info] - test_equals_equal_decimalPointChar
[info] - test_equals_equal_forcedDecimalPoint_false
[info] - test_equals_equal_forcedDecimalPoint_true
[info] - test_equals_equal_groupingChar
[info] - test_equals_equal_groupingSize
[info] - test_equals_equal_groupingStyle
[info] - test_equals_equal_negativeChar
[info] - test_equals_equal_positiveChar
[info] - test_equals_equal_zeroChar
[info] - test_equals_notEqual_absValue
[info] - test_equals_notEqual_decimalPointChar
[info] - test_equals_notEqual_forcedDecimalPoint
[info] - test_equals_notEqual_groupingChar
[info] - test_equals_notEqual_groupingSize
[info] - test_equals_notEqual_groupingStyle
[info] - test_equals_notEqual_negativeChar
[info] - test_equals_notEqual_positiveChar
[info] - test_equals_notEqual_zeroChar
[info] - test_equals_null
[info] - test_equals_otherType
[info] - test_equals_same
[info] - test_localize_DE
[info] - test_localize_DE_fixedDecimal
[info] - test_localize_DE_fixedGrouping
[info] - test_localize_DE_fixedNegative
[info] - test_localize_DE_fixedPositive
[info] - test_localize_DE_fixedZero
[info] - test_localize_DE_fixedZeroAndDecimal
[info] - test_localize_GB
[info] - test_of_Locale_DE
[info] - test_of_Locale_GB
[info] - test_print_groupBeforeDecimal
[info] - test_toString
[info] - test_withAbsValue
[info] - test_withAbsValue_same
[info] - test_withDecimalPointCharacter
[info] - test_withDecimalPointCharacter_same
[info] - test_withDecimalPointCharacter_sameNull
[info] - test_withForcedDecimalPoint
[info] - test_withForcedDecimalPoint_same
[info] - test_withGroupingCharacter
[info] - test_withGroupingCharacter_same
[info] - test_withGroupingCharacter_sameNull
[info] - test_withGroupingSize
[info] - test_withGroupingSize_negative
[info] - test_withGroupingSize_same
[info] - test_withGroupingSize_sameNull
[info] - test_withGroupingStyle
[info] - test_withGroupingStyle_same
[info] - test_withNegativeSignCharacter
[info] - test_withNegativeSignCharacter_same
[info] - test_withNegativeSignCharacter_sameNull
[info] - test_withPositiveSignCharacter
[info] - test_withPositiveSignCharacter_same
[info] - test_withPositiveSignCharacter_sameNull
[info] - test_withZeroCharacter
[info] - test_withZeroCharacter_same
[info] - test_withZeroCharacter_sameNull
[info] TestCurrencyUnit:
[info] - test_compareTo
[info] - test_compareTo_null
[info] - test_constants
[info] - test_constructor_nullCode
[info] - test_equals_false
[info] - test_equals_hashCode
[info] - test_factory_getInstance_Locale
[info] - test_factory_getInstance_Locale_nullString
[info] - test_factory_getInstance_Locale_unknownCurrency
[info] - test_factory_getInstance_String
[info] - test_factory_getInstance_String_nullString
[info] - test_factory_getInstance_String_unknownCurrency
[info] - test_factory_ofCountry_String
[info] - test_factory_ofCountry_String_nullString
[info] - test_factory_ofCountry_String_unknownCurrency
[info] - test_factory_ofNumericCode_String
[info] - test_factory_ofNumericCode_String_1char
[info] - test_factory_ofNumericCode_String_1charNoPad
[info] - test_factory_ofNumericCode_String_2char
[info] - test_factory_ofNumericCode_String_2charNoPad
[info] - test_factory_ofNumericCode_String_empty
[info] - test_factory_ofNumericCode_String_negative
[info] - test_factory_ofNumericCode_String_nullString
[info] - test_factory_ofNumericCode_String_tooLong
[info] - test_factory_ofNumericCode_String_unknownCurrency
[info] - test_factory_ofNumericCode_int
[info] - test_factory_ofNumericCode_int_1char
[info] - test_factory_ofNumericCode_int_2char
[info] - test_factory_ofNumericCode_int_negative
[info] - test_factory_ofNumericCode_int_tooLong
[info] - test_factory_ofNumericCode_int_unknownCurrency
[info] - test_factory_of_Currency
[info] - test_factory_of_Currency_nullCurrency
[info] - test_factory_of_Locale
[info] - test_factory_of_Locale_nullLocale
[info] - test_factory_of_Locale_unknownCurrency
[info] - test_factory_of_String
[info] - test_factory_of_String_empty
[info] - test_factory_of_String_nullString
[info] - test_factory_of_String_tooLong_unknown
[info] - test_factory_of_String_tooShort_unknown
[info] - test_factory_of_String_unknownCurrency
[info] - test_getCurrencyCode_GBP
[info] - test_getCurrencyCodes_GBP
[info] - test_getDecimalPlaces_GBP
[info] - test_getDecimalPlaces_JPY
[info] - test_getDecimalPlaces_XXX
[info] - test_getDefaultFractionDigits_GBP
[info] - test_getDefaultFractionDigits_JPY
[info] - test_getDefaultFractionDigits_XXX
[info] - test_getNumeric3Code_ALL
[info] - test_getNumeric3Code_AMD
[info] - test_getNumeric3Code_GBP
[info] - test_getNumeric3Code_XFU
[info] - test_getNumericCode_GBP
[info] - test_getSymbol_GBP
[info] - test_getSymbol_JPY
[info] - test_getSymbol_Locale_GBP_France
[info] - test_getSymbol_Locale_GBP_UK
[info] - test_getSymbol_Locale_JPY_Japan
[info] - test_getSymbol_Locale_USD_France
[info] - test_getSymbol_Locale_USD_UK
[info] - test_getSymbol_Locale_XXX
[info] - test_getSymbol_TMT
[info] - test_getSymbol_TMT_UK
[info] - test_getSymbol_XXX
[info] - test_isPseudoCurrency_GBP
[info] - test_isPseudoCurrency_JPY
[info] - test_isPseudoCurrency_XXX
[info] - test_registeredCurrencies
[info] - test_registeredCurrencies_sorted
[info] - test_registeredCurrency_alreadyRegisteredCode
[info] - test_registeredCurrency_alreadyRegisteredCountry
[info] - test_registeredCurrency_alreadyRegisteredNumericCode
[info] - test_registeredCurrency_invalidDP_big
[info] - test_registeredCurrency_invalidDP_small
[info] - test_registeredCurrency_invalidNumericCode_big
[info] - test_registeredCurrency_invalidNumericCode_small
[info] - test_registeredCurrency_invalidStringCode_1letter
[info] - test_registeredCurrency_invalidStringCode_2letters
[info] - test_registeredCurrency_invalidStringCode_4letters
[info] - test_registeredCurrency_invalidStringCode_dash
[info] - test_registeredCurrency_invalidStringCode_empty
[info] - test_registeredCurrency_invalidStringCode_lowerCase
[info] - test_registeredCurrency_invalidStringCode_number
[info] - test_registeredCurrency_nullCode
[info] - test_registeredCurrency_nullCountry
[info] - test_serialization
[info] - test_serialization_invalidDecimalPlaces
[info] - test_serialization_invalidNumericCode
[info] - test_toCurrency
[info] - test_toString
[TestNG] Reporter org.testng.reporters.JUnitReportReporter@2bdb5b84 failed
java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:901)
	at java.util.ArrayList$Itr.next(ArrayList.java:851)
	at org.testng.reporters.JUnitReportReporter.getNextConfiguration(JUnitReportReporter.java:220)
	at org.testng.reporters.JUnitReportReporter.generateReport(JUnitReportReporter.java:105)
	at org.testng.TestNG.generateReports(TestNG.java:1175)
	at org.testng.TestNG.run(TestNG.java:1102)
	at org.scalatest.testng.TestNGSuiteLike$class.run(TestNGSuiteLike.scala:261)
	at org.scalatest.testng.TestNGSuite.run(TestNGSuite.scala:67)
	at org.scalatest.testng.TestNGSuiteLike$class.runTestNG(TestNGSuiteLike.scala:248)
	at org.scalatest.testng.TestNGSuite.runTestNG(TestNGSuite.scala:67)
	at org.scalatest.testng.TestNGSuiteLike$class.run(TestNGSuiteLike.scala:149)
	at org.scalatest.testng.TestNGSuite.run(TestNGSuite.scala:67)
	at org.scalatest.tools.Framework.org$scalatest$tools$Framework$$runSuite(Framework.scala:314)
	at org.scalatest.tools.Framework$ScalaTestTask.execute(Framework.scala:472)
	at sbt.TestRunner.runTest$1(TestFramework.scala:76)
	at sbt.TestRunner.run(TestFramework.scala:85)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1$$anonfun$apply$8.apply(TestFramework.scala:202)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1$$anonfun$apply$8.apply(TestFramework.scala:202)
	at sbt.TestFramework$.sbt$TestFramework$$withContextLoader(TestFramework.scala:185)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1.apply(TestFramework.scala:202)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1.apply(TestFramework.scala:202)
	at sbt.TestFunction.apply(TestFramework.scala:207)
	at sbt.Tests$$anonfun$9.apply(Tests.scala:216)
	at sbt.Tests$$anonfun$9.apply(Tests.scala:216)
	at sbt.std.Transform$$anon$3$$anonfun$apply$2.apply(System.scala:44)
	at sbt.std.Transform$$anon$3$$anonfun$apply$2.apply(System.scala:44)
	at sbt.std.Transform$$anon$4.work(System.scala:63)
	at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:226)
	at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:226)
	at sbt.ErrorHandling$.wideConvert(ErrorHandling.scala:17)
	at sbt.Execute.work(Execute.scala:235)
	at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:226)
	at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:226)
	at sbt.ConcurrentRestrictions$$anon$4$$anonfun$1.apply(ConcurrentRestrictions.scala:159)
	at sbt.CompletionService$$anon$2.call(CompletionService.scala:28)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)

===============================================
Command line suite
Total tests run: 315, Failures: 0, Skips: 0
===============================================


===============================================
Command line suite
Total tests run: 492, Failures: 0, Skips: 0
===============================================


===============================================
Command line suite
Total tests run: 371, Failures: 3, Skips: 0
===============================================

[info] TestMoneyParseContext:
[info] - test_getTextSubstring_afterEnd
[info] - test_getTextSubstring_beforeStart
[info] - test_getTextSubstring_ok
[info] - test_initialState
[info] - test_isComplete_noAmount
[info] - test_isComplete_noCurrency
[info] - test_setError
[info] - test_setErrorIndex
[info] - test_setError_withIndex
[info] - test_setIndex
[info] - test_toBigMoney_noAmount
[info] - test_toBigMoney_noCurrency
[info] TestMoney:
[info] - test_abs_negative
[info] - test_abs_positive
[info] - test_compareTo_BigMoney
[info] - test_compareTo_Money
[info] - test_compareTo_currenciesDiffer
[info] - test_compareTo_wrongType
[info] - test_constructor_null1
[info] - test_constructor_scale
[info] - test_convertedTo_BigDecimalRoundingMode_negative
[info] - test_convertedTo_BigDecimalRoundingMode_nullBigDecimal
[info] - test_convertedTo_BigDecimalRoundingMode_nullCurrency
[info] - test_convertedTo_BigDecimalRoundingMode_nullRoundingMode
[info] - test_convertedTo_BigDecimalRoundingMode_positive
[info] - test_convertedTo_BigDecimalRoundingMode_positive_halfUp
[info] - test_convertedTo_BigDecimalRoundingMode_sameCurrency
[info] - test_dividedBy_BigDecimalRoundingMode_negative
[info] - test_dividedBy_BigDecimalRoundingMode_nullBigDecimal
[info] - test_dividedBy_BigDecimalRoundingMode_nullRoundingMode
[info] - test_dividedBy_BigDecimalRoundingMode_one
[info] - test_dividedBy_BigDecimalRoundingMode_positive
[info] - test_dividedBy_BigDecimalRoundingMode_positive_halfUp
[info] - test_dividedBy_doubleRoundingMode_negative
[info] - test_dividedBy_doubleRoundingMode_nullRoundingMode
[info] - test_dividedBy_doubleRoundingMode_one
[info] - test_dividedBy_doubleRoundingMode_positive
[info] - test_dividedBy_doubleRoundingMode_positive_halfUp
[info] - test_dividedBy_long_negative
[info] - test_dividedBy_long_one
[info] - test_dividedBy_long_positive
[info] - test_dividedBy_long_positive_roundDown
[info] - test_dividedBy_long_positive_roundUp
[info] - test_equals_false
[info] - test_equals_hashCode_positive
[info] - test_factory_from_BigMoneyProvider
[info] - test_factory_from_BigMoneyProvider_RoundingMode
[info] - test_factory_from_BigMoneyProvider_RoundingMode_nullBigMoneyProvider
[info] - test_factory_from_BigMoneyProvider_RoundingMode_nullRoundingMode
[info] - test_factory_from_BigMoneyProvider_fixScale
[info] - test_factory_from_BigMoneyProvider_invalidCurrencyScale
[info] - test_factory_from_BigMoneyProvider_nullBigMoneyProvider
[info] - test_factory_ofMajor_Currency_long
[info] - test_factory_ofMajor_Currency_long_nullCurrency
[info] - test_factory_ofMinor_Currency_long
[info] - test_factory_ofMinor_Currency_long_nullCurrency
[info] - test_factory_of_Currency_BigDecimal
[info] - test_factory_of_Currency_BigDecimal_GBP_RoundingMode_DOWN
[info] - test_factory_of_Currency_BigDecimal_JPY_RoundingMode_DOWN
[info] - test_factory_of_Currency_BigDecimal_JPY_RoundingMode_UP
[info] - test_factory_of_Currency_BigDecimal_RoundingMode_UNNECESSARY
[info] - test_factory_of_Currency_BigDecimal_RoundingMode_nullBigDecimal
[info] - test_factory_of_Currency_BigDecimal_RoundingMode_nullCurrency
[info] - test_factory_of_Currency_BigDecimal_RoundingMode_nullRoundingMode
[info] - test_factory_of_Currency_BigDecimal_correctScale
[info] - test_factory_of_Currency_BigDecimal_invalidScaleGBP
[info] - test_factory_of_Currency_BigDecimal_invalidScaleJPY
[info] - test_factory_of_Currency_BigDecimal_nullBigDecimal
[info] - test_factory_of_Currency_BigDecimal_nullCurrency
[info] - test_factory_of_Currency_double
[info] - test_factory_of_Currency_double_GBP_RoundingMode_DOWN
[info] - test_factory_of_Currency_double_JPY_RoundingMode_DOWN
[info] - test_factory_of_Currency_double_JPY_RoundingMode_UP
[info] - test_factory_of_Currency_double_RoundingMode_UNNECESSARY
[info] - test_factory_of_Currency_double_RoundingMode_nullCurrency
[info] - test_factory_of_Currency_double_RoundingMode_nullRoundingMode
[info] - test_factory_of_Currency_double_big
[info] - test_factory_of_Currency_double_correctScale
[info] - test_factory_of_Currency_double_invalidScaleGBP
[info] - test_factory_of_Currency_double_invalidScaleJPY
[info] - test_factory_of_Currency_double_medium
[info] - test_factory_of_Currency_double_nullCurrency
[info] - test_factory_of_Currency_double_trailingZero1
[info] - test_factory_of_Currency_double_trailingZero2
[info] - test_factory_parse(GBP 2.43,GBP,243)
[info] - test_factory_parse(GBP +12.57,GBP,1257)
[info] - test_factory_parse(GBP -5.87,GBP,-587)
[info] - test_factory_parse(GBP 0.99,GBP,99)
[info] - test_factory_parse(GBP .99,GBP,99)
[info] - test_factory_parse(GBP +.99,GBP,99)
[info] - test_factory_parse(GBP +0.99,GBP,99)
[info] - test_factory_parse(GBP -.99,GBP,-99)
[info] - test_factory_parse(GBP -0.99,GBP,-99)
[info] - test_factory_parse(GBP 0,GBP,0)
[info] - test_factory_parse(GBP 2,GBP,200)
[info] - test_factory_parse(GBP 123.,GBP,12300)
[info] - test_factory_parse(GBP3,GBP,300)
[info] - test_factory_parse(GBP3.10,GBP,310)
[info] - test_factory_parse(GBP  3.10,GBP,310)
[info] - test_factory_parse(GBP   3.10,GBP,310)
[info] - test_factory_parse(GBP                           3.10,GBP,310)
[info] - test_factory_parse_String_badCurrency
[info] - test_factory_parse_String_nullString
[info] - test_factory_parse_String_tooShort
[info] - test_factory_total_CurrencyUnitArray_1
[info] - test_factory_total_CurrencyUnitArray_3
[info] - test_factory_total_CurrencyUnitArray_currenciesDiffer
[info] - test_factory_total_CurrencyUnitArray_currenciesDifferInArray
[info] - test_factory_total_CurrencyUnitArray_empty
[info] - test_factory_total_CurrencyUnitArray_nullFirst
[info] - test_factory_total_CurrencyUnitArray_nullNotFirst
[info] - test_factory_total_CurrencyUnitIterable
[info] - test_factory_total_CurrencyUnitIterable_currenciesDiffer
[info] - test_factory_total_CurrencyUnitIterable_currenciesDifferInIterable
[info] - test_factory_total_CurrencyUnitIterable_empty
[info] - test_factory_total_CurrencyUnitIterable_nullFirst
[info] - test_factory_total_CurrencyUnitIterable_nullNotFirst
[info] - test_factory_total_CurrencyUnitVarargs_1
[info] - test_factory_total_CurrencyUnitVarargs_3
[info] - test_factory_total_CurrencyUnitVarargs_currenciesDiffer
[info] - test_factory_total_CurrencyUnitVarargs_currenciesDifferInArray
[info] - test_factory_total_CurrencyUnitVarargs_empty
[info] - test_factory_total_CurrencyUnitVarargs_nullFirst
[info] - test_factory_total_CurrencyUnitVarargs_nullNotFirst
[info] - test_factory_total_Iterable
[info] - test_factory_total_Iterable_currenciesDiffer
[info] - test_factory_total_Iterable_empty
[info] - test_factory_total_Iterable_nullFirst
[info] - test_factory_total_Iterable_nullNotFirst
[info] - test_factory_total_array_1
[info] - test_factory_total_array_3
[info] - test_factory_total_array_currenciesDiffer
[info] - test_factory_total_array_empty
[info] - test_factory_total_array_nullFirst
[info] - test_factory_total_array_nullNotFirst
[info] - test_factory_total_varargs_1
[info] - test_factory_total_varargs_3
[info] - test_factory_total_varargs_currenciesDiffer
[info] - test_factory_total_varargs_empty
[info] - test_factory_total_varargs_nullFirst
[info] - test_factory_total_varargs_nullNotFirst
[info] - test_factory_zero_Currency
[info] - test_factory_zero_Currency_nullCurrency
[info] - test_getAmountMajorInt_negative
[info] - test_getAmountMajorInt_positive
[info] - test_getAmountMajorInt_tooBigNegative
[info] - test_getAmountMajorInt_tooBigPositive
[info] - test_getAmountMajorLong_negative
[info] - test_getAmountMajorLong_positive
[info] - test_getAmountMajorLong_tooBigNegative
[info] - test_getAmountMajorLong_tooBigPositive
[info] - test_getAmountMajor_negative
[info] - test_getAmountMajor_positive
[info] - test_getAmountMinorInt_negative
[info] - test_getAmountMinorInt_positive
[info] - test_getAmountMinorInt_tooBigNegative
[info] - test_getAmountMinorInt_tooBigPositive
[info] - test_getAmountMinorLong_negative
[info] - test_getAmountMinorLong_positive
[info] - test_getAmountMinorLong_tooBigNegative
[info] - test_getAmountMinorLong_tooBigPositive
[info] - test_getAmountMinor_negative
[info] - test_getAmountMinor_positive
[info] - test_getAmount_negative
[info] - test_getAmount_positive
[info] - test_getCurrencyUnit_EUR
[info] - test_getCurrencyUnit_GBP
[info] - test_getMinorPart_negative
[info] - test_getMinorPart_positive
[info] - test_getScale_GBP
[info] - test_getScale_JPY
[info] - test_isEqual
[info] - test_isEqual_Money
[info] - test_isEqual_currenciesDiffer
[info] - test_isGreaterThan
[info] - test_isGreaterThan_currenciesDiffer
[info] - test_isLessThan
[info] - test_isLessThan_currenciesDiffer
[info] - test_isNegative
[info] - test_isNegativeOrZero
[info] - test_isPositive
[info] - test_isPositiveOrZero
[info] - test_isSameCurrency_BigMoney_different
[info] - test_isSameCurrency_BigMoney_same
[info] - test_isSameCurrency_Money_different
[info] - test_isSameCurrency_Money_nullMoney
[info] - test_isSameCurrency_Money_same
[info] - test_isZero
[info] - test_minusMajor_negative
[info] - test_minusMajor_positive
[info] - test_minusMajor_zero
[info] - test_minusMinor_negative
[info] - test_minusMinor_positive
[info] - test_minusMinor_zero
[info] - test_minus_BigDecimalRoundingMode_negative
[info] - test_minus_BigDecimalRoundingMode_nullBigDecimal
[info] - test_minus_BigDecimalRoundingMode_nullRoundingMode
[info] - test_minus_BigDecimalRoundingMode_positive
[info] - test_minus_BigDecimalRoundingMode_roundDown
[info] - test_minus_BigDecimalRoundingMode_roundUnecessary
[info] - test_minus_BigDecimalRoundingMode_zero
[info] - test_minus_BigDecimal_invalidScale
[info] - test_minus_BigDecimal_negative
[info] - test_minus_BigDecimal_nullBigDecimal
[info] - test_minus_BigDecimal_positive
[info] - test_minus_BigDecimal_zero
[info] - test_minus_Iterable
[info] - test_minus_Iterable_currencyMismatch
[info] - test_minus_Iterable_nullEntry
[info] - test_minus_Iterable_nullIterable
[info] - test_minus_Iterable_zero
[info] - test_minus_Money_currencyMismatch
[info] - test_minus_Money_negative
[info] - test_minus_Money_nullMoney
[info] - test_minus_Money_positive
[info] - test_minus_Money_zero
[info] - test_minus_doubleRoundingMode_negative
[info] - test_minus_doubleRoundingMode_nullRoundingMode
[info] - test_minus_doubleRoundingMode_positive
[info] - test_minus_doubleRoundingMode_roundDown
[info] - test_minus_doubleRoundingMode_roundUnecessary
[info] - test_minus_doubleRoundingMode_zero
[info] - test_minus_double_invalidScale
[info] - test_minus_double_negative
[info] - test_minus_double_positive
[info] - test_minus_double_zero
[info] - test_multipliedBy_BigDecimalRoundingMode_negative
[info] - test_multipliedBy_BigDecimalRoundingMode_nullBigDecimal
[info] - test_multipliedBy_BigDecimalRoundingMode_nullRoundingMode
[info] - test_multipliedBy_BigDecimalRoundingMode_one
[info] - test_multipliedBy_BigDecimalRoundingMode_positive
[info] - test_multipliedBy_BigDecimalRoundingMode_positive_halfUp
[info] - test_multipliedBy_doubleRoundingMode_negative
[info] - test_multipliedBy_doubleRoundingMode_nullRoundingMode
[info] - test_multipliedBy_doubleRoundingMode_one
[info] - test_multipliedBy_doubleRoundingMode_positive
[info] - test_multipliedBy_doubleRoundingMode_positive_halfUp
[info] - test_multipliedBy_long_negative
[info] - test_multipliedBy_long_one
[info] - test_multipliedBy_long_positive
[info] - test_negated_negative
[info] - test_negated_positive
[info] - test_nonNull_MoneyCurrencyUnit_nonNull
[info] - test_nonNull_MoneyCurrencyUnit_nonNullCurrencyMismatch
[info] - test_nonNull_MoneyCurrencyUnit_nonNull_nullCurrency
[info] - test_nonNull_MoneyCurrencyUnit_null
[info] - test_nonNull_MoneyCurrencyUnit_null_nullCurrency
[info] - test_plusMajor_negative
[info] - test_plusMajor_positive
[info] - test_plusMajor_zero
[info] - test_plusMinor_negative
[info] - test_plusMinor_positive
[info] - test_plusMinor_zero
[info] - test_plus_BigDecimalRoundingMode_negative
[info] - test_plus_BigDecimalRoundingMode_nullBigDecimal
[info] - test_plus_BigDecimalRoundingMode_nullRoundingMode
[info] - test_plus_BigDecimalRoundingMode_positive
[info] - test_plus_BigDecimalRoundingMode_roundDown
[info] - test_plus_BigDecimalRoundingMode_roundUnecessary
[info] - test_plus_BigDecimalRoundingMode_zero
[info] - test_plus_BigDecimal_invalidScale
[info] - test_plus_BigDecimal_negative
[info] - test_plus_BigDecimal_nullBigDecimal
[info] - test_plus_BigDecimal_positive
[info] - test_plus_BigDecimal_zero
[info] - test_plus_Iterable
[info] - test_plus_Iterable_currencyMismatch
[info] - test_plus_Iterable_nullEntry
[info] - test_plus_Iterable_nullIterable
[info] - test_plus_Iterable_zero
[info] - test_plus_Money_currencyMismatch
[info] - test_plus_Money_negative
[info] - test_plus_Money_nullMoney
[info] - test_plus_Money_positive
[info] - test_plus_Money_zero
[info] - test_plus_doubleRoundingMode_negative
[info] - test_plus_doubleRoundingMode_nullRoundingMode
[info] - test_plus_doubleRoundingMode_positive
[info] - test_plus_doubleRoundingMode_roundDown
[info] - test_plus_doubleRoundingMode_roundUnecessary
[info] - test_plus_doubleRoundingMode_zero
[info] - test_plus_double_invalidScale
[info] - test_plus_double_negative
[info] - test_plus_double_positive
[info] - test_plus_double_zero
[info] - test_round_0down
[info] - test_round_0up
[info] - test_round_1down
[info] - test_round_1up
[info] - test_round_2down
[info] - test_round_2up
[info] - test_round_3
[info] - test_round_M1down
[info] - test_round_M1up
[info] - test_serialization
[info] - test_serialization_invalidDecimalPlaces
[info] - test_serialization_invalidNumericCode
[info] - test_toBigMoney
[info] - test_toString_negative
[info] - test_toString_positive
[info] - test_withAmount_BigDecimal
[info] - test_withAmount_BigDecimalRoundingMode
[info] - test_withAmount_BigDecimalRoundingMode_nullBigDecimal
[info] - test_withAmount_BigDecimalRoundingMode_nullRoundingMode
[info] - test_withAmount_BigDecimalRoundingMode_roundDown
[info] - test_withAmount_BigDecimalRoundingMode_roundUnecessary
[info] - test_withAmount_BigDecimalRoundingMode_same
[info] - test_withAmount_BigDecimal_invalidScale
[info] - test_withAmount_BigDecimal_nullBigDecimal
[info] - test_withAmount_BigDecimal_same
[info] - test_withAmount_double
[info] - test_withAmount_doubleRoundingMode
[info] - test_withAmount_doubleRoundingMode_nullRoundingMode
[info] - test_withAmount_doubleRoundingMode_roundDown
[info] - test_withAmount_doubleRoundingMode_roundUnecessary
[info] - test_withAmount_doubleRoundingMode_same
[info] - test_withAmount_double_invalidScale
[info] - test_withAmount_double_same
[info] - test_withCurrencyUnit_Currency
[info] - test_withCurrencyUnit_CurrencyRoundingMode_DOWN
[info] - test_withCurrencyUnit_CurrencyRoundingMode_UNECESSARY
[info] - test_withCurrencyUnit_CurrencyRoundingMode_UP
[info] - test_withCurrencyUnit_CurrencyRoundingMode_nullCurrency
[info] - test_withCurrencyUnit_CurrencyRoundingMode_same
[info] - test_withCurrencyUnit_Currency_nullCurrency
[info] - test_withCurrencyUnit_Currency_same
[info] - test_withCurrencyUnit_Currency_scaleProblem
[TestNG] Reporter org.testng.reporters.JUnitReportReporter@65eba78a failed
java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:901)
	at java.util.ArrayList$Itr.next(ArrayList.java:851)
	at org.testng.reporters.JUnitReportReporter.getNextConfiguration(JUnitReportReporter.java:220)
	at org.testng.reporters.JUnitReportReporter.generateReport(JUnitReportReporter.java:105)
	at org.testng.TestNG.generateReports(TestNG.java:1175)
	at org.testng.TestNG.run(TestNG.java:1102)
	at org.scalatest.testng.TestNGSuiteLike$class.run(TestNGSuiteLike.scala:261)
	at org.scalatest.testng.TestNGSuite.run(TestNGSuite.scala:67)
	at org.scalatest.testng.TestNGSuiteLike$class.runTestNG(TestNGSuiteLike.scala:248)
	at org.scalatest.testng.TestNGSuite.runTestNG(TestNGSuite.scala:67)
	at org.scalatest.testng.TestNGSuiteLike$class.run(TestNGSuiteLike.scala:149)
	at org.scalatest.testng.TestNGSuite.run(TestNGSuite.scala:67)
	at org.scalatest.tools.Framework.org$scalatest$tools$Framework$$runSuite(Framework.scala:314)
	at org.scalatest.tools.Framework$ScalaTestTask.execute(Framework.scala:472)
	at sbt.TestRunner.runTest$1(TestFramework.scala:76)
	at sbt.TestRunner.run(TestFramework.scala:85)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1$$anonfun$apply$8.apply(TestFramework.scala:202)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1$$anonfun$apply$8.apply(TestFramework.scala:202)
	at sbt.TestFramework$.sbt$TestFramework$$withContextLoader(TestFramework.scala:185)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1.apply(TestFramework.scala:202)
	at sbt.TestFramework$$anon$2$$anonfun$$init$$1.apply(TestFramework.scala:202)
	at sbt.TestFunction.apply(TestFramework.scala:207)
	at sbt.Tests$$anonfun$9.apply(Tests.scala:216)
	at sbt.Tests$$anonfun$9.apply(Tests.scala:216)
	at sbt.std.Transform$$anon$3$$anonfun$apply$2.apply(System.scala:44)
	at sbt.std.Transform$$anon$3$$anonfun$apply$2.apply(System.scala:44)
	at sbt.std.Transform$$anon$4.work(System.scala:63)
	at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:226)
	at sbt.Execute$$anonfun$submit$1$$anonfun$apply$1.apply(Execute.scala:226)
	at sbt.ErrorHandling$.wideConvert(ErrorHandling.scala:17)
	at sbt.Execute.work(Execute.scala:235)
	at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:226)
	at sbt.Execute$$anonfun$submit$1.apply(Execute.scala:226)
	at sbt.ConcurrentRestrictions$$anon$4$$anonfun$1.apply(ConcurrentRestrictions.scala:159)
	at sbt.CompletionService$$anon$2.call(CompletionService.scala:28)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
[info] TestMoneyUtils_BigMoney:
[info] - test_add
[info] - test_add_differentCurrencies
[info] - test_add_null1
[info] - test_add_null2
[info] - test_add_nullBoth
[info] - test_constructor
[info] - test_isNegative
[info] - test_isNegativeOrZero
[info] - test_isPositive
[info] - test_isPositiveOrZero
[info] - test_isZero
[info] - test_max1
[info] - test_max2
[info] - test_max_differentCurrencies
[info] - test_max_null1
[info] - test_max_null2
[info] - test_max_nullBoth
[info] - test_min1
[info] - test_min2
[info] - test_min_differentCurrencies
[info] - test_min_null1
[info] - test_min_null2
[info] - test_min_nullBoth
[info] - test_subtract
[info] - test_subtract_differentCurrencies
[info] - test_subtract_null1
[info] - test_subtract_null2
[info] - test_subtract_nullBoth
[info] TestMoneyFormatter:
[info] - test_getLocale
[info] - test_parseBigMoney_CharSequence
[info] - test_parseBigMoney_CharSequence_cannotParse
[info] - test_parseBigMoney_CharSequence_incomplete
[info] - test_parseBigMoney_CharSequence_incompleteEmptyParser
[info] - test_parseBigMoney_CharSequence_incompleteLongText
[info] - test_parseBigMoney_CharSequence_invalidCurrency
[info] - test_parseBigMoney_CharSequence_missingCurrency
[info] - test_parseBigMoney_CharSequence_notFullyParsed
[info] - test_parseBigMoney_CharSequence_nullCharSequence
[info] - test_parseBigMoney_noAmount
[info] - test_parseBigMoney_notFullyParsed
[info] - test_parseMoney_CharSequence
[info] - test_parseMoney_CharSequence_cannotParse
[info] - test_parseMoney_CharSequence_incomplete
[info] - test_parseMoney_CharSequence_invalidCurrency
[info] - test_parseMoney_CharSequence_notFullyParsed
[info] - test_parseMoney_CharSequence_nullCharSequence
[info] - test_parseMoney_noAmount
[info] - test_parseMoney_notFullyParsed
[info] - test_parse_CharSequenceInt(12.34 GBP,12.34,GBP,9,-1,false,true,true)
[info] - test_parse_CharSequenceInt(1,2.34 GBP,12.34,GBP,10,-1,false,true,true)
[info] - test_parse_CharSequenceInt(12,.34 GBP,12.34,GBP,10,-1,false,true,true)
[info] - test_parse_CharSequenceInt(12.,34 GBP,12.34,GBP,10,-1,false,true,true)
[info] - test_parse_CharSequenceInt(12.3,4 GBP,12.34,GBP,10,-1,false,true,true)
[info] - test_parse_CharSequenceInt(.12 GBP,0.12,GBP,7,-1,false,true,true)
[info] - test_parse_CharSequenceInt(12. GBP,12,GBP,7,-1,false,true,true)
[info] - test_parse_CharSequenceInt(12,34 GBP,1234,GBP,9,-1,false,true,true)
[info] - test_parse_CharSequenceInt(-12.34 GBP,-12.34,GBP,10,-1,false,true,true)
[info] - test_parse_CharSequenceInt(+12.34 GBP,12.34,GBP,10,-1,false,true,true)
[info] - test_parse_CharSequenceInt(12.34 GB,12.34,null,6,6,true,false,false)
[info] - test_parse_CharSequenceInt(,12.34 GBP,null,null,0,0,true,false,false)
[info] - test_parse_CharSequenceInt(12..34 GBP,12,null,3,3,true,false,false)
[info] - test_parse_CharSequenceInt(12,,34 GBP,12,null,2,2,true,false,false)
[info] - test_parse_CharSequenceInt(12.34 GBX,12.34,null,6,6,true,false,false)
[info] - test_parse_CharSequenceInt(12.34 GBPX,12.34,GBP,9,-1,false,false,true)
[info] - test_parse_CharSequenceInt_cannotParse
[info] - test_parse_CharSequenceInt_continueAfterDoubleComma
[info] - test_parse_CharSequenceInt_continueAfterDoubleDecimal
[info] - test_parse_CharSequenceInt_continueAfterSingleComma
[info] - test_parse_CharSequenceInt_incomplete
[info] - test_parse_CharSequenceInt_nullCharSequence
[info] - test_parse_CharSequenceInt_startIndexTooBig
[info] - test_parse_CharSequenceInt_startIndexTooSmall
[info] - test_parse_notFullyParsed
[info] - test_printIO_AppendableBigMoneyProvider
[info] - test_printIO_AppendableBigMoneyProvider_IOException
[info] - test_printIO_AppendableBigMoneyProvider_cannotPrint
[info] - test_printIO_AppendableBigMoneyProvider_nullAppendable
[info] - test_printIO_AppendableBigMoneyProvider_nullBigMoneyProvider
[info] - test_printParse_zeroChar
[info] - test_print_AppendableBigMoneyProvider
[info] - test_print_AppendableBigMoneyProvider_IOException *** FAILED ***
[info]   java.io.IOException:
[info]   at org.joda.money.format.TestMoneyFormatter$IOAppendable.append(TestMoneyFormatter.scala:37)
[info]   at org.joda.money.format.MoneyFormatterBuilder$Singletons$CODE$.print(MoneyFormatterBuilder.scala:23)
[info]   at org.joda.money.format.MultiPrinterParser$$anonfun$print$1.apply(MultiPrinterParser.scala:35)
[info]   at org.joda.money.format.MultiPrinterParser$$anonfun$print$1.apply(MultiPrinterParser.scala:34)
[info]   at scala.collection.IndexedSeqOptimized$class.foreach(IndexedSeqOptimized.scala:33)
[info]   at scala.collection.mutable.ArrayOps$ofRef.foreach(ArrayOps.scala:186)
[info]   at org.joda.money.format.MultiPrinterParser.print(MultiPrinterParser.scala:34)
[info]   at org.joda.money.format.MoneyFormatter.printIO(MoneyFormatter.scala:149)
[info]   at org.joda.money.format.MoneyFormatter.print(MoneyFormatter.scala:125)
[info]   at org.joda.money.format.TestMoneyFormatter.test_print_AppendableBigMoneyProvider_IOException(TestMoneyFormatter.scala:136)
[info]   ...
[info] - test_print_AppendableBigMoneyProvider_cannotPrint
[info] - test_print_AppendableBigMoneyProvider_nullAppendable
[info] - test_print_AppendableBigMoneyProvider_nullBigMoneyProvider
[info] - test_print_BigMoneyProvider
[info] - test_print_BigMoneyProvider_cannotPrint
[info] - test_print_BigMoneyProvider_nullBigMoneyProvider
[info] - test_serialization *** FAILED ***
[info]   java.io.InvalidClassException: org.joda.money.format.MoneyFormatterBuilder$Singletons$CODE$; no valid constructor
[info]   at java.io.ObjectStreamClass$ExceptionInfo.newInvalidClassException(ObjectStreamClass.java:150)
[info]   at java.io.ObjectStreamClass.checkDeserialize(ObjectStreamClass.java:790)
[info]   at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1775)
[info]   at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1351)
[info]   at java.io.ObjectInputStream.readArray(ObjectInputStream.java:1707)
[info]   at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1345)
[info]   at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2000)
[info]   at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1924)
[info]   at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1801)
[info]   at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1351)
[info]   ...
[info] - test_toString
[info] - test_toString_differentPrinterParser
[info] - test_withLocale
[info] - test_withLocale_nullLocale
[info] TestBigMoney:
[info] - test_abs_negative
[info] - test_abs_positive
[info] - test_compareTo_BigMoney
[info] - test_compareTo_Money
[info] - test_compareTo_currenciesDiffer
[info] - test_compareTo_wrongType
[info] - test_constructor_null1 *** FAILED ***
[info]   java.lang.AssertionError: expected [false] but found [true]
[info]   at org.testng.Assert.fail(Assert.java:94)
[info]   at org.testng.Assert.failNotEquals(Assert.java:513)
[info]   at org.testng.Assert.assertEqualsImpl(Assert.java:135)
[info]   at org.testng.Assert.assertEquals(Assert.java:116)
[info]   at org.testng.Assert.assertEquals(Assert.java:305)
[info]   at org.testng.Assert.assertEquals(Assert.java:315)
[info]   at org.joda.money.TestBigMoney.test_constructor_null1(TestBigMoney.scala:697)
[info]   at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
[info]   at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
[info]   at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
[info]   ...
[info] - test_constructor_null2 *** FAILED ***
[info]   java.lang.IllegalArgumentException: wrong number of arguments
[info]   at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
[info]   at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
[info]   at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
[info]   at java.lang.reflect.Constructor.newInstance(Constructor.java:422)
[info]   at org.joda.money.TestBigMoney.test_constructor_null2(TestBigMoney.scala:712)
[info]   at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
[info]   at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
[info]   at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
[info]   at java.lang.reflect.Method.invoke(Method.java:497)
[info]   at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:100)
[info]   ...
[info] - test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_negative
[info] - test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_nullBigDecimal
[info] - test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_nullCurrency
[info] - test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_nullRoundingMode
[info] - test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_positive
[info] - test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_roundHalfUp
[info] - test_convertRetainScale_CurrencyUnit_BigDecimal_RoundingMode_sameCurrency
[info] - test_convertedTo_CurrencyUnit_BigDecimal_negative
[info] - test_convertedTo_CurrencyUnit_BigDecimal_nullBigDecimal
[info] - test_convertedTo_CurrencyUnit_BigDecimal_nullCurrency
[info] - test_convertedTo_CurrencyUnit_BigDecimal_positive
[info] - test_convertedTo_CurrencyUnit_BigDecimal_sameCurrencyCorrectFactor
[info] - test_convertedTo_CurrencyUnit_BigDecimal_sameCurrencyWrongFactor
[info] - test_dividedBy_BigDecimalRoundingMode_negative
[info] - test_dividedBy_BigDecimalRoundingMode_nullBigDecimal
[info] - test_dividedBy_BigDecimalRoundingMode_nullRoundingMode
[info] - test_dividedBy_BigDecimalRoundingMode_one
[info] - test_dividedBy_BigDecimalRoundingMode_positive
[info] - test_dividedBy_BigDecimalRoundingMode_positive_halfUp
[info] - test_dividedBy_doubleRoundingMode_negative
[info] - test_dividedBy_doubleRoundingMode_nullRoundingMode
[info] - test_dividedBy_doubleRoundingMode_one
[info] - test_dividedBy_doubleRoundingMode_positive
[info] - test_dividedBy_doubleRoundingMode_positive_halfUp
[info] - test_dividedBy_long_negative
[info] - test_dividedBy_long_one
[info] - test_dividedBy_long_positive
[info] - test_dividedBy_long_positive_roundDown
[info] - test_dividedBy_long_positive_roundUp
[info] - test_equals_false
[info] - test_equals_hashCode_positive
[info] - test_factory_from_BigMoneyProvider
[info] - test_factory_from_BigMoneyProvider_badProvider
[info] - test_factory_from_BigMoneyProvider_nullBigMoneyProvider
[info] - test_factory_ofMajor_Currency_long
[info] - test_factory_ofMajor_Currency_long_nullCurrency
[info] - test_factory_ofMinor_Currency_long
[info] - test_factory_ofMinor_Currency_long_nullCurrency
[info] - test_factory_ofScale_Currency_BigDecimal_int
[info] - test_factory_ofScale_Currency_BigDecimal_int_JPY_RoundingMode_UP
[info] - test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_DOWN
[info] - test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_UNNECESSARY
[info] - test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_negativeScale
[info] - test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_nullBigDecimal
[info] - test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_nullCurrency
[info] - test_factory_ofScale_Currency_BigDecimal_int_RoundingMode_nullRoundingMode
[info] - test_factory_ofScale_Currency_BigDecimal_invalidScale
[info] - test_factory_ofScale_Currency_BigDecimal_negativeScale
[info] - test_factory_ofScale_Currency_BigDecimal_nullBigDecimal
[info] - test_factory_ofScale_Currency_BigDecimal_nullCurrency
[info] - test_factory_ofScale_Currency_long_int
[info] - test_factory_ofScale_Currency_long_int_negativeScale
[info] - test_factory_ofScale_Currency_long_int_nullCurrency
[info] - test_factory_of_Currency_BigDecimal
[info] - test_factory_of_Currency_BigDecimal_nullBigDecimal
[info] - test_factory_of_Currency_BigDecimal_nullCurrency
[info] - test_factory_of_Currency_double
[info] - test_factory_of_Currency_double_big
[info] - test_factory_of_Currency_double_medium
[info] - test_factory_of_Currency_double_nullCurrency
[info] - test_factory_of_Currency_double_trailingZero1
[info] - test_factory_of_Currency_double_trailingZero2
[info] - test_factory_of_Currency_subClass1
[info] - test_factory_of_Currency_subClass2 *** FAILED ***
[info]   java.lang.NumberFormatException: Zero length BigInteger
[info]   at java.math.BigInteger.<init>(BigInteger.java:293)
[info]   at org.joda.money.TestBigMoney$BadInteger$1.<init>(TestBigMoney.scala:129)
[info]   at org.joda.money.TestBigMoney$BadDecimal$2.unscaledValue(TestBigMoney.scala:133)
[info]   at org.joda.money.BigMoney$.of(BigMoney.scala:40)
[info]   at org.joda.money.TestBigMoney.test_factory_of_Currency_subClass2(TestBigMoney.scala:138)
[info]   at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
[info]   at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
[info]   at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
[info]   at java.lang.reflect.Method.invoke(Method.java:497)
[info]   at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:100)
[info]   ...
[info] - test_factory_parse(GBP 2.43,GBP,2.43,2)
[info] - test_factory_parse(GBP +12.57,GBP,12.57,2)
[info] - test_factory_parse(GBP -5.87,GBP,-5.87,2)
[info] - test_factory_parse(GBP 0.99,GBP,0.99,2)
[info] - test_factory_parse(GBP .99,GBP,0.99,2)
[info] - test_factory_parse(GBP +.99,GBP,0.99,2)
[info] - test_factory_parse(GBP +0.99,GBP,0.99,2)
[info] - test_factory_parse(GBP -.99,GBP,-0.99,2)
[info] - test_factory_parse(GBP -0.99,GBP,-0.99,2)
[info] - test_factory_parse(GBP 0,GBP,0,0)
[info] - test_factory_parse(GBP 2,GBP,2,0)
[info] - test_factory_parse(GBP 123.,GBP,123,0)
[info] - test_factory_parse(GBP3,GBP,3,0)
[info] - test_factory_parse(GBP3.10,GBP,3.10,2)
[info] - test_factory_parse(GBP  3.10,GBP,3.10,2)
[info] - test_factory_parse(GBP   3.10,GBP,3.10,2)
[info] - test_factory_parse(GBP                           3.10,GBP,3.10,2)
[info] - test_factory_parse(GBP 123.456789,GBP,123.456789,6)
[info] - test_factory_parse_String_badCurrency
[info] - test_factory_parse_String_exponent
[info] - test_factory_parse_String_nullString
[info] - test_factory_parse_String_tooShort
[info] - test_factory_total_CurrencyUnitArray_1
[info] - test_factory_total_CurrencyUnitArray_3
[info] - test_factory_total_CurrencyUnitArray_3Mixed
[info] - test_factory_total_CurrencyUnitArray_3Money
[info] - test_factory_total_CurrencyUnitArray_badProvider
[info] - test_factory_total_CurrencyUnitArray_currenciesDiffer
[info] - test_factory_total_CurrencyUnitArray_currenciesDifferInArray
[info] - test_factory_total_CurrencyUnitArray_empty
[info] - test_factory_total_CurrencyUnitArray_nullFirst
[info] - test_factory_total_CurrencyUnitArray_nullNotFirst
[info] - test_factory_total_CurrencyUnitIterable
[info] - test_factory_total_CurrencyUnitIterable_Mixed
[info] - test_factory_total_CurrencyUnitIterable_badProvider
[info] - test_factory_total_CurrencyUnitIterable_currenciesDiffer
[info] - test_factory_total_CurrencyUnitIterable_currenciesDifferInIterable
[info] - test_factory_total_CurrencyUnitIterable_empty
[info] - test_factory_total_CurrencyUnitIterable_nullFirst
[info] - test_factory_total_CurrencyUnitIterable_nullNotFirst
[info] - test_factory_total_CurrencyUnitVarargs_1
[info] - test_factory_total_CurrencyUnitVarargs_3
[info] - test_factory_total_CurrencyUnitVarargs_3Mixed
[info] - test_factory_total_CurrencyUnitVarargs_badProvider
[info] - test_factory_total_CurrencyUnitVarargs_currenciesDiffer
[info] - test_factory_total_CurrencyUnitVarargs_currenciesDifferInArray
[info] - test_factory_total_CurrencyUnitVarargs_empty
[info] - test_factory_total_CurrencyUnitVarargs_nullFirst
[info] - test_factory_total_CurrencyUnitVarargs_nullNotFirst
[info] - test_factory_total_Iterable
[info] - test_factory_total_Iterable_Mixed
[info] - test_factory_total_Iterable_badProvider
[info] - test_factory_total_Iterable_currenciesDiffer
[info] - test_factory_total_Iterable_empty
[info] - test_factory_total_Iterable_nullFirst
[info] - test_factory_total_Iterable_nullNotFirst
[info] - test_factory_total_array_1BigMoney
[info] - test_factory_total_array_3Mixed
[info] - test_factory_total_array_3Money
[info] - test_factory_total_array_badProvider
[info] - test_factory_total_array_currenciesDiffer
[info] - test_factory_total_array_empty
[info] - test_factory_total_array_nullFirst
[info] - test_factory_total_array_nullNotFirst
[info] - test_factory_total_varargs_1BigMoney
[info] - test_factory_total_varargs_3Mixed
[info] - test_factory_total_varargs_badProvider
[info] - test_factory_total_varargs_currenciesDiffer
[info] - test_factory_total_varargs_empty
[info] - test_factory_total_varargs_nullFirst
[info] - test_factory_total_varargs_nullNotFirst
[info] - test_factory_zero_Currency
[info] - test_factory_zero_Currency_int
[info] - test_factory_zero_Currency_int_negativeScale
[info] - test_factory_zero_Currency_int_nullCurrency
[info] - test_factory_zero_Currency_nullCurrency
[info] - test_getAmountMajorInt_negative
[info] - test_getAmountMajorInt_positive
[info] - test_getAmountMajorInt_tooBigNegative
[info] - test_getAmountMajorInt_tooBigPositive
[info] - test_getAmountMajorLong_negative
[info] - test_getAmountMajorLong_positive
[info] - test_getAmountMajorLong_tooBigNegative
[info] - test_getAmountMajorLong_tooBigPositive
[info] - test_getAmountMajor_negative
[info] - test_getAmountMajor_positive
[info] - test_getAmountMinorInt_negative
[info] - test_getAmountMinorInt_positive
[info] - test_getAmountMinorInt_tooBigNegative
[info] - test_getAmountMinorInt_tooBigPositive
[info] - test_getAmountMinorLong_negative
[info] - test_getAmountMinorLong_positive
[info] - test_getAmountMinorLong_tooBigNegative
[info] - test_getAmountMinorLong_tooBigPositive
[info] - test_getAmountMinor_negative
[info] - test_getAmountMinor_positive
[info] - test_getAmount_negative
[info] - test_getAmount_positive
[info] - test_getCurrencyUnit_EUR
[info] - test_getCurrencyUnit_GBP
[info] - test_getMinorPart_negative
[info] - test_getMinorPart_positive
[info] - test_getScale_GBP
[info] - test_getScale_JPY
[info] - test_isCurrencyScale_GBP
[info] - test_isCurrencyScale_JPY
[info] - test_isEqual
[info] - test_isEqual_Money
[info] - test_isEqual_currenciesDiffer
[info] - test_isGreaterThan
[info] - test_isGreaterThan_currenciesDiffer
[info] - test_isLessThan
[info] - test_isLessThan_currenciesDiffer
[info] - test_isNegative
[info] - test_isNegativeOrZero
[info] - test_isPositive
[info] - test_isPositiveOrZero
[info] - test_isSameCurrency_BigMoney_different
[info] - test_isSameCurrency_BigMoney_same
[info] - test_isSameCurrency_Money_different
[info] - test_isSameCurrency_Money_nullMoney
[info] - test_isSameCurrency_Money_same
[info] - test_isZero
[info] - test_minusMajor_negative
[info] - test_minusMajor_positive
[info] - test_minusMajor_zero
[info] - test_minusMinor_negative
[info] - test_minusMinor_positive
[info] - test_minusMinor_scale
[info] - test_minusMinor_zero
[info] - test_minusRetainScale_BigDecimalRoundingMode_negative
[info] - test_minusRetainScale_BigDecimalRoundingMode_nullBigDecimal
[info] - test_minusRetainScale_BigDecimalRoundingMode_nullRoundingMode
[info] - test_minusRetainScale_BigDecimalRoundingMode_positive
[info] - test_minusRetainScale_BigDecimalRoundingMode_roundDown
[info] - test_minusRetainScale_BigDecimalRoundingMode_roundUnecessary
[info] - test_minusRetainScale_BigDecimalRoundingMode_zero
[info] - test_minusRetainScale_BigMoneyProviderRoundingMode_negative
[info] - test_minusRetainScale_BigMoneyProviderRoundingMode_nullBigMoneyProvider
[info] - test_minusRetainScale_BigMoneyProviderRoundingMode_nullRoundingMode
[info] - test_minusRetainScale_BigMoneyProviderRoundingMode_positive
[info] - test_minusRetainScale_BigMoneyProviderRoundingMode_roundDown
[info] - test_minusRetainScale_BigMoneyProviderRoundingMode_roundUnecessary
[info] - test_minusRetainScale_BigMoneyProviderRoundingMode_zero
[info] - test_minusRetainScale_doubleRoundingMode_negative
[info] - test_minusRetainScale_doubleRoundingMode_nullRoundingMode
[info] - test_minusRetainScale_doubleRoundingMode_positive
[info] - test_minusRetainScale_doubleRoundingMode_roundDown
[info] - test_minusRetainScale_doubleRoundingMode_roundUnecessary
[info] - test_minusRetainScale_doubleRoundingMode_zero
[info] - test_minus_BigDecimal_negative
[info] - test_minus_BigDecimal_nullBigDecimal
[info] - test_minus_BigDecimal_positive
[info] - test_minus_BigDecimal_scale
[info] - test_minus_BigDecimal_zero
[info] - test_minus_BigMoneyProvider_Money
[info] - test_minus_BigMoneyProvider_badProvider
[info] - test_minus_BigMoneyProvider_currencyMismatch
[info] - test_minus_BigMoneyProvider_negative
[info] - test_minus_BigMoneyProvider_nullBigMoneyProvider
[info] - test_minus_BigMoneyProvider_positive
[info] - test_minus_BigMoneyProvider_scale
[info] - test_minus_BigMoneyProvider_zero
[info] - test_minus_Iterable_BigMoney
[info] - test_minus_Iterable_BigMoneyProvider
[info] - test_minus_Iterable_Mixed
[info] - test_minus_Iterable_Money
[info] - test_minus_Iterable_badProvider
[info] - test_minus_Iterable_currencyMismatch
[info] - test_minus_Iterable_nullEntry
[info] - test_minus_Iterable_nullIterable
[info] - test_minus_Iterable_zero
[info] - test_minus_double_negative
[info] - test_minus_double_positive
[info] - test_minus_double_scale
[info] - test_minus_double_zero
[info] - test_multipliedBy_BigDecimal_negative
[info] - test_multipliedBy_BigDecimal_nullBigDecimal
[info] - test_multipliedBy_BigDecimal_one
[info] - test_multipliedBy_BigDecimal_positive
[info] - test_multipliedBy_doubleRoundingMode_negative
[info] - test_multipliedBy_doubleRoundingMode_one
[info] - test_multipliedBy_doubleRoundingMode_positive
[info] - test_multipliedBy_long_negative
[info] - test_multipliedBy_long_one
[info] - test_multipliedBy_long_positive
[info] - test_multiplyRetainScale_BigDecimalRoundingMode_negative
[info] - test_multiplyRetainScale_BigDecimalRoundingMode_nullBigDecimal
[info] - test_multiplyRetainScale_BigDecimalRoundingMode_nullRoundingMode
[info] - test_multiplyRetainScale_BigDecimalRoundingMode_one
[info] - test_multiplyRetainScale_BigDecimalRoundingMode_positive
[info] - test_multiplyRetainScale_BigDecimalRoundingMode_positive_halfUp
[info] - test_multiplyRetainScale_doubleRoundingMode_negative
[info] - test_multiplyRetainScale_doubleRoundingMode_nullRoundingMode
[info] - test_multiplyRetainScale_doubleRoundingMode_one
[info] - test_multiplyRetainScale_doubleRoundingMode_positive
[info] - test_multiplyRetainScale_doubleRoundingMode_positive_halfUp
[info] - test_negated_negative
[info] - test_negated_positive
[info] - test_negated_zero
[info] - test_nonNull_BigMoneyCurrencyUnit_nonNull
[info] - test_nonNull_BigMoneyCurrencyUnit_nonNullCurrencyMismatch
[info] - test_nonNull_BigMoneyCurrencyUnit_nonNull_nullCurrency
[info] - test_nonNull_BigMoneyCurrencyUnit_null
[info] - test_nonNull_BigMoneyCurrencyUnit_null_nullCurrency
[info] - test_plusMajor_negative
[info] - test_plusMajor_positive
[info] - test_plusMajor_zero
[info] - test_plusMinor_negative
[info] - test_plusMinor_positive
[info] - test_plusMinor_scale
[info] - test_plusMinor_zero
[info] - test_plusRetainScale_BigDecimalRoundingMode_negative
[info] - test_plusRetainScale_BigDecimalRoundingMode_nullBigDecimal
[info] - test_plusRetainScale_BigDecimalRoundingMode_nullRoundingMode
[info] - test_plusRetainScale_BigDecimalRoundingMode_positive
[info] - test_plusRetainScale_BigDecimalRoundingMode_roundDown
[info] - test_plusRetainScale_BigDecimalRoundingMode_roundUnecessary
[info] - test_plusRetainScale_BigDecimalRoundingMode_zero
[info] - test_plusRetainScale_BigMoneyProviderRoundingMode_negative
[info] - test_plusRetainScale_BigMoneyProviderRoundingMode_nullBigDecimal
[info] - test_plusRetainScale_BigMoneyProviderRoundingMode_nullRoundingMode
[info] - test_plusRetainScale_BigMoneyProviderRoundingMode_positive
[info] - test_plusRetainScale_BigMoneyProviderRoundingMode_roundDown
[info] - test_plusRetainScale_BigMoneyProviderRoundingMode_roundUnecessary
[info] - test_plusRetainScale_BigMoneyProviderRoundingMode_zero
[info] - test_plusRetainScale_doubleRoundingMode_negative
[info] - test_plusRetainScale_doubleRoundingMode_nullRoundingMode
[info] - test_plusRetainScale_doubleRoundingMode_positive
[info] - test_plusRetainScale_doubleRoundingMode_roundDown
[info] - test_plusRetainScale_doubleRoundingMode_roundUnecessary
[info] - test_plusRetainScale_doubleRoundingMode_zero
[info] - test_plus_BigDecimal_negative
[info] - test_plus_BigDecimal_nullBigDecimal
[info] - test_plus_BigDecimal_positive
[info] - test_plus_BigDecimal_scale
[info] - test_plus_BigDecimal_zero
[info] - test_plus_BigMoneyProvider_Money
[info] - test_plus_BigMoneyProvider_badProvider
[info] - test_plus_BigMoneyProvider_currencyMismatch
[info] - test_plus_BigMoneyProvider_negative
[info] - test_plus_BigMoneyProvider_nullBigMoneyProvider
[info] - test_plus_BigMoneyProvider_positive
[info] - test_plus_BigMoneyProvider_scale
[info] - test_plus_BigMoneyProvider_zero
[info] - test_plus_Iterable_BigMoney
[info] - test_plus_Iterable_BigMoneyProvider
[info] - test_plus_Iterable_Mixed
[info] - test_plus_Iterable_Money
[info] - test_plus_Iterable_badProvider
[info] - test_plus_Iterable_currencyMismatch
[info] - test_plus_Iterable_nullEntry
[info] - test_plus_Iterable_nullIterable
[info] - test_plus_Iterable_zero
[info] - test_plus_double_negative
[info] - test_plus_double_positive
[info] - test_plus_double_scale
[info] - test_plus_double_zero
[info] - test_round_0down
[info] - test_round_0up
[info] - test_round_1down
[info] - test_round_1up
[info] - test_round_2down
[info] - test_round_2up
[info] - test_round_3
[info] - test_round_M1down
[info] - test_round_M1up
[info] - test_scaleNormalization1
[info] - test_scaleNormalization2
[info] - test_scaleNormalization3
[info] - test_serialization
[info] - test_serialization_invalidDecimalPlaces
[info] - test_serialization_invalidNumericCode
[info] - test_toBigMoney
[info] - test_toMoney
[info] - test_toMoney_RoundingMode
[info] - test_toMoney_RoundingMode_round
[info] - test_toString_negative
[info] - test_toString_positive
[info] - test_withAmount_BigDecimal
[info] - test_withAmount_BigDecimal_nullBigDecimal
[info] - test_withAmount_BigDecimal_same
[info] - test_withAmount_double
[info] - test_withAmount_double_same
[info] - test_withCurrencyScale_intRoundingMode_less
[info] - test_withCurrencyScale_intRoundingMode_lessJPY
[info] - test_withCurrencyScale_intRoundingMode_more
[info] - test_withCurrencyScale_int_less
[info] - test_withCurrencyScale_int_more
[info] - test_withCurrencyScale_int_same
[info] - test_withCurrencyUnit_Currency
[info] - test_withCurrencyUnit_Currency_differentCurrencyScale
[info] - test_withCurrencyUnit_Currency_nullCurrency
[info] - test_withCurrencyUnit_Currency_same
[info] - test_withScale_intRoundingMode_less
[info] - test_withScale_intRoundingMode_more
[info] - test_withScale_int_less
[info] - test_withScale_int_more
[info] - test_withScale_int_same
[info] TestCurrencyMismatchException:
[info] - test_new_GBPEUR
[info] - test_new_GBPnull
[info] - test_new_nullEUR
[info] - test_new_nullnull
[info] TestMoneyUtils_Money:
[info] - test_add
[info] - test_add_differentCurrencies
[info] - test_add_null1
[info] - test_add_null2
[info] - test_add_nullBoth
[info] - test_checkNotNull_notNull
[info] - test_checkNotNull_null
[info] - test_isNegative
[info] - test_isNegativeOrZero
[info] - test_isPositive
[info] - test_isPositiveOrZero
[info] - test_isZero
[info] - test_max1
[info] - test_max2
[info] - test_max_differentCurrencies
[info] - test_max_null1
[info] - test_max_null2
[info] - test_max_nullBoth
[info] - test_min1
[info] - test_min2
[info] - test_min_differentCurrencies
[info] - test_min_null1
[info] - test_min_null2
[info] - test_min_nullBoth
[info] - test_subtract
[info] - test_subtract_differentCurrencies
[info] - test_subtract_null1
[info] - test_subtract_null2
[info] - test_subtract_nullBoth
[info] TestIllegalCurrencyException:
[info] - test_String
[info] - test_String_nullString
[info] TestMoneyFormatterBuilder:
[info] - test_appendAmount(GBP 2.34,2.34)
[info] - test_appendAmount(GBP 23.45,23.45)
[info] - test_appendAmount(GBP 234.56,234.56)
[info] - test_appendAmount(GBP 2345.67,2,345.67)
[info] - test_appendAmount(GBP 1234567.89,1,234,567.89)
[info] - test_appendAmount(GBP 1234.56789,1,234.567,89)
[info] - test_appendAmount(GBP 1234567891234.1234567891,1,234,567,891,234.123,456,789,1)
[info] - test_appendAmount(GBP -234.56,-234.56)
[info] - test_appendAmountLocalized(GBP 2.34,2,34)
[info] - test_appendAmountLocalized(GBP 23.45,23,45)
[info] - test_appendAmountLocalized(GBP 234.56,234,56)
[info] - test_appendAmountLocalized(GBP 2345.67,2 345,67)
[info] - test_appendAmountLocalized(GBP 1234567.89,1 234 567,89)
[info] - test_appendAmountLocalized(GBP 1234.56789,1 234,567 89)
[info] - test_appendAmountLocalized(GBP -234.56,-234,56)
[info] - test_appendAmountLocalized_GBP_1234_56789_US
[info] - test_appendAmountLocalized_JPY_2345
[info] - test_appendAmount_3dp_BHD
[info] - test_appendAmount_GBP_1234_56789_France
[info] - test_appendAmount_JPY_2345
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2123456,2123456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2345.34,2345.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],23456.34,23456.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],234567.34,234567.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2345678.34,2345678.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.3456,2.3456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.34567,2.34567)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.345678,2.345678)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.3456789,2.3456789)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2123456,2 123 456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2345.34,2 345.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],23456.34,23 456.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],234567.34,234 567.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2345678.34,2 345 678.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.3456,2.345 6)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.34567,2.345 67)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.345678,2.345 678)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.3456789,2.345 678 9)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.3456,2.3456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.34567,2.34567)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.345678,2.345678)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.3456789,2.3456789)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2,2.)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2123456,2,123,456.)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],2,2)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2,2)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2123456,2-1-2-3-4-5-6)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.34,2.3-4)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],23.34,2-3.3-4)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],234.34,2-3-4.3-4)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2345.34,2-3-4-5.3-4)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],23456.34,2-3-4-5-6.3-4)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],234567.34,2-3-4-5-6-7.3-4)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2345678.34,2-3-4-5-6-7-8.3-4)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.345,2.3-4-5)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.3456,2.3-4-5-6)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.34567,2.3-4-5-6-7)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.345678,2.3-4-5-6-7-8)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.3456789,2.3-4-5-6-7-8-9)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2123456,2-12-34-56)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],234.34,2-34.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2345.34,23-45.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],23456.34,2-34-56.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],234567.34,23-45-67.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2345678.34,2-34-56-78.34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.345,2.34-5)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.3456,2.34-56)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.34567,2.34-56-7)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.345678,2.34-56-78)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.3456789,2.34-56-78-9)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2,2@)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2123456,212,3456@)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.34,2@34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],23.34,23@34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],234.34,234@34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2345.34,2345@34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],23456.34,2,3456@34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],234567.34,23,4567@34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2345678.34,234,5678@34)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.345,2@345)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.3456,2@3456)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.34567,2@3456,7)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.345678,2@3456,78)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.3456789,2@3456,789)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2,C)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2123456,C,BCD,EFG)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.34,C.DE)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],23.34,CD.DE)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],234.34,CDE.DE)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2345.34,C,DEF.DE)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],23456.34,CD,EFG.DE)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],234567.34,CDE,FGH.DE)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2345678.34,C,DEF,GHI.DE)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.345,C.DEF)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.3456,C.DEF,G)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.34567,C.DEF,GH)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.345678,C.DEF,GHI)
[info] - test_appendAmount_MoneyAmountStyle_BHD(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.3456789,C.DEF,GHI,J)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2123456,2123456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2345.34,2345.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],23456.34,23456.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],234567.34,234567.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2345678.34,2345678.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.3456,2.3456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.34567,2.34567)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.345678,2.345678)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.3456789,2.3456789)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2123456,2 123 456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2345.34,2 345.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],23456.34,23 456.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],234567.34,234 567.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2345678.34,2 345 678.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.3456,2.345 6)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.34567,2.345 67)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.345678,2.345 678)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.3456789,2.345 678 9)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.3456,2.3456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.34567,2.34567)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.345678,2.345678)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.3456789,2.3456789)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2,2.)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2123456,2,123,456.)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],2,2)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2,2)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2123456,2-1-2-3-4-5-6)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.34,2.3-4)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],23.34,2-3.3-4)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],234.34,2-3-4.3-4)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2345.34,2-3-4-5.3-4)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],23456.34,2-3-4-5-6.3-4)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],234567.34,2-3-4-5-6-7.3-4)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2345678.34,2-3-4-5-6-7-8.3-4)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.345,2.3-4-5)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.3456,2.3-4-5-6)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.34567,2.3-4-5-6-7)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.345678,2.3-4-5-6-7-8)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.3456789,2.3-4-5-6-7-8-9)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2123456,2-12-34-56)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],234.34,2-34.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2345.34,23-45.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],23456.34,2-34-56.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],234567.34,23-45-67.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2345678.34,2-34-56-78.34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.345,2.34-5)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.3456,2.34-56)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.34567,2.34-56-7)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.345678,2.34-56-78)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.3456789,2.34-56-78-9)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2,2@)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2123456,212,3456@)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.34,2@34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],23.34,23@34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],234.34,234@34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2345.34,2345@34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],23456.34,2,3456@34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],234567.34,23,4567@34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2345678.34,234,5678@34)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.345,2@345)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.3456,2@3456)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.34567,2@3456,7)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.345678,2@3456,78)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.3456789,2@3456,789)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2,C)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2123456,C,BCD,EFG)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.34,C.DE)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],23.34,CD.DE)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],234.34,CDE.DE)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2345.34,C,DEF.DE)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],23456.34,CD,EFG.DE)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],234567.34,CDE,FGH.DE)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2345678.34,C,DEF,GHI.DE)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.345,C.DEF)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.3456,C.DEF,G)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.34567,C.DEF,GH)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.345678,C.DEF,GHI)
[info] - test_appendAmount_MoneyAmountStyle_GBP(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.3456789,C.DEF,GHI,J)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2123456,2123456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2345.34,2345.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],23456.34,23456.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],234567.34,234567.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2345678.34,2345678.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.3456,2.3456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.34567,2.34567)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.345678,2.345678)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','NONE,,','3',false',false],2.3456789,2.3456789)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',false],2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2123456,2 123 456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2345.34,2 345.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],23456.34,23 456.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],234567.34,234 567.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2345678.34,2 345 678.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.3456,2.345 6)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.34567,2.345 67)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.345678,2.345 678)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL, ','3',false',false],2.3456789,2.345 678 9)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.3456,2.3456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.34567,2.34567)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.345678,2.345678)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','BEFORE_DECIMAL_POINT,,','3',false',false],2.3456789,2.3456789)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2,2.)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2123456,2,123,456.)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',true',false],2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],2,2)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2,2)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2123456,2,123,456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-234.34,234.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2345.34,2,345.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-23456.34,23,456.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-234567.34,234,567.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2345678.34,2,345,678.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.345,2.345)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.3456,2.345,6)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.34567,2.345,67)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.345678,2.345,678)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,,','3',false',true],-2.3456789,2.345,678,9)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2123456,2-1-2-3-4-5-6)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.34,2.3-4)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],23.34,2-3.3-4)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],234.34,2-3-4.3-4)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2345.34,2-3-4-5.3-4)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],23456.34,2-3-4-5-6.3-4)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],234567.34,2-3-4-5-6-7.3-4)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2345678.34,2-3-4-5-6-7-8.3-4)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.345,2.3-4-5)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.3456,2.3-4-5-6)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.34567,2.3-4-5-6-7)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.345678,2.3-4-5-6-7-8)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','1',false',false],2.3456789,2.3-4-5-6-7-8-9)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2,2)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2123456,2-12-34-56)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.34,2.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],23.34,23.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],234.34,2-34.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2345.34,23-45.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],23456.34,2-34-56.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],234567.34,23-45-67.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2345678.34,2-34-56-78.34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.345,2.34-5)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.3456,2.34-56)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.34567,2.34-56-7)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.345678,2.34-56-78)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','.','FULL,-','2',false',false],2.3456789,2.34-56-78-9)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2,2@)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2123456,212,3456@)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.34,2@34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],23.34,23@34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],234.34,234@34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2345.34,2345@34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],23456.34,2,3456@34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],234567.34,23,4567@34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2345678.34,234,5678@34)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.345,2@345)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.3456,2@3456)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.34567,2@3456,7)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.345678,2@3456,78)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['0','+','-','@','FULL,,','4',true',false],2.3456789,2@3456,789)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2,C)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2123456,C,BCD,EFG)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.34,C.DE)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],23.34,CD.DE)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],234.34,CDE.DE)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2345.34,C,DEF.DE)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],23456.34,CD,EFG.DE)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],234567.34,CDE,FGH.DE)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2345678.34,C,DEF,GHI.DE)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.345,C.DEF)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.3456,C.DEF,G)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.34567,C.DEF,GH)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.345678,C.DEF,GHI)
[info] - test_appendAmount_MoneyAmountStyle_JPY(MoneyAmountStyle['A','+','-','.','FULL,,','3',false',false],2.3456789,C.DEF,GHI,J)
[info] - test_appendAmount_MoneyAmountStyle_JPY_issue49
[info] - test_appendAmount_MoneyAmountStyle_null
[info] - test_appendAmount_parseExcessGrouping
[info] - test_appendAmount_parseExtendedGroupingSize(GBP 2.34,2.34)
[info] - test_appendAmount_parseExtendedGroupingSize(GBP 23.45,23.45)
[info] - test_appendAmount_parseExtendedGroupingSize(GBP 234.56,234.56)
[info] - test_appendAmount_parseExtendedGroupingSize(GBP 2345.67,2,345.67)
[info] - test_appendAmount_parseExtendedGroupingSize(GBP 1234567.89,12,34,567.89)
[info] - test_appendAmount_parseExtendedGroupingSize(GBP 1234.56789,1,234.567,89)
[info] - test_appendAmount_parseExtendedGroupingSize(GBP 1234567891234.1234567891,12,34,56,78,91,234.123,45,67,89,1)
[info] - test_appendAmount_parseExtendedGroupingSize(GBP -234.56,-234.56)
[info] - test_appendCurrencyCode_parse_empty
[info] - test_appendCurrencyCode_parse_ok
[info] - test_appendCurrencyCode_parse_tooShort
[info] - test_appendCurrencyCode_print
[info] - test_appendCurrencyNumeric3Code_parse_badCurrency
[info] - test_appendCurrencyNumeric3Code_parse_empty
[info] - test_appendCurrencyNumeric3Code_parse_ok
[info] - test_appendCurrencyNumeric3Code_parse_tooShort
[info] - test_appendCurrencyNumeric3Code_print
[info] - test_appendCurrencyNumericCode_parse_badCurrency
[info] - test_appendCurrencyNumericCode_parse_empty
[info] - test_appendCurrencyNumericCode_parse_ok
[info] - test_appendCurrencyNumericCode_parse_ok_notPadded1
[info] - test_appendCurrencyNumericCode_parse_ok_notPadded2
[info] - test_appendCurrencyNumericCode_parse_ok_padded
[info] - test_appendCurrencyNumericCode_parse_tooShort
[info] - test_appendCurrencyNumericCode_print
[info] - test_appendCurrencySymbolLocalized_parse
[info] - test_appendCurrencySymbolLocalized_print
[info] - test_appendLiteral_parse_noMatch
[info] - test_appendLiteral_parse_ok
[info] - test_appendLiteral_parse_tooShort
[info] - test_appendLiteral_print
[info] - test_appendLiteral_print_empty
[info] - test_appendLiteral_print_null
[info] - test_appendSigned_PN
[info] - test_appendSigned_PZN
[info] - test_appendSigned_PZN_edgeCases
[info] - test_append_MoneyFormatter
[info] - test_append_MoneyPrinterMoneyParser_parser
[info] - test_append_MoneyPrinterMoneyParser_printer
[info] - test_append_MoneyPrinter_nullMoneyPrinter_nullMoneyParser
[info] - test_empty
[info] - test_toFormatter_Locale
[info] - test_toFormatter_defaultLocale
[info] Fast optimizing /Users/eric/Work/scala-joda-money/js/target/scala-2.11/scala-joda-money-test-fastopt.js
[info] Run completed in 2 seconds, 182 milliseconds.
[info] Total number of tests run: 1485
[info] Suites: completed 11, aborted 0
[info] Tests: succeeded 1480, failed 5, canceled 0, ignored 0, pending 0
[info] *** 5 TESTS FAILED ***
[error] Failed tests:
[error] 	org.joda.money.TestBigMoney
[error] 	org.joda.money.format.TestMoneyFormatter
[error] (scalajodamoneyJVM/test:test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 10 s, completed Nov 9, 2016 1:24:24 PM
eric@Erics-MacBook-Pro:~/Work/scala-joda-money$

```