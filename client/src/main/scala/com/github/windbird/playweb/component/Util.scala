package com.github.windbird.playweb.component
import com.raquo.laminar.api.L._

object Util {
  def summary(title: String, urlOpt: Option[String]): HtmlElement =
    div(
      cls := "mt-5",
      h1(title),
      urlOpt.map { url =>
        a(href := url, target := "_blank", "bootstrap")
      }.getOrElse(emptyMod)
    )
}
