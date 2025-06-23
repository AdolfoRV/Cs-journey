package model.usable.potion.common

import model.entity.characters.ICharacter
import model.usable.potion.AbstractPotion

/** Class representing a defense potion that enhances a character's defense.
 *
 * `DefensePotion` extends `AbstractPotion` and provides a specific effect
 * that increases the character's defense by 15% when consumed.
 *
 * @constructor
 *   Creates a new defense potion with a default ID and name.
 *
 * @param id
 *   The unique identifier for the defense potion, defaults to "1".
 * @param name
 *   The name of the defense potion, defaults to "Defense".
 *
 * @example
 *   {{{
 * val defensePotion = new DefensePotion()
 * defensePotion.applyEffect(character)
 *   }}}
 *
 * @see
 *   [[model.usable.potion.IPotion]] for potion behavior.
 *
 * @see
 *   [[AbstractPotion]] for the base potion functionality.
 *
 * @author
 *   Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class DefensePotion(id: String = "1", name: String = "Defense") extends AbstractPotion(id, name) {

  /** Increases the character's defense by 15%. */
  override def applyEffect(character: ICharacter): Unit = {
    character.defense_=((character.getDefense * 1.15).toInt)
  }
}

