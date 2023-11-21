package com.github.windbird.playweb

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal
import scala.scalajs.js.|

@js.native
@JSGlobal
object Plotly extends js.Object {
  def newPlot(
    graphDiv: String | js.Object,
    data: js.Array[js.Object],
    layout: js.UndefOr[js.Object] = js.undefined,
    config: js.UndefOr[js.Object] = js.undefined
  ): Unit = js.native
}
