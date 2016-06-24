package services

import musixmatch._
import scala.collection._

class WordCounter {

  private def musixMatchService: MusixMatchClient = {
    new MusixMatchClient()
  }

  def getWordCounts(dirtyLyrics: StringBuilder) = {
    val wordCounts = mutable.Map.empty[String, Int].withDefaultValue(0)
    cleanLyrics(dirtyLyrics).split("\\s+").foreach { rawWord => wordCounts(rawWord) += 1 }
    wordCounts
  }

  def getAllWordsSet(dirtyLyrics: StringBuilder) = {
    val allWords = mutable.Set.empty[String]
    cleanLyrics(dirtyLyrics).split("\\s+").foreach { rawWord => allWords.add(rawWord) }
    allWords
  }
  
  def getSingleInstanceWords(dirtyLyrics : StringBuilder) = {
    getWordCounts(dirtyLyrics).filter(_._2 == 1)
  }
  
  def getMultipleInstanceWords(dirtyLyrics : StringBuilder) = {
    getWordCounts(dirtyLyrics).filter(_._2 > 1)
  }
  
  def cleanLyrics(dirtyLyrics : StringBuilder) = {
    dirtyLyrics.toString().toLowerCase().replaceAll("""[\p{Punct}&&[^']]""", "").replaceAll("this lyrics is not for commercial use", "")
  }

}