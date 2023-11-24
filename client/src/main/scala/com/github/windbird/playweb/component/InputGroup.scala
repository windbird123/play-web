package com.github.windbird.playweb.component

import com.github.windbird.playweb.Util
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLDivElement, HTMLInputElement}

object InputGroup {
  val titleVar: Var[String]  = Var("bar chart")
  val submitVar: Var[String] = Var("")

  def textInput: ReactiveHtmlElement[HTMLInputElement] =
    input(
      typ := "text",
      cls := "form-control",
      onMountFocus,
      placeholder := "title input"
    )

  val plainButton: ReactiveHtmlElement[HTMLInputElement] =
    input(
      typ := "button",
      cls := "btn btn-primary",
      defaultValue := "Submit"
    )

  val spinnerButton = SpinnerButton("SpinnerSubmit")

  val inputGroup: ReactiveHtmlElement[HTMLDivElement] = div(
    cls := "input-group mb-3",
    span(cls := "input-group-text", "@"),
    textInput.amend(
      value <-- titleVar,
      onInput.mapToValue --> titleVar
    ),
    plainButton.amend(
      onClick --> { _ => submitVar.update(_ => titleVar.now()) }
    ),
    spinnerButton.amend(
      onClick --> { _ => spinnerButton.run() },
      onClick.compose(_.delay(2000)) --> { _ => // event 를 지연시켜 2초 뒤에 결과가 노출 되도록 함
        submitVar.update(_ => titleVar.now())
        spinnerButton.stop()
      }
    )
  )

  def demo: ReactiveHtmlElement[HTMLDivElement] =
    div(
      Util.summary("TextInput, Button", Some("https://getbootstrap.com/docs/5.3/forms/input-group/")),
      inputGroup,
      div(child.text <-- titleVar.signal),
      div(child.text <-- submitVar.signal)
    )
}
