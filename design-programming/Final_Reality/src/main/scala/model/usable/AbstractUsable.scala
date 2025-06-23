package model.usable

import api.TargetHandler
import util.SprayJson.{*, given}

/** Abstract class representing a usable item within the game.
 *
 * `AbstractUsable` serves as a base class for all usable items, providing
 * basic properties such as a unique identifier and a display name.
 * This class extends `TargetHandler`, allowing it to throw exceptions
 * for unsupported actions, and implements `IUsable` to act as a game object
 * that can be targeted and used in gameplay.
 *
 * Concrete subclasses of `AbstractUsable` should define specific behavior for
 * each usable item type, such as potions, or weapons.
 *
 * @constructor
 *   Initializes the usable item with a unique ID and name.
 *
 * @param usableId A unique identifier for the usable item.
 * @param name The display name of the usable item.
 *
 * @see [[TargetHandler]] for handling unsupported actions.
 * @see [[IUsable]] for usable item properties and functionalities.
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
abstract class AbstractUsable(val usableId: String, val name: String) extends TargetHandler with IUsable {
  
  def id: String = usableId
  
  def toJson: JsObj = JsObj(
    "id" -> id,
    "name" -> name
  )
}
