package exceptions

import model.entity.characters.ICharacter
import model.usable.weapon.IWeapon


/** Exception indicating that a weapon is incompatible with the character trying to equip it.
 *
 *  This exception is thrown when a character attempts to equip a weapon that
 *  they are not authorized or able to wield.
 *
 *  @param character The character attempting to equip the weapon.
 *  @param weapon The incompatible weapon.
 *  @throws InvalidWeaponException when a weapon is not equippable by the character.
 *
 *  @see [[ICharacter]] for character behaviors.
 *  @see [[IWeapon]] for weapon details.
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class InvalidWeaponException(character: ICharacter, weapon: IWeapon) extends Exception(s"${weapon.name} cannot be equipped by ${character.name}.")