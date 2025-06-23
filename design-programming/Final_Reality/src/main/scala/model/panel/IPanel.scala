package model.panel

import api.{GameObject, Target}
import model.entity.IEntity

import scala.collection.mutable.ArrayBuffer

/** Trait representing a game panel.
  *
  * The `IPanel` trait defines the structure for panels within the game world.
  * Each panel has a unique identifier, coordinates, and storage for entities
  * currently located on the panel. Additionally, it maintains a list of
  * adjacent panels to facilitate movement between them.
  *
  * @see
  *   [[Target]] for target-related functionalities.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
trait IPanel extends GameObject with Target {

  /** The panel's id */
  val panelId: String

  /** The panel's x coordinate. */
  val x: Int

  /** The panel's y coordinate. */
  val y: Int

  /** The set of all panels in the game.
    *
    * This list contains all panels that were defined in the game.
    */
  var panels: ArrayBuffer[IPanel]

  /** The panel's current entities.
    *
    * This mutable list stores all entities currently present on the panel.
    */
  var storage: ArrayBuffer[IEntity]

  /** The list of the panel's current adjacent panels.
    *
    * This list contains all panels that are adjacent to the current panel,
    * allowing entities to move between them.
    */
  def adjacentPanels: List[IPanel]

  /** Checks if a given entity is on the panel's storage.
    *
    * @param entity
    *   The entity to be checked.
    * @return
    *   true if the given entity is in the current panel.
    */
  def isPresent(entity: IEntity): Boolean

  /** Adds an entity to the current panel's storage.
    *
    * This might be invoked when a player moves to this panel or starts their
    * turn on it.
    *
    * @param entity
    *   The entity to be added to this panel.
    */
  def addCharacter(entity: IEntity): Unit

  /** Removes an entity from the current panel's storage.
    *
    * This might be invoked when a player moves off this panel.
    *
    * @param entity
    *   The entity to be removed from this panel.
    */
  def removeCharacter(entity: IEntity): Unit
}
