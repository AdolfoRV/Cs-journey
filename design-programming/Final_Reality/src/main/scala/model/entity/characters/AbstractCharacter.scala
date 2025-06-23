package model.entity.characters

import exceptions.{
  InvalidPotionException,
  InvalidWeaponException,
  NotInInventoryException
}
import model.action.IAction
import model.action.common.*
import model.entity.{AbstractEntity, IEntity}
import model.usable.IUsable
import model.usable.potion.IPotion
import model.usable.weapon.IWeapon

/** Abstract class representing a character in the game, extending the basic
  * entity functionality.
  *
  * `AbstractCharacter` manages the character's inventory, equipped weapon, and
  * actions. It provides methods for equipping weapons, consuming potions, and
  * calculating attack values.
  *
  * @constructor
  *   Creates a new character with specified attributes and an inventory of
  *   usable items.
  *
  * @param name
  *   The name of the character.
  * @param maxHp
  *   The maximum health points of the character.
  * @param baseDefense
  *   The base defense value of the character.
  * @param baseWeight
  *   The weight of the character.
  * @param inventory
  *   A list of usable items that the character can access.
  * @param id
  *   The unique identifier for the character.
  *
  * @example
  *   {{{
  * val character = new AbstractCharacter("Hero", 100, 10, 5, List(), "hero_001") {}
  * character.equipWeapon(new Sword("Excalibur", 15, 5, "sword_001"))
  * character.consumePotion(new HealingPotion())
  * val attackValue = character.getAtk
  *   }}}
  *
  * @see
  *   [[ICharacter]] for character behavior.
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
abstract class AbstractCharacter(name: String, maxHp: Int, baseDefense: Int, baseWeight: Int, var inventory: List[IUsable], id: String) extends AbstractEntity(name, maxHp, baseDefense, baseWeight, id) with ICharacter {

  /** The character's equipped weapon */
  private var equippedWeapon: Option[IWeapon] = None

  var magicStrengthBuff: Boolean = false

  def potions: List[IPotion] = inventory.collect { case p: IPotion => p }

  def weapons: List[IWeapon] = inventory.collect { case w: IWeapon => w }

  def baseActions: List[IAction] = List(
    new AttackAction(),
    new EquipWeaponAction(weapons),
    new MoveAction(),
    new UsePotionAction(potions)
  )

  def actions: List[IAction] = baseActions

  def getWeapon: Option[IWeapon] = equippedWeapon

  def weapon_=(currentWeapon: Option[IWeapon]): Unit = equippedWeapon =
    currentWeapon

  override def maxActionBar: Int = {
    val weaponWeight = getWeapon.map(_.weight).getOrElse(0)
    this.baseWeight + (0.5 * weaponWeight).toInt
  }

  override def equipWeapon(weapon: IWeapon): Unit = {
    if (!inventory.contains(weapon))
      throw new NotInInventoryException(this, weapon)

    if (weapon.canBeEquippedBy(this)) {
      if (getWeapon == weapon) {
        throw new InvalidWeaponException(
          this,
          weapon
        ) // Is redundant, but I was thinking of adding a custom message to indicate the character is trying to equip an already equipped item
      } else {
        getWeapon.foreach(_.owner =
          None
        ) // Disown current weapon, may be unnecessary if the character has None as weapon

        weapon_=(Some(weapon)) // Equip the weapon
        weight_=(baseWeight + weapon.weight) // Update overall weight
        weapon.owner = Some(this) // Update weapon's owner
      }
    } else throw new InvalidWeaponException(this, weapon)
  }

  override def consumePotion(potion: IPotion): Unit = {
    if (!inventory.contains(potion))
      throw new NotInInventoryException(this, potion)

    if (potion.canBeConsumedBy(this)) {
      potion.applyEffect(this)
    } else throw new InvalidPotionException(this, potion)
  }

  def getAtk: Int = getWeapon.map(_.atk).getOrElse(0)
  def getMagicAtk: Int = {
    val baseMagicAtk = getWeapon.map(_.getMagicAtk).getOrElse(0)
    if (magicStrengthBuff) {
      magicStrengthBuff = false
      (baseMagicAtk * 1.5).toInt
    } else baseMagicAtk
  }

  // target handlers
  override def receiveAttack(source: IEntity): Unit = source.dealAttackTo(this)
  override def receiveHealing(source: IEntity): Unit = hp_=(getHp + (maxHp * 0.2).toInt + source.getMagicAtk / 4)
}
