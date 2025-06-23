package factory.entities

import factory.AbstractFactory
import model.entity.characters.magical.BlackMage
import scala.util.Random

/** A factory object for creating BlackMage characters.
 *
 * The BlackMageFactory is responsible for generating BlackMage instances with randomized attributes.
 *
 * @example
 * {{{
 * val blackMage = BlackMageFactory.create("Vivi")
 * }}}
 *
 * @see [[model.entity.characters.magical.BlackMage]] for the BlackMage character class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *    
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]     
 */
object BlackMageFactory extends AbstractFactory[BlackMage] {
  override val names: List[String] = List("Terra", "Shadow")

  override def create(name: String): BlackMage = {
    val maxHp = Random.between(60, 120)
    val maxMana = Random.between(150, 250)
    val defense = Random.between(15, 25)
    val weight = Random.between(20, 80)
    val id = generateId(name)
    new BlackMage(name, maxHp, maxMana, defense, weight, id=id)
  }
}