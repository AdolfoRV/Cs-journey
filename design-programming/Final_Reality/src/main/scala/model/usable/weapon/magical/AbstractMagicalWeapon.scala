package model.usable.weapon.magical

import model.entity.characters.magical.{BlackMage, WhiteMage}
import model.usable.weapon.AbstractWeapon

/** Abstract class representing a magical weapon with attack and magical attack
  * power.
  *
  * `AbstractMagicalWeapon` extends `AbstractWeapon` and provides specific
  * properties and methods for weapons that are designed for magical characters.
  * This class allows only magical classes to equip it.
  *
  * @constructor
  *   Creates a new magical weapon with specific magical properties.
  *
  * @param name
  *   The name of the magical weapon.
  * @param atk
  *   The physical attack power of the weapon.
  * @param baseMagicAtk
  *   The magic attack power of the weapon.
  * @param weight
  *   The weight of the weapon, affecting character speed.
  * @param id
  *   A unique identifier for the weapon.
  *
  * @see
  *   [[IMagicalWeapon]] for magical weapon behavior.
  * @see
  *   [[AbstractWeapon]] for common weapon properties and methods.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
abstract class AbstractMagicalWeapon(name: String, atk: Int, override val baseMagicAtk: Int, weight: Int, id: String) extends AbstractWeapon(name, atk, weight, id) with IMagicalWeapon {

  override def canBeEquippedByBlackMage(blackMage: BlackMage): Boolean = true
  override def canBeEquippedByWhiteMage(whiteMage: WhiteMage): Boolean = true
}
