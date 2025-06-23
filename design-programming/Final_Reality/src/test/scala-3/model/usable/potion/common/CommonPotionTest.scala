package model.usable.potion.common

import model.entity.characters.common.Thief
import model.entity.characters.magical.WhiteMage
import util.SprayJson.*
import munit.FunSuite

class CommonPotionTest extends FunSuite {
  var thief: Thief = _
  var whiteMage: WhiteMage = _
  
  var defensePotion: DefensePotion = _
  var healingPotion: HealingPotion = _

  override def beforeEach(context: BeforeEach): Unit = {
    thief = new Thief(name="Jonathan", maxHp=100, defense=100, weight=55)
    whiteMage = new WhiteMage(name="Lozano", maxHp=100, maxMana=100, defense=100, weight=65)
    
    defensePotion = new DefensePotion()
    healingPotion = new HealingPotion()
  }

  test("DefensePotion initial attributes") {
    assertEquals(defensePotion.name, "Defense potion")
    assertEquals(defensePotion.id, "potion1")
  }

  test("HealingPotion initial attributes") {
    assertEquals(healingPotion.name, "Healing potion")
    assertEquals(healingPotion.id, "potion2")
  }

  test("DefensePotion increases the current defense by 15%") {
    defensePotion.applyEffect(thief)
    assertEquals(thief.getDefense, 114)

    defensePotion.applyEffect(thief)
    assertEquals(thief.getDefense, 131)     // (155*1.15).toInt = 131
  }

  test("HealingPotion heals 20% of max HP") {
    whiteMage.hp_=(50)
    healingPotion.applyEffect(whiteMage)
    assertEquals(whiteMage.getHp, 70)
  }

  test("HealingPotion does not exceed max HP") {
    thief.hp_=(90)
    healingPotion.applyEffect(thief)
    assertEquals(thief.getHp, thief.maxHp)
  }

  test("toJson works correctly for potions") {
    val expectedJson = JsObj(
      "id" -> JsStr("potion2"),
      "name" -> JsStr("Healing potion")
    )

    assertEquals(healingPotion.toJson, expectedJson)
  }
}
