package model.entity.characters.magical

import model.entity.characters.ICharacter

/** Interface for magical characters.
 *
 * This interface extends the `ICharacter` interface and adds additional functionality
 * specific to characters that can cast spells and utilize magical abilities. Magical characters 
 * have a base mana attribute that governs their ability to perform magical actions.
 *
 * The interface defines methods for managing mana, including getting and setting the current
 * mana of the character, as well as retrieving magic attack power based on the currently 
 * equipped magical weapon.
 *
 * @see [[ICharacter]] for basic character functionalities.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait IMagicalCharacter extends ICharacter {
}
