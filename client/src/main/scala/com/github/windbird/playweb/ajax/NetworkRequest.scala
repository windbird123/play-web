package com.github.windbird.playweb.ajax
import com.github.windbird.playweb.component.Util
import com.raquo.laminar.api.L._
import org.scalajs.dom.MouseEvent

object NetworkRequest {
  val urlVar: Var[String]      = Var("https://api.zippopotam.us/us/90210")
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
      div("another: https://jsonplaceholder.typicode.com/todos/2", cls:="mb-1"),
      inputGroup,
      div(child.text <-- responseVar.signal)
    )

}
