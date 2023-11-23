package com.github.windbird.playweb

import com.github.windbird.playweb.blog.BlogDemo
import com.github.windbird.playweb.component._
import com.github.windbird.playweb.facade.FacadeDemo
import com.github.windbird.playweb.home.HomeDemo
import com.raquo.laminar.api.L._
import frontroute._
import org.scalajs.dom

object Main {
  def blog(url: String): HtmlElement = {
    if (url.nonEmpty) BlogDemo.urlVar.update(_ => url)

    div(
      cls := "container mt-3",
      BlogDemo.demo
    )
  }

  def main(args: Array[String]): Unit = {
    val app = div(
      initRouting,  // routing 을 위해 반드시 포함되어야 함
      pathEnd(HomeDemo.demo), // home
      path("component")(ComponentDemo.demo),  // component
      (path("blog") & maybeParam("url")) { urlOpt =>  // blog
        blog(urlOpt.getOrElse(""))
      },
      path("facade")(FacadeDemo.demo) // facade
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, app)
  }
}
