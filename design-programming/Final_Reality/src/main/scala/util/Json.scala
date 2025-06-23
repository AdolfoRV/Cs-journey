package util

import spray.json.*
import util.SprayJson.JsVal

trait Json {
  type JsObj
  type JsVal
  def JsObj(obj: (String, JsVal)*): JsObj
  def JsStr(obj: String): JsVal
  def JsNum(obj: Int): JsVal
  def JsBool(obj: Boolean): JsVal
}

object SprayJson extends Json {
  type JsObj = JsObject
  type JsVal = JsValue

  given Conversion[Boolean, JsVal] = JsBoolean(_)
  given Conversion[String, JsVal] = JsString(_)
  given Conversion[Int, JsVal] = JsNumber(_)
  // given x[T](using Conversion[T, JsVal]): Conversion[List[T], JsVal] = JsArr(_)

  given fromOption[T](using
      Conversion[T, JsVal]
  ): Conversion[Option[T], JsVal] =
    _.map(summon[Conversion[T, JsVal]].apply(_)).getOrElse(JsNull)

  def JsArr(obj: Iterable[JsObj]): JsArray = JsArray(obj.toVector)
  def JsStr(obj: String): JsVal = JsString(obj)
  def JsNum(obj: Int): JsVal = JsNumber(obj)
  def JsBool(obj: Boolean): JsVal = JsBoolean(obj)
  def JsArr(obj: JsVal*): JsArray = JsArray(obj*)
  def JsArr(obj: List[String]): JsArray = JsArray(obj.map(JsString(_)).toVector)
  // def JsArr(objs: Iterable[String]) = JsArray(objs.map(JsString(_)).toVector)
  def JsObj(obj: (String, JsVal)*): JsObject = JsObject(obj*)

  /*def JsObj[V](obj: Map[String, V])(implicit f: Conversion[V, JsVal]): JsVal =
    JsObject(obj.map { case (k, v) => (k, f(v)) }.toList*)*/
}

object Json {
  export SprayJson.{given, *}
}
