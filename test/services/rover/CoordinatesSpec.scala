package services.rover

import com.brickx.services.rover.universe.Coordinates
import org.scalatest.{FlatSpec, Matchers}


class CoordinatesSpec extends FlatSpec with Matchers {

  "Coordinates x and y" should " be incrementable" in {
    Coordinates(1, 2).incrementBy(1, 2) should equal(Coordinates(2, 4))
  }

}

