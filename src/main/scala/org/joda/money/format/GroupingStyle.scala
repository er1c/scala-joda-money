package org.joda.money.format

//remove if not needed
import scala.collection.JavaConversions._

object GroupingStyle extends Enumeration {

  val NONE = new GroupingStyle()

  val FULL = new GroupingStyle()

  val BEFORE_DECIMAL_POINT = new GroupingStyle()

  class GroupingStyle extends Val

  implicit def convertValue(v: Value): GroupingStyle = v.asInstanceOf[GroupingStyle]
}
