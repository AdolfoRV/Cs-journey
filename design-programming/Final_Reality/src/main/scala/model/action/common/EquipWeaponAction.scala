package model.action.common

import api.Target
import model.action.AbstractActionWithUsable
import model.entity.IEntity
import model.usable.weapon.IWeapon

/** A class representing an Equip Weapon action.
  *
  * The EquipWeapon action allows a character to equip a weapon from their
  * inventory. The action succeeds only if the character is able to equip the
  * weapon.
  *
  * @param weapons
  *   A list of weapons that can be equipped during this action.
  *
  * @constructor
  *   Creates a new Equip Weapon action with the specified weapons.
  *
  * @example
  *   {{{
  * val sword = new Sword(name="Meowmere", atk = 50, weight = 30)
  * val equipAction = new EquipWeaponAction(List(sword))
  *   }}}
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class EquipWeaponAction(weapons: List[IWeapon]) extends AbstractActionWithUsable(actionId = "action2", name = "Equip weapon", weapons) {

  override def executeAction(source: IEntity, target: Target): Unit = {
    target.equipWeaponTo(source)
  }
}
