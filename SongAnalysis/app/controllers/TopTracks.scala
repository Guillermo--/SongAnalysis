package controllers
import play.api.mvc.{ Action, Controller }
import play.api.http.MimeTypes
import scala.collection.JavaConversions._
import musixmatch._
import services._
import scala.collection._
import play.api.libs.json._
import com.google.gson.Gson

class TopTracks extends Controller {

  private def musixMatchService: MusixMatchClient = {
    new MusixMatchClient()
  }

  private def wordProcessor: WordProcessor = {
    new WordProcessor()
  }

  def getTopTracks(pageSize: Int = -1) = Action {
    var trackList = musixMatchService.getTrackCharts(null, null, -1, pageSize)
    val gsonString: String = new Gson().toJson(trackList)
    Ok(gsonString).as("application/json")  
  }

  def getTopWordCount(pageSize: Int = -1) = Action {
    var aggregatedLyrics = mutable.StringBuilder.newBuilder

    musixMatchService.getTrackCharts(null, null, -1, pageSize).foreach {
      track =>
        if (musixMatchService.getLyrics(track.id) != null)
          aggregatedLyrics.append(musixMatchService.getLyrics(track.id).body)
    }
    
    var array = wordProcessor.getSortedWordCounts(aggregatedLyrics)
    val gsonString: String = new Gson().toJson(array)
    Ok(gsonString).as("application/json") 
  }

  def getAllDifferentWords(country: String = null, hasLyrics: String = null, page: Int = -1, pageSize: Int = -1) = {
    var trackWithMostUniqueWords: Track = null
    var maxUniquenessPercent = 0.0
    var maxUniqueWords = 0.0
    var maxTotalWords = 0.0

    musixMatchService.getTrackCharts(null, null, -1, pageSize).foreach {
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

  def getTrackWithMostNonRepeatingWords() = Action {
    var trackWithMostNonRepeatingWords: Track = null
    var maxRatio = 0.0
    var maxNonRepeatingWords = 0.0
    var maxTotalWords = 0.0

    musixMatchService.getTrackCharts(null, null, -1, 10).foreach {
      track =>
        var lyrics = musixMatchService.getLyrics(track.id).body
        if (lyrics != null) {
        	var totalWordsMap = wordProcessor.getWordCounts(new StringBuilder(lyrics))
        	var totalWords = totalWordsMap.size
          var nonRepeatingWordsMap = wordProcessor.getNonRepeatingWords(totalWordsMap)
          var nonRepeatingWords = nonRepeatingWordsMap.size
          var ratio = nonRepeatingWords.doubleValue() / totalWords.doubleValue() * 100
          
          if (ratio > maxRatio) {
            maxTotalWords = totalWords
            maxNonRepeatingWords = nonRepeatingWords
            maxRatio = BigDecimal(ratio).setScale(2, BigDecimal.RoundingMode.HALF_UP).doubleValue()
            trackWithMostNonRepeatingWords = track
          }
        }
    }
    
    val tuple = new Tuple5(trackWithMostNonRepeatingWords.name, trackWithMostNonRepeatingWords.artistName, maxTotalWords, maxNonRepeatingWords, maxRatio)
    val gsonString: String = new Gson().toJson(tuple)
    Ok(gsonString).as("application/json")
  }
  
  def getTrackWithMostRepeatingWords() = Action {
    var trackWithMostRepeatingWords: Track = null
    var minRatio = 0.0
    var minRepeatingWords = 0.0
    var minTotalWords = 0.0
    var wordMap = mutable.ListMap.empty[String, Int]
    var count = 0;

    musixMatchService.getTrackCharts(null, null, -1, 10).foreach {
      track =>
        
        var lyrics = musixMatchService.getLyrics(track.id).body
        if (lyrics != null) {
          var totalWordsMap = wordProcessor.getWordCounts(new StringBuilder(lyrics))
        	var totalWords = totalWordsMap.size
          var repeatingWordsMap = wordProcessor.getNonRepeatingWords(totalWordsMap)
          var nonRepeatingWords = repeatingWordsMap.size
          var ratio = nonRepeatingWords.doubleValue() / totalWords.doubleValue() * 100
          
          if(count == 0) {
              minTotalWords = totalWords
              minRepeatingWords = nonRepeatingWords
              minRatio = BigDecimal(ratio).setScale(2, BigDecimal.RoundingMode.HALF_UP).doubleValue()
              trackWithMostRepeatingWords = track
              count = count+1
          }
          else {
            if (ratio < minRatio) {
              minTotalWords = totalWords
              minRepeatingWords = nonRepeatingWords
              minRatio = BigDecimal(ratio).setScale(2, BigDecimal.RoundingMode.HALF_UP).doubleValue()
              trackWithMostRepeatingWords = track
            }
          }
        }
    }
    val tuple = new Tuple5(trackWithMostRepeatingWords.name, trackWithMostRepeatingWords.artistName, minTotalWords, minRepeatingWords, minRatio)
    val gsonString: String = new Gson().toJson(tuple)
    Ok(gsonString).as("application/json")
  
  }

}