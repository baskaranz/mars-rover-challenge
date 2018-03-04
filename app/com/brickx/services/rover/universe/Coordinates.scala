package com.brickx.services.rover.universe

case class Coordinates(x: Int, y: Int) {
  def incrementBy(x: Int, y: Int): Coordinates = {
    Coordinates(this.x + x, this.y + y)
  }
}
