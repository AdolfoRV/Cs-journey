package model.entity.characters.magical

import util.SprayJson.{*, given}
import model.entity.characters.AbstractCharacter
import model.usable.IUsable

/** Abstract class for magical characters */
abstract class AbstractMagicalCharacter(name: String, maxHp: Int, override val maxMana: Int, defense: Int, weight: Int, inventory: List[IUsable], id: String) extends AbstractCharacter(name, maxHp, defense, weight, inventory, id) with IMagicalCharacter {
  
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "attributes" -> JsArr(
      JsObj("name" -> "name", "value" -> name),
      JsObj("name" -> "hp", "value" -> getHp),
      JsObj("name" -> "atk", "value" -> getAtk),
      JsObj("name" -> "def", "value" -> getDefense),
      JsObj("name" -> "mp", "value" -> getMana)
    ),
    "img" -> (name + ".gif")
  )
}
