package musixmatch

import java.lang.RuntimeException
import java.util.{ Date, ArrayList }
import java.text.{ ParseException, SimpleDateFormat }
import scala.collection.JavaConversions._

trait ReflectiveToString {
  override def toString(): String = {
    val buff: StringBuffer = new StringBuffer()
    val fields = this.getClass.getDeclaredFields
    for (field <- fields) {

      val value = this.getFieldValue(field.getName)
      if (buff.length() > 0)
        buff.append(", ")
      buff.append(field.getName + "=" + value)

    }

    buff.toString
  }

  private def getFieldValue(fieldName: String) = this.getClass.getDeclaredMethod(fieldName).invoke(this)
}

class Header extends ReflectiveToString {
  var status_code: Int = -1
  var execute_time: Number = -1
  var available: Number = -1
}

class RawAlbum extends ReflectiveToString {
  var album_id: String = null
  var artist_id: String = null
  var artist_name: String = null
  var album_name: String = null
  var album_release_date: String = null
  var album_release_type: String = null
  var album_coverart_100x100: String = null

  def toAlbum: Album = {
    val a = new Album()

    a.id = this.album_id
    a.artistId = this.artist_id
    a.artistName = this.artist_name
    a.name = this.album_name
    a.releaseDate = Formatter.parse(this.album_release_date)
    a.releaseDateString = Formatter.format(a.releaseDate)
    a.releaseType = this.album_release_type
    a.coverart = this.album_coverart_100x100

    a
  }
}

class RawAlbumWrapper {
  var album: RawAlbum = null
}

class RawArtist extends ReflectiveToString {
  var artist_id: String = null
  var artist_mbid: String = null
  var artist_name: String = null
  var artist_alias_list: java.util.List[RawArtistAliasWrapper] = null

  def toArtist: Artist = {
    val a = new Artist()
    a.id = this.artist_id
    a.mbid = this.artist_mbid
    a.name = this.artist_name

    for (rawAlias <- artist_alias_list) {
      a.alias.add(rawAlias.artist_alias)
    }

    a
  }

}

class RawArtistWrapper {
  var artist: RawArtist = null

  override def toString = if (artist == null) null else artist.toString

}

class RawArtistAliasWrapper {
  var artist_alias: String = null
}

class RawTrack extends ReflectiveToString {
  var track_id: String = null
  var track_name: String = null
  var artist_name: String = null
  var track_mbid: String = null
  var track_length: String = null
  var lyrics_id: String = null
  var instrumental: String = null
  var subtitle_id: String = null
  var album_name: String = null
  var album_id: String = null
  var artist_id: String = null
  var album_coverart_100x100: String = null
  var artist_mbid: String = null

  def toTrack: Track = {
    val track = new Track()

    track.id = this.track_id
    track.name = this.track_name
    track.artistName = this.artist_name
    track.mbid = this.track_mbid
    track.length = this.track_length
    track.lyricsId = this.lyrics_id
    track.instrumental = this.instrumental
    track.subtitleId = this.subtitle_id
    track.albumName = this.album_name
    track.albumId = this.album_id
    track.artistId = this.artist_id
    track.artistMbid = this.artist_mbid
    track.coverart = this.album_coverart_100x100

    track
  }
}

class RawTrackWrapper {
  var track: RawTrack = null
}

class RawLyrics {
  var lyrics_id: String = null
  var restricted: Number = null
  var lyrics_body: String = null
  var lyrics_language: String = null
  var script_tracking_url: String = null
  var pixel_tracking_url: String = null
  var lyrics_copyright: String = null

  def toLyrics = {
    val l = new Lyrics

    l.id = this.lyrics_id
    l.body = this.lyrics_body
    l.language = this.lyrics_language
    l.scriptTrackingUrl = this.script_tracking_url
    l.pixelTrackingUrl = this.pixel_tracking_url
    l.copyright = this.lyrics_copyright
    l.restricted = this.restricted == 1

    l
  }
}

class Body {
  var album_list: java.util.List[RawAlbumWrapper] = null
  var artist_list: java.util.List[RawArtistWrapper] = null
  var track_list: java.util.List[RawTrackWrapper] = null
  var artist: RawArtist = null
  var album: RawAlbum = null
  var track: RawTrack = null
  var lyrics: RawLyrics = null
}

class Message {
  var header: Header = null
  var body: Body = null
}

class AuxServiceResponse {
  var message: AuxMessage = null

  def status_code = message.header.status_code

  def check = {
    status_code match {
      case 400 => throw new RuntimeException("The request had bad syntax or was inherently impossible to be satisfied")
      case 401 => throw new RuntimeException("authentication failed, probably because of a bad API key")
      case 402 => throw new RuntimeException("a limit was reached, either you exceeded per hour requests limits or your balance is insufficient")
      case 403 => throw new RuntimeException("You are not authorized to perform this operation / the api version you�re trying to use has been shut down.")
      case 405 => throw new RuntimeException("requested method was not found")
      case _ => true
    }
  }

  def is404 = status_code == 404
}

class AuxMessage {
  var header: Header = null
}

class ServiceResponse {
  var message: Message = null

  def artists = {
    if (message.body == null || message.body.artist_list == null)
      null
    else {
      val artists: java.util.List[Artist] = new ArrayList[Artist]

      for (rawArtist <- message.body.artist_list) {
        artists.add(rawArtist.artist.toArtist)
      }

      artists
    }
  }

  def artist = {
    if (message.body == null || message.body.artist == null)
      null
    else {
      message.body.artist
    }
  }

  def albums = {
    if (message.body == null || message.body.album_list == null)
      null
    else {
      val albums: java.util.List[Album] = new ArrayList[Album]

      for (rawAlbum <- message.body.album_list) {
        albums.add(rawAlbum.album.toAlbum)
      }

      albums
    }
  }

  def album = {
    if (message.body == null || message.body.album == null)
      null
    else {
      message.body.album
    }
  }

  def tracks = {
    if (message.body == null || message.body.track_list == null)
      null
    else {
      val tracks: java.util.List[Track] = new ArrayList[Track]

      for (rawTrack <- message.body.track_list) {
        tracks.add(rawTrack.track.toTrack)
      }

      tracks
    }
  }

  def lyrics = {
    if (message.body == null || message.body.lyrics == null)
      null
    else
      message.body.lyrics.toLyrics
  }
}

object Formatter {
  val df = new SimpleDateFormat("yyyy-MM-dd")
  val dfyear = new SimpleDateFormat("yyyy")
  val df2 = new SimpleDateFormat("MMMMM yyyy")

  def parse(s: String) = if (s == null) null else try {
    df.parse(s)
  } catch {
    case e: ParseException => dfyear.parse(s)
  }

  def format(d: Date) = if (d == null) "" else df2.format(d)
}