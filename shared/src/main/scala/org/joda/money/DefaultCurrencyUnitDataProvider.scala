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
  // #Code,Numeric,DecPlaces,CountryCodes
  private val CountryCodeData: Seq[Tuple4[String, Int, Int, Seq[String]]] = Seq(
    ("AED",784,2,Seq("AE")),
    ("AFN",971,2,Seq("AF")),
    ("ALL",8,2,Seq("AL")),
    ("AMD",51,0,Seq("AM")),
    ("ANG",532,2,Seq("AN")),
    ("AOA",973,1,Seq("AO")),
    ("ARS",32,2,Seq("AR")),
    ("AUD",36,2,Seq("AU","CX","CC","HM","KI","NR","NF","TV")),
    ("AWG",533,2,Seq("AW")),
    ("AZN",944,2,Seq("AZ")),
    ("BAM",977,2,Seq("BA")),
    ("BBD",52,2,Seq("BB")),
    ("BDT",50,2,Seq("BD")),
    ("BGN",975,2,Seq("BG")),
    ("BHD",48,3,Seq("BH")),
    ("BIF",108,0,Seq("BI")),
    ("BMD",60,2,Seq("BM")),
    ("BND",96,2,Seq("BN")),
    ("BOB",68,2,Seq("BO")),
    //("#BOV",984,2,Seq("BO")),
    ("BRL",986,2,Seq("BR")),
    ("BSD",44,2,Seq("BS")),
    ("BTN",64,2,Seq("BT")),
    ("BWP",72,2,Seq("BW")),
    ("BYR",974,0,Seq("BY")),
    ("BZD",84,2,Seq("BZ")),
    ("CAD",124,2,Seq("CA")),
    ("CDF",976,2,Seq("CD")),
    //("#CHE",947,2,Seq("CH")),
    ("CHF",756,2,Seq("CH","LI")),
    //("#CHW",948,2,Seq("CH")),
    //("#CLF",990,0,Seq("CL")),
    ("CLP",152,0,Seq("CL")),
    ("CNY",156,2,Seq("CN")),
    ("COP",170,2,Seq("CO")),
    //("#COU",970,2,Seq("CO")),
    ("CRC",188,2,Seq("CR")),
    //("#CUC",931,2,Seq("CU")),
    ("CUP",192,2,Seq("CU")),
    ("CVE",132,2,Seq("CV")),
    ("CZK",203,2,Seq("CZ")),
    ("DJF",262,0,Seq("DJ")),
    ("DKK",208,2,Seq("DK","FO","GL")),
    ("DOP",214,2,Seq("DO")),
    ("DZD",12,2,Seq("DZ")),
    ("EGP",818,2,Seq("EG")),
    ("ERN",232,2,Seq("ER")),
    ("ETB",230,2,Seq("ET")),
    ("EUR",978,2,Seq("IE","FR","ES","PT","FI","BE","NL","LU","DE","AT","IT","MT","SK","SI","GR","CY","AD","MC","ME","SM","VA","EE")),
    ("FJD",242,2,Seq("FJ")),
    ("FKP",238,2,Seq("FK")),
    ("GBP",826,2,Seq("GB","IM","JE","GG","GS","IO")),
    ("GEL",981,2,Seq("GE")),
    ("GHS",936,2,Seq("GH")),
    ("GIP",292,2,Seq("GI")),
    ("GMD",270,2,Seq("GM")),
    ("GNF",324,0,Seq("GN")),
    ("GTQ",320,2,Seq("GT")),
    ("GYD",328,2,Seq("GY")),
    ("HKD",344,2,Seq("HK")),
    ("HNL",340,2,Seq("HN")),
    ("HRK",191,2,Seq("HR")),
    ("HTG",332,2,Seq("HT")),
    ("HUF",348,2,Seq("HU")),
    ("IDR",360,2,Seq("ID")),
    ("ILS",376,2,Seq("IL")),
    ("INR",356,2,Seq("IN")),
    ("IQD",368,0,Seq("IQ")),
    ("IRR",364,0,Seq("IR")),
    ("ISK",352,0,Seq("IS")),
    ("JMD",388,2,Seq("JM")),
    ("JOD",400,3,Seq("JO")),
    ("JPY",392,0,Seq("JP")),
    ("KES",404,2,Seq("KE")),
    ("KGS",417,2,Seq("KG")),
    ("KHR",116,0,Seq("KH")),
    ("KMF",174,0,Seq("KM")),
    ("KPW",408,0,Seq("KP")),
    ("KRW",410,0,Seq("KR")),
    ("KWD",414,3,Seq("KW")),
    ("KYD",136,2,Seq("KY")),
    ("KZT",398,2,Seq("KZ")),
    ("LAK",418,0,Seq("LA")),
    ("LBP",422,2,Seq("LB")),
    ("LKR",144,2,Seq("LK")),
    ("LRD",430,2,Seq("LR")),
    ("LSL",426,2,Seq("LS")),
    ("LTL",440,2,Seq("LT")),
    ("LVL",428,2,Seq("LV")),
    ("LYD",434,3,Seq("LY")),
    ("MAD",504,2,Seq("MA","EH")),
    ("MDL",498,2,Seq("MD")),
    ("MGA",969,1,Seq("MG")), //#Adjusted decimal places
    ("MKD",807,2,Seq("MK")),
    ("MMK",104,0,Seq("MM")),
    ("MNT",496,2,Seq("MN")),
    ("MOP",446,1,Seq("MO")),
    ("MRO",478,1,Seq("MR")), // Adjusted decimal places
    ("MUR",480,2,Seq("MU")),
    ("MVR",462,2,Seq("MV")),
    ("MWK",454,2,Seq("MW")),
    ("MXN",484,2,Seq("MX")),
    //("#MXV",979,2,Seq("MX")),
    ("MYR",458,2,Seq("MY")),
    ("MZN",943,2,Seq("MZ")),
    ("NAD",516,2,Seq("NA")),
    ("NGN",566,2,Seq("NG")),
    ("NIO",558,2,Seq("NI")),
    ("NOK",578,2,Seq("NO","BV")),
    ("NPR",524,2,Seq("NP")),
    ("NZD",554,2,Seq("NZ","CK","NU","PN","TK")),
    ("OMR",512,3,Seq("OM")),
    ("PAB",590,2,Seq("PA")),
    ("PEN",604,2,Seq("PE")),
    ("PGK",598,2,Seq("PG")),
    ("PHP",608,2,Seq("PH")),
    ("PKR",586,2,Seq("PK")),
    ("PLN",985,2,Seq("PL")),
    ("PYG",600,0,Seq("PY")),
    ("QAR",634,2,Seq("QA")),
    ("RON",946,2,Seq("RO")),
    ("RSD",941,2,Seq("RS")),
    ("RUB",643,2,Seq("RU")),
    ("RUR",810,2,Seq("SU")), //#Old ruble
    ("RWF",646,0,Seq("RW")),
    ("SAR",682,2,Seq("SA")),
    ("SBD",90,2,Seq("SB")),
    ("SCR",690,2,Seq("SC")),
    ("SDG",938,2,Seq("SD")),
    ("SEK",752,2,Seq("SE")),
    ("SGD",702,2,Seq("SG")),
    ("SHP",654,2,Seq("SH")),
    ("SLL",694,0,Seq("SL")),
    ("SOS",706,2,Seq("SO")),
    ("SRD",968,2,Seq("SR")),
    ("STD",678,0,Seq("ST")),
    ("SYP",760,2,Seq("SY")),
    ("SZL",748,2,Seq("SZ")),
    ("THB",764,2,Seq("TH")),
    ("TJS",972,2,Seq("TJ")),
    ("TMT",934,2,Seq("TM")),
    ("TND",788,3,Seq("TN")),
    ("TOP",776,2,Seq("TO")),
    ("TRY",949,2,Seq("TR")),
    ("TTD",780,2,Seq("TT")),
    ("TWD",901,1,Seq("TW")),
    ("TZS",834,2,Seq("TZ")),
    ("UAH",980,2,Seq("UA")),
    ("UGX",800,0,Seq("UG")),
    ("USD",840,2,Seq("US","AS","EC","SV","GU","MH","FM","MP","PW","PR","TL","TC","VG","VI")), // #HTPA
    //("#USN",997,2,Seq("US")),
    //("#USS",998,2,Seq("US")),
    ("UYU",858,2,Seq("UY")),
    ("UZS",860,2,Seq("UZ")),
    ("VEF",937,2,Seq("VE")),
    ("VND",704,0,Seq("VN")),
    ("VUV",548,0,Seq("VU")),
    ("WST",882,2,Seq("WS")),
    ("XAF",950,0,Seq("CM","CF","CG","TD","GQ","GA")),
    ("XAG",961,-1,Seq.empty),
    ("XAU",959,-1,Seq.empty),
    ("XBA",955,-1,Seq.empty),
    ("XBB",956,-1,Seq.empty),
    ("XBC",957,-1,Seq.empty),
    ("XBD",958,-1,Seq.empty),
    ("XCD",951,2,Seq("AI","AG","DM","GD","MS","KN","LC","VC")),
    ("XDR",960,-1,Seq.empty),
    ("XFU",-1,-1,Seq.empty),
    ("XOF",952,0,Seq("BJ","BF","CI","GW","ML","NE","SN","TG")),
    ("XPD",964,-1,Seq.empty),
    ("XPF",953,0,Seq("PF","NC","WF")),
    ("XPT",962,-1,Seq.empty),
    ("XTS",963,-1,Seq.empty),
    ("XXX",999,-1,Seq.empty),
    ("YER",886,0,Seq("YE")),
    ("ZAR",710,2,Seq("ZA")),
    ("ZMW",894,2,Seq("ZM")),
    ("ZWL",932,2,Seq("ZW"))
  )
}


/**
 * Provider for available currencies using a file.
 * <p>
 * Use the original joda money data, but codify it since the file io in scala.js is...meh
 */
class DefaultCurrencyUnitDataProvider extends CurrencyUnitDataProvider {

  /**
   * Registers all the currencies known by this provider.
   *
   * @throws Exception if an error occurs
   */
  def registerCurrencies() {
    DefaultCurrencyUnitDataProvider.CountryCodeData.foreach{ case (currencyCode: String, numericCode: Int, digits: Int, countryCodes: Seq[String]) =>
      registerCurrency(currencyCode, numericCode, digits, countryCodes)
    }
  }
}
