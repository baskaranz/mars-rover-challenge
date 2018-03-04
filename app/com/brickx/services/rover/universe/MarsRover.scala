package com.brickx.services.rover

import com.brickx.services.rover.commands.RoverCommand
import com.brickx.services.rover.parser.CommandParser
import com.brickx.services.rover.universe._
import com.google.inject.Inject
import play.api.{Configuration, Logger}

import scala.concurrent.Future
import scala.util.Try

import scala.concurrent.ExecutionContext.Implicits.global

case class MarsRover(plateau: Plateau, currentPos: Coordinates, facingTowards: Direction) {

  def run(commandStr: Option[String]) = {
    val commands = CommandParser(commandStr).commands
    commands.foldLeft(this)((rover, command) => command.execute(rover))
  }

  def move = {
    val newCoordinate = facingTowards match {
      case NORTH => currentPos.incrementBy(0, 1)
      case SOUTH => currentPos.incrementBy(0, -1)
      case EAST => currentPos.incrementBy(1, 0)
      case WEST => currentPos.incrementBy(-1, 0)
    }
    plateau.withinRange(newCoordinate) match {
      case true => MarsRover(plateau, newCoordinate, facingTowards)
      case false => this
    }
  }

  def turnLeft = MarsRover(plateau, currentPos, facingTowards.left)

  def turnRight = MarsRover(plateau, currentPos, facingTowards.right)

  def  currentLocation: String = toString

  override def toString: String = s"${currentPos.x} ${currentPos.y} ${facingTowards}"

}

class MarsRoverService @Inject()(config: Configuration) {

  val plateauUpperRight: (Int, Int) = (config.get[Int]("plateau.upperRight.x"), config.get[Int]("plateau.upperRight.y"))

  def navigateRover(paramStrOpt: Option[String]): Future[Try[Option[MarsRover]]] = Future {
    Try {
      paramStrOpt match {
        case Some(paramStr) =>
          Logger.info(s"paramStr : ${paramStr}")
          val paramsList: List[String] = paramStr.split(",").toList
          if (paramsList.size == 2) {
            (validateDirections(paramsList(0)), validateCommands(paramsList(1))) match {
              case (Some(directions), Some(commands)) =>
                val (coordinates: Coordinates, direction: Direction) = directions
                val rover = MarsRover(Plateau(upperRight = Coordinates(plateauUpperRight._1, plateauUpperRight._2)), coordinates, direction)
                var newRover = rover
                commands.map { f =>
                  val res = f.execute(newRover)
                  newRover = res
                }
                Some(newRover)
              case _ =>
                Logger.warn(s"Invalid input parameters ${paramStr}")
                throw new IllegalArgumentException
            }
          } else {
            Logger.warn(s"Invalid input parameters ${paramStr}")
            throw new IllegalArgumentException
          }
        case None =>
          Logger.warn(s"Input parameters required")
          throw new IllegalArgumentException
      }
    }
  }


  private def validateDirections(directionsStr: String): Option[(Coordinates, Direction)] = {
    val directionsStrListOpt = Try(directionsStr.map(_.toString).toList).toOption
    directionsStrListOpt match {
      case Some(directionsStrList) =>
        Logger.info(s"commandStrChars : ${directionsStrList}")
        if ((directionsStrList.size == 3)) {
          val x: Int = directionsStrList(0).toInt
          val y: Int = directionsStrList(1).toInt
          val dirOpt: Option[Direction] = directionsStrList(2) match {
            case "N" => Some(NORTH)
            case "W" => Some(WEST)
            case "S" => Some(SOUTH)
            case "E" => Some(EAST)
            case  _  => None
          }
          Logger.info(s"x: ${x} | y: ${y} | direction : ${dirOpt}")
          dirOpt match {
            case Some(direction) =>
              Logger.info(s"Valid parameters ${direction}")
              Some(Coordinates(x, y), direction)
            case None =>
              Logger.info(s"Invalid parameters ${directionsStr}")
              None
          }
        } else {
          Logger.info(s"Invalid parameters ${directionsStr}")
          None
        }
      case _ =>
        Logger.info(s"Invalid parameters ${directionsStr}")
        None
    }
  }

  private def validateCommands(commandsStr: String): Option[Seq[RoverCommand]] = {
    Try(CommandParser(Some(commandsStr)).commands).toOption
  }
}
