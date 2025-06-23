package factory.entities

import factory.AbstractFactory
import model.entity.characters.common.Knight
import scala.util.Random

/** A factory object for creating Knight characters.
 *
 * The KnightFactory is responsible for generating Knight instances with randomized attributes.
 *
 * @example
 * {{{
 * val knight = KnightFactory.create("Gogo")
 * }}}
 *
 * @see [[model.entity.characters.common.Knight]] for the Knight character class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]   
 */
object KnightFactory extends AbstractFactory[Knight] {
  override val names: List[String] = List("Gogo", "Relm", "Setzer", "Shadow", "Terra")

  override def create(name: String): Knight = {
    val maxHp = Random.between(120, 220)
    val defense = Random.between(25, 45)
    val weight = Random.between(60, 130)
    val id = generateId(name)
    new Knight(name, maxHp, defense, weight, id=id)
  }
}