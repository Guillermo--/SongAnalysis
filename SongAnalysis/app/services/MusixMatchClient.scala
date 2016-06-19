package services

class MusixMatchClient {

  private def service: services.MusixMatchService = {
    new services.MusixMatchService()
  }

  def getTrackCharts(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    service.getTrackCharts(new services.GetTrackChart(country, hasLyrics, page, pageSize)).tracks
  }
}