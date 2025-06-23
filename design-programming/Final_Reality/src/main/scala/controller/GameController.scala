package controller

import api.{GameApi, IGameController, Target}
import exceptions.{DeadSourceException, PlayerDefeatedException}
import model.entity.IEntity
import model.panel.{IPanel, Panel}
import model.player.{IPlayer, Player}
import model.turnscheduler.TurnScheduler
import model.usable.IUsable
import util.SprayJson.{JsArr, JsObj, JsVal}
import factory.usables.*
import factory.entities.*
import model.entity.characters.ICharacter
import model.observer.{Observer, Subject}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object GameController extends IGameController with Observer[String] {

  private var items: ArrayBuffer[IUsable] = ArrayBuffer()
  private var entities: ArrayBuffer[IEntity] = ArrayBuffer()
  private var players: ArrayBuffer[IPlayer] = ArrayBuffer()
  private var panels: ArrayBuffer[IPanel] = ArrayBuffer()
  private var turnScheduler: TurnScheduler = TurnScheduler()

  def main(args: Array[String]): Unit = {
    reset()
    GameApi.run(args)
  }

  override def getGamePanels: JsVal = JsArr(panels.map(_.toJson))

  override def getPlayers: JsVal = JsArr(players.map(_.toJson))

  override def getCurrentGameUnitId: String = {
    turnScheduler.getCurrentEntity.map(_.id).getOrElse("")
  }

  override def decideNextGameUnitId: String = {
    turnScheduler.getEntitiesReadyForTurn.headOption.map(_.id).getOrElse("")
  }

  override def findActionsByGameUnitId(id: String): Option[JsVal] = {
    entities.find(entity => entity.id == id) match {
      case Some(entity) => Some(JsObj("actions" -> JsArr(entity.actions.map(_.toJson))))
      case None => None
    }
  }

  private var targets: Iterable[Target] = items ++ entities ++ panels

  override def doAction(actionId: String, sourceId: String, targetId: String): String = {
    val source = entities.filter(entity => entity.id == sourceId).head
    val target = targets.filter(target => target.id == targetId).head
    val action = source.findActionById(actionId).get

    try {
      source.doAction(action, target)
      turnScheduler.resetActionBar(source)
    } catch {
      case e: PlayerDefeatedException => 
        reset()
        return "Player defeated. Game reset."
      case e: DeadSourceException =>
        turnScheduler.removeEntity(source)
        return e.getMessage
      case e: Exception => return e.getMessage
    }

    ""
  }

  override def reset(): Unit = {
    val bow = BowFactory.create("Elven Bow")
    val dagger = DaggerFactory.create("Reduvia")
    val sword = SwordFactory.create("Excalibur")
    val wand = WandFactory.create("Elder Wand")
    val staff = StaffFactory.create("Staffado")

    val potions = PotionFactory.createMany(4) ++ PotionFactory.createMany(4)

    val relm = ThiefFactory.create("Relm")
    val terra = BlackMageFactory.create("Setzer")
    val setzer = WhiteMageFactory.create("Terra")

    val enemies = EnemyFactory.createMany(2)

    val player1 = Player(List(relm, setzer, terra), "player1")
    val player2 = Player(enemies, "player2")

    val panel11 = Panel(x=1, y=1, storage=ArrayBuffer(relm), panelId="panel11")
    val panel21 = Panel(x=2, y=1, storage=ArrayBuffer(setzer), panelId="panel21")
    val panel22 = Panel(x=2, y=2, storage=ArrayBuffer(enemies(0)), panelId="panel22")
    val panel12 = Panel(x=1, y=2, storage=ArrayBuffer(terra, enemies(1)), panelId="panel12")

    entities = ArrayBuffer(relm, setzer, terra) ++ enemies
    items = ArrayBuffer(bow, dagger, sword, wand, staff) ++ potions
    panels = ArrayBuffer(panel11, panel21, panel22, panel12)
    players = ArrayBuffer(player1, player2)

    targets = entities ++ items ++ panels

    turnScheduler = TurnScheduler()
    entities.foreach(entity => turnScheduler.addEntity(entity))

    players.foreach(player => player.addObserver(this))
    panels.foreach(panel => panel.panels = panels)
  }

  for (entity <- entities) {
    entity match {
    case character: ICharacter =>
      val randomItemCount = Random.nextInt(items.size + 1)
      val randomItems = Random.shuffle(items).take(randomItemCount)
      character.inventory = randomItems.toList
    case _ =>
    }
  }

  override def update(subject: Subject[String], value: String): Unit = {
    println(value)
  }
}