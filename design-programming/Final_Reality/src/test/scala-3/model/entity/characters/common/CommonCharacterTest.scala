package model.entity.characters.common

import exceptions.{InvalidPotionException, InvalidWeaponException, NotInInventoryException}
import model.action.common.{AttackAction, EquipWeaponAction, MoveAction, UsePotionAction}
import model.usable.potion.common.HealingPotion
import model.usable.potion.magical.ManaPotion
import model.usable.weapon.common.Sword
import model.usable.weapon.magical.Staff
import util.SprayJson.*
import munit.FunSuite

class CommonCharacterTest extends FunSuite {
  var sword: Sword = _
  var exclusive_sword: Sword = _
  var staff: Staff = _
  var potion: HealingPotion = _
  var magicPotion: ManaPotion = _

  var archer: Archer = _
  var knight: Knight = _
  var thief: Thief = _

  override def beforeEach(context: BeforeEach): Unit = {
    sword = new Sword(name="Buster Sword", atk=45, weight=25, id="sword1")
    exclusive_sword = new Sword(name="Awesome Stick", atk=100, weight=5, id="sword2")
    staff = new Staff(name="Cool Stick", atk=20, magicAtk=45, weight=15, id="staff1")
    potion = new HealingPotion()
    magicPotion = new ManaPotion()

    archer = new Archer(name="Legolas", maxHp=95, defense=10, weight=20, inventory=List.empty,id="archer1")
    knight = new Knight("Meta Knight", 120, 50, 50, List(sword, potion, magicPotion), "knight1")
    thief = new Thief("Brayan", 90, 10, 10, List(sword, staff), "thief1")
  }

  test("Thief initial attributes") {
    assertEquals(thief.name, "Brayan")
    assertEquals(thief.id, "thief1")
    assertEquals(thief.getHp, 90)
    assertEquals(thief.getDefense, 10)
    assertEquals(thief.getWeight, 10)
    assertEquals(thief.inventory, List(sword, staff))
    assertEquals(thief.getAtk, 0)
    assertEquals(thief.getMagicAtk, 0)
    assertEquals(thief.maxActionBar, 10)
  }

  test("Character has correct actions") {
    val actions = thief.actions
    assertEquals(actions.size, 4, "Enemy should have exactly 2 actions")
    assert(actions.head.isInstanceOf[AttackAction])
    assert(actions(1).isInstanceOf[EquipWeaponAction])
    assert(actions(2).isInstanceOf[MoveAction])
    assert(actions(3).isInstanceOf[UsePotionAction])
  }

  test("Thief takes damage") {
    thief.hp_=(40)
    assertEquals(thief.getHp, 40)
  }

  test("Thief cannot have more HP than maxHp") {
    thief.hp_=(150)
    assertEquals(thief.getHp, 90) // Max HP es 90
  }

  test("Thief cannot have less than 0 HP") {
    thief.hp_=( -10)
    assertEquals(thief.getHp, 0) // HP no puede ser negativo
  }

  test("Common characters can consume only specific potions") {
    assert(knight.canConsumePotion(potion))
    assert(thief.canConsumePotion(potion))
    assert(archer.canConsumePotion(potion))

    assert(!knight.canConsumePotion(magicPotion))
    assert(!thief.canConsumePotion(magicPotion))
    assert(!archer.canConsumePotion(magicPotion))
    intercept[InvalidPotionException] {
      knight.consumePotion(magicPotion)
      thief.consumePotion(magicPotion)
      archer.consumePotion(magicPotion)
    }
  }

  test("Thief is defeated when HP is 0") {
    thief.hp_=(0)
    assert(thief.defeated)
  }

  test("Thief is not defeated if HP is greater than 0") {
    thief.hp_=(1)
    assert(!thief.defeated)
  }

  test("Thief updates defense") {
    thief.defense_=(20)
    assertEquals(thief.getDefense, 20)
  }

  test("Thief equips weapon, updates attack, weight and ownership") {
    assert(thief.canEquipWeapon(sword))
    thief.equipWeapon(sword)
    val expectedOwner = sword.owner.get
    
    assertEquals(thief.getAtk, 45)
    assertEquals(thief.getWeight, 35)
    assertEquals(thief, expectedOwner)
  }

  test("Thief cannot equip a staff") {
    intercept[InvalidWeaponException] {
      thief.equipWeapon(staff)
    }
  }

  test("Thief cannot equip a weapon unavailable in inventory") {
    intercept[NotInInventoryException] {
      thief.equipWeapon(exclusive_sword)
    }
  }

  test("Thief updates maxActionBar") {
    thief.equipWeapon(sword)
    assertEquals(thief.maxActionBar, 22)
  }

  test("toJson works correctly") {
    thief.hp_=(40)
    thief.weapon_=(Some(sword))

    val expectedJson = JsObj(
      "id" -> JsStr("thief1"),
      "attributes" -> JsArr(
        JsObj("name" -> JsStr("name"), "value" -> JsStr("Brayan")),
        JsObj("name" -> JsStr("hp"), "value" -> JsNum(40)),
        JsObj("name" -> JsStr("atk"), "value" -> JsNum(45)),
        JsObj("name" -> JsStr("def"), "value" -> JsNum(10))
      ),
      "img" -> JsStr("Brayan.gif")
    )
    assertEquals(thief.toJson, expectedJson)
  }
}