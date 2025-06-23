package api

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.*
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.*
import akka.http.scaladsl.Http

import scala.io.StdIn
import util.Json.{*, given}

import controller.GameController

object GameApi {
  implicit val system: ActorSystem[_] = ActorSystem(Behaviors.empty, "GameApi")
  implicit val executionContext: ExecutionContext = system.executionContext

  def run(args: Array[String]): Unit = {
    val route: Route =
      concat(
        get {
          pathPrefix("start") {
            cors() {
              complete(
                JsObj(
                  "players" -> GameController.getPlayers,
                  "panels" -> GameController.getGamePanels,
                  "currentUnit" -> GameController.getCurrentGameUnitId
                )
              )
            }
          }
        },
        get {
          path("reset") {
            GameController.reset()
            cors() {
              complete("Game reset")
            }
          }
        },
        get {
          pathPrefix("show-actions" / Segment) { requesterId =>
            cors() {
              GameController.findActionsByGameUnitId(requesterId) match {
                case Some(x) => complete(x)
                case None    => complete(StatusCodes.NotFound)
              }
            }
          }
        },
        post {
          path("execute-action" / Segment / Segment / Segment) {
            (actionId, sourceId, targetId) =>
              val message =
                GameController.doAction(actionId, sourceId, targetId)
              val nextUnitId: String = GameController.decideNextGameUnitId

              cors() {
                complete(
                  JsObj(
                    "message" -> message,
                    "currentUnit" -> nextUnitId
                  )
                )
              }
          }
        },
        get {
          pathPrefix("static" / "resource") {
            getFromResourceDirectory("static")
          }
        }
      )

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server online at http://localhost:8080/\nPress ENTER to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
