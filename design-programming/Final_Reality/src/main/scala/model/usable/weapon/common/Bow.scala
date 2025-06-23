package model.usable.weapon.common

import model.entity.characters.common.{Archer, Knight}
import model.entity.characters.magical.WhiteMage
import model.usable.weapon.AbstractWeapon

/** Class representing a Bow weapon, specialized for certain character classes.
  *
  * The `Bow` class allows certain character classes (Knight, Archer, WhiteMage)
  * to equip it by providing specific methods for each allowed class.
  *
  * @constructor
  *   Creates a new Bow instance.
  *
  * @param name
  *   The name of the bow.
  * @param atk
  *   The attack power of the bow.
  * @param weight
  *   The weight of the bow, affecting character speed.
  * @param id
  *   The unique identifier for the bow, defaulting to "weapon1".
  *   
  * @see
  *   [[model.usable.weapon.IWeapon]] for weapon-specific behavior.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class Bow(name: String, atk: Int, weight: Int, id: String = "weapon1") extends AbstractWeapon(name, atk, weight, id) {

  // override val allowedClass: List[String] = List("Knight", "Archer", "WhiteMage")
  override def canBeEquippedByKnight(knight: Knight): Boolean = true
  override def canBeEquippedByArcher(archer: Archer): Boolean = true
  override def canBeEquippedByWhiteMage(whiteMage: WhiteMage): Boolean = true
}
