package factory.usables

import factory.AbstractFactory
import model.usable.potion.common.{DefensePotion, HealingPotion}
import model.usable.potion.magical.{MagicStrengthPotion, ManaPotion}
import model.usable.potion.IPotion

/** A factory object for creating potion instances.
 *
 * The PotionFactory is responsible for generating various types of potion instances.
 *
 * @example
 * {{{
 * val healingPotion = PotionFactory.create("Healing")
 * }}}
 *
 * @see [[model.usable.potion.IPotion]] for the potion interface.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *    
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
object PotionFactory extends AbstractFactory[IPotion] {
  override val names: List[String] = List("Healing", "Defense", "Mana", "MagicStrength")

  override def create(name: String): IPotion = name match {
    case "Healing" => new HealingPotion()
    case "Defense" => new DefensePotion()
    case "Mana" => new ManaPotion()
    case "MagicStrength" => new MagicStrengthPotion()
    case _ => throw new IllegalArgumentException(s"Unknown potion type: $name")
  }
}