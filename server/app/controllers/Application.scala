package controllers

import com.github.windbird.playweb.shared.SharedMessages
import play.api.mvc._

import javax.inject._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index("My Title"))
  }
}
