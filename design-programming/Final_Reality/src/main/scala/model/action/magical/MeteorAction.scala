package model.action.magical

import api.Target
import model.entity.IEntity

/** A class representing a Meteor spell action.
  *
  * The Meteor action inflicts damage on a panel. The action
  * succeeds if the entities in the target receive the damage from the meteor spell.
  *
  * @constructor
  *   Creates a new Meteor spell action.
  *
  * @example
  *   {{{
  * val meteor = new MeteorAction()
  *   }}}
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class MeteorAction
    extends AbstractSpell(manaCost=50, "2", "Meteor") {

  override protected def performSpell(source: IEntity, target: Target): Unit = {
    target.receiveMeteorite(source)
  }
}
