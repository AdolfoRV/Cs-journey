package exceptions

import model.entity.IEntity

/** Exception indicating that a character lacks attack points to perform an action.
 *
 *  This exception is thrown when a character attempts an action requiring
 *  more attack points than it currently possesses.
 *
 *  @param character The entity lacking attack points.
 *  @throws NotEnoughDamageException when damage is insufficient for performing an action.
 *
 *  @see [[IEntity]] for general entity details.
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class NotEnoughDamageException(character: IEntity) extends Exception(s"${character.name} is too weak to perform this action.")