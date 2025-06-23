package factory.usables

import factory.AbstractFactory
import model.usable.weapon.common.Bow

import scala.util.Random

/** A factory object for creating Bow instances.
 *
 * The BowFactory is responsible for generating Bow instances with randomized attributes.
 *
 * @example
 * {{{
 * val bow = BowFactory.create("Longbow")
 * }}}
 *
 * @see [[model.usable.weapon.Bow]] for the Bow class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *    
 */
object BowFactory extends AbstractFactory[Bow] {
  override val names: List[String] = List("Longbow", "Shortbow", "Crossbow")

  override def create(name: String): Bow = {
    val atk = Random.between(10, 50)
    val weight = Random.between(5, 20)
    new Bow(name, atk, weight)
  }
}