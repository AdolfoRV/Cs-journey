package model.usable.weapon.magical

import model.entity.characters.common.Archer

/** Class representing a Wand, a type of magical weapon primarily used by
  * magical characters.
  *
  * The `Wand` class specifies a magical weapon that can be equipped by
  * `BlackMage` and `WhiteMage`. It inherits the properties and behavior defined
  * in `AbstractMagicalWeapon`.
  *
  * @constructor
  *   Creates a new Wand instance.
  *
  * @param name
  *   The name of the wand.
  * @param atk
  *   The physical attack power of the wand.
  * @param magicAtk
  *   The magic attack power of the wand.
  * @param weight
  *   The weight of the wand.
  * @param id
  *   The unique identifier for the wand, defaulting to "magic_weapon2".
  *
  * @see
  *   [[IMagicalWeapon]] for magical weapon behavior.
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class Wand(name: String, atk: Int, magicAtk: Int, weight: Int, id: String = "magic_weapon2") extends AbstractMagicalWeapon(name, atk, magicAtk, weight, id) {

  // override val allowedClass: List[String] = List("Archer", "BlackMage", "WhiteMage")
  override def canBeEquippedByArcher(archer: Archer): Boolean = true
}
