package factory.usables

import factory.AbstractFactory
import model.usable.weapon.common.Dagger

import scala.util.Random

/** A factory object for creating Dagger instances.
 *
 * The DaggerFactory is responsible for generating Dagger instances with randomized attributes.
 *
 * @example
 * {{{
 * val dagger = DaggerFactory.create("Shadow Blade")
 * }}}
 *
 * @see [[model.usable.weapon.Dagger]] for the Dagger class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *    
 */
object DaggerFactory extends AbstractFactory[Dagger] {
  override val names: List[String] = List("Shadow Blade", "Stiletto", "Kris")

  override def create(name: String): Dagger = {
    val atk = Random.between(15, 40)
    val weight = Random.between(3, 10)
    new Dagger(name, atk, weight)
  }
}