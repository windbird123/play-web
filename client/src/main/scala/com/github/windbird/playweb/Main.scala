package com.github.windbird.playweb

import com.raquo.laminar.api.L._
import org.scalajs.dom

import scala.scalajs.js

object Main {
  def main(args: Array[String]): Unit = {
    val titleVar: Var[String] = Var("bar chart")
    val titleInput: Component = InputText(textVar = titleVar, name = "My Title", description = "title input")

    val chart = div(
      onMountCallback(ctx =>
        Plotly.newPlot(
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

//    val chart = div(
//      onMountCallback(ctx =>
//        js.Dynamic.global.Plotly.newPlot(
//          ctx.thisNode.ref,
//          js.Array(
//            js.Dictionary(
//              "x" -> js.Array("giraffes", "orangutans", "monkeys"),
//              "y" -> js.Array(20, 14, 23),
//              "type" -> "bar"
//            )
//          )
//        )
//      )
//    )

//    val chart = div()
//    js.Dynamic.global.Plotly.newPlot(
//      chart.ref,
//      js.Array(
//        js.Dictionary(
//          "x"    -> js.Array("giraffes", "orangutans", "monkeys"),
//          "y"    -> js.Array(20, 14, 23),
//          "type" -> "bar"
//        )
//      )
//    )

    val button = Button.of(
      _ => "WebComponent Button",
      _ => onClick --> { _ => dom.window.alert("Clicked") },
      _.typ := "button",
      _.cls := "btn btn-primary"
    )

    val content = div(
      cls := "container-fluid mt-3",
      button,
      titleInput,
      child.text <-- titleVar.signal,
      chart
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, content)
  }
}
