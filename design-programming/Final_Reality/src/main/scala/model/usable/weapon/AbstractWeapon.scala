package model.usable.weapon

import model.entity.IEntity
import model.entity.characters.ICharacter
import model.entity.characters.common.{Archer, Knight, Thief}
import model.entity.characters.magical.{BlackMage, WhiteMage}
import model.usable.AbstractUsable

/** Abstract class representing a weapon in the game.
  *
  * `AbstractWeapon` extends `AbstractUsable`, providing specific properties and
  * methods for items that can be used as weapons, such as attack power and
  * weight. This class also implements the `IWeapon` trait, making it a usable
  * item that can be equipped by certain character classes.
  *
  * Concrete subclasses should define specific allowed character classes for
  * each weapon type by implementing relevant methods for each class.
  *
  * @constructor
  *   Creates a new weapon instance with the specified properties.
  *
  * @param name
  *   The name of the weapon.
  * @param atk
  *   The attack power of the weapon.
  * @param weight
  *   The weight of the weapon, affecting character speed.
  * @param weaponId
  *   A unique identifier for the weapon.
  *
  * @see
  *   [[IWeapon]] for weapon-specific behavior.
  * @see
  *   [[AbstractUsable]] for general usable item properties.
  *
  * @author
  *   Adolfo Rojas [[https://github.com/AdolfoRV]]
  */
abstract class AbstractWeapon(name: String,val atk: Int,val weight: Int,weaponId: String) extends AbstractUsable(weaponId, name) with IWeapon {

  val baseMagicAtk: Int = 0

  var owner: Option[ICharacter] = None

  /** Current amount of magic attack points */
  private var _magicAtk: Int = baseMagicAtk

  override def getMagicAtk: Int = _magicAtk

  def magicAtk_=(currentMagicAtk: Int): Unit = {
    _magicAtk = currentMagicAtk
  }

  def canBeEquippedBy(character: ICharacter): Boolean =
    character.canEquipWeapon(this)

  def canBeEquippedByArcher(archer: Archer): Boolean = false
  def canBeEquippedByKnight(knight: Knight): Boolean = false
  def canBeEquippedByThief(thief: Thief): Boolean = false
  def canBeEquippedByBlackMage(blackMage: BlackMage): Boolean = false
  def canBeEquippedByWhiteMage(whiteMage: WhiteMage): Boolean = false

  // Target handler
  override def equipWeaponTo(source: IEntity): Unit = source.equipWeapon(this)

//  My ilegal way of checking for weapon compatibility
//  val allowedClass: List[String] = List()

//  def canBeEquippedBy(character: ICharacter): Boolean = {
//    val charClass = character.getClass.getSimpleName
//    allowedClass.contains(charClass)
//  }
}
