package model.usable.potion

import model.entity.IEntity
import model.entity.characters.ICharacter
import model.entity.characters.common.{Archer, Knight, Thief}
import model.entity.characters.magical.{BlackMage, WhiteMage}
import model.usable.AbstractUsable

/** Abstract class implementing a base potion with default compatibility.
  *
  * `AbstractPotion` defines a base structure for potions, specifying a unique
  * ID and name, as well as default behavior for compatibility checks and potion
  * effects. It can be extended to create specific types of potions.
  *
  * @constructor
  *   Creates a potion with a unique ID and name.
 *
  * @param id
  *   The potion's unique identifier.
  * @param name
  *   The name of the potion.
  *
  * @see
  *   [[IPotion]] for potion behavior.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
abstract class AbstractPotion(id: String, name: String)
    extends AbstractUsable(usableId = s"potion$id", name = s"$name potion")
    with IPotion {

  def canBeConsumedBy(character: ICharacter): Boolean =
    character.canConsumePotion(this)

  def canBeConsumedByArcher(archer: Archer): Boolean = true
  def canBeConsumedByKnight(knight: Knight): Boolean = true
  def canBeConsumedByThief(thief: Thief): Boolean = true
  def canBeConsumedByBlackMage(blackMage: BlackMage): Boolean = true
  def canBeConsumedByWhiteMage(whiteMage: WhiteMage): Boolean = true

  // Target handler
  override def applyPotionTo(source: IEntity): Unit = source.consumePotion(this)
}
// All this behaviour could be implemented via generics and make the code cleaner and less redundant (I guess), but since we must have abstracts for potions and weapons I'll just make the double dispatch this way
