package com.github.windbird.playweb

import com.github.windbird.playweb.ajax.NetworkRequest
import com.github.windbird.playweb.component.{Select, _}
import com.github.windbird.playweb.facade.PlotlyDemo
import com.raquo.laminar.api.L._
import frontroute._
import org.scalajs.dom

object Main {
  def main(args: Array[String]): Unit = {
    val home = div(
      cls := "container mt-3",
      a("component", href := "/component", target := "_blank", display := "block"),
      a("route", href := "/blog", target := "_blank", display := "block")
    )

    val component = div(
      cls := "container-fluid mt-3",
      InputGroup.demo,
      Select.demo,
      Check.demo,
      Radio.demo,
      NetworkRequest.demo,
      PlotlyDemo.demo
    )

    def blog(url: String): HtmlElement = {
      if (url.nonEmpty)
        Blog.urlVar.update(_ => url)

      div(
        cls := "container mt-3",
        Blog.demo
      )
    }

    val app = div(
      initRouting,
      pathEnd(home),
      path("component")(component),
      (path("blog") & maybeParam("url")) { urlOpt =>
        blog(urlOpt.getOrElse(""))
      }
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, app)
  }
}
