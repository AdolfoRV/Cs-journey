package model.usable.weapon.common

import model.entity.characters.common.{Knight, Thief}
import model.entity.characters.magical.BlackMage
import model.usable.weapon.AbstractWeapon

/** Class representing a Dagger weapon, specialized for certain character
  * classes.
  *
  * The `Dagger` class allows certain character classes (Knight, Thief,
  * BlackMage) to equip it by providing specific methods for each allowed class.
  *
  * @constructor
  *   Creates a new Dagger instance.
  *
  * @param name
  *   The name of the dagger.
  * @param atk
  *   The attack power of the dagger.
  * @param weight
  *   The weight of the dagger, affecting character speed.
  * @param id
  *   The unique identifier for the dagger, defaulting to "weapon2".
  *
  * @see
  *   [[model.usable.weapon.IWeapon]] for weapon-specific behavior.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class Dagger(name: String, atk: Int, weight: Int, id: String = "weapon2") extends AbstractWeapon(name, atk, weight, id) {

  // override val allowedClass: List[String] = List("Knight", "Thief", "BlackMage")
  override def canBeEquippedByKnight(knight: Knight): Boolean = true
  override def canBeEquippedByThief(thief: Thief): Boolean = true
  override def canBeEquippedByBlackMage(blackMage: BlackMage): Boolean = true
}
