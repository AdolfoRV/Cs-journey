package api

import model.entity.IEntity

/** Defines behaviors for target entities within the game, representing
 *  any game element that can be the recipient of actions, such as attacks,
 *  healing spells, or equipment interactions.
 *
 *  This trait allows various entities to be targeted by specific actions
 *  from other entities (e.g., characters or enemies). Methods in this trait
 *  provide functionality to apply effects such as healing, attacks, purification,
 *  and elemental damage, allowing the target entity to respond accordingly.
 *
 *  Implementing classes are expected to define specific behavior for each action,
 *  or throw exceptions if the action is not applicable.
 *
 *  @see [[api.Source]] for entities capable of initiating actions.
 *  @see [[model.entity.IEntity]] for general entity details.
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait Target extends GameObject {

  /** The name of the target. */
  val name: String
  
  /** Applies a potion to the specified entity.
   *
   *  @param source
   *  The entity applying the potion, usually a friendly character.
   *
   *  @throws NotInInventoryException if the potion is not present in the entity's inventory.
   *  @throws InvalidTargetException if the target is not eligible for potion consumption.
   *  @throws InvalidPotionException if the target cannot consume the potion.
   */
  def applyPotionTo(source: IEntity): Unit

  /** Attempts to equip a weapon to the target entity.
   *
   *  @param source
   *  The entity attempting to equip the weapon, typically a character with weapon compatibility.
   *
   *  @throws NotInInventoryException if the weapon is not present in the entity's inventory.
   *  @throws InvalidTargetException if the target is not eligible for weapon equipping.
   *  @throws InvalidWeaponException if the weapon is incompatible with the entity.
   */
  def equipWeaponTo(source: IEntity): Unit

  /** Moves the target entity to a new position.
   *
   *  @param source
   *  The entity initiating the move action.
   *
   *  @throws InvalidTargetException
   *  if the target cannot be moved, or if movement is restricted for the entity.
   */
  def moveEntity(source: IEntity): Unit

  /** Purifies the target.
   *
   *  @param source
   *  The entity performing the purification, often a character with magical abilities.
   *
   *  @throws InvalidTargetException
   *  if the target cannot be purified or if the action is otherwise invalid.
   */
  def purifiedBy(source: IEntity): Unit

  /** Applies a basic attack to the target, potentially reducing health based on attack damage.
   *
   *  @param source
   *  The entity initiating the attack.
   *
   *  @throws InvalidTargetException
   *  if the target cannot be attacked or if attacking is not permitted.
   */
  def receiveAttack(source: IEntity): Unit

  /** Inflicts meteorite-based damage on the target/panel, typically dealing area-of-effect damage.
   *
   *  @param source
   *  The entity casting the meteorite attack.
   *
   *  @throws InvalidTargetException
   *  if the target cannot handle meteorite attacks.
   */
  def receiveMeteorite(source: IEntity): Unit

  /** Inflicts thunder-based damage on the target.
   *
   *  @param source
   *  The entity casting the thunder attack.
   *
   *  @throws InvalidTargetException
   *  if the target cannot be affected by thunder attacks.
   */
  def receiveThunder(source: IEntity): Unit

  /** Heals the target.
   *
   *  @param source
   *  The entity performing the healing action.
   *
   *  @throws InvalidTargetException
   *  if the target cannot be healed or if healing is restricted.
   */
  def receiveHealing(source: IEntity): Unit
}