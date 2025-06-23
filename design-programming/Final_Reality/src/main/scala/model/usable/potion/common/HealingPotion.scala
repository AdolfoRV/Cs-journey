package model.usable.potion.common

import model.entity.characters.ICharacter
import model.usable.potion.AbstractPotion

/** Class representing a healing potion that restores a portion of a character's
  * health.
  *
  * `HealingPotion` extends `AbstractPotion` and provides a specific healing
  * effect that restores 20% of the character's maximum health when consumed.
  *
  * @constructor
  *   Creates a new healing potion with a default ID and name.
  *
  * @param id
  *   The unique identifier for the healing potion, defaults to "2".
  * @param name
  *   The name of the healing potion, defaults to "Healing".
  *
  * @example
  *   {{{
  * val healingPotion = new HealingPotion()
  * healingPotion.applyEffect(character)
  *   }}}
  *
  * @see
  *   [[model.usable.potion.IPotion]] for potion behavior.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
class HealingPotion(id: String = "2", name: String = "Healing") extends AbstractPotion(id, name) {

  /** Restores 20% of the character's maximum health. */
  override def applyEffect(character: ICharacter): Unit = {
    character.hp_=((character.maxHp * 0.2).toInt + character.getHp)
  }
}
