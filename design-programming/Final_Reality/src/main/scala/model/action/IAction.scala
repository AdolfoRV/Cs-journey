package model.action

import api.{GameObject, Target}
import model.entity.IEntity
import model.usable.IUsable

/** Trait representing an action in the game.
  *
  * The `IAction` trait defines the structure for actions that can be performed
  * by game entities. Each action has a name, an identifier, and a list of
  * usable items that may be involved in executing the action.
  *
  * Actions can vary in type, such as attacks, healing, or equipping items, and
  * their implementation will depend on the specific action being defined.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
trait IAction extends GameObject {

  /** The action's name. */
  val name: String

  /** The action's id. */
  val actionId: String

  /** The list of possible items that the action may use.
    *
    * This list contains all usable items (e.g., potions, weapons) that can be
    * associated with the action.
    */
  val possibleItems: List[IUsable]

  /** Executes an action.
    *
    * @param source
    *   The entity performing an action.
    * @param target
    *   The entity/ies or panel receiving the action.
    */
  def executeAction(source: IEntity, target: Target): Unit
}
