package model.turnscheduler

import model.entity.IEntity

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map}

/** A class that manages the turn scheduling of entities in the combat system.
  *
  * The TurnScheduler keeps track of all entities involved in combat, their
  * action bar progress, and determines which entities are ready to take their
  * turn based on their action bar values.
  *
  * It maintains a list of entities and a corresponding map to track their
  * action bar progress, allowing the system to manage turns effectively.
  *
  * @constructor
  *   Creates a new TurnScheduler instance.
  *
  * @example
  *   {{{
  * val turnScheduler = new TurnScheduler()
  * val knight = new Knight("Arthur", 100, 20, 10, List())
  * val blackMage = new BlackMage("Merlin", 80, 50, 5, List())
  *
  * turnScheduler.addEntity(knight)
  * turnScheduler.addEntity(blackMage)
  * turnScheduler.increaseActionBars()
  * val readyEntities = turnScheduler.getEntitiesReadyForTurn
  * println(s"Entities ready for turn: ${readyEntities.map(_.name)}")
  *   }}}
  *
  * @see
  *   [[ITurnScheduler]] for the turn scheduler functionality.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class TurnScheduler(val amount: Int = 20) extends ITurnScheduler {

  /** ArrayBuffer to hold the entities in the combat system */
  private val entities: ArrayBuffer[IEntity] = ArrayBuffer()

  /** Mutable map to track the action bar progress of each entity */
  private val actionBars: mutable.Map[IEntity, Int] = mutable.Map()

  override def addEntity(entity: IEntity): Unit = {
    entities += entity
    actionBars += (entity -> 0)
  }

  override def removeEntity(entity: IEntity): Unit = {
    entities -= entity
    actionBars -= entity
  }

  override def recordActionBars: mutable.Map[IEntity, Int] = actionBars

  override def resetActionBar(entity: IEntity): Unit = {
    actionBars(entity) = 0
  }

  override def increaseActionBars(): Unit = {
    for (entity <- entities) {
      actionBars(entity) += amount
    }
  }

  override def isActionBarComplete(entity: IEntity): Boolean = {
    actionBars.get(entity).exists(_ >= entity.maxActionBar)
  }

  override def getEntitiesReadyForTurn: ArrayBuffer[IEntity] = {
    var readyEntities = entities.filter(isActionBarComplete)
    while (readyEntities.isEmpty) {
      increaseActionBars()
      readyEntities = entities.filter(isActionBarComplete)
    }
    readyEntities.sortBy(entity => actionBars(entity)) // we'll order based on excedente
  }

  override def getCurrentEntity: Option[IEntity] = {
    val readyEntities = getEntitiesReadyForTurn
    if (readyEntities.nonEmpty) {
      val currentEntity = readyEntities.head
      Some(currentEntity)
    } else {
      None
    }
  }
}
