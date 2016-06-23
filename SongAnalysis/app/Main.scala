import scala.collection.JavaConversions._
import musixmatch._
import controllers._

object Main {

  private def service: MusixMatchClient = {
    new MusixMatchClient()
  }

  def main(args: Array[String]) = {
     println(TopTracks.getOverallWordCount(null, null, -1, 5))
  }

}