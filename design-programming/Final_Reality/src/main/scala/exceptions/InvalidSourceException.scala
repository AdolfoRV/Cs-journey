package exceptions

import model.entity.IEntity

/** Exception indicating that an entity is not eligible to perform a specific action.
 *
 *  This exception is thrown when an entity attempts to perform an action that it
 *  is not qualified to execute based on its type or state.
 *
 *  @param entity The entity attempting to perform the action.
 *  @throws InvalidSourceException when the entity cannot perform the action.
 *
 *  @see [[IEntity]] for general entity details.
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class InvalidSourceException(entity: IEntity) extends Exception(s"${entity.name} cannot perform this action.")