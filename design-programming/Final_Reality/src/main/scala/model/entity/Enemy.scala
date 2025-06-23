package model.entity

import exceptions.InvalidTargetException
import model.action.IAction
import model.action.common.{AttackAction, MoveAction}
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** A class representing an Enemy character.
  *
  * The Enemy is a hostile entity that can engage in combat with player
  * characters. It has attributes such as health points, defense, attack power,
  * and weight.
  *
  * @param name
  *   The name of the Enemy.
  * @param maxHp
  *   The maximum health points (HP) of the Enemy.
  * @param defense
  *   The defensive strength of the Enemy, reducing damage taken from attacks.
  * @param atk
  *   The attack power of the Enemy, determining how much damage it deals to
  *   other entities.
  * @param weight
  *   The weight of the Enemy, influencing movement and action speed.
  * @param id
  *   A unique identifier for the Enemy.
  *
  * @constructor
  *   Creates a new Enemy with the specified attributes.
  *
  * @example
  *   {{{
  * val goblin = new Enemy("Goblin", 50, 5, 10, 15)
  *   }}}
  *
  * @see
  *   [[model.entity.IEntity]] for common entity behaviors.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class Enemy(name: String, maxHp: Int, defense: Int, atk: Int, weight: Int, id: String = "enemy1") extends AbstractEntity(name, maxHp, defense, weight, id) {

  override val actions: List[IAction] = List(new AttackAction(), new MoveAction())

  override def getAtk: Int = atk
  override def getMagicAtk: Int = 0

  override def receiveAttack(source: IEntity): Unit = source.dealAttackTo(this)
  override def purifiedBy(source: IEntity): Unit = hp_=((getHp * 0.85).toInt - source.getMagicAtk / 2)
  override def equipWeapon(weapon: IWeapon): Unit = throw new InvalidTargetException(this)
  override def consumePotion(potion: IPotion): Unit = throw new InvalidTargetException(this)
}
