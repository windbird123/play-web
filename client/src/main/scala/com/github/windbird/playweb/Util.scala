package com.github.windbird.playweb

import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object Util {
  def summary(title: String, urlOpt: Option[String]): ReactiveHtmlElement[HTMLDivElement] =
    div(
      cls := "mt-5 mb-2",
      h1(title),
      urlOpt.map { url =>
        a(href := url, target := "_blank", "bootstrap")
      }.getOrElse(emptyMod)
    )
}
