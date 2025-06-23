package factory.usables

import factory.AbstractFactory
import model.usable.weapon.magical.Wand
import scala.util.Random

/** A factory object for creating Wand instances.
 *
 * The WandFactory is responsible for generating Wand instances with randomized attributes.
 *
 * @example
 * {{{
 * val wand = WandFactory.create("The Elder Wand")
 * }}}
 *
 * @see [[model.usable.weapon.Wand]] for the Wand class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *    
 */
object WandFactory extends AbstractFactory[Wand] {
  override val names: List[String] = List("The Elder Wand", "Phoenix Feather Wand", "Dragon Heartstring Wand")

  override def create(name: String): Wand = {
    val atk = Random.between(10, 30)
    val magicAtk = Random.between(20, 50)
    val weight = Random.between(5, 15)
    new Wand(name, atk, magicAtk, weight)
  }
}