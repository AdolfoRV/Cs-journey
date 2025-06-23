package model.action.magical

import api.Target
import model.action.IAction
import model.entity.IEntity

/** Trait representing a spell action in the game.
 *
 * The `ISpell` trait defines the structure for spell-based actions that can
 * be performed by magical entities in the game. Each spell has a unique
 * identifier, a name, and a mana cost required to cast it.
 *
 * Implementations of `ISpell` can include various types of spells, such as
 * healing spells or offensive magic, and will define the specific behavior of
 * each spell.
 *
 * All spells must verify that the caster has sufficient mana before execution.
 *
 * @see [[model.action.IAction]] for common entity behaviors.
 *
 * @author
 *   Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait ISpell extends IAction {

  /** The mana cost required to cast the spell. */
  val manaCost: Int

  /** Casts the spell on the target.
   *
   * Checks if the caster has enough mana to perform the spell. If the caster's
   * mana is below the required `manaCost`, an exception is thrown.
   *
   * @param source
   *   The magical entity casting the spell.
   * @param target
   *   The entity or object receiving the effect of the spell.
   *
   * @throws NotEnoughManaException
   *   if the caster does not have sufficient mana.
   */
  protected def performSpell(source: IEntity, target: Target): Unit
}