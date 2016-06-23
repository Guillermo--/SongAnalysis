package musixmatch

import java.util.{ Date, ArrayList }

class Artist extends ReflectiveToString {
  var id: String = null
  var mbid: String = null
  var name: String = null
  var alias: java.util.List[String] = new ArrayList[String]
}

class Album extends ReflectiveToString {
  var id: String = null
  var name: String = null
  var artistId: String = null
  var artistName: String = null
  var releaseDate: Date = null
  var releaseDateString: String = null
  var releaseType: String = null
  var coverart: String = null
}

class Track extends ReflectiveToString {
  var id: String = null
  var mbid: String = null
  var length: String = null
  var lyricsId: String = null
  var instrumental: String = null
  var subtitleId: String = null
  var name: String = null
  var albumName: String = null
  var albumId: String = null
  var artistId: String = null
  var coverart: String = null
  var artistMbid: String = null
  var artistName: String = null
}

class Lyrics extends ReflectiveToString {
  var id: String = null
  var body: String = null
  var language: String = null
  var scriptTrackingUrl: String = null
  var pixelTrackingUrl: String = null
  var copyright: String = null
  var restricted: Boolean = false
}