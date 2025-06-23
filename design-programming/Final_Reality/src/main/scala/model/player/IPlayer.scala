package model.player

import api.GameObject
import model.entity.IEntity
import model.observer.Subject

/** Interface for player in the game.
  *
  * The IPlayer interface defines the structure and behavior of a player,
  * including a list of characters they control and methods to check the status
  * of those characters.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
trait IPlayer extends GameObject with Subject[String] {

  /** The player's id */
  val playerId: String

  /** The list of the player's characters */
  val playerCharacters: List[IEntity]

  /** Checks if all player's entities have been defeated.
    *
    * An entity is defeated if their HP is equal to 0.
    *
    * @return
    *   true if all the player's entities are defeated, false otherwise.
    */
  def playerDefeated: Boolean
}
