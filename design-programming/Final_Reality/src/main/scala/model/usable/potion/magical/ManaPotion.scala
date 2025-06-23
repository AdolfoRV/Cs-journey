package model.usable.potion.magical

import model.entity.characters.ICharacter

/** Class representing a mana potion that restores a portion of a character's mana.
 *
 * `ManaPotion` extends `AbstractMagicalPotion` and provides an effect that 
 * replenishes 30% of the character's maximum mana upon consumption.
 *
 * @constructor
 *   Creates a new mana potion with a default ID and name.
 *
 * @param id
 *   The unique identifier for the mana potion, defaults to "2".
 * @param name
 *   The name of the mana potion, defaults to "Mana".
 *
 * @example
 *   {{{
 * val manaPotion = new ManaPotion()
 * manaPotion.applyEffect(character)
 *   }}}
 *
 * @see
 *   [[AbstractMagicalPotion]] for base magical potion functionality.
 *
 * @see
 *   [[IPotion]] for potion behavior.
 *
 * @author
 *   Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class ManaPotion(id: String = "2", name: String = "Mana") extends AbstractMagicalPotion(id, name) {

  /** Restores 30% of the character's maximum mana. */
  override def applyEffect(character: ICharacter): Unit = {
    character.mana_=((character.maxMana * 0.3).toInt + character.getMana)
  }
}
