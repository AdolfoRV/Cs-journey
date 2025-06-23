package model.action.common

import api.Target
import model.action.AbstractAction
import model.entity.IEntity

/** A class representing an Attack action.
  *
  * The Attack action allows an entity to inflict damage on a target. This
  * action will succeed if the target receives the calculated damage.
  *
  * @constructor
  *   Creates a new Attack action.
  *
  * @example
  *   {{{
  * val attack = new AttackAction()
  *   }}}
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class AttackAction extends AbstractAction(actionId = "action1", name = "Attack") {

  override def executeAction(source: IEntity, target: Target): Unit = {
    target.receiveAttack(source)
  }
}
