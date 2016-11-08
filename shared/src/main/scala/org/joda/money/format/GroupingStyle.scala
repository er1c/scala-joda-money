package org.joda.money.format

//remove if not needed
import scala.collection.JavaConversions._

object GroupingStyle extends Enumeration {
  type GroupingStyle = Value

  val NONE, FULL, BEFORE_DECIMAL_POINT = Value

  implicit def convertValue(v: Value): GroupingStyle = v.asInstanceOf[GroupingStyle]
}
