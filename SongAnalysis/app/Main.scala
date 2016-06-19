import scala.collection.JavaConversions._


object Main {

  private def service: services.MusixMatch = {
    new services.MusixMatch()
  }

  def main(args: Array[String]) {
    println("Charts: "+getTrackCharts())
  }

  def getTrackCharts(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    println("\n\nInside api cal\n\n")
    val tracks = service.getTrackCharts(new services.GetTrackChart(country, hasLyrics, page, pageSize)).tracks
    
    for(t  <- tracks) {
      println(t.name)
    }
    
  }
}