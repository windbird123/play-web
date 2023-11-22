package com.github.windbird.playweb

import com.github.windbird.playweb.ajax.NetworkRequest
import com.github.windbird.playweb.component.{Check, InputGroup, Radio, Select, Util}
import com.github.windbird.playweb.facade.PlotlyDemo
import com.raquo.laminar.api.L._
import frontroute._
import org.scalajs.dom

object Main {
  def main(args: Array[String]): Unit = {
    val home = div(
      cls := "container-fluid mt-3",
      InputGroup.demo,
      Select.demo,
      Check.demo,
      Radio.demo,
      NetworkRequest.demo,
      PlotlyDemo.demo
    )

    def blog(url: String): HtmlElement = div(
      cls := "container mt-3",
      Blog.demo
    )

    val app = div(
      initRouting,
      pathEnd(home),
      (path("blog") & maybeParam("url")) { urlOpt =>
        blog(urlOpt.getOrElse(""))
      }
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, app)
  }
}


object Blog {
  val urlVar: Var[String] = Var("https://api.zippopotam.us/us/90210")
  val actionUrlVar: Var[String] = Var("")
  val responseVar: Var[String] = Var("")

  def textInput: HtmlElement =
    input(
      typ := "text",
      cls := "form-control",
      onMountFocus,
      placeholder := "title input"
    )

  def button: HtmlElement =
    input(
      typ := "button",
      cls := "btn btn-primary",
      defaultValue := "Submit"
    )

  val inputGroup: HtmlElement = div(
    cls := "input-group mb-3",
    textInput.amend(
      value <-- urlVar,
      onInput.mapToValue --> urlVar
    ),
    button.amend(
      // https://laminar.dev/documentation#network-requests
      onClick.flatMap(_ => FetchStream.get(urlVar.now())) --> responseVar
    )
  )

  def demo: HtmlElement =
    div(
      Util.summary("Ajax Request", None),
      div("another: https://jsonplaceholder.typicode.com/todos/2", cls := "mb-1"),
      inputGroup,
      div(child.text <-- responseVar.signal)
    )
}
