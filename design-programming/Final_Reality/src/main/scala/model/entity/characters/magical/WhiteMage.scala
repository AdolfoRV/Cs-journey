package model.entity.characters.magical

import model.action.IAction
import model.action.magical.{HealAction, PurifyAction}
import model.usable.IUsable
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** A class representing a White Mage character.
 *
 * The White Mage is a type of character specializing in white magic spells and can use weapons such as a Bow, Wand, and Staff.
 *
 * @param name
 *   The name of the White Mage.
 * @param maxHp
 *   The maximum health points (HP) of the White Mage.
 * @param maxMana
 *   The maximum mana points (MP) of the White Mage, used for casting healing or support spells.
 * @param defense
 *   The defensive strength of the White Mage, reducing damage taken from attacks.
 * @param weight
 *   The weight of the White Mage, influencing movement and action speed.
 * @param inventory
 *   The list of items the White Mage currently possesses, which can include potions and weapons.
 * @param id
 *   A unique identifier for the White Mage.
 *
 * @constructor Creates a new White Mage with the specified attributes.
 *
 * @example
 * {{{
 * val whiteMage = new WhiteMage("Frieren", 100, 120, 12, 28, List(new HealingPotion(), new Staff("Luminasta", 10, 100, 15)), "magic_char2")
 * }}}
 *
 * @see [[model.entity.characters.magical.IMagicalCharacter]] for common mage behavior.
 *         
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class WhiteMage(name: String, maxHp: Int, maxMana: Int, defense: Int, weight: Int, inventory: List[IUsable] = List.empty, id: String = "magic_char2") extends AbstractMagicalCharacter(name, maxHp, maxMana, defense, weight, inventory, id) {

  override def actions: List[IAction] = baseActions ++ List(new HealAction(), new PurifyAction())

  override def canEquipWeapon(weapon: IWeapon): Boolean = weapon.canBeEquippedByWhiteMage(this)
  
  override def canConsumePotion(potion: IPotion): Boolean = potion.canBeConsumedByWhiteMage(this)
}