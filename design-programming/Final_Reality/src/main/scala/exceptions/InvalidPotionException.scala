package exceptions

import model.entity.characters.ICharacter
import model.usable.potion.IPotion


/** Exception indicating that a potion is incompatible with the character trying to consume it.
 *
 *  This exception is thrown when an attempt is made to use a potion that is not consumable
 *  by a specific character, based on compatibility rules or restrictions.
 *
 *  @param character The character attempting to consume the potion.
 *  @param potion The incompatible potion.
 *  @throws InvalidPotionException when a character tries to consume an invalid potion.
 *
 *  @see [[ICharacter]] for character behaviors.
 *  @see [[IPotion]] for potion details.
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class InvalidPotionException(character: ICharacter, potion: IPotion) extends Exception(s"${potion.name} cannot be consumed by ${character.name}.")