package model.panel

import api.TargetHandler
import exceptions.InvalidTargetException
import model.entity.IEntity
import util.SprayJson.{*, given}

import scala.collection.mutable.ArrayBuffer
import scala.math.abs

/** Class representing a concrete implementation of a game panel.
 *
 * The `Panel` class implements the `IPanel` trait, defining the behavior 
 * and properties of a panel in the game world. It manages the storage of 
 * entities currently on the panel and handles adjacency checks for movement.
 *
 * @param x
 *   The x coordinate of the panel.
 * @param y
 *   The y coordinate of the panel.
 * @param storage
 *   The current entities present on the panel, represented as 
 *   a List.
 * @param panelId
 *   The unique identifier for this panel, defaults to "panel1".
 *   
 * @see [[IPanel]] for panel functionality.
 *      
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
class Panel(val x: Int, val y: Int, var storage: ArrayBuffer[IEntity] = ArrayBuffer(), val panelId: String = "panel1"
) extends TargetHandler with IPanel {

  override val name: String = s"Panel ($x, $y)"
  
  var panels: ArrayBuffer[IPanel] = ArrayBuffer()

  override def adjacentPanels: List[IPanel] = {
    panels.filter(panel =>
      (panel.x == x && math.abs(panel.y - y) == 1) || (panel.y == y && math.abs(panel.x - x) == 1)
    ).toList
  }

  override def isPresent(entity: IEntity): Boolean = {
    storage.contains(entity)
  }

  override def addCharacter(entity: IEntity): Unit = {
    if (isPresent(entity)) {
      throw new InvalidTargetException(this)
    } else {
      storage += entity
    }
  }

  override def removeCharacter(entity: IEntity): Unit = {
    if (!isPresent(entity)) {
      throw new InvalidTargetException(this)      // This will never happen
    } else {
      storage -= entity
    }
  }
  
  override def id: String = panelId
  
  override def toJson: JsObj = {
    val storageIds = storage.map(_.id).toList         
    JsObj(
      "id" -> id,
      "x" -> x,
      "y" -> y,
      "storage" -> JsArr(storageIds)
    )
  }
  
  // Target handler
  override def moveEntity(source: IEntity): Unit = {
    val currentPanel = adjacentPanels.find(panel => panel.isPresent(source))

    if (currentPanel.isEmpty) {
      throw new InvalidTargetException(this)
    }

    currentPanel.get.removeCharacter(source)
    addCharacter(source)
  }

  override def receiveMeteorite(source: IEntity): Unit = {
    storage.foreach(entity => entity.hp_=(entity.getHp - source.getMagicAtk))
  }
}
