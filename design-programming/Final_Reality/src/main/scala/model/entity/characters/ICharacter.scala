package model.entity.characters

import model.action.IAction
import model.entity.IEntity
import model.usable.IUsable
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** Interface for common and magical characters.
 *
 * This interface defines the essential behaviors and attributes of characters in the game,
 * including the ability to equip weapons, manage potions, and perform actions.
 *
 * A character can have a weapon, which may be equipped to enhance their capabilities.
 * Additionally, characters can maintain a list of potions and weapons in their inventory,
 * along with a set of base and special actions (such as spells) they can perform during gameplay.
 *
 * @see [[model.entity.IEntity]] for common entity behaviors.
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait ICharacter extends IEntity {

  /** The character's inventory */
  var inventory: List[IUsable]

  /** Determines if the character has a buff (magicStrength) active */
  var magicStrengthBuff: Boolean
  
  /** Gets the entity's current weapon.
   *
   * @return An Option containing the current weapon if equipped, None otherwise.
   */
  def getWeapon: Option[IWeapon]

  /** Sets the current weapon of the character.
   *
   * @param currentWeapon The character's current weapon.
   */
  def weapon_=(currentWeapon: Option[IWeapon]): Unit
  
  /** Checks if the specified weapon can be equipped by this character.
   *
   * @param weapon The weapon to be tested for compatibility.
   * @return true if this character can equip the specified weapon.
   */
  def canEquipWeapon(weapon: IWeapon): Boolean
  
  /** Checks if the specified potion can be consumed by this character.
   *
   * @param potion The potion to be tested for compatibility.
   * @return true if this character can consume the specified potion.
   */
  def canConsumePotion(potion: IPotion): Boolean

  /** The list of potions available to the character.
   *
   * @return A list of IPotion objects.
   */
  def potions: List[IPotion]

  /** The list of weapons available to the character.
   *
   * @return A list of IWeapon objects.
   */
  def weapons: List[IWeapon]

  /** The base actions available to the character.
   *
   * @return A list of IAction objects representing the character's base actions.
   */
  def baseActions: List[IAction]

  /** The current actions that can be performed by the character.
   *
   * @return A list of IAction objects representing the character's available actions.
   */
  def actions: List[IAction]
}
