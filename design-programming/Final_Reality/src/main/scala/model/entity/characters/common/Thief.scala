package model.entity.characters.common

import model.entity.characters.AbstractCharacter
import model.usable.IUsable
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** A class representing a Thief character.
 *
 * The Thief is a fast and agile character specializing in stealth and quick attacks. They can wield weapons such as a Sword and Dagger.
 *
 * @param name
 *   The name of the Thief.
 * @param maxHp
 *   The maximum health points (HP) of the Thief.
 * @param defense
 *   The defensive strength of the Thief, reducing damage taken from attacks.
 * @param weight
 *   The weight of the Thief, influencing movement and action speed.
 * @param inventory
 *   The list of items the Thief currently possesses, which can include potions and weapons.
 * @param id
 *   A unique identifier for the Thief.
 *
 * @constructor Creates a new Thief with the specified attributes.
 *
 * @example
 * {{{
 * val thief = new Thief("Bayron", 85, 15, 25, List(new DefensePotion()), "thief1")
 * }}}
 *
 * @see [[model.entity.characters.ICharacter]] for basic character behavior.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class Thief(name: String, maxHp: Int, defense: Int, weight: Int, inventory: List[IUsable] = List.empty, id: String = "char3"
) extends AbstractCharacter(name, maxHp, defense, weight, inventory, id) {

  override def canEquipWeapon(weapon: IWeapon): Boolean = weapon.canBeEquippedByThief(this)

  override def canConsumePotion(potion: IPotion): Boolean = potion.canBeConsumedByThief(this)
}
