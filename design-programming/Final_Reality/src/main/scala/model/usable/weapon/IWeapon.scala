package model.usable.weapon

import model.entity.characters.ICharacter
import model.entity.characters.common.{Archer, Knight, Thief}
import model.entity.characters.magical.{BlackMage, WhiteMage}
import model.usable.IUsable

/** Trait representing a weapon within the game.
 *
 * `IWeapon` defines the basic structure for items that can be equipped as weapons.
 * Weapons implement `IUsable`, meaning they can be used by game entities,
 * and potentially targeted by certain game actions.
 *
 * @see [[IUsable]] for general usable item behavior.
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait IWeapon extends IUsable {

  /** The weapon's attack */
  val atk: Int

  /** The weapon's weight */
  val weight: Int

  //** The list of characters that can equip the weapon */
  //val allowedClass: List[String]

  /** The weapon's base magic attack */
  val baseMagicAtk: Int

  /** Gets the weapon's current magic attack
   *
   * @return
   * The weapon's base magic damage
   */
  def getMagicAtk: Int

  /** Sets the current weapon's magic attack points
   *
   * @param currentMagicAtk
   * The weapon's current magic damage
   */
  def magicAtk_=(currentMagicAtk: Int): Unit
  
  /** The weapon's owner */
  var owner: Option[ICharacter]
  
  /** Determines if a character can use a certain item.
    *
    * @param character
    *   The character attempting to equip the item.
    * @return
    *   true if it can be equipped.
    */
  def canBeEquippedBy(character: ICharacter): Boolean

  /** Checks if this weapon can be equipped by an Archer character.
   *
   * @param archer The Archer character attempting to equip this weapon.
   * @return true if this weapon is compatible with an Archer.
   */
  def canBeEquippedByArcher(archer: Archer): Boolean

  /** Checks if this weapon can be equipped by a Knight character.
   *
   * @param knight The Knight character attempting to equip this weapon.
   * @return true if this weapon is compatible with a Knight.
   */
  def canBeEquippedByKnight(knight: Knight): Boolean

  /** Checks if this weapon can be equipped by a Thief character.
   *
   * @param thief The Thief character attempting to equip this weapon.
   * @return true if this weapon is compatible with a Thief.
   */
  def canBeEquippedByThief(thief: Thief): Boolean

  /** Checks if this weapon can be equipped by a Black Mage character.
   *
   * @param blackMage The Black Mage character attempting to equip this weapon.
   * @return true if this weapon is compatible with a Black Mage.
   */
  def canBeEquippedByBlackMage(blackMage: BlackMage): Boolean

  /** Checks if this weapon can be equipped by a White Mage character.
   *
   * @param whiteMage The White Mage character attempting to equip this weapon.
   * @return true if this weapon is compatible with a White Mage.
   */
  def canBeEquippedByWhiteMage(whiteMage: WhiteMage): Boolean

}
