package com.github.windbird.playweb

import com.raquo.laminar.api.L._

case class InputText(textVar: Var[String], name: String, description: String) extends Component {
  override def body: HtmlElement =
    div(
      label(name),
      cls := "ui input",
      input(
        typ := "text",
        onMountFocus,
        value <-- textVar,
        onInput.mapToValue --> textVar,
        placeholder := description
      )
    )
}
