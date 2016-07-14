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
    val wordCounts = getWordCounts(dirtyLyrics)
    var objectList = ArrayBuffer[Word]()
    var sortedMap = immutable.ListMap(wordCounts.toSeq.sortWith(_._2 > _._2):_*).take(50)
    
    for ((word, count) <- sortedMap) objectList += new Word(word, count)
    
    objectList
  }

  def getAllDifferentWords(dirtyLyrics: StringBuilder) = {
    val allWords = mutable.Set.empty[String]
    cleanLyrics(dirtyLyrics).split("\\s+").foreach { rawWord => allWords.add(rawWord) }
    allWords
  }
  
  def getNonRepeatingWords(dirtyLyrics : StringBuilder) = {
    getWordCounts(dirtyLyrics).filter(_._2 == 1)
  }
  
  def getRepeatingWords(dirtyLyrics : StringBuilder) = {
    var map = getWordCounts(dirtyLyrics).filter(_._2 > 1)
    mutable.ListMap(map.toSeq.sortWith(_._2 > _._2):_*)
  }
  
  def cleanLyrics(dirtyLyrics : StringBuilder) = {
    dirtyLyrics.toString().toLowerCase().replaceAll("""[\p{Punct}&&[^']]""", "").replaceAll("this lyrics is not for commercial use", "")
  }

}