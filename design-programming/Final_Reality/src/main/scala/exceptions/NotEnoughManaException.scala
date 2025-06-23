package exceptions

import model.entity.IEntity


/** Exception indicating that a character lacks sufficient mana to perform an action.
 *
 *  This exception is thrown when a magical entity attempts an action requiring
 *  more mana than it currently possesses.
 *
 *  @param mage The entity lacking sufficient mana.
 *  @throws NotEnoughManaException when mana is insufficient for an action.
 *
 *  @see [[IEntity]] for general entity details.
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class NotEnoughManaException(mage: IEntity) extends Exception(s"${mage.name} does not have enough mana.")