package com.github.windbird.playweb.facade

import com.github.windbird.playweb.component.Util
import com.raquo.laminar.api.L._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal
object Plotly extends js.Object {
  def newPlot(
    graphDiv: js.Any,
    data: js.Array[js.Dictionary[js.Any]],
    layout: js.UndefOr[js.Dictionary[js.Any]] = js.undefined,
    config: js.UndefOr[js.Dictionary[js.Any]] = js.undefined
  ): Unit = js.native
}

object PlotlyDemo {
  val chart: HtmlElement = div(
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

  def demo: HtmlElement =
    div(
      Util.summary("Plotly Graph", None),
      chart
    )
}
