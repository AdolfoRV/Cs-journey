package model.entity.characters.magical

import model.action.IAction
import model.action.magical.{MeteorAction, ThunderAction}
import model.usable.IUsable
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** A class representing a Black Mage character.
 *
 * The Black Mage is a type of character specializing in black magic spells and can use weapons such as a Dagger, Wand, and Staff.
 *
 * @param name
 *   The name of the Black Mage.
 * @param maxHp
 *   The maximum health points (HP) of the Black Mage.
 * @param maxMana
 *   The maximum mana points (MP) of the Black Mage, used for casting spells.
 * @param defense
 *   The defensive strength of the Black Mage, reducing damage taken from attacks.
 * @param weight
 *   The weight of the Black Mage, influencing movement and action speed.
 * @param inventory
 *   The list of items the Black Mage currently possesses, which can include potions and weapons.
 * @param id
 *   A unique identifier for the Black Mage.
 *
 * @constructor Creates a new Black Mage with the specified attributes.
 *
 * @example
 * {{{
 * val blackMage = new BlackMage("Niggician", 100, 150, 10, 30, List(new HealingPotion(), new Staff("Wabbajack", 25, 52, 15)), "magic_char1")
 * }}}
 *
 * @see [[model.entity.characters.magical.IMagicalCharacter]] for common mage behavior.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class BlackMage(name: String, maxHp: Int, maxMana: Int, defense: Int, weight: Int, inventory: List[IUsable] = List.empty, id: String = "magic_char1") extends AbstractMagicalCharacter(name, maxHp, maxMana, defense, weight, inventory, id) {

  override def actions: List[IAction] = baseActions ++ List(new MeteorAction(), new ThunderAction())

  override def canEquipWeapon(weapon: IWeapon): Boolean = weapon.canBeEquippedByBlackMage(this)

  override def canConsumePotion(potion: IPotion): Boolean = potion.canBeConsumedByBlackMage(this)
}