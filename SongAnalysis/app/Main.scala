import scala.collection._
import scala.collection.JavaConversions._
import musixmatch._
import services._
import controllers._
import com.google.gson.Gson


object Main {

  private def service: MusixMatchClient = {
    new MusixMatchClient()
  }
  
  private def wordProcessor: WordProcessor = {
    new WordProcessor()
  }

  def main(args: Array[String]) = {
    var aggregatedLyrics = mutable.StringBuilder.newBuilder

    service.getTrackCharts(null, null, -1, 10).foreach {
      track =>
        if (service.getLyrics(track.id) != null)
          aggregatedLyrics.append(service.getLyrics(track.id).body)
    }
    
    var map = wordProcessor.getSortedWordCounts(aggregatedLyrics)    
    
    println(map.length);
    map.foreach { x => println(x.text) };


  }

}