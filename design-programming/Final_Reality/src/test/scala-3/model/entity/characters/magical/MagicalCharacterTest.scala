package model.entity.characters.magical

import exceptions.InvalidWeaponException
import model.action.common.{AttackAction, EquipWeaponAction, MoveAction, UsePotionAction}
import model.action.magical.{HealAction, MeteorAction, PurifyAction, ThunderAction}
import model.usable.potion.magical.ManaPotion
import model.usable.weapon.common.Sword
import model.usable.weapon.magical.Wand
import util.SprayJson.*
import munit.FunSuite

class MagicalCharacterTest extends FunSuite{
  var sword: Sword = _
  var wand: Wand = _
  var magicPotion: ManaPotion = _

  var blackMage: BlackMage = _
  var whiteMage: WhiteMage = _

  override def beforeEach(context: BeforeEach): Unit = {
    sword = new Sword(name="Murasama", atk=35, weight=15)
    wand = new Wand(name="The Elder Wand", atk=25, magicAtk=20, weight=10)
    magicPotion = new ManaPotion()

    blackMage = new BlackMage(name="Niggician", maxHp=90, maxMana=100, defense=10, weight=20, inventory=List(sword, wand), id="magic_char1")
    whiteMage = new WhiteMage("Dumbledore", 85, 110, 5, 15, id="magic_char2")
  }

  test("Black mage initial attributes") {
    assertEquals(blackMage.name, "Niggician")
    assertEquals(blackMage.id, "magic_char1")
    assertEquals(blackMage.getHp, 90)
    assertEquals(blackMage.getMana, 100)
    assertEquals(blackMage.getDefense, 10)
    assertEquals(blackMage.getWeight, 20)
    assertEquals(blackMage.inventory, List(sword, wand))
    assertEquals(blackMage.getAtk, 0)
    assertEquals(blackMage.getMagicAtk, 0)
    assertEquals(blackMage.magicStrengthBuff, false)

    assertEquals(blackMage.maxActionBar, 20)
  }

  test("BlackMage has correct actions") {
    val actions = blackMage.actions
    assertEquals(actions.size, 6)
    assert(actions.head.isInstanceOf[AttackAction])
    assert(actions(1).isInstanceOf[EquipWeaponAction])
    assert(actions(2).isInstanceOf[MoveAction])
    assert(actions(3).isInstanceOf[UsePotionAction])
    assert(actions(4).isInstanceOf[MeteorAction])
    assert(actions(5).isInstanceOf[ThunderAction])
  }

  test("WhiteMage has correct actions") {
    val actions = whiteMage.actions
    assertEquals(actions.size, 6)
    assert(actions.head.isInstanceOf[AttackAction])
    assert(actions(1).isInstanceOf[EquipWeaponAction])
    assert(actions(2).isInstanceOf[MoveAction])
    assert(actions(3).isInstanceOf[UsePotionAction])
    assert(actions(4).isInstanceOf[HealAction])
    assert(actions(5).isInstanceOf[PurifyAction])
  }

  test("Black mage equips weapon, updates attack, magic damage and weight") {
    assert(blackMage.canEquipWeapon(wand))
    blackMage.equipWeapon(wand)
    assertEquals(blackMage.getAtk, 25)
    assertEquals(blackMage.getMagicAtk, 20)
    assertEquals(blackMage.getWeight, 30)
  }

  test("Black mage cannot equip a sword") {
    assert(!blackMage.canEquipWeapon(sword))
    intercept[InvalidWeaponException] {
      blackMage.equipWeapon(sword)
    }
  }


  test("Magical characters can consume only specific potions") {
    assert(blackMage.canConsumePotion(magicPotion))
    assert(whiteMage.canConsumePotion(magicPotion))
  }

  test("Black mage updates maxActionBar") {
    blackMage.equipWeapon(wand)
    assertEquals(blackMage.maxActionBar, 25)
  }

  test("toJson works correctly for magical characters") {
    blackMage.weapon_=(Some(wand))

    val expectedJson = JsObj(
      "id" -> JsStr("magic_char1"),
      "attributes" -> JsArr(
        JsObj("name" -> JsStr("name"), "value" -> JsStr("Niggician")),
        JsObj("name" -> JsStr("hp"), "value" -> JsNum(90)),
        JsObj("name" -> JsStr("atk"), "value" -> JsNum(25)),
        JsObj("name" -> JsStr("def"), "value" -> JsNum(10)),
        JsObj("name" -> JsStr("mp"), "value" -> JsNum(100))
      ),
      "img" -> JsStr("Niggician.gif")
    )

    assertEquals(blackMage.toJson, expectedJson)
  }
}
