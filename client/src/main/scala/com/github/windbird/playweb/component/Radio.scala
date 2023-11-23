package com.github.windbird.playweb.component

import com.raquo.laminar.api.L._

object Radio {
  val radioVar: Var[String] = Var("2")

  def radioBox(opts: Seq[(String, String)]): HtmlElement =
    div(
      opts.map {
        case (name, value) =>
          div(
            cls := "form-check",
            input(
              typ := "radio",
              cls := "form-check-input",
              checked <-- radioVar.signal.map(_ == value),
              onChange.mapTo(value) --> radioVar
            ),
            label(
              name,
              cls := "form-check-label"
            )
          )
      }.toList
    )

  def demo: HtmlElement =
    div(
      Util.summary("Radio", Some("https://getbootstrap.com/docs/5.3/forms/checks-radios/")),
      radioBox(List("One" -> "1", "Two" -> "2", "Three" -> "3")),
      child.text <-- radioVar.signal
    )
}
