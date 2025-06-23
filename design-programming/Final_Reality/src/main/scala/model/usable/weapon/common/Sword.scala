package model.usable.weapon.common

import model.entity.characters.common.{Archer, Knight, Thief}
import model.usable.weapon.AbstractWeapon

/** Class representing a Sword weapon, specialized for certain character
  * classes.
  *
  * The `Sword` class allows certain character classes (Knight, Archer, Thief)
  * to equip it by providing specific methods for each allowed class.
  *
  * @constructor
  *   Creates a new Sword instance.
  *
  * @param name
  *   The name of the sword.
  * @param atk
  *   The attack power of the sword.
  * @param weight
  *   The weight of the sword, affecting character speed.
  * @param id
  *   The unique identifier for the sword, defaulting to "weapon3".
  *
  * @see
  *   [[model.usable.weapon.IWeapon]] for weapon-specific behavior.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class Sword(name: String, atk: Int, weight: Int, id: String = "weapon3") extends AbstractWeapon(name, atk, weight, id) {

  // override val allowedClass: List[String] = List("Knight", "Archer", "Thief")
  override def canBeEquippedByKnight(knight: Knight): Boolean = true
  override def canBeEquippedByArcher(archer: Archer): Boolean = true
  override def canBeEquippedByThief(thief: Thief): Boolean = true
}
