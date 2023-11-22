package com.github.windbird.playweb.component
import com.raquo.laminar.api.L._

object Check {
  val checkedVar: Var[Boolean] = Var(false)

  def checkInput(name: String): HtmlElement =
    div(
      cls := "form-check",
      input(
        typ := "checkbox",
        cls := "form-check-input"
      ),
      label(
        name,
        cls := "form-check-label"
      )
    )

  val checkBox: HtmlElement = checkInput("my boolean checkbox").amend(onInput.mapToChecked --> checkedVar)

  def demo: HtmlElement =
    div(
      Util.summary("Check", Some("https://getbootstrap.com/docs/5.3/forms/checks-radios/")),
      checkBox,
      child.text <-- checkedVar.signal.map(_.toString)
    )
}
