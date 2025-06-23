package model.action.magical

import model.entity.Enemy
import model.entity.characters.common.Archer
import model.entity.characters.magical.{BlackMage, WhiteMage}
import model.usable.IUsable
import model.usable.weapon.common.Sword
import model.usable.weapon.magical.Wand
import util.SprayJson.*
import munit.FunSuite
import exceptions.{NotEnoughDamageException, NotEnoughManaException}

class MagicalActionTest extends FunSuite {
  var sword: Sword = _
  var wand: Wand = _
  var fake_wand: Wand = _
  var inventory: List[IUsable] = _

  var entity1: Archer = _
  var entity2: WhiteMage = _
  var entity3: BlackMage = _
  var enemy: Enemy = _

  var healAction: HealAction = _
  var meteorAction: MeteorAction = _
  var purifyAction: PurifyAction = _
  var thunderAction: ThunderAction = _

  override def beforeEach(context: BeforeEach): Unit = {
    sword = new Sword(name = "Meowmere", atk = 50, weight = 30)
    wand = new Wand(name = "The Elder Wand", atk = 25, magicAtk = 20, weight = 10)
    fake_wand = new Wand(name = "The Newer Wand", atk = 25, magicAtk = 0, weight = 10)
    inventory = List(sword, wand, fake_wand)

    entity1 = new Archer("Cupid", 100, 20, 50, inventory, "archer1")
    entity2 = new WhiteMage("Aqua", 70, 100, 10, 10, inventory, "mage1")
    entity3 = new BlackMage(name = "Niggician", maxHp = 90, maxMana = 100, defense = 10, weight = 20, inventory = inventory, id = "magic_char1")
    enemy = new Enemy(name = "King Dedede", maxHp = 100, defense = 20, atk = 30, weight = 50)

    healAction = new HealAction()
    meteorAction = new MeteorAction()
    purifyAction = new PurifyAction()
    thunderAction = new ThunderAction()
  }

  test("HealAction has correct attributes") {
    assertEquals(healAction.name, "Spell→Heal")
    assertEquals(healAction.id, "spell_action1")
  }

  test("MeteorAction has correct attributes") {
    assertEquals(meteorAction.name, "Spell→Meteor")
    assertEquals(meteorAction.id, "spell_action2")
  }

  test("PurifyAction has correct attributes") {
    assertEquals(purifyAction.name, "Spell→Purify")
    assertEquals(purifyAction.id, "spell_action3")
  }

  test("ThunderAction has correct attributes") {
    assertEquals(thunderAction.name, "Spell→Thunder")
    assertEquals(thunderAction.id, "spell_action4")
  }

  test("toJson works correctly for HealAction") {
    val expectedJson = JsObj(
      "id" -> JsStr("spell_action1"),
      "action" -> JsStr("Spell→Heal")
    )
    assertEquals(healAction.toJson, expectedJson)
  }

  test("toJson works correctly for MeteorAction") {
    val expectedJson = JsObj(
      "id" -> JsStr("spell_action2"),
      "action" -> JsStr("Spell→Meteor")
    )
    assertEquals(meteorAction.toJson, expectedJson)
  }

  test("toJson works correctly for PurifyAction") {
    val expectedJson = JsObj(
      "id" -> JsStr("spell_action3"),
      "action" -> JsStr("Spell→Purify")
    )
    assertEquals(purifyAction.toJson, expectedJson)
  }

  test("toJson works correctly for ThunderAction") {
    val expectedJson = JsObj(
      "id" -> JsStr("spell_action4"),
      "action" -> JsStr("Spell→Thunder")
    )
    assertEquals(thunderAction.toJson, expectedJson)
  }

  test("HealAction executes correctly") {
    entity2.mana_=(30)
    entity2.equipWeapon(wand)
    entity1.hp_=(50)

    healAction.executeAction(entity2, entity1)
    assertEquals(entity1.getHp, 50 + 20 + 5)
  }

  test("MeteorAction throws NotEnoughManaException when not enough mana") {
    entity3.mana_=(10)
    intercept[NotEnoughManaException] {
      meteorAction.executeAction(entity3, enemy)
    }
  }

  test("MeteorAction throws NotEnoughDamageException when not enough magic attack") {
    entity3.mana_=(100)
    entity3.equipWeapon(fake_wand) // Insufficient magic attack
    intercept[NotEnoughDamageException] {
      meteorAction.executeAction(entity3, enemy)
    }
  }

  test("PurifyAction executes correctly") {
    entity2.mana_=(30) // Set enough mana for purifying
    entity2.equipWeapon(wand)

    purifyAction.executeAction(entity2, enemy)
    assertEquals(enemy.getHp, 100 - 15 - 10)
  }

  test("ThunderAction executes correctly") {
    entity3.mana_=(30)
    entity3.equipWeapon(wand)

    thunderAction.executeAction(entity3, enemy)
    assertEquals(enemy.getHp, enemy.maxHp - entity3.getMagicAtk)
  }
}