package com.brickx.services.rover.universe

case class Plateau(lowerLeft: Coordinates = Coordinates(0, 0), upperRight: Coordinates) {
  def withinRange(coordinates: Coordinates): Boolean = {
      coordinates.x >= lowerLeft.x &&
      coordinates.x <= upperRight.x &&
      coordinates.y >= lowerLeft.y &&
      coordinates.y <= upperRight.y
  }
}
