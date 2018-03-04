package services.rover

import com.brickx.services.rover.MarsRover
import com.brickx.services.rover.commands.{Move, TurnLeft, TurnRight}
import com.brickx.services.rover.universe.{Coordinates, NORTH, Plateau}
import org.scalatest.{FlatSpec, Matchers}

class RoverCommandSpec extends FlatSpec with Matchers {

  val plateau = Plateau(upperRight = Coordinates(5, 5))

  "MoveCommand" should "move rover forward" in {
    val rover = MarsRover(plateau, Coordinates(1, 1), NORTH)
    Move.execute(rover).currentLocation should equal("1 2 N")
  }

  "TurnLeftCommand" should "turn rover left" in {
    val rover = MarsRover(plateau, Coordinates(1, 1), NORTH)
    TurnLeft.execute(rover).currentLocation should equal("1 1 W")
  }

  "TurnRightCommand" should "turn rover right" in {
    val rover = MarsRover(plateau, Coordinates(1, 3), NORTH)
    TurnRight.execute(rover).currentLocation should equal("1 3 E")
  }

}
