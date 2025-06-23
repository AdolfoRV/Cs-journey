package model.entity

import api.Source
import api.Target
import model.entity.characters.ICharacter
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** Interface for general entities in the game,
 * such as enemies or friendly playable characters.
 *
 * This interface defines the essential attributes and behaviors that all entities 
 * in the game must implement. Entities can have health points, defense, weight, 
 * and can participate in actions as sources and targets within the game.
 *
 * Each entity is identified by a unique ID and name, allowing for easy referencing 
 * during gameplay. The interface also includes methods for managing health, defense, 
 * and weight attributes, as well as checking if the entity has been defeated.
 *
 * @see [[api.Source]] for behaviors related to the source of actions.
 * @see [[api.Target]] for behaviors related to the target of actions.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait IEntity extends Source with Target {

  /** The entity's id. */
  val entityId: String

  /** The entity's name. */
  val name: String

  /** The entity's base health. */
  val maxHp: Int

  /** The entity's base mana. */
  val maxMana: Int

  /** The entity's base defense. */
  val baseDefense: Int

  /** The entity's base weight. */
  val baseWeight: Int

  /** The current maximum set of points of the character's action bar. */
  def maxActionBar: Int

  /** Gets the entity's current health.
   *
   * @return The current health points of the entity.
   */
  def getHp: Int

  /** Gets the entity's current defense.
   *
   * @return The current defense value of the entity.
   */
  def getDefense: Int
  
  /** Gets the entity's current weight.
   *
   * @return The current weight of the entity.
   */
  def getWeight: Int

  /** Gets the entity's current mana.
   *
   * @return The current mana points of the entity/magician.
   */
  def getMana: Int
  
  /** Gets the entity's attack points.
   *
   * @return The current attack points of the entity.
   */
  def getAtk: Int

  /** Gets the entity's magic damage.
   *
   * @return The current magic attack points of the entity.
   */
  def getMagicAtk: Int
  
  /** Sets the current HP of the character.
   *
   * @param currentHp The entity's current health.
   */
  def hp_=(currentHp: Int): Unit
  
  /** Sets the defense value of the character.
   *
   * @param currentDefense The entity's current defense.
   */
  def defense_=(currentDefense: Int): Unit

  /** Sets the weight of the character.
   *
   * @param currentWeight The entity's current weight.
   */
  def weight_=(currentWeight: Int): Unit

  /** Sets the current mana of the entity.
   *
   * @param currentMana The entity's current mana.
   */
  def mana_=(currentMana: Int): Unit

  /** Determines if an entity was defeated.
   *
   * @return true if the entity's health points are less than or equal to zero, false otherwise.
   */
  def defeated: Boolean

  /** Equips a weapon to the current character and updates the character's weight with the weapon's weight.
   *
   * @param weapon The weapon to be equipped.
   * @throws InvalidWeaponException if the weapon cannot be equipped.
   * @throws NotInInventoryException if the weapon is not available in the inventory.
   */
  def equipWeapon(weapon: IWeapon): Unit

  /** Consumes a potion and apply the effect to the current character.
   *
   * @param potion The potion to be consumed.
   * @throws InvalidPotionException if the potion cannot be consumed.
   * @throws NotInInventoryException if the potion is not available in the inventory.
   */
  def consumePotion(potion: IPotion): Unit
  
  /** Receives an attack from an enemy, applying damage to the character.
   *
   * @param enemy The enemy attacking the character. Must be of type Enemy.
   * @throws InvalidTargetException if the attack source is not an enemy.
   * @throws NotEnoughDamageException if the character lacks attack points.

   */
  def dealAttackTo(enemy: Enemy): Unit

  /** Receives an attack from a character, applying damage to the enemy.
   *
   * @param character The character attacking the enemy. Must be of type Enemy.
   * @throws InvalidTargetException if the attack source is not a character.
   */
  def dealAttackTo(character: ICharacter): Unit
}
