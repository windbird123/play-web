package com.github.windbird.playweb

import com.raquo.laminar.api.L._
import org.scalajs.dom
import scala.scalajs.js

object Main {
  def main(args: Array[String]): Unit = {
    val selected = Var("NoWay")

    val content = div(
      onMountCallback(ctx =>
        js.Dynamic.global.Plotly.newPlot(
          ctx.thisNode.ref,
          js.Array(
            js.Dictionary(
              "x" -> js.Array("giraffes", "orangutans", "monkeys"),
              "y" -> js.Array(20, 14, 23),
              "type" -> "bar"
            )
          )
        )
      )
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, content)
  }
}
