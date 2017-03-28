package dk.nscp.scala_server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, HttpMethods, Uri}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.Future
import scala.io.StdIn
import scala.util.{Failure, Success}

object Main extends App {

  implicit val system = ActorSystem()
  import system.dispatcher
  implicit val materializer = ActorMaterializer()


  val route: Route = 
    get {
      path("hello") {
        complete("Hello, world!")
      } ~
      path("world") {
        complete("World, hello!")
      }
    }


  Http().bindAndHandleAsync(Route.asyncHandler(route), "localhost", 8080)
    .onComplete {
      case Success(_) =>
        println("Server started on port 8080. Type ENTER to terminate")
        StdIn.readLine()
        system.terminate()
      case Failure(e) =>
        println("Binding failed.")
        e.printStackTrace
        system.terminate()
    }
}

