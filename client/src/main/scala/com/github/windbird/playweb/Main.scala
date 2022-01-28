package com.github.windbird.playweb

import com.raquo.laminar.api.L
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import com.raquo.waypoint._
import org.scalajs.dom
import org.scalajs.dom.html
import upickle.default._

class Radio(name: String, items: Seq[String], $defaultSelected: Signal[String]) {
  val selectedVar: Var[String]            = Var("")
  val elem: ReactiveHtmlElement[html.Div] = div(
    cls := "ui form",
    div(
      cls := "inline fields",
      label(name),
      items.map(item =>
        div(
          cls := "field",
          div(
            cls := "ui radio checkbox",
            input(
              typ := "radio",
              checked <-- selectedVar.signal.map(_ == item),
              $defaultSelected --> selectedVar,
              onChange.mapTo(item) --> selectedVar
            ),
            label(item)
          )
        )
      )
    )
  )
}

class TextInput(name: String, description: String, $initValue: Signal[String]) {
  val textVar: Var[String]                = Var("")
  val elem: ReactiveHtmlElement[html.Div] = div(
    label(name),
    cls := "ui input",
    input(
      typ := "text",
      onMountFocus,
      value <-- textVar,
      onInput.mapToValue --> textVar,
      $initValue --> textVar,
      placeholder := description
    )
  )
}

object Main {
  sealed trait Page
  case class SearchPage(engine: String, query: Option[String]) extends Page
  case object LoginPage                                        extends Page

  implicit val SearchPageRW: ReadWriter[SearchPage] = macroRW
  implicit val rw: ReadWriter[Page]                 = macroRW

  def main(args: Array[String]): Unit = {
    // path 까지 route 로 사용될 경우 Route.withQuery 사용
    val searchRoute = Route.onlyQuery[SearchPage, (String, Option[String])](
      encode = searchPage => (searchPage.engine, searchPage.query),
      decode = args => SearchPage(args._1, args._2),
      pattern = (root / "search" / endOfSegments) ? (param[String]("engine") & param[String]("query").?)
    )

    val loginRoute = Route.static(LoginPage, root / "login" / endOfSegments)

    val router = new Router[Page](
      routes = List(searchRoute, loginRoute),
      getPageTitle = (page: Page) => "my title",
      serializePage = page => write(page)(rw),
      deserializePage = pageStr => read(pageStr)(rw)
    )(
      $popStateEvent = L.windowEvents.onPopState,
      owner = L.unsafeWindowOwner
    )

    def renderSearchPage($searchPage: Signal[SearchPage]): Div = {
      val radio     = new Radio("Search Engine", List("naver", "google", "yahoo"), $searchPage.map(_.engine))
      val textInput = new TextInput("Search Query: ", "Enter query", $searchPage.map(_.query.getOrElse("")))

      div(
        h3("Search Page"),
        radio.elem,
        textInput.elem,
        br(),
        button(
          "Submit",
          onClick --> (_ => router.pushState(SearchPage(radio.selectedVar.now(), Some(textInput.textVar.now()))))
        ),
        div(
          hr(),
          div(
            "selected engine: ",
            child.text <-- $searchPage.map(_.engine)
          ),
          div(
            "query: ",
            child.text <-- $searchPage.map(_.query.getOrElse(""))
          )
        )
      )
    }

    def renderLoginPage: Div =
      div(
        h3("Login Page")
      )

    val splitter = SplitRender[Page, HtmlElement](router.$currentPage)
      .collectSignal[SearchPage]($searchPage => renderSearchPage($searchPage))
      .collectStatic(LoginPage)(renderLoginPage)

    val content: Div = div(
      h1("Routing App"),
      child <-- splitter.$view
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, content)
  }
}
