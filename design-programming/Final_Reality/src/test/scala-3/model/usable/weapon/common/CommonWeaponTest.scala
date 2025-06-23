package model.usable.weapon.common

import model.entity.characters.common.{Archer, Knight, Thief}
import model.entity.characters.magical.{BlackMage, WhiteMage}
import model.usable.IUsable
import util.SprayJson.*
import munit.FunSuite

class CommonWeaponTest extends FunSuite {

  var bow: Bow = _
  var dagger: Dagger = _
  var sword: Sword = _
  var inventory: List[IUsable] = _

  var archer: Archer = _
  var knight: Knight = _
  var thief: Thief = _
  var blackMage: BlackMage = _
  var whiteMage: WhiteMage = _

  override def beforeEach(context: BeforeEach): Unit = {
    bow = new Bow(name="Elven Bow", atk=30, weight=10, id="weapon1")
    dagger = new Dagger("Reduvia", 25, 5, "weapon2")
    sword = new Sword("Excalibur", 45, 25, "weapon3")
    inventory = List(bow, dagger, sword)

    knight = new Knight(name="Kabru", maxHp=100, defense=50, weight=30, inventory=inventory)
    archer = new Archer("Kiki", 90, 20, 15)
    thief = new Thief("Chilchuck", 80, 15, 10)
    blackMage = new BlackMage(name="Thistle", maxHp=100, maxMana=100, defense=10, weight=20)
    whiteMage = new WhiteMage("Marcille", 100, 100, 10, 20)
  }

  test("Bow initial attributes") {
    assertEquals(bow.name, "Elven Bow")
    assertEquals(bow.id, "weapon1")
    assertEquals(bow.atk, 30)
    assertEquals(bow.weight, 10)
  }

  test("Sword can be equipped by knight, archer and thief") {
    assert(sword.canBeEquippedByKnight(knight))
    assert(sword.canBeEquippedByArcher(archer))
    assert(sword.canBeEquippedByThief(thief))
  }

  test("Sword cannot be equipped by black and white mage") {
    assert(!sword.canBeEquippedByBlackMage(blackMage))
    assert(!sword.canBeEquippedByWhiteMage(whiteMage))
  }

  test("Dagger can be equipped by knight, thief and black mage") {
    assert(dagger.canBeEquippedByKnight(knight))
    assert(dagger.canBeEquippedByThief(thief))
    assert(dagger.canBeEquippedByBlackMage(blackMage))
  }

  test("Dagger cannot be equipped by archer and white mage") {
    assert(!dagger.canBeEquippedByArcher(archer))
    assert(!dagger.canBeEquippedByWhiteMage(whiteMage))
  }

  test("Bow can be equipped by knight, archer and white mage") {
    assert(bow.canBeEquippedByKnight(knight))
    assert(bow.canBeEquippedByArcher(archer))
    assert(bow.canBeEquippedByWhiteMage(whiteMage))
  }

  test("Bow can be owned by knight") {
    knight.equipWeapon(bow)
    assertEquals(bow.owner.get, knight)
  }

  test("Bow cannot be equipped by thief and black mage") {
    assert(!bow.canBeEquippedByThief(thief))
    assert(!bow.canBeEquippedByBlackMage(blackMage))
  }

  test("toJson works correctly for weapons") {
    val expectedJson = JsObj(
      "id" -> JsStr("weapon2"),
      "name" -> JsStr("Reduvia")
    )

    assertEquals(dagger.toJson, expectedJson)
  }
}
