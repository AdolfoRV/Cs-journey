package model.panel

import exceptions.InvalidTargetException
import model.entity.IEntity
import model.entity.characters.common.Archer
import model.entity.characters.magical.WhiteMage
import model.usable.weapon.magical.Wand
import util.SprayJson.*
import munit.FunSuite

import scala.collection.mutable.ArrayBuffer

class PanelTest extends FunSuite {
  var wand: Wand = _

  var panel11: IPanel = _
  var panel21: IPanel = _
  var panel22: IPanel = _

  var entity1: IEntity = _
  var entity2: IEntity = _

  override def beforeEach(context: BeforeEach): Unit = {
    wand = new Wand(name = "The Elder Wand", atk = 25, magicAtk = 20, weight = 10)

    panel11 = new Panel(x=1, y=1, storage=ArrayBuffer.empty, panelId="panel11")
    panel21 = new Panel(2, 1, ArrayBuffer.empty, "panel21")
    panel22 = new Panel(2, 2, ArrayBuffer.empty, "panel22")

    entity1 = new Archer("Cupid", 100, 20, 50, List(wand), "archer1")
    entity2 = new WhiteMage("Aqua", 70, 10, 10, 100, List(wand), "mage1")
  }

  test("Add entity to panel") {
    assert(!panel11.isPresent(entity1))

    panel11.addCharacter(entity1)
    assert(panel11.isPresent(entity1))
  }

  test("Remove entity from panel") {
    panel11.addCharacter(entity1)
    assert(panel11.isPresent(entity1))

    panel11.removeCharacter(entity1)
    assert(!panel11.isPresent(entity1))
  }

  test("Cannot add the same entity twice") {
    panel11.addCharacter(entity1)

    intercept[InvalidTargetException] {
      panel11.addCharacter(entity1)
    }

    assertEquals(panel11.storage.count(_ == entity1), 1)
  }


  test("Cannot remove non-present entity") {
    assert(!panel11.isPresent(entity1))

    intercept[InvalidTargetException] {
      panel11.removeCharacter(entity1)
    }
  }

  test("Panel toJson with entities") {
    panel11.addCharacter(entity2)

    val expectedJson = JsObj(
      "id" -> JsStr("panel11"),
      "x" -> JsNum(1),
      "y" -> JsNum(1),
      "storage" -> JsArr(
        JsStr("mage1"),
      )
    )

    assertEquals(panel11.toJson, expectedJson)
  }

  test("Move entity to an adjacent panel") {
    panel11.addCharacter(entity1)
    panel11.panels ++= ArrayBuffer(panel21)
    panel21.panels ++= ArrayBuffer(panel11)

    panel21.moveEntity(entity1)

    assert(!panel11.isPresent(entity1))
    assert(panel21.isPresent(entity1))
  }

  test("Cannot move entity to a non-adjacent panel") {
    panel11.addCharacter(entity1)
    panel11.panels += panel22 // Only non-adjacent panel

    intercept[InvalidTargetException] {
      panel11.moveEntity(entity1)
    }

    assert(panel11.isPresent(entity1)) // Entity remains on the original panel
  }

  test("receiveMeteorite reduces HP of entities") {
    entity1.hp_=(100)
    entity2.hp_=(70)

    panel11.addCharacter(entity1)
    panel11.addCharacter(entity2)

    entity2.equipWeapon(wand)
    panel11.receiveMeteorite(entity2)

    assertEquals(entity1.getHp, 80) // 100 - 20 = 80
    assertEquals(entity2.getHp, 50) // 70 - 20 = 50
  }

}
