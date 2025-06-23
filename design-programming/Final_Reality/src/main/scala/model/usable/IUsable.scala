package model.usable

import api.{GameObject, Target}

/** Trait representing an item that can be used by a character.
 *
 * The `IUsable` trait defines a structure for items that can be used
 * within the game, such as weapons/potions.
 * Implementing classes define specific behaviors and properties for each usable item.
 *
 * Each usable item has a unique identifier and a name, allowing it to be tracked
 * and referenced in the game. Additionally, `IUsable` extends `Target`, so these items
 * can serve as potential targets for actions within the game.
 *
 * @see [[Target]] for target-related functionalities.
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait IUsable extends GameObject with Target {

  /** The item's id */
  val usableId: String
  
  /** The item's name */
  val name: String
}
