package com.brickx.services.rover.parser

import com.brickx.services.rover.commands.{Move, RoverCommand, TurnLeft, TurnRight}

case class CommandParser(commandStr: Option[String]) {

  def commands: Seq[RoverCommand] = {
    commandStr.getOrElse("").map {
      case 'M' => Move
      case 'L' => TurnLeft
      case 'R' => TurnRight
    }
  }

}

