package exceptions

import model.entity.characters.ICharacter
import model.usable.IUsable

/** Exception indicating that an item is not present in the character's inventory.
 *
 *  This exception is thrown when a character attempts to use an item that
 *  is not in their current inventory.
 *
 *  @param character The character whose inventory is being accessed.
 *  @param usable The item that is missing from the inventory.
 *  @throws NotInInventoryException when the item is not found in the inventory.
 *
 *  @see [[ICharacter]] for character behaviors.
 *  @see [[IUsable]] for usable items.
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class NotInInventoryException(character: ICharacter, usable: IUsable) extends Exception(s"The item ${usable.name} is not available in ${character.name}'s inventory.")