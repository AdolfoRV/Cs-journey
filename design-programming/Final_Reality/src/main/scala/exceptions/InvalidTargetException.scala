package exceptions

import api.Target


/** Exception indicating that the selected target is invalid for the intended action.
 *
 *  This exception is thrown when an action is directed at a target that is
 *  ineligible for that action type based on game rules or the target’s state.
 *
 *  @param target The invalid target.
 *  @throws InvalidTargetException when an action targets an invalid entity.
 *
 *  @see [[Target]] for target interface details.
 *
 *  @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class InvalidTargetException(target: Target) extends Exception(s"${target.name} cannot be selected as target.")
