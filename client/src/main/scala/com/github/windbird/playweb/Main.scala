package com.github.windbird.playweb

import com.raquo.laminar.api.L._
import org.scalajs.dom

import scala.scalajs.js

object Main {
  def main(args: Array[String]): Unit = {
    val titleVar: Var[String]  = Var("bar chart")
    val submitVar: Var[String] = Var("")

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

    val inputGroup = div(
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

    val chart = div(
      onMountCallback(ctx =>
        Plotly.newPlot( // js.Dynamic.global.Plotly.newPlot(...) for dynamic
          ctx.thisNode.ref,
          js.Array(
            js.Dictionary(
              "x"    -> js.Array("giraffes", "orangutans", "monkeys"),
              "y"    -> js.Array(20, 14, 23),
              "type" -> "bar"
            )
          )
        )
      )
    )

    val content = div(
      cls := "container-fluid mt-3",
      inputGroup,
      div(child.text <-- titleVar.signal),
      div(child.text <-- submitVar.signal),
      chart
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, content)
  }
}
