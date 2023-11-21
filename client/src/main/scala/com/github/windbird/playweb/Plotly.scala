package com.github.windbird.playweb

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
