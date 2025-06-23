package factory.entities

import factory.AbstractFactory
import model.entity.characters.common.Archer
import scala.util.Random

/** A factory object for creating Archer characters.
 *
 * The ArcherFactory is responsible for generating Archer instances with randomized attributes.
 *
 * @example
 * {{{
 * val archer = ArcherFactory.create("Robin")
 * }}}
 *
 * @see [[model.entity.characters.common.Archer]] for the Archer character class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *   
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]   
 */
object ArcherFactory extends AbstractFactory[Archer] {
  override val names: List[String] = List("Relm", "Setzer", "Terra", "Shadow","Gogo")
  
  override def create(name: String): Archer = {
    val maxHp = Random.between(60, 110) 
    val defense = Random.between(12, 20)
    val weight = Random.between(30, 70)
    val id = generateId(name)
    new Archer(name, maxHp, defense, weight, id=id)
  }
}