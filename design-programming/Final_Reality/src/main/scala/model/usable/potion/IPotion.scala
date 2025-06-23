package model.usable.potion

import model.entity.characters.ICharacter
import model.entity.characters.common.{Archer, Knight, Thief}
import model.entity.characters.magical.{BlackMage, WhiteMage}
import model.usable.IUsable

/** Trait representing a potion item that can be consumed by a character.
  *
  * `IPotion` defines the structure and behavior of potions, allowing various
  * types of characters (e.g., Knight, Archer, BlackMage) to consume potions and
  * gain effects from them.
  *
  * Each potion specifies a name, defines an effect when used by a character,
  * and provides compatibility checks for different character classes.
  *
  * @see
  *   [[IUsable]] for general item functionality.
  * @see
  *   [[model.entity.characters.ICharacter]] for character interactions.
  * @see
  *   [[AbstractPotion]] for the base implementation of potions.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
trait IPotion extends IUsable {

  /** The potion's name */
  override val name: String

  /** Apply the potion effect to the specified character
    *
    * @param character
    *   The character using the potion
    */
  def applyEffect(character: ICharacter): Unit

  /** Determines if a character can use a certain item.
    *
    * @param character
    *   The character attempting to consume the item.
    * @return
    *   true if it can be consumed.
    */
  def canBeConsumedBy(character: ICharacter): Boolean

  /** Checks if this potion can be consumed by an Archer character.
    *
    * @param archer
    *   The Archer character attempting to consume this potion.
    * @return
    *   true if this potion is compatible with an Archer.
    */
  def canBeConsumedByArcher(archer: Archer): Boolean

  /** Checks if this potion can be consumed by a Knight character.
    *
    * @param knight
    *   The Knight character attempting to consume this potion.
    * @return
    *   true if this potion is compatible with a Knight.
    */
  def canBeConsumedByKnight(knight: Knight): Boolean

  /** Checks if this potion can be consumed by a Thief character.
    *
    * @param thief
    *   The Thief character attempting to consume this potion.
    * @return
    *   true if this potion is compatible with a Thief.
    */
  def canBeConsumedByThief(thief: Thief): Boolean

  /** Checks if this potion can be consumed by a Black Mage character.
    *
    * @param blackMage
    *   The Black Mage character attempting to consume this potion.
    * @return
    *   true if this potion is compatible with a Black Mage.
    */
  def canBeConsumedByBlackMage(blackMage: BlackMage): Boolean

  /** Checks if this potion can be consumed by a White Mage character.
    *
    * @param whiteMage
    *   The White Mage character attempting to consume this potion.
    * @return
    *   true if this potion is compatible with a White Mage.
    */
  def canBeConsumedByWhiteMage(whiteMage: WhiteMage): Boolean

}
