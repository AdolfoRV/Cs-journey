package model.action.common

import api.Target
import model.action.AbstractAction
import model.entity.IEntity

/** A class representing a Move action.
  *
  * The Move action allows an entity to move from one location to an adjacent
  * one. The action succeeds only if the unit is able to move to the target
  * location.
  *
  * @constructor
  *   Creates a new Move action.
  *
  * @example
  *   {{{
  * val move = new MoveAction()
  *   }}}
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class MoveAction extends AbstractAction(actionId = "2", name = "Move") {

  override def executeAction(source: IEntity, target: Target): Unit = {
    target.moveEntity(source)
  }
}
