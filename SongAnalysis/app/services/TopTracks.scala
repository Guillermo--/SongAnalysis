package services
import play.api.libs.json._

class TopTracks {
  

  def getTopTracks(resultLimit: Int): JsValue = {
    return Json.toJson(resultLimit)
  }



}