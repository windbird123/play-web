package com.github.windbird.playweb.home
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object HomeDemo {
  val demo: ReactiveHtmlElement[HTMLDivElement] = div(
    cls := "container mt-3",
    a("component", href := "/component", target := "_blank", display := "block"),
    a("route", href := "/blog", target := "_blank", display := "block"),
    a("facade", href := "/facade", target := "_blank", display := "block")
  )
}
