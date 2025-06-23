package model.action.common

import model.entity.Enemy
import model.entity.characters.common.Archer
import model.entity.characters.magical.WhiteMage
import model.panel.Panel
import model.usable.IUsable
import model.usable.weapon.common.Sword
import model.usable.potion.common.{DefensePotion, HealingPotion}
import model.usable.weapon.magical.Wand
import util.SprayJson.*
import munit.FunSuite

import scala.collection.mutable.ArrayBuffer

class CommonActionTest extends FunSuite {
  var sword: Sword = _
  var wand: Wand = _
  var defensePotion: DefensePotion = _
  var healingPotion: HealingPotion = _
  var inventory: List[IUsable] = _

  var entity1: Archer = _
  var entity2: WhiteMage = _
  var enemy: Enemy = _

  var panel11: Panel = _
  var panel21: Panel = _

  var attackAction: AttackAction = _
  var equipWeaponAction: EquipWeaponAction = _
  var moveAction: MoveAction = _
  var usePotionAction: UsePotionAction = _

  override def beforeEach(context: BeforeEach): Unit = {
    sword = new Sword(name="Meowmere", atk = 50, weight = 30)
    wand = new Wand(name = "The Elder Wand", atk = 25, magicAtk = 20, weight = 10)
    defensePotion = new DefensePotion()
    healingPotion = new HealingPotion()
    inventory = List(sword, wand, defensePotion, healingPotion)

    entity1 = new Archer("Cupid", 100, 20, 50, inventory, "archer1")
    entity2 = new WhiteMage("Aqua", 70, 10, 10, 100, inventory, "mage1")
    enemy = new Enemy(name = "King Dedede", maxHp = 100, defense = 20, atk = 30, weight = 50)

    panel11 = new Panel(x = 1, y = 1, storage = ArrayBuffer.empty, panelId = "panel11")
    panel21 = new Panel(2, 1, ArrayBuffer.empty, "panel21")

    attackAction = new AttackAction()
    equipWeaponAction = new EquipWeaponAction(List(sword))
    moveAction = new MoveAction()
    usePotionAction = new UsePotionAction(List(defensePotion, healingPotion))
  }

  test("AttackAction has correct attributes") {
    assertEquals(attackAction.name, "Attack")
    assertEquals(attackAction.id, "action1")
    assert(attackAction.possibleItems.isEmpty)
  }

  test("EquipWeaponAction has correct attributes and possibleItems") {
    assertEquals(equipWeaponAction.name, "Items→Equip weapon")
    assertEquals(equipWeaponAction.id, "action2")
    assertEquals(equipWeaponAction.possibleItems, List(sword))
  }

  test("MoveAction has correct attributes") {
    assertEquals(moveAction.name, "Move")
    assertEquals(moveAction.id, "2")
    assert(moveAction.possibleItems.isEmpty)
  }

  test("UsePotionAction has correct attributes and possibleItems") {
    assertEquals(usePotionAction.name, "Items→Use potion")
    assertEquals(usePotionAction.id, "action4")
    assertEquals(usePotionAction.possibleItems, List(defensePotion, healingPotion))
  }

  test("toJson should return correct JsObj") {
    val expectedJson = JsObj(
      "id" -> JsStr("action1"),
      "action" -> JsStr("Attack")
    )
    val expectedJsonWithItems = JsObj(
      "id" -> JsStr("action4"),
      "action" -> JsStr("Items→Use potion"),
      "targets" -> JsArr(
        JsObj(
          "id" -> JsStr("potion1"),
          "name" -> JsStr("Defense potion")
        ),
        JsObj(
          "id" -> JsStr("potion2"),
          "name" -> JsStr("Healing potion")
        )
      )
    )

    assertEquals(attackAction.toJson, expectedJson)
    assertEquals(usePotionAction.toJson, expectedJsonWithItems)
  }

  test("AttackAction executes receiveAttack on target") {
    entity1.equipWeapon(sword)
    val initialHp = enemy.getHp

    attackAction.executeAction(entity1, enemy)
    assertEquals(enemy.getHp, initialHp + enemy.getDefense - sword.atk)
  }

  test("EquipWeaponAction executes equipWeaponTo on target") {
    assert(entity1.getWeapon.isEmpty)

    equipWeaponAction.executeAction(entity1, sword)
    assert(entity1.getWeapon.isDefined)
    assertEquals(entity1.getWeapon.get, sword)
  }

  test("MoveAction executes moveEntity on target panel") {
    panel11.addCharacter(entity1)
    panel11.panels += panel21
    panel21.panels += panel11

    moveAction.executeAction(entity1, panel21)
    assert(!panel11.isPresent(entity1)) // Check removed from initial panel
    assert(panel21.isPresent(entity1)) // Check added to adjacent panel
  }

  test("UsePotionAction executes applyPotionTo on target") {
    entity2.hp_=(60) // Lower HP to test healing potion
    usePotionAction.executeAction(entity2, healingPotion)

    assertEquals(entity2.getHp, 70) // Confirm healing effect
  }
}
