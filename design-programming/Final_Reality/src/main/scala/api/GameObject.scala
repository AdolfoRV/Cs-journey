package api

import util.Json.*

/** Provides basic object functionality for objects that can be visualized in
  * the user interface.
  */
trait GameObject {

  /** The unique identifier of the object. It will be used to identify the
    * object in the client.
    */
  def id: String

  /** Converts the object to a JSON object. This method is used to send
    * information of the object to the client. The client will receive the JSON
    * object and will be able to render the object in the user interface.
    * @return
    *   a JSON representing the object
    */
  def toJson: JsObj
}
