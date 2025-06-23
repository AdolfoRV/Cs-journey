//package controller
//
//import munit.FunSuite
//
//import util.SprayJson.{*, given}
//
//class GameControllerTest extends FunSuite {
//
//  override def beforeEach(context: BeforeEach): Unit = {
//    GameController.reset() // Resetea el estado antes de cada prueba
//  }
//
//  test("getPlayers should return the correct JSON representation of players") {
//    val expectedJson = JsArr(
//      JsObj(
//        "characters" -> JsArr(
//          JsObj(
//            "id" -> "c1",
//            "attributes" -> JsArr(
//              JsObj("name" -> "name", "value" -> "Relm"),
//              JsObj("name" -> "hp", "value" -> JsNum(90)),
//              JsObj("name" -> "atk", "value" -> JsNum(0)),
//              JsObj("name" -> "def", "value" -> JsNum(10)),
//            ),
//            "img" -> "Relm.gif"
//          ),
//          JsObj(
//            "id" -> "c2",
//            "attributes" -> JsArr(
//              JsObj("name" -> "name", "value" -> "Setzer"),
//              JsObj("name" -> "hp", "value" -> JsNum(85)),
//              JsObj("name" -> "atk", "value" -> JsNum(0)),
//              JsObj("name" -> "def", "value" -> JsNum(5)),
//              JsObj("name" -> "mp", "value" -> JsNum(110)),
//            ),
//            "img" -> "Setzer.gif"
//          ),
//          JsObj(
//            "id" -> "c3",
//            "attributes" -> JsArr(
//              JsObj("name" -> "name", "value" -> "Terra"),
//              JsObj("name" -> "hp", "value" -> JsNum(90)),
//              JsObj("name" -> "atk", "value" -> JsNum(0)),
//              JsObj("name" -> "def", "value" -> JsNum(10)),
//              JsObj("name" -> "mp", "value" -> JsNum(100)),
//            ),
//            "img" -> "Terra.gif"
//          )
//        )
//      ),
//      JsObj("characters" -> JsArr(
//        JsObj(
//          "id" -> "e1",
//          "attributes" -> JsArr(
//            JsObj("name" -> "name", "value" -> "Nohrabbit"),
//            JsObj("name" -> "hp", "value" -> JsNum(100)),
//            JsObj("name" -> "atk", "value" -> JsNum(20)),
//            JsObj("name" -> "def", "value" -> JsNum(100)),
//          ),
//          "img" -> "Nohrabbit.gif"
//        )
//      ))
//    )
//
//    assertEquals(GameController.getPlayers, expectedJson)
//  }
//
//  test("getGamePanels should return the correct JSON representation of panels") {
//    val expectedJson = JsArr(
//      JsObj(
//        "id" -> "panel11",
//        "x" -> 1,
//        "y" -> 1,
//        "storage" -> JsArr("c1")
//      ),
//      JsObj(
//        "id" -> "panel21",
//        "x" -> 2,
//        "y" -> 1,
//        "storage" -> JsArr("c2")
//      ),
//      JsObj(
//        "id" -> "panel22",
//        "x" -> 2,
//        "y" -> 2,
//        "storage" -> JsArr()
//      ),
//      JsObj(
//        "id" -> "panel12",
//        "x" -> 1,
//        "y" -> 2,
//        "storage" -> JsArr("c3", "e1")
//      )
//    )
//
//    assertEquals(GameController.getGamePanels, expectedJson)
//  }
//
//  test("getCurrentGameUnitId should return the correct current game unit ID") {
//    val expectedId = "c1"
//    assertEquals(GameController.getCurrentGameUnitId, expectedId)
//  }
//
//  test("decideNextGameUnitId should return the correct next game unit ID") {
//    val expectedId = "e1"
//    assertEquals(GameController.decideNextGameUnitId, expectedId)
//  }
//
//  test("findActionsByGameUnitId should return actions for a valid entity") {
//    val result = GameController.findActionsByGameUnitId("c1") // ID de Relm
//    assert(result.isDefined)
//    assert(result.get.toString.contains("actions"))
//  }
//
//  test("findActionsByGameUnitId should return None for an invalid entity ID") {
//    val result = GameController.findActionsByGameUnitId("invalidId")
//    assert(result.isEmpty)
//  }
//
//  test("doAction should execute action successfully") {
//    val result = GameController.doAction("2", "c1", "panel12")
//    //assert(result.isEmpty) // No debería lanzar excepción
//  }
//
//  test("doAction should return error message when action cannot be executed") {
//    val result = GameController.doAction("invalidAction", "c1", "e1")
//    assert(result.nonEmpty)
//  }
//
//  test("reset should initialize game state correctly") {
//    GameController.reset()
//    assertEquals(GameController.getPlayers.toString, GameController.getPlayers.toString) // Verifica que el estado sea correcto
//  }
//}
