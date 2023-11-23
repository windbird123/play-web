package com.github.windbird.playweb.facade

import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object FacadeDemo {
  val demo: ReactiveHtmlElement[HTMLDivElement] = div(
    cls := "container-fluid mt-3",
    PlotlyDemo.demo
  )
}
