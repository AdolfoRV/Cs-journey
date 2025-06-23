package model.usable.potion.magical

import model.entity.characters.ICharacter

/** Class representing a magic strengthening potion for enhancing magical strength.
 *
 * `MagicStrengthPotion` extends `AbstractMagicalPotion` and provides an effect 
 * that temporarily boosts the character's magic strength upon consumption.
 *
 * @constructor
 *   Creates a new magic strengthening potion with a default ID and name.
 *
 * @param id
 *   The unique identifier for the magic strengthening potion, defaults to "1".
 * @param name
 *   The name of the magic strengthening potion, defaults to "Magic strengthening".
 *
 * @example
 *   {{{
 * val magicPotion = new MagicStrengthPotion()
 * magicPotion.applyEffect(character)
 *   }}}
 *
 * @see
 *   [[AbstractMagicalPotion]] for base magical potion functionality.
 * @see
 *   [[IPotion]] for potion behavior.
 *
 * @author
 *   Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class MagicStrengthPotion(id: String = "1", name: String = "Magic strengthening") extends AbstractMagicalPotion(id, name) {

  /** Activates a magic strength buff for the character. */
  override def applyEffect(character: ICharacter): Unit = {
    character.magicStrengthBuff = true
  }
}
