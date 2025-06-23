package model.player

import model.entity.IEntity
import util.SprayJson.{*, given}

/** A class representing a player in the game.
  *
  * The Player class holds a list of playable characters and their unique
  * identifier. It provides functionality to check if all characters are
  * defeated and to represent the player in JSON format.
  *
  * @param playerCharacters
  *   A list of characters controlled by the player.
  * @param playerId
  *   A unique identifier for the player.
  *
  * @constructor
  *   Creates a new player with the specified characters and ID.
  *
  * @example
  *   {{{
  * val character1 = new Knight("Arthur", 100, 20, 10, List())
  * val character2 = new BlackMage("Merlin", 80, 50, 5, List())
  * val player = new Player(List(character1, character2))
  *   }}}
  *
  * @see
  *   [[IPlayer]] for player behavior.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class Player(val playerCharacters: List[IEntity], val playerId: String = "player1") extends IPlayer {
  
  override def playerDefeated: Boolean = {
    val defeated = playerCharacters.forall(_.defeated)
    if (defeated) {
      notifyObservers(s"Player $playerId has been defeated.")
    }
    defeated
  }

  override def id: String = playerId

  override def toJson: JsObj = JsObj(
    "characters" -> JsArr(playerCharacters.map(_.toJson))
  )
}
