package factory.entities

import factory.AbstractFactory
import model.entity.characters.magical.WhiteMage
import scala.util.Random

/** A factory object for creating WhiteMage characters.
 *
 * The WhiteMageFactory is responsible for generating WhiteMage instances with randomized attributes.
 *
 * @example
 * {{{
 * val whiteMage = WhiteMageFactory.create("Setzer")
 * }}}
 *
 * @see [[model.entity.characters.magical.WhiteMage]] for the WhiteMage character class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 * 
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]     
 */
object WhiteMageFactory extends AbstractFactory[WhiteMage] {
  override val names: List[String] = List("Setzer", "Relm")

  override def create(name: String): WhiteMage = {
    val maxHp = Random.between(70, 130)
    val maxMana = Random.between(120, 220)
    val defense = Random.between(12, 28)
    val weight = Random.between(25, 85)
    val id = generateId(name)
    new WhiteMage(name, maxHp, maxMana, defense, weight, id=id)
  }
}