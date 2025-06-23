package model.entity.characters.common

import model.entity.characters.AbstractCharacter
import model.usable.IUsable
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** A class representing an Archer character.
 *
 * The Archer is a versatile character specializing in ranged attacks. They can wield weapons such as a Sword, Bow, and Wand.
 *
 * @param name
 *   The name of the Archer.
 * @param maxHp
 *   The maximum health points (HP) of the Archer.
 * @param defense
 *   The defensive strength of the Archer, reducing damage taken from attacks.
 * @param weight
 *   The weight of the Archer, influencing movement and action speed.
 * @param inventory
 *   The list of items the Archer currently possesses, which can include potions and weapons.
 * @param id
 *   A unique identifier for the Archer.
 *
 * @constructor Creates a new Archer with the specified attributes.
 *
 * @example
 * {{{
 * val archer = new Archer("Robin", 90, 14, 27, List(new Bow()), "archer1")
 * }}}
 *
 * @see [[model.entity.characters.ICharacter]] for basic character behavior.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class Archer(name: String, maxHp: Int, defense: Int, weight: Int, inventory: List[IUsable] = List.empty, id: String = "char1"
) extends AbstractCharacter(name, maxHp, defense, weight, inventory, id) {

  override def canEquipWeapon(weapon: IWeapon): Boolean = weapon.canBeEquippedByArcher(this)

  override def canConsumePotion(potion: IPotion): Boolean = potion.canBeConsumedByArcher(this)
}
