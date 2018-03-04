package services.rover

import com.brickx.services.rover.universe._
import org.scalatest.{FlatSpec, Matchers}

class DirectionSpec extends FlatSpec with Matchers {

  "Direction" should "be WEST for a left turn from NORTH " in {
    val direction: Direction = NORTH
    direction.left should equal(WEST)
  }

  "Direction" should "be SOUTH for 2 left turns from NORTH" in {
    val direction: Direction = NORTH
    direction.left.left should equal(SOUTH)
  }

  "Direction" should "be EAST for 3 left turns from NORTH" in {
    val direction: Direction = NORTH
    direction.left.left.left should equal(EAST)
  }

}
