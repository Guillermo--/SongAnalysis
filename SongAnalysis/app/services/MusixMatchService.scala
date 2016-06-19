package services

import org.slf4j.LoggerFactory
import scala.collection.JavaConverters._
import com.google.gson.Gson

class MusixMatchService {
  private val apiKey: String = "c3bdc694604d4a059cf880fe509c032e";
  private val LOGGER = LoggerFactory.getLogger(classOf[MusixMatchService])
  private val apiEndpoint = "http://api.musixmatch.com/ws/1.1/"
  private val SEARCH_ARTIST = "artist.search"
  private val GET_ARTIST = "artist.get"
  private val GET_ARTIST_ALBUMS = "artist.albums.get"
  private val GET_ALBUM = "album.get"
  private val GET_ALBUM_TRACKS = "album.tracks.get"
  private val GET_ARTIST_CHART = "artist.chart.get"
  private val GET_MATCHER_TRACK = "matcher.track.get"
  private val GET_TRACK_CHART = "track.chart.get"
  private val SEARCH_TRACK = "track.search"
  private val GET_TRACK = "track.get"
  private val GET_TRACK_SUBTITLE = "track.subtitle.get"
  private val GET_TRACK_LYRICS = "track.lyrics.get"

  private def makeApiUrl(apiName: String, qsObject: AsQueryString) =
    apiEndpoint + apiName + "?apikey=" + apiKey + "&format=json&" + qsObject.queryString()

  private def call(url: String) = {
    val jsonResponse = scala.io.Source.fromURL(url).mkString
    
    val aux = new Gson().fromJson(jsonResponse, classOf[AuxServiceResponse])
    aux.check

    try {
      if (aux.is404) {
        val r = new ServiceResponse
        r.message = new Message
        r.message.header = aux.message.header
        r
      } else
        new Gson().fromJson(jsonResponse, classOf[ServiceResponse])
    }
    catch {
      case e: Exception => LOGGER.error("Unable to unmarshall json " + jsonResponse, e); throw e
    }
  }

  def getTrackCharts(req: GetTrackChart) = {
    call(makeApiUrl(GET_TRACK_CHART, req))
  }
  
}

