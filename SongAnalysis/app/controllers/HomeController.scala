package controllers

import play.api.mvc.{ Action, Controller }
import play.api.http.MimeTypes

class HomeController extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

} 