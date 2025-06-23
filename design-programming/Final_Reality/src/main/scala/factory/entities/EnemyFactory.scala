package factory.entities

import factory.AbstractFactory
import model.entity.Enemy
import scala.util.Random

/** A factory object for creating Enemy characters.
 *
 * The EnemyFactory is responsible for generating Enemy instances with randomized attributes.
 *
 * @example
 * {{{
 * val enemy = EnemyFactory.create("Crawly")
 * }}}
 *
 * @see [[model.entity.Enemy]] for the Enemy character class.
 * @see [[factory.AbstractFactory]] for the base factory behavior.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]   
 */
object EnemyFactory extends AbstractFactory[Enemy] {
  override val names: List[String] = List("Crawly", "Mag Roader", "Nohrabbit", "Primordite", "Scorpion", "Wizard")

  override def create(name: String): Enemy = {
    val maxHp = Random.between(80, 180)
    val defense = Random.between(18, 38)
    val atk = Random.between(35, 75)
    val weight = Random.between(40, 100)
    val id = generateId(name)
    new Enemy(name, maxHp, defense, atk, weight, id)
  }
}