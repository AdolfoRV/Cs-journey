package model.action

import util.SprayJson.{*, given}
import model.usable.IUsable

/** An abstract class representing an action that involves usable items.
  *
  * This class serves as a base for actions that may involve various items,
  * allowing for item-specific interactions. Each action has a name prefixed
  * with "Items→" to clarify its nature, and a list of items is included in its
  * JSON representation.
  *
  * @param actionId
  *   The unique identifier for this item-based action.
  * @param name
  *   The base name of the action, to be prefixed with "Items→" in output.
  * @param possibleItems
  *   The list of items that the action may utilize.
  *
  * @see
  *   [[IAction]] for base action details.
  * @see
  *   [[util.SprayJson]] for JSON serialization.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
abstract class AbstractActionWithUsable(
    actionId: String,
    name: String,
    override val possibleItems: List[IUsable]
) extends AbstractAction(actionId, name=s"Items→$name") {

  override def toJson: JsObj = JsObj(
    "id" -> id,
    "action" -> s"Items→$name",
    "targets" -> JsArr(possibleItems.map(_.toJson))
  )
}
