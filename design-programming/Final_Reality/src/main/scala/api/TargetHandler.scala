package api

import exceptions.InvalidTargetException
import model.entity.IEntity

/** Abstract handler for target behaviors in the game, defining default actions 
 *  that may be executed upon target entities, such as attacks, healing, 
 *  or elemental effects.
 *
 *  This abstract class provides a default implementation of the [[Target]] trait, 
 *  where each method is defined to throw an [[InvalidTargetException]] by default. 
 *  This approach allows for selectively overriding specific behaviors in 
 *  subclasses based on the type of target and applicable actions.
 *
 *  Subclasses should override the appropriate methods to implement actual 
 *  behavior where necessary. If an action is not applicable to the target, 
 *  the default exception will indicate that the target is invalid for that action.
 *
 *  @throws InvalidTargetException if an action is attempted on an invalid target.
 *  @see [[Target]] for details on target-specific actions.
 *  @see [[model.entity.IEntity]] for general entity information.
 *  @example To implement specific target behavior:
 *  {{{
 *  class CustomTargetHandler extends TargetHandler {
 *    override def receiveHealing(source: IEntity): Unit = {
 *      // custom healing logic
 *    }
 *  }
 *  }}}
 **  @throws InvalidTargetException if the target cannot perform the specified action.
 * 
 *  @see [[model.action.common.UsePotionAction]] to apply potions to entities.
 *  @see [[model.action.common.EquipWeaponAction]] to equip weapons to entities.
 *  @see [[model.action.common.MoveAction]] to move an entity.
 *  @see [[model.action.common.AttackAction]] to process an attack on an entity.
 *  @see [[model.action.magical.PurifyAction]] to purify an entity.
 *  @see [[model.action.magical.MeteorAction]] to apply meteorite damage.
 *  @see [[model.action.magical.ThunderAction]] to apply thunder damage.
 *  @see [[model.action.magical.HealAction]] to heal an entity.
 *       
 *  @example Default usage of TargetHandler:
 *  {{{
 *  val targetHandler = new CustomTargetHandler()
 *  targetHandler.receiveAttack(enemy) // overridden behavior
 *  }}}
 *  @see [[InvalidTargetException]] for exception handling details.
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
abstract class TargetHandler extends Target {
  
  def applyPotionTo(source: IEntity): Unit = throw new InvalidTargetException(this)
  
  def equipWeaponTo(source: IEntity): Unit = throw new InvalidTargetException(this)
  
  def receiveAttack(source: IEntity): Unit = throw new InvalidTargetException(this)
  
  def receiveHealing(source: IEntity): Unit = throw new InvalidTargetException(this)
  
  def receiveMeteorite(source: IEntity): Unit = throw new InvalidTargetException(this)
  
  def purifiedBy(source: IEntity): Unit = throw new InvalidTargetException(this)
  
  def receiveThunder(source: IEntity): Unit = throw new InvalidTargetException(this)
  
  def moveEntity(source: IEntity): Unit = throw new InvalidTargetException(this)
}
