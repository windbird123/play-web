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

  def button: ReactiveHtmlElement[HTMLInputElement] =
    input(
      typ := "button",
      cls := "btn btn-primary",
      defaultValue := "Submit"
    )

  val inputGroup: ReactiveHtmlElement[HTMLDivElement] = div(
    cls := "input-group mb-3",
    span(cls := "input-group-text", "@"),
    textInput.amend(
      value <-- titleVar,
      onInput.mapToValue --> titleVar
    ),
    button.amend(
      onClick --> { _ => submitVar.update(_ => titleVar.now()) }
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
