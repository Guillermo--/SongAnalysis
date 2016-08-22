package services

import musixmatch._
import scala.collection._
import models._
import scala.collection.mutable.ArrayBuffer


class WordProcessor {

  private def musixMatchService: MusixMatchClient = {
    new MusixMatchClient()
  }

  def getWordCounts(dirtyLyrics: StringBuilder) = {
    val wordCounts = mutable.Map.empty[String, Int].withDefaultValue(0)
    cleanLyrics(dirtyLyrics).split("\\s+").foreach { rawWord => wordCounts(rawWord) += 1 }
    wordCounts
  }
  
  def getSortedWordCounts(dirtyLyrics: StringBuilder) = {
    var wordCounts = getWordCounts(dirtyLyrics)
    var resultList = ArrayBuffer[Word]()
    var mapWithoutInterjections = wordCounts.filterKeys { 
      x => 
        !FilterWords.interjectionsList.contains(x) && 
        !FilterWords.numbersList.contains(x) &&
        !FilterWords.contractionsList.contains(x.toLowerCase()) &&
        !FilterWords.prepositionsList.contains(x)    
    }
    
    var sortedMap = mutable.ListMap(mapWithoutInterjections.toSeq.sortWith(_._2 > _._2):_*).take(35)
    
    for ((word, count) <- mapWithoutInterjections) resultList += new Word(word, count)
    
    resultList
  }

  def getAllDifferentWords(dirtyLyrics: StringBuilder) = {
    val allWords = mutable.Set.empty[String]
    cleanLyrics(dirtyLyrics).split("\\s+").foreach { rawWord => allWords.add(rawWord) }
    allWords
  }
  
  def getNonRepeatingWords(totalWordsMap : Map[String, Int]) = {
    totalWordsMap.filter(_._2 == 1)
  }
  
  def cleanLyrics(dirtyLyrics : StringBuilder) = {
    dirtyLyrics.toString().toLowerCase().replaceAll("""[\p{Punct}&&[^']]""", "").replaceAll("this lyrics is not for commercial use", "")
  }

}