package factory.usables

import factory.AbstractFactory
import model.usable.weapon.magical.Staff
import scala.util.Random

/** A factory object for creating Staff instances.
 *
 * The StaffFactory is responsible for generating Staff instances with randomized attributes.
 *
 * @example
 * {{{
 * val staff = StaffFactory.create("Arcane Staff")
 * }}}
 *
 * @see [[model.usable.weapon.Staff]] for the Staff class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *    
 */
object StaffFactory extends AbstractFactory[Staff] {
  override val names: List[String] = List("Arcane Staff", "Mystic Staff", "Elder Staff")

  override def create(name: String): Staff = {
    val atk = Random.between(15, 40)
    val magicAtk = Random.between(30, 60)
    val weight = Random.between(10, 25)
    new Staff(name, atk, magicAtk, weight)
  }
}