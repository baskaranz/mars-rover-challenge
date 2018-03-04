package controllers

import javax.inject._

import com.brickx.services.rover.MarsRoverService
import play.Logger
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

import scala.util.{Failure, Success}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * mars rover service.
  */
@Singleton
class RoverController @Inject()(cc: ControllerComponents, marsRoverService: MarsRoverService) extends AbstractController(cc) {

  def position() = Action.async { implicit request: Request[AnyContent] =>
    val paramOpt = request.getQueryString("params")
    marsRoverService.navigateRover(paramOpt) map {
      case Success(resultOpt) =>
        resultOpt match {
          case Some(result) =>
            Ok(Json.obj("msg" -> "Successfully retrieved rover positon for given input", "result" -> result.toString))
          case None =>
            Ok(Json.obj("msg" -> "Not able to retrieve rover position for given input", "result" -> ""))
        }
      case Failure(ex) =>
        Logger.error(ex.getMessage, ex)
        ex match {
          case _: IllegalArgumentException => BadRequest(Json.obj("msg" -> "Failed to retrieve rover position", "error" -> "Invalid input parameter"))
          case _ => InternalServerError(Json.obj("msg" -> "Failed to retrieve rover position", "error" -> "Oops, system went for a toss. Please try later"))
        }

    }
  }
}
