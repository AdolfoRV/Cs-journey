package api

import util.Json.JsVal
import util.Json.{*, given}

/** Interface for the controller of the game. It defines the methods that the
  * controller should implement. The controller is responsible for managing the
  * game state and the game logic.
  */
trait IGameController {

  /** Returns the players of the game. In this version of the project, there are
    * strictly two players. Each player should send the information of his game
    * units.
    */
  def getPlayers: JsVal

  /** Returns the game panels. These panels are basically the map where the game
    * units will be interacting with.
    */
  def getGamePanels: JsVal

  /** Returns the id of the current game unit. The current game unit is the unit
    * that can do actions, in other words, it's their turn.
    */
  def getCurrentGameUnitId: String

  /** Decides the next game unit id. This method is called after an action is
    * executed. It should return the id of the next game unit that will play.
    */
  def decideNextGameUnitId: String

  /** Returns the actions that a character can do. It receives the id of the
    * character and returns the actions that the character can do.
    *
    * @param id
    *   the id of the game unit
    * @return
    *   the actions that the game unit can do, or None if the game unit is not
    *   found
    */
  def findActionsByGameUnitId(id: String): Option[JsVal]

  /** Executes an action.
    *
    * @param actionId
    *   the id of the action to execute
    * @param sourceId
    *   the id of the game unit that is executing the action
    * @param targetId
    *   the id of the game unit that is the target of the action
    * @return
    *   a message that describes the result of the action
    */
  def doAction(actionId: String, sourceId: String, targetId: String): String

  /** Resets the game. All configurations and parameters should be reset too. */
  def reset(): Unit
}
