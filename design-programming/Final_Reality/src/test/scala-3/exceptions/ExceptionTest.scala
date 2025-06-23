package exceptions

import exceptions.*
import munit.FunSuite
import model.entity.*
import model.entity.characters.common.Knight
import model.entity.characters.magical.BlackMage
import model.player.Player
import model.usable.IUsable
import model.usable.potion.magical.ManaPotion
import model.usable.weapon.common.Sword
import model.usable.weapon.magical.Staff
import util.SprayJson.*

class ExceptionTest extends FunSuite {

  var weapon: Staff = _
  var nonMagicWeapon: Sword = _
  var potion: ManaPotion = _
  var character1: Knight = _
  var character2: BlackMage = _
  var enemy: Enemy = _
  var inventory: List[IUsable] = _

  override def beforeEach(context: BeforeEach): Unit = {
    weapon = new Staff(name = "Stick", atk = 30, magicAtk = 40, weight = 10)
    nonMagicWeapon = new Sword(name = "Basic Sword", atk = 20, weight = 5)
    potion = new ManaPotion()
    inventory = List(weapon, nonMagicWeapon, potion)

    character1 = new Knight(name="Link", maxHp = 100, defense = 20, weight = 50, inventory = inventory)
    character2 = new BlackMage(name = "Kuro", maxHp = 80, maxMana = 30, defense = 10, weight = 45, inventory = inventory)
    enemy = new Enemy(name = "Ganondorf", maxHp = 150, defense = 25, atk = 40, weight = 60)
  }

    test("DeadSourceException message is correct") {
    character1.hp_=(0)
    val exception = intercept[DeadSourceException](throw new DeadSourceException(character1))
    assertEquals(exception.getMessage, s"${character1.name} is in another plane of existence.")
  }

  test("InvalidPotionException message is correct") {
    val invalidPotionException = intercept[InvalidPotionException](throw new InvalidPotionException(character1, potion))
    assertEquals(invalidPotionException.getMessage, s"${potion.name} cannot be consumed by ${character1.name}.")
  }

  test("InvalidSourceException message is correct") {
    val invalidSourceException = intercept[InvalidSourceException](throw new InvalidSourceException(enemy))
    assertEquals(invalidSourceException.getMessage, s"${enemy.name} cannot perform this action.")
  }

  test("InvalidStatException message is correct") {
    val invalidStatException = intercept[InvalidStatException](throw new InvalidStatException)
    assertEquals(invalidStatException.getMessage, s"An invalid stat was found")
  }

  test("InvalidTargetException message is correct") {
    val invalidTargetException = intercept[InvalidTargetException](throw new InvalidTargetException(enemy))
    assertEquals(invalidTargetException.getMessage, s"${enemy.name} cannot be selected as target.")
  }

  test("InvalidWeaponException message is correct") {
    val invalidWeaponException = intercept[InvalidWeaponException](throw new InvalidWeaponException(character1, weapon))
    assertEquals(invalidWeaponException.getMessage, s"${weapon.name} cannot be equipped by ${character1.name}.")
  }

  test("NotEnoughDamageException message is correct") {
    val notEnoughDamageException = intercept[NotEnoughDamageException](throw new NotEnoughDamageException(character1))
    assertEquals(notEnoughDamageException.getMessage, s"${character1.name} is too weak to perform this action.")
  }

  test("NotEnoughManaException message is correct") {
    character2.mana_=(10) // Insufficient mana
    val notEnoughManaException = intercept[NotEnoughManaException](throw new NotEnoughManaException(character2))
    assertEquals(notEnoughManaException.getMessage, s"${character2.name} does not have enough mana.")
  }

  test("NotInInventoryException message is correct") {
    val notInInventoryException = intercept[NotInInventoryException](throw new NotInInventoryException(character1, potion))
    assertEquals(notInInventoryException.getMessage, s"The item ${potion.name} is not available in ${character1.name}'s inventory.")
  }

  test("PlayerDefeatedException message is correct") {
    val player = new Player(List(character1, character2), "player1")
    val exception = intercept[PlayerDefeatedException](throw new PlayerDefeatedException(s"Player ${player.playerId} has been defeated."))
    assertEquals(exception.getMessage, s"Player ${player.playerId} has been defeated.")
  }
}
