package factory.usables

import factory.AbstractFactory
import model.usable.weapon.common.Sword
import scala.util.Random

/** A factory object for creating Sword instances.
 *
 * The SwordFactory is responsible for generating Sword instances with randomized attributes.
 *
 * @example
 * {{{
 * val sword = SwordFactory.create("Excalibur")
 * }}}
 *
 * @see [[model.usable.weapon.Sword]] for the Sword class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *    
 */
object SwordFactory extends AbstractFactory[Sword] {
  override val names: List[String] = List("Excalibur", "Katana", "Claymore")

  override def create(name: String): Sword = {
    val atk = Random.between(20, 70)
    val weight = Random.between(10, 30)
    new Sword(name, atk, weight)
  }
}