package musixmatch

class GetTrackChart(
    var country: String = null,
    var f_has_lyrics: String = null,
    var page: Number = -1,
    var page_size: Number = -1) extends AsQueryString {
  
    def this() = this(null, null, -1, -1)
}

class GetTrack(var track_id: String) extends AsQueryString {

}


