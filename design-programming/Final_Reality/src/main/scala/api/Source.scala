package api

import model.action.IAction

trait Source extends GameObject {

  /** The name of the source. */
  val name: String

  /** The actions that the source can perform. */
  def actions: List[IAction]

  /** Finds an action by its id. */
  def findActionById(id: String): Option[IAction]

  /** Performs an action on a target. */
  def doAction(action: IAction, target: Target): Unit
}