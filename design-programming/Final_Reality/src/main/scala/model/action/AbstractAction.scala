package model.action

import util.SprayJson.{*, given}
import model.usable.IUsable

/** Represents a general, abstract action without specific item requirements.
  *
  * `AbstractAction` provides a template for basic actions in the game,
  * characterized by an ID and a name. It can be extended to implement any
  * action that does not require specific items for execution.
  *
  * @param actionId
  *   The unique identifier for the action.
  * @param name
  *   The name of the action.
  *
  * @see
  *   [[IAction]] for the base action functionality.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
abstract class AbstractAction(val actionId: String, val name: String)
    extends IAction {

  val possibleItems: List[IUsable] = List()

  def id: String = actionId

  def toJson: JsObj = JsObj(
    "id" -> id,
    "action" -> name
  )
}
