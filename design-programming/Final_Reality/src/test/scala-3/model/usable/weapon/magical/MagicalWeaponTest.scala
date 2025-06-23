package model.usable.weapon.magical

import model.entity.characters.common.{Archer, Knight, Thief}
import model.entity.characters.magical.{BlackMage, WhiteMage}
import munit.FunSuite

class MagicalWeaponTest extends FunSuite {
  var staff: Staff = _
  var wand: Wand = _

  var archer: Archer = _
  var knight: Knight = _
  var thief: Thief = _
  var blackMage: BlackMage = _
  var whiteMage: WhiteMage = _

  override def beforeEach(context: BeforeEach): Unit = {
    knight = new Knight(name="Kabru", maxHp=100, defense=50, weight=30)
    archer = new Archer("Kiki", 90, 20, 15)
    thief = new Thief("Chilchuck", 80, 15, 10)
    blackMage = new BlackMage(name="Thistle", maxHp=90, maxMana=120, defense=10, weight=20)
    whiteMage = new WhiteMage("Marcille", 100, 100, 10, 20)

    staff = new Staff(name="Wabbajack", atk=25, magicAtk=52, weight=15)
    wand = new Wand("Wand of Sparking", 30, 45, 10)
  }

  test("Staff initial attributes") {
    assertEquals(staff.name, "Wabbajack")
    assertEquals(staff.id, "magic_weapon1")
    assertEquals(staff.atk, 25)
    assertEquals(staff.baseMagicAtk, 52)
    assertEquals(staff.getMagicAtk, 52)
    assertEquals(staff.weight, 15)
  }

  test("Staff updates magic damage") {
    staff.magicAtk_=(101)
    assertEquals(staff.getMagicAtk, 101)
  }

  test("Staff can be equipped by BlackMage") {
    assert(staff.canBeEquippedByBlackMage(blackMage))
    assert(staff.canBeEquippedBy(blackMage))

  }

  test("Staff can be equipped by WhiteMage") {
    assert(staff.canBeEquippedByWhiteMage(whiteMage))
  }

  test("Staff cannot be equipped by Knight, Archer and Thief") {
    assert(!staff.canBeEquippedBy(knight))
    assert(!staff.canBeEquippedBy(archer))
    assert(!staff.canBeEquippedBy(thief))
  }

  test("Wand can be equipped by Archer") {
    assert(wand.canBeEquippedByArcher(archer))
  }

  test("Wand can be equipped by BlackMage") {
    assert(wand.canBeEquippedByBlackMage(blackMage))
  }

  test("Wand can be equipped by WhiteMage") {
    assert(wand.canBeEquippedByWhiteMage(whiteMage))
  }

  test("Wand cannot be equipped by Knight and Thief") {
    assert(!wand.canBeEquippedBy(knight))
    assert(!wand.canBeEquippedByKnight(knight))

    assert(!wand.canBeEquippedBy(thief))
    assert(!wand.canBeEquippedByThief(thief))
  }

  test("Wand updates magic damage") {
    wand.magicAtk_=(100)
    assertEquals(wand.getMagicAtk, 100)
  }
}