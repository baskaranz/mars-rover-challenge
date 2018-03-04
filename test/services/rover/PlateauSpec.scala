package services.rover

import com.brickx.services.rover.universe.{Coordinates, Plateau}
import org.scalatest.{FlatSpec, Matchers}

class PlateauSpec extends FlatSpec with Matchers {

  val plateau = Plateau(upperRight = Coordinates(5, 5))

  "Plateau" should "lower left should by default be at origin" in {
    Plateau(upperRight = Coordinates(5, 5)).lowerLeft should equal(Coordinates(0,0))
  }

  "Plateau" should "return out of range for Coordinates out of upper right" in {
    plateau.withinRange(Coordinates(6, 5)) should equal(false)
  }

  "Plateau" should "say out of range for Coordinates below lower left" in {
    plateau.withinRange(Coordinates(-1, 0)) should equal(false)
  }

}

