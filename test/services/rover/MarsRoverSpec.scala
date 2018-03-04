package services.rover

import com.brickx.services.rover.MarsRover
import com.brickx.services.rover.universe._
import org.scalatest.{FlatSpec, Matchers}

class MarsRoverSpec extends FlatSpec with Matchers {

  val plateau = Plateau(upperRight = Coordinates(5, 5))

  "MarsRover" should " move 1 step in Y axis from NORTH" in {
    val rover = MarsRover(plateau, Coordinates(1, 1), NORTH)
    rover.move.currentLocation should equal("1 2 N")
  }

  "MarsRover" should " move 1 step in Y axis from SOUTH" in {
    val rover = MarsRover(plateau, Coordinates(1, 2), SOUTH)
    rover.move.currentLocation should equal("1 1 S")
  }

/*  "MarsRover" should " take multiple commands and move" in {
    val rover = MarsRover(plateau, Coordinates(1, 2), NORTH)
    rover.turnLeft.move.turnLeft.move.turnLeft.move.turnLeft.move.move.currentLocation should equal("1 3 N")
  }*/

  "MarsRover" should " take multiple commands and move" in {
    val rover = MarsRover(plateau, Coordinates(3, 3), EAST)
    rover.move.move.turnRight.move.move.turnRight.move.turnRight.turnRight.move.currentLocation should equal("5 1 E")
  }

  "MarsRover" should " not go off the plateau" in {
    val rover = MarsRover(plateau, Coordinates(4, 4), NORTH)
    rover.move.move.move.currentLocation should equal("4 5 N")
  }

  "MarsRover" should " should not fail for None parameter" in {
    val rover = MarsRover(plateau, Coordinates(1, 1), EAST)
    rover.run(None).currentLocation should equal("1 1 E")
  }

}
