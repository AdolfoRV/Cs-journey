package model.entity

import api.{Target, TargetHandler}
import exceptions.{DeadSourceException, InvalidTargetException, NotEnoughDamageException}
import model.action.IAction
import model.entity.characters.ICharacter
import model.observer.Subject
import util.SprayJson.{*, given}

/** Abstract class representing a game entity with health, defense, weight, and mana attributes.
 *
 * `AbstractEntity` provides the basic functionality for game entities, 
 * allowing them to manage their health points, defense, weight, and mana.
 * It also includes methods for executing actions and dealing damage to other entities.
 *
 * @constructor
 *   Creates a new entity with specified attributes.
 *
 * @param name
 *   The name of the entity.
 * @param maxHp
 *   The maximum health points of the entity.
 * @param baseDefense
 *   The base defense value of the entity.
 * @param baseWeight
 *   The weight of the entity.
 * @param entityId
 *   The unique identifier for the entity.
 *
 * @see
 *   [[IEntity]] for the entity interface.
 *
 * @author
 *   Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
abstract class AbstractEntity(val name: String, val maxHp: Int, val baseDefense: Int, val baseWeight: Int, val entityId: String) extends TargetHandler with IEntity with Subject[String] {

  val maxMana: Int = 0

  /** Current amount of health points */
  private var _hp: Int = maxHp

  /** Current amount of defense */
  private var _defense: Int = baseDefense

  /** Current amount of weight */
  private var _weight: Int = baseWeight

  /** Current amount of mana */
  private var _mana: Int = maxMana

  def maxActionBar: Int = this.getWeight

  def getHp: Int = _hp

  def getMana: Int = _mana

  def getDefense: Int = _defense

  def getWeight: Int = _weight

  def hp_=(currentHp: Int): Unit = {
    // the entity can't cure itself more than its base HP and can't have negative HP as well since that would mean the entity has been defeated
    _hp = math.min(
      maxHp,
      math.max(currentHp, 0)
    )
  }

  def mana_=(currentMana: Int): Unit = {
    _mana = math.min(
      maxMana,
      math.max(currentMana, 0)
    ) // same logic as HP, mana can't exceed maxMana or go below 0
  }

  def defense_=(currentDefense: Int): Unit = _defense = currentDefense

  def weight_=(currentWeight: Int): Unit = _weight = currentWeight

  def defeated: Boolean = {
    if (getHp <= 0) {
      notifyObservers(s"$name has been defeated.")
    }
    getHp <= 0
  }

  def doAction(action: IAction, target: Target): Unit = {
    if (defeated) {
      throw new DeadSourceException(this)
    }
    action.executeAction(this, target)
  }

  override def findActionById(id: String): Option[IAction] = {
    actions.find(action => action.id == id)
  }

  def id: String = entityId

  def toJson: JsObj = JsObj(
    "id" -> id,
    "attributes" -> JsArr(
      JsObj("name" -> "name", "value" -> name),
      JsObj("name" -> "hp", "value" -> getHp),
      JsObj("name" -> "atk", "value" -> getAtk),
      JsObj("name" -> "def", "value" -> getDefense)
    ),
    "img" -> (name + ".gif")
  )

  // Could be done with generics: deaf dealAttackTo[T <: IEntity](target: T)
  def dealAttackTo(character: ICharacter): Unit = character.hp_=(character.getHp + math.min(character.getDefense - getAtk, 0))

  def dealAttackTo(enemy: Enemy): Unit = {
    if (getAtk <= 0) {
      throw new NotEnoughDamageException(this)
    } else if (enemy.defeated) {
      throw new InvalidTargetException(enemy)
    }
    enemy.hp_=(enemy.getHp + math.min(enemy.getDefense - getAtk, 0))
  }

  override def receiveThunder(source: IEntity): Unit = {
    if (defeated) {
      throw new InvalidTargetException(this)
    }
    hp_=(getHp - source.getMagicAtk)
  }
}