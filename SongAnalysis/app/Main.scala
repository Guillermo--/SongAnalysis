import scala.collection.JavaConversions._
import services._

object Main {

  private def service: MusixMatchClient = {
    new MusixMatchClient()
  }

  def main(args: Array[String]) {
     val tracks = service.getTrackCharts()
     
     for(track <- tracks) {
       println(track)
     }
  }

}