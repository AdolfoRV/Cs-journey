package model.player

import model.entity.Enemy
import model.entity.characters.ICharacter
import model.entity.characters.common.Thief
import model.entity.characters.magical.WhiteMage
import util.SprayJson.*
import munit.FunSuite

class PlayerTest extends FunSuite {
  var player1: Player = _
  var player2: Player = _

  var char1: ICharacter = _
  var char2: ICharacter = _
  var enemy1: Enemy = _
  var enemy2: Enemy = _

  override def beforeEach(context: BeforeEach): Unit = {
    char1 = new Thief(name="Kazuma", maxHp=0, defense=0, weight=0, id="thief1")
    char2 = new WhiteMage(name="Gojo", maxHp=0, maxMana=999, defense=0, weight=0, id="whiteMage1")
    enemy1 = new Enemy(name="Radiance", maxHp=999, defense=999, atk=999, weight=999, id="enemy1")
    enemy2 = new Enemy("Papyrus", 0, 0, 0, 0, "enemy2")

    player1 = new Player(List(char1, char2), "player1")
    player2 = new Player(List(enemy1, enemy2), "player2")
  }

  test("Player1 initial attributes") {
    assertEquals(player1.playerCharacters, List(char1, char2))
    assertEquals(player1.playerId, "player1")
    assertEquals(player1.id, "player1")
  }

  test("Player is defeated when all characters are defeated") {
    assert(player1.playerDefeated)
  }

  test("Player is not defeated when at least one character is not defeated") {
    assert(!player2.playerDefeated)
  }

  test("toJson works correctly for player") {
    val expectedJson = JsObj(
      "characters" -> JsArr(
        JsObj(
          "id" -> JsStr("thief1"),
          "attributes" -> JsArr(
            JsObj("name" -> JsStr("name"), "value" -> JsStr("Kazuma")),
            JsObj("name" -> JsStr("hp"), "value" -> JsNum(0)),
            JsObj("name" -> JsStr("atk"), "value" -> JsNum(0)),
            JsObj("name" -> JsStr("def"), "value" -> JsNum(0))
          ),
          "img" -> JsStr("Kazuma.gif")
        ),
        JsObj(
          "id" -> JsStr("whiteMage1"),
          "attributes" -> JsArr(
            JsObj("name" -> JsStr("name"), "value" -> JsStr("Gojo")),
            JsObj("name" -> JsStr("hp"), "value" -> JsNum(0)),
            JsObj("name" -> JsStr("atk"), "value" -> JsNum(0)),
            JsObj("name" -> JsStr("def"), "value" -> JsNum(0)),
            JsObj("name" -> JsStr("mp"), "value" -> JsNum(999))
          ),
          "img" -> JsStr("Gojo.gif")
        )
      )
    )
    assertEquals(player1.toJson, expectedJson)
  }
}
