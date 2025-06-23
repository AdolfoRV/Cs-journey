package model.usable.weapon.magical

/** Class representing a Staff, a type of magical weapon primarily used by
  * magical characters.
  *
  * The `Staff` class specifies a magical weapon that can be equipped by
  * `BlackMage` and `WhiteMage`. It inherits the properties and behavior defined
  * in `AbstractMagicalWeapon`.
  *
  * @constructor
  *   Creates a new Staff instance.
  *
  * @param name
  *   The name of the staff.
  * @param atk
  *   The physical attack power of the staff.
  * @param magicAtk
  *   The magic attack power of the staff.
  * @param weight
  *   The weight of the staff.
  * @param id
  *   The unique identifier for the staff, defaulting to "magic_weapon1".
  *
  * @see
  *   [[IMagicalWeapon]] for magical weapon behavior.
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class Staff(name: String, atk: Int, magicAtk: Int, weight: Int, id: String = "magic_weapon1") extends AbstractMagicalWeapon(name, atk, magicAtk, weight, id) {

  // override val allowedClass: List[String] = List("BlackMage", "WhiteMage")
}
