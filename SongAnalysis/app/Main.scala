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
    

  }

}