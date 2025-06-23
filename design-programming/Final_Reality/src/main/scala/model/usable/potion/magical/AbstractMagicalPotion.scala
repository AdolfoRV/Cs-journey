package model.usable.potion.magical

import model.entity.characters.common.{Archer, Knight, Thief}
import model.usable.potion.AbstractPotion

/** Abstract class representing a magical potion for magical character classes.
 *
 * `AbstractMagicalPotion` extends `AbstractPotion` and restricts usage to
 * magical classes by disabling compatibility for non-magical classes such as
 * Archer, Knight, and Thief.
 *
 * @constructor
 *   Creates a magical potion with a specific ID and name.
 *
 * @param id
 *   The potion's unique identifier, modified for magical potions.
 * @param name
 *   The name of the magical potion.
 *
 * @see
 *   [[model.usable.potion.AbstractPotion]] for potion behavior.
 * @see
 *   [[IPotion]] for potion details.*
 * @see
 *   [[MagicStrengthPotion]], [[ManaPotion]]
 *
 * @author
 *   Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
abstract class AbstractMagicalPotion(id: String, name: String) extends AbstractPotion(id=s"_magic$id", name) {

  override def canBeConsumedByArcher(archer: Archer): Boolean = false
  override def canBeConsumedByKnight(knight: Knight): Boolean = false
  override def canBeConsumedByThief(thief: Thief): Boolean = false
}