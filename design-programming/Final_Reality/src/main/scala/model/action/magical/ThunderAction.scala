package model.action.magical

import api.Target
import model.entity.IEntity

/** A class representing a Thunder spell action.
 *
 * The Thunder action inflicts lightning damage on a target unit.
 * The action succeeds if the target receives the lightning damage from the spell.
 *
 * @constructor Creates a new Thunder spell action.
 *
 * @example
 * {{{
 * val thunder = new ThunderAction()
 * }}}
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class ThunderAction extends AbstractSpell(manaCost=20, actionId="4", name="Thunder") {

  override protected def performSpell(source: IEntity, target: Target): Unit = {
    target.receiveThunder(source)
  }
}
