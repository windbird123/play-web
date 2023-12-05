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

case class Menu(name: String, path: String, iconCls: String)
object Menu {
  val Home: Menu      = Menu("Home", "/", "fas fa-home me-2")
  val Component: Menu = Menu("Component", "component", "fas fa-user me-2")
  val Facade: Menu    = Menu("Facade", "facade", "fas fa-marker me-2")
  val Network: Menu   = Menu("Network", "network", "fas fa-envelope-open-text me-2")
}

class Layout {
  private val selectedMenu: Var[Menu] = Var(Menu.Home)

  private def navi(menu: Menu): ReactiveHtmlElement[HTMLDivElement] =
    div(
      cls := "flex-column nav nav-pills",
      a(
        cls := "nav-link",
        href := menu.path,
        i(cls := menu.iconCls),
        span(menu.name)
      ).amend(
        cls.toggle("active") <-- selectedMenu.signal.map(_ == menu),
        onClick.mapTo(menu) --> selectedMenu
      )
    )

  private def sidebar(): ReactiveHtmlElement[HTMLDivElement] =
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
      navi(Menu.Home),
      navi(Menu.Component),
      navi(Menu.Facade),
      navi(Menu.Network)
    )

  def build(menu: Menu, content: HtmlElement): HtmlElement =
    div(
      EventStream.fromValue(menu) --> selectedMenu,
      sidebar(),
      div(
        cls := "content",
        content
      )
    )
}

object Main {
  def network(url: String): HtmlElement = {
    if (url.nonEmpty) NetworkDemo.urlVar.update(_ => url)

    div(
      cls := "container mt-3",
      NetworkDemo.demo
    )
  }

  def main(args: Array[String]): Unit = {
    val layout: Layout = new Layout()

    // routing 의 HtmlElement 는
    // passed by-name, HtmlElement will not be evaluated until it's needed
    val app = div(
      initRouting,      // routing 을 위해 반드시 포함되어야 함
      LinkHandler.bind, // anchor 에 대한 browser URL 업데이트를 위함 (https://frontroute.dev/v/0.18.x/getting-started/links-and-navigation)
      pathEnd { // home
        layout.build(Menu.Home, HomeDemo.demo)
      },
      path("component") {                                // component
        layout.build(Menu.Component, ComponentDemo.demo)
      },
      (path("network") & maybeParam("url")) { urlOpt => // network
        layout.build(Menu.Network, network(urlOpt.getOrElse("")))
      },
      path("facade") { // facade
        layout.build(Menu.Facade, FacadeDemo.demo)
      }
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, app)
  }
}
