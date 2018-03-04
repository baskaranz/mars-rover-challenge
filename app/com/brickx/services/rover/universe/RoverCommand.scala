package com.brickx.services.rover.commands

import com.brickx.services.rover.MarsRover


trait RoverCommand {
  def execute(rover: MarsRover): MarsRover
}

object Move extends RoverCommand {
  override def execute(rover: MarsRover): MarsRover = rover.move
}

object TurnLeft extends RoverCommand {
  override def execute(rover: MarsRover): MarsRover = rover.turnLeft
}

object TurnRight extends RoverCommand {
  override def execute(rover: MarsRover): MarsRover = rover.turnRight
}


