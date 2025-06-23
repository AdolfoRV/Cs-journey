package factory.entities

import factory.AbstractFactory
import model.entity.characters.common.Thief
import scala.util.Random

/** A factory object for creating Thief characters.
 *
 * The ThiefFactory is responsible for generating Thief instances with randomized attributes.
 *
 * @example
 * {{{
 * val thief = ThiefFactory.create("Shadow")
 * }}}
 *
 * @see [[model.entity.characters.common.Thief]] for the Thief character class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 * 
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]        
 */
object ThiefFactory extends AbstractFactory[Thief] {
  override val names: List[String] = List("Shadow", "Terra", "Gogo", "Relm", "Setzer")

  override def create(name: String): Thief = {
    val maxHp = Random.between(70, 120) // Adjusted range for Thief's health
    val defense = Random.between(10, 20) // Adjusted range for Thief's defense
    val weight = Random.between(20, 50) // Adjusted range for Thief's weight
    val id = generateId(name)
    new Thief(name, maxHp, defense, weight, id=id)
  }
}