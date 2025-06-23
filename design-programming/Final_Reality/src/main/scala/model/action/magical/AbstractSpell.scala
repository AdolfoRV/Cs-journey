package model.action.magical

import api.Target
import exceptions.{NotEnoughDamageException, NotEnoughManaException}
import model.action.AbstractAction
import model.entity.IEntity

/** Represents a generic spell action in the game, requiring a certain amount of
  * mana to cast.
  *
  * The `AbstractSpell` class serves as a base class for all spell-based actions
  * in the game, enforcing a mana cost requirement and handling the basic
  * execution logic, including mana deduction. Specific spell actions should
  * extend this class and implement the `performSpell` method, defining the
  * unique behavior of each spell.
  *
  * @constructor
  *   Creates a new spell action with a specified mana cost, action ID, and
  *   spell name.
  *
  * @param manaCost
  *   The amount of mana required to cast the spell.
  * @param actionId
  *   The unique identifier for this spell action.
  * @param name
  *   The name of the spell.
  *
  * @throws NotEnoughManaException
  *   if the source entity does not have sufficient mana to cast the spell.
  * @throws NotEnoughDamageException
  *   if the source entity does not have sufficient magic attack points to cast the spell.
  *
  * @see
  *   [[IEntity]] for entity details.
  * @see
  *   [[Target]] for target details.
  *
  * @note
  *   Classes extending `AbstractSpell` should override `performSpell` to define
  *   the spell’s specific effects.
  *
  * @see
  *   [[ISpell]] for general spell details.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
abstract class AbstractSpell(val manaCost: Int, actionId: String, name: String)
    extends AbstractAction(
      actionId = s"spell_action$actionId",
      name = s"Spell→$name"
    )
    with ISpell {

  override def executeAction(source: IEntity, target: Target): Unit = {
    if (source.getMana < manaCost) {
      throw new NotEnoughManaException(source)
    } else if (source.getMagicAtk <= 0) {
      throw new NotEnoughDamageException(source)
    }
    performSpell(source, target)
    source.mana_=(source.getMana - manaCost)
  }
}
