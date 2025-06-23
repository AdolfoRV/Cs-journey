package factory

/** A factory trait for creating entities/items of type T.
 *
 * This trait defines the essential methods for creating single or multiple entities/items,
 * as well as generating unique identifiers for them.
 *
 * @tparam T the type that this factory creates.
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait IFactory[T] {

    /** A list of names for the objects to create.
     *
     * @return a list of names for the objects to create.
     */
    val names: List[String]

    /** Creates a single entity with the given name.
     *
     * @param name the name of the object to create.
     * @return a concrete instance of type T.
     */
    def create(name: String): T

    /** Creates multiple entities with the given names.
     *
     * @param count the number of objects to create.
     * @param objectNames a list of names for the objects to create.
     * @return a list of type T.
     */
    def createMany(count: Int, objectNames: List[String]): List[T]

    /** Generates a unique identifier for an entity based on its class, name and maximum HP.
     *
     * @param name the name of the entity.
     * @return a unique identifier for the entity.
     */
    def generateId(name: String): String
}
