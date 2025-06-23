package model.usable.weapon.magical

import model.usable.weapon.IWeapon

/** Trait for weapons with magical properties that can be equipped by magical characters.
 *
 * `IMagicalWeapon` extends `IWeapon`, providing additional specifications for
 * weapons that possess magical attributes (e.g., a base magic attack stat).
 * Weapons implementing this trait are meant to be usable by magical classes,
 * such as `BlackMage` and `WhiteMage`.
 *
 * @see [[IWeapon]] for general weapon functionality.
 * @see [[model.entity.characters.magical.BlackMage]] for black mage characters.
 * @see [[model.entity.characters.magical.WhiteMage]] for white mage characters.
 *
 * @author Adolfo Rojas [[https://github.com/AdolfoRV]]
 */
trait IMagicalWeapon extends IWeapon {
}
