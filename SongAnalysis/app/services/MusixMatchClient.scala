package services

class MusixMatchClient {

  private def musixMatchService: services.MusixMatchService = {
    new services.MusixMatchService()
  }

  def getTrackCharts(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    musixMatchService.getTrackCharts(new GetTrackChart(country, hasLyrics, page, pageSize)).tracks
  }

  def getLyrics(trackId: String): services.Lyrics = {
    val serviceResponse = musixMatchService.getLyrics(new GetTrack(trackId))

    if (serviceResponse.lyrics == null)
      null
    else
      serviceResponse.lyrics
  }
  

}