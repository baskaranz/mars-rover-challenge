package services.rover

import com.brickx.services.rover.commands.{Move, TurnLeft, TurnRight}
import com.brickx.services.rover.parser.CommandParser
import org.scalatest.{FlatSpec, Matchers}

class CommandParserSpec extends FlatSpec with Matchers {

  "CommandParser" should " create three Commands" in {
    CommandParser(Some("MLR")).commands should equal(Seq(Move, TurnLeft, TurnRight))
  }

  "CommandParser" should " return empty sequence for None/empty parameter" in {
    CommandParser(Some("")).commands should equal(Seq())
    CommandParser(None).commands should equal(Seq())
  }

}
