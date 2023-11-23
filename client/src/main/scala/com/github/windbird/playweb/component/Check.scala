package com.github.windbird.playweb.component
import com.github.windbird.playweb.Util
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object Check {
  val checkedVar: Var[Boolean] = Var(false)

  def checkInput(name: String): ReactiveHtmlElement[HTMLDivElement] =
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

  val checkBox: ReactiveHtmlElement[HTMLDivElement] =
    checkInput("my boolean checkbox").amend(onInput.mapToChecked --> checkedVar)

  def demo: ReactiveHtmlElement[HTMLDivElement] =
    div(
      Util.summary("Check", Some("https://getbootstrap.com/docs/5.3/forms/checks-radios/")),
      checkBox,
      child.text <-- checkedVar.signal.map(_.toString)
    )
}
