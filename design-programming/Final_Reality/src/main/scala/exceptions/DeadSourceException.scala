package exceptions

import model.entity.IEntity

/** Exception indicating that the specified entity is no longer in the current plane of existence.
 *
 *  This exception is thrown when an action is attempted on an entity that is considered
 *  "dead" or otherwise unavailable due to its state.
 *
 *  @param entity The entity that is in an unavailable state.
 *  @throws DeadSourceException when attempting actions on a "dead" entity.
 *
 *  @see [[IEntity]] for general entity behavior.
 *
 *  @example Usage:
 *  {{{
 *  if (entity.defeated) throw new DeadSourceException(entity)
 *  }}}
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class DeadSourceException(entity: IEntity) extends Exception(s"${entity.name} is in another plane of existence.")
