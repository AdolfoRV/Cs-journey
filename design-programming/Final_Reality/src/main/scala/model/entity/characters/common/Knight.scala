package model.entity.characters.common

import model.entity.characters.AbstractCharacter
import model.usable.IUsable
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** A class representing a Knight character.
 *
 * The Knight is a strong and durable character specializing in melee combat. They can wield weapons such as a Sword, Dagger, and Bow.
 *
 * @param name
 *   The name of the Knight.
 * @param maxHp
 *   The maximum health points (HP) of the Knight.
 * @param defense
 *   The defensive strength of the Knight, reducing damage taken from attacks.
 * @param weight
 *   The weight of the Knight, influencing movement and action speed.
 * @param inventory
 *   The list of items the Knight currently possesses, which can include potions and weapons.
 * @param id
 *   A unique identifier for the Knight.
 *
 * @constructor Creates a new Knight with the specified attributes.
 *
 * @example
 * {{{
 * val knight = new Knight("Gawain", 120, 18, 40, List(new Sword()), "knight1")
 * }}}
 *
 * @see [[model.entity.characters.ICharacter]] for basic character behavior.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class Knight(name: String, maxHp: Int, defense: Int, weight: Int, inventory: List[IUsable] = List.empty, id: String = "char2"
) extends AbstractCharacter(name, maxHp, defense, weight, inventory, id) {

  override def canEquipWeapon(weapon: IWeapon): Boolean = weapon.canBeEquippedByKnight(this)

  override def canConsumePotion(potion: IPotion): Boolean = potion.canBeConsumedByKnight(this)
}
