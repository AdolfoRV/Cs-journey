package model.action.magical

import api.Target
import model.entity.IEntity

/** A class representing a Purify spell action.
 *
 * The Purify action removes negative effects from a target unit.
 * The action succeeds if the target successfully receives the purifying effect.
 *
 * @constructor Creates a new Purify spell action.
 *
 * @example
 * {{{
 * val purify = new PurifyAction()
 * }}}
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class PurifyAction extends AbstractSpell(manaCost=25, actionId="3", name="Purify") {

  override protected def performSpell(source: IEntity, target: Target): Unit = {
    target.purifiedBy(source)
  }
}
