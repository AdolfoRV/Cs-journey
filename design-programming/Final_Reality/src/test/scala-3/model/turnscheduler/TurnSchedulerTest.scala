package model.turnscheduler

import model.entity.characters.ICharacter
import model.entity.{Enemy, IEntity}
import model.entity.characters.common.Knight
import model.usable.weapon.common.Sword
import munit.FunSuite

class TurnSchedulerTest extends FunSuite {
  var scheduler: TurnScheduler = _

  var entity1: ICharacter = _
  var entity2: IEntity = _

  var sword: Sword = _

  override def beforeEach(context: BeforeEach): Unit = {
    scheduler = new TurnScheduler()

    entity1 = new Knight("Laios", 120, 50, 80)
    entity2 = new Enemy("Wall of Flesh", 500, 100, 90, 120)

    sword = new Sword("Kensuke", 1000, 20)  // Laios will have a maxActionBar = 80+(0.5*20)
    entity1.weapon_=(Some(sword))
  }

  test("Adding entities correctly initializes action bars") {
    scheduler.addEntity(entity1)
    scheduler.addEntity(entity2)

    assertEquals(scheduler.recordActionBars(entity1), 0)
    assertEquals(scheduler.recordActionBars(entity2), 0)
  }

  test("Increasing action bars increments correctly") {
    scheduler.addEntity(entity1)
    scheduler.addEntity(entity2)
    scheduler.increaseActionBars()

    assertEquals(scheduler.recordActionBars(entity1), 20)  // 20 is the fixed amount
    assertEquals(scheduler.recordActionBars(entity2), 20)

    scheduler.increaseActionBars()
    assertEquals(scheduler.recordActionBars(entity1), 40)
    assertEquals(scheduler.recordActionBars(entity2), 40)
  }

  test("Resetting an action bar sets it to 0") {
    scheduler.addEntity(entity1)
    scheduler.increaseActionBars()

    scheduler.resetActionBar(entity1)
    assertEquals(scheduler.recordActionBars(entity1), 0)
  }

  test("Action bar completes when the progress meets or exceeds the max action bar") {
    scheduler.addEntity(entity1)
    scheduler.addEntity(entity2)

    scheduler.increaseActionBars() // +20
    scheduler.increaseActionBars() // +40
    scheduler.increaseActionBars() // +60
    scheduler.increaseActionBars() // +80
    scheduler.increaseActionBars() // +100

    assert(scheduler.isActionBarComplete(entity1))   // surplus of 10
    assert(!scheduler.isActionBarComplete(entity2))  // needs 100 to complete
  }

  test("Entities ready for turn are returned in order of their action bar surplus") {
    scheduler.addEntity(entity1)
    scheduler.addEntity(entity2)

    for (_ <- 1 to 6) scheduler.increaseActionBars() // +120

    val readyEntities = scheduler.getEntitiesReadyForTurn

    assertEquals(readyEntities.head, entity1) // My boy Laios has +20 of surplus
    assertEquals(readyEntities(1), entity2)
  }

  test("Current entity is the first ready entity") {
    scheduler.addEntity(entity1)
    scheduler.addEntity(entity2)

    for (_ <- 1 to 6) scheduler.increaseActionBars() // +120
    val currentEntity = scheduler.getCurrentEntity

    assertEquals(currentEntity.get, entity1)
  }

  test("Removing an entity correctly updates the scheduler") {
    scheduler.addEntity(entity1)
    scheduler.addEntity(entity2)

    scheduler.removeEntity(entity1)

    assert(!scheduler.recordActionBars.contains(entity1)) // Laios not present
    assert(scheduler.recordActionBars.contains(entity2))  // Wall of Flesh still present
  }
}
