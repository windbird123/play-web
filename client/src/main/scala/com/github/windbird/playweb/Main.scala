package com.github.windbird.playweb

import com.github.windbird.playweb.component._
import com.github.windbird.playweb.facade.FacadeDemo
import com.github.windbird.playweb.home.HomeDemo
import com.github.windbird.playweb.network.NetworkDemo
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import frontroute._
import org.scalajs.dom
import org.scalajs.dom.HTMLDivElement

object SideMenu {
  val Home: String      = "Home"
  val Component: String = "Component"
  val Facade: String    = "Facade"
  val Network: String   = "Network"
}

object Main {
  def network(url: String): HtmlElement = {
    if (url.nonEmpty) NetworkDemo.urlVar.update(_ => url)

    div(
      cls := "container mt-3",
      NetworkDemo.demo
    )
  }

  def sidebar(selectedMenu: Var[String]): ReactiveHtmlElement[HTMLDivElement] = {
    def navi(
      name: String,
      url: String,
      iconCls: String,
      selectedMenu: Var[String]
    ): ReactiveHtmlElement[HTMLDivElement] =
      div(
        cls := "flex-column nav nav-pills",
        a(
          cls := "nav-link",
          href := url,
          i(cls := iconCls),
          span(name)
        ).amend(
          cls.toggle("active") <-- selectedMenu.signal.map(_ == name),
          onClick.mapTo(name) --> selectedMenu
        )
      )

    div(
      cls := "sidebar",
      div(
        cls := "sidebar-header",
        img(
          src := "https://images.plot.ly/logo/new-branding/plotly-logomark.png",
          width := "3rem"
        ),
        h2("Sidebar")
      ),
      hr(),
      navi(SideMenu.Home, url = "/", iconCls = "fas fa-home me-2", selectedMenu),
      navi(SideMenu.Component, url = "component", iconCls = "fas fa-user me-2", selectedMenu),
      navi(SideMenu.Facade, url = "facade", iconCls = "fas fa-marker me-2", selectedMenu),
      navi(SideMenu.Network, url = "network", iconCls = "fas fa-envelope-open-text me-2", selectedMenu)
    )
  }

  def layout(content: HtmlElement, selectedMenu: Var[String]): HtmlElement =
    div(
      sidebar(selectedMenu),
      div(
        cls := "content",
        content
      )
    )

  def main(args: Array[String]): Unit = {
    val selectedMenu: Var[String] = Var(SideMenu.Home)

    val app = div(
      initRouting,                                                 // routing 을 위해 반드시 포함되어야 함
      LinkHandler.bind,                                            // anchor 에 대한 browser URL 업데이트를 위함
      pathEnd(layout(HomeDemo.demo, selectedMenu)),                // home
      path("component")(layout(ComponentDemo.demo, selectedMenu)), // component
      (path("network") & maybeParam("url")) { urlOpt => // network
        layout(network(urlOpt.getOrElse("")), selectedMenu)
      },
      path("facade")(layout(FacadeDemo.demo, selectedMenu))        // facade
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, app)
  }
}
