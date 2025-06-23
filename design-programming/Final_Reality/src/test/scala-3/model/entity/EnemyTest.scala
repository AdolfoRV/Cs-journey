package model.entity

import util.SprayJson.*
import munit.FunSuite
import exceptions.InvalidTargetException
import model.action.common.{AttackAction, MoveAction}
import model.entity.characters.magical.WhiteMage
import model.usable.potion.common.HealingPotion
import model.usable.weapon.magical.Wand

class EnemyTest extends FunSuite {
  var potion: HealingPotion = _
  var wand: Wand = _

  var enemy: Enemy = _
  var mage: WhiteMage = _

  override def beforeEach(context: BeforeEach): Unit = {
    potion = new HealingPotion()
    wand = new Wand(name="Wabbajack", atk=25, magicAtk=52, weight=15)

    enemy = new Enemy(name="King Dedede", maxHp=100, defense=20, atk=30, weight=50)
    mage = new WhiteMage(name = "Junia", maxHp = 100, maxMana = 100, defense = 10, weight = 45, inventory = List(potion, wand))
    mage.equipWeapon(wand)
  }

  test("Enemy initial attributes") {
    assertEquals(enemy.name, "King Dedede")
    assertEquals(enemy.id, "enemy1")
    assertEquals(enemy.getHp, 100)
    assertEquals(enemy.getDefense, 20)
    assertEquals(enemy.getAtk, 30)
    assertEquals(enemy.getMagicAtk, 0)
    assertEquals(enemy.getWeight, 50)
    assertEquals(enemy.maxActionBar, 50)
  }

  test("Enemy has correct actions") {
    val actions = enemy.actions
    assertEquals(actions.size, 2, "Enemy should have exactly 2 actions")
    assert(actions.head.isInstanceOf[AttackAction], "First action should be AttackAction")
    assert(actions(1).isInstanceOf[MoveAction], "Second action should be MoveAction")
  }

  test("Enemy takes damage") {
    enemy.hp_=(50)
    assertEquals(enemy.getHp, 50)
  }

  test("Enemy cannot have more HP than maxHp") {
    enemy.hp_=(150)
    assertEquals(enemy.getHp, 100) // Max HP is 100
  }

  test("Enemy cannot have less than 0 HP") {
    enemy.hp_=(-10)
    assertEquals(enemy.getHp, 0) // HP can't be negative
  }

  test("Enemy is defeated when HP is 0") {
    enemy.hp_=(0)
    assert(enemy.defeated)
  }

  test("Enemy is not defeated if HP is greater than 0") {
    enemy.hp_=(1)
    assert(!enemy.defeated)
  }

  test("toJson works correctly for enemy") {
    val expectedJson = JsObj(
      "id" -> JsStr("enemy1"),
      "attributes" -> JsArr(
        JsObj("name" -> JsStr("name"), "value" -> JsStr("King Dedede")),
        JsObj("name" -> JsStr("hp"), "value" -> JsNum(100)),
        JsObj("name" -> JsStr("atk"), "value" -> JsNum(30)),
        JsObj("name" -> JsStr("def"), "value" -> JsNum(20))
      ),
      "img" -> JsStr("King Dedede.gif")
    )
    assertEquals(enemy.toJson, expectedJson)
  }

  test("Enemy receives attack") {
    enemy.hp_=(100)
    enemy.receiveAttack(mage)
    assert(enemy.getHp < 100)
  }

  test("Enemy purified by source") {
    enemy.hp_=(100)
    enemy.purifiedBy(mage)
    assert(enemy.getHp < 100)
  }

  test("Enemy cannot equip weapon") {
    intercept[InvalidTargetException] {
      enemy.equipWeapon(wand)
    }
  }

  test("Enemy cannot consume potion") {
    intercept[InvalidTargetException] {
      enemy.consumePotion(potion)
    }
  }
}
