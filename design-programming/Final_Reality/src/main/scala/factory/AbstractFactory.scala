package factory

import java.util.Objects

/** Abstract factory class for creating game entities.
 *
 * `AbstractEntityFactory` provides the basic functionality for creating multiple instances of game entities.
 * It includes methods for creating a specified number of entities and generating unique identifiers for them.
 *
 * @constructor
 *   Creates a new factory with default entity names.
 *
 * @tparam T
 *   The type of entity to be created.
 *
 * @see
 *   [[IEntity]] for the entity interface.
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
abstract class AbstractFactory[T] extends IFactory[T] {
  val names: List[String] = List.empty // default name
  
  def create(str: String): T = throw new NotImplementedError("Method create not implemented")

  def createMany(count: Int, objectNames: List[String] = names): List[T] = {
    if (count > objectNames.length) {
      throw new IllegalArgumentException("Not enough names to create")
    }
    val concretes = for (i <- 0 until count) yield create(objectNames(i))
    concretes.toList
  }

  // Reference in the faq for hashMap
  def generateId(name: String): String = {
    val hash = Objects.hash(name, getClass.getName)
    hash.toString
  }
}