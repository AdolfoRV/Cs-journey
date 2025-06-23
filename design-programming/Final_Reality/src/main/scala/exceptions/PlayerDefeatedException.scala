package exceptions

import model.player.IPlayer

/** Exception indicating that a player has been defeated.
 *
 *  This exception is thrown when a player is defeated in the game.
 *
 *  @param message The message to be displayed.
 *  @throws PlayerDefeatedException when a player is defeated.
 *
 *  @see [[IPlayer]] for general player behavior.
 *
 *  @example Usage:
 *  {{{
 *  if (player.playerDefeated) throw new PlayerDefeatedException(player)
 *  }}}
 */
class PlayerDefeatedException(message: String) extends Exception(message)
