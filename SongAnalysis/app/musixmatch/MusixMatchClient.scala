package musixmatch


class MusixMatchClient {

  private def musixMatchService: MusixMatchService = {
    new MusixMatchService()
  }

  def getTrackCharts(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    musixMatchService.getTrackCharts(new GetTrackChart(country, hasLyrics, page, pageSize)).tracks
  }

  def getLyrics(trackId: String): Lyrics = {
    val serviceResponse = musixMatchService.getLyrics(new GetTrack(trackId))

    if (serviceResponse.lyrics == null)
      null
    else
      serviceResponse.lyrics
  }
  

}