package model.action.magical

import api.Target
import model.entity.IEntity

/** A class representing a Heal spell action.
 *
 * The Heal action restores health points to a target unit.
 * The action succeeds if the target receives the healing effect.
 *
 * @constructor Creates a new Heal spell action.
 *
 * @example
 * {{{
 * val heal = new HealAction()
 * }}}
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class HealAction extends AbstractSpell(manaCost=15, actionId="1", name="Heal") {

  override protected def performSpell(source: IEntity, target: Target): Unit = {
    target.receiveHealing(source)
  }
}