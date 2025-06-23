package model.action.common

import api.Target
import model.action.AbstractActionWithUsable
import model.entity.IEntity
import model.usable.potion.IPotion

/** A class representing a Use Potion action.
  *
  * The UsePotion action allows a character to consume a potion from its
  * inventory. The action succeeds if the character is able to consume the
  * selected potion.
  *
  * @param potions
  *   A list of potions that can be used during this action.
  *
  * @constructor
  *   Creates a new Use Potion action with the specified potions.
  *
  * @example
  *   {{{
  * val healthPotion = new HealingPotion()
  * val usePotionAction = new UsePotionAction(List(healthPotion))
  *   }}}
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class UsePotionAction(potions: List[IPotion]) extends AbstractActionWithUsable(actionId = "action4", name = "Use potion", potions) {

  override def executeAction(source: IEntity, target: Target): Unit = {
    target.applyPotionTo(source)
  }
}
