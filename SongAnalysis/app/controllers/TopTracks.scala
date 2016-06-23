package controllers

import scala.collection.JavaConversions._
import musixmatch._
import services._
import scala.collection._

object TopTracks {

  private def musixMatchService: MusixMatchClient = {
    new MusixMatchClient()
  }

  private def wordCounter: WordCounter = {
    new WordCounter()
  }

  def getTopTracks(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    musixMatchService.getTrackCharts(country, hasLyrics, page, pageSize)
  }

  def getOverallWordCount(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    var aggregatedLyrics = mutable.StringBuilder.newBuilder

    getTopTracks(country, hasLyrics, page, pageSize).foreach {
      track => 
        if(musixMatchService.getLyrics(track.id) != null)
          aggregatedLyrics.append(musixMatchService.getLyrics(track.id).body)
    }

    var cleanedAggregatedLyrics = aggregatedLyrics.toString().toLowerCase().replaceAll("[^A-Za-z0-9 ]", "").replaceAll("this lyrics is not for commercial use", "")

    println("\ncleanedAggregatedLyrics lyrics: " + cleanedAggregatedLyrics)
    wordCounter.getWordCounts(cleanedAggregatedLyrics)

  }

}