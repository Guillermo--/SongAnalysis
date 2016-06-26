package controllers

import scala.collection.JavaConversions._
import musixmatch._
import services._
import scala.collection._
import musixmatch.MusixMatchService

object TopTracks {

  private def musixMatchService: MusixMatchClient = {
    new MusixMatchClient()
  }

  private def wordProcessor: WordProcessor = {
    new WordProcessor()
  }

  def getTopTracks(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    musixMatchService.getTrackCharts(country, hasLyrics, page, pageSize)
  }

  def getOverallWordCount(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    var aggregatedLyrics = mutable.StringBuilder.newBuilder

    getTopTracks(country, hasLyrics, page, pageSize).foreach {
      track =>
        if (musixMatchService.getLyrics(track.id) != null)
          aggregatedLyrics.append(musixMatchService.getLyrics(track.id).body)
    }
    wordProcessor.getWordCounts(aggregatedLyrics)
  }

  def getAllDifferentWords(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    var trackWithMostUniqueWords: Track = null
    var maxUniquenessPercent = 0.0
    var maxUniqueWords = 0.0
    var maxTotalWords = 0.0

    getTopTracks(country, hasLyrics, page, pageSize).foreach {
      track =>
        var lyrics = musixMatchService.getLyrics(track.id).body
        if (lyrics != null) {
          var uniqueWordsAmount = wordProcessor.getAllDifferentWords(new StringBuilder(lyrics)).size.doubleValue()
          var totalWords = wordProcessor.cleanLyrics(new StringBuilder(lyrics)).size.doubleValue()
          var uniquenessPercent = uniqueWordsAmount / totalWords * 100
          if (uniquenessPercent > maxUniquenessPercent) {
            maxUniqueWords = uniqueWordsAmount
            maxTotalWords = totalWords
            maxUniquenessPercent = BigDecimal(uniquenessPercent).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
            trackWithMostUniqueWords = track
          }
        }
    }
    new Tuple4(trackWithMostUniqueWords, maxUniqueWords, maxTotalWords, maxUniquenessPercent)
  }

  def getTrackWithMostNonRepeatingWords(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    var trackWithMostNonRepeatingWords: Track = null
    var maxRatio = 0.0
    var maxNonRepeatingWords = 0.0
    var maxTotalWords = 0.0
    var wordMap = mutable.Map.empty[String, Int]

    getTopTracks(country, hasLyrics, page, pageSize).foreach {
      track =>
        var lyrics = musixMatchService.getLyrics(track.id).body
        if (lyrics != null) {
          var map = wordProcessor.getNonRepeatingWords(new StringBuilder(lyrics))
          var nonRepatingWords = map.size
          var totalWords = wordProcessor.cleanLyrics(new StringBuilder(lyrics)).size
          var ratio = nonRepatingWords.doubleValue() / totalWords.doubleValue() * 100
          if (ratio > maxRatio) {
            maxTotalWords = totalWords
            maxNonRepeatingWords = nonRepatingWords
            maxRatio = BigDecimal(ratio).setScale(2, BigDecimal.RoundingMode.HALF_UP).doubleValue()
            trackWithMostNonRepeatingWords = track
            wordMap = map
          }
        }
    }
    new Tuple5(trackWithMostNonRepeatingWords.name, maxNonRepeatingWords, maxTotalWords, maxRatio, wordMap)
  }
  
  def getTrackWithMostRepeatingWords(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    var trackWithMostRepeatingWords: Track = null
    var maxRatio = 0.0
    var maxRepeatingWords = 0.0
    var maxTotalWords = 0.0
    var wordMap = mutable.Map.empty[String, Int]

    getTopTracks(country, hasLyrics, page, pageSize).foreach {
      track =>
        var lyrics = musixMatchService.getLyrics(track.id).body
        if (lyrics != null) {
          var map = wordProcessor.getRepeatingWords(new StringBuilder(lyrics))
          var nonRepatingWords = map.size
          var totalWords = wordProcessor.cleanLyrics(new StringBuilder(lyrics)).size
          var ratio = nonRepatingWords.doubleValue() / totalWords.doubleValue() * 100
          if (ratio > maxRatio) {
            maxTotalWords = totalWords
            maxRepeatingWords = nonRepatingWords
            maxRatio = BigDecimal(ratio).setScale(2, BigDecimal.RoundingMode.HALF_UP).doubleValue()
            trackWithMostRepeatingWords = track
            wordMap = map
          }
        }
    }
    new Tuple5(trackWithMostRepeatingWords.name, maxRepeatingWords, maxTotalWords, maxRatio, wordMap)
  }

}