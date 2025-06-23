package model.usable.potion.magical

import model.entity.characters.magical.WhiteMage
import model.usable.weapon.magical.Staff
import munit.FunSuite

class MagicalPotionTest extends FunSuite {
  var whiteMage: WhiteMage = _
  var staff: Staff = _

  var magicStrengthPotion: MagicStrengthPotion = _
  var manaPotion: ManaPotion = _

  override def beforeEach(context: BeforeEach): Unit = {
    staff = new Staff(name="Rutificador arcano", atk=100, magicAtk=100, weight=15)
    magicStrengthPotion = new MagicStrengthPotion()
    manaPotion = new ManaPotion()

    whiteMage = new WhiteMage(name="Lozano", maxHp=100, maxMana=100, defense=100, weight=65, inventory=List(magicStrengthPotion, manaPotion, staff))
  }

  test("MagicStrengthPotion initial attributes") {
    assertEquals(magicStrengthPotion.name, "Magic strengthening potion")
    assertEquals(magicStrengthPotion.id, "potion_magic1")
  }

  test("ManaPotion initial attributes") {
    assertEquals(manaPotion.name, "Mana potion")
    assertEquals(manaPotion.id, "potion_magic2")
  }

  test("MagicStrengthPotion increases magic damage by 50% until a spell has been cast") {
    whiteMage.equipWeapon(staff)
    magicStrengthPotion.applyEffect(whiteMage)
    assertEquals(whiteMage.getMagicAtk, 150)
  }

  test("MagicStrengthPotion applyPotionTo calls consumePotion on WhiteMage") {
    whiteMage.equipWeapon(staff)
    magicStrengthPotion.applyPotionTo(whiteMage)

    assertEquals(whiteMage.getMagicAtk, 150)
  }

  test("ManaPotion restores mana by 30% of max Mana") {
    whiteMage.mana_=(70)
    manaPotion.applyEffect(whiteMage)
    assertEquals(whiteMage.getMana, 100)
  }
}
