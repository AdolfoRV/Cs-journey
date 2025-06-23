package model.turnscheduler

import model.entity.IEntity

// IntelliJ throws a warning if I don't prefix map, but it works without it since we imported mutable.Map
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map}

/** Trait for the Turn Scheduler, responsible for managing the turn order of
  * entities.
  *
  * The `ITurnScheduler` trait defines the functionalities required to manage
  * the turn order of entities in a game. It provides methods to add and remove
  * entities, track their action bar progress, and determine which entity is
  * ready to take its turn.
  *
  * The action bar represents the readiness of an entity to act, which is
  * influenced by the actions performed and the game mechanics.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
trait ITurnScheduler {

  /** Fixed amount to be added to the action bar progress of each entity */
  val amount: Int

  /** Add an entity to the scheduler.
    *
    * @param entity
    *   The entity to be added to the scheduler.
    */
  def addEntity(entity: IEntity): Unit

  /** Remove an entity from the scheduler.
    *
    * @param entity
    *   The entity to be removed from the scheduler.
    */
  def removeEntity(entity: IEntity): Unit

  /** Record the current action bar progress of all entities.
    *
    * @return
    *   A mutable map tracking each entity's action bar progress.
    */
  def recordActionBars: mutable.Map[IEntity, Int]

  /** Reset the action bar for a given entity.
    *
    * @param entity
    *   The entity's action bar to be reset.
    */
  def resetActionBar(entity: IEntity): Unit

  /** Increase the action bars for all entities by a fixed amount.
    *
    * This method is called to simulate the passage of time in the game and to
    * determine which entities are ready to take their turns.
    */
  def increaseActionBars(): Unit

  /** Check if a specific entity has completed its action bar.
    *
    * @param entity
    *   The entity to check.
    * @return
    *   true if the entity's action bar is complete; false otherwise.
    */
  def isActionBarComplete(entity: IEntity): Boolean

  /** Get all entities that have completed their action bar, sorted by surplus.
    *
    * @return
    *   A list of entities ready for their turn in the order of their action bar
    *   completion.
    */
  def getEntitiesReadyForTurn: ArrayBuffer[IEntity]

  /** Get the entity that should take the current turn.
    *
    * @return
    *   An optional entity that is currently active for the turn.
    */
  def getCurrentEntity: Option[IEntity]
}
