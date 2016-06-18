package services

import com.google.gson.Gson
import org.slf4j.LoggerFactory

class MusixMatch {
  private val apiKey: String = "c3bdc694604d4a059cf880fe509c032e";
  private val LOGGER = LoggerFactory.getLogger(classOf[MusixMatch])
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
  }

  def getTrackCharts(req: GetTrackChart) = {
    call(makeApiUrl(GET_TRACK_CHART, req))
  }
}