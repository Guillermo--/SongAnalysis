package services

import musixmatch._
import scala.collection._


class WordCounter {

  private def musixMatchService: MusixMatchClient = {
    new MusixMatchClient()
  }

  def getWordCounts(lyrics: String) = {
    val counts = mutable.Map.empty[String, Int].withDefaultValue(0)
    for (rawWord <- lyrics.split("\\s+")) {
      counts(rawWord) += 1
    }
    counts
  }

}