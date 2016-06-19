package services

class GetTrackChart(
    var country: String = null,
    var f_has_lyrics: String = null,
    var page: Number = -1,
    var page_size: Number = -1) extends AsQueryString {
  
    def this() = this(null, null, -1, -1)
}
