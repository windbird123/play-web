package com.github.windbird.playweb.home
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLAnchorElement, HTMLDivElement}

object HomeDemo {
  def make_anchor(description: String, link: String): ReactiveHtmlElement[HTMLAnchorElement] =
    a(description, href := link, target := "_blank", display := "block")

  val demo: ReactiveHtmlElement[HTMLDivElement] = div(
    cls := "container mt-3",
    h1("개요"),
    ul(
      li(make_anchor("개발 설명 블로그", "https://wefree.tistory.com/324")),
      li(make_anchor("소스 코드 github", "https://github.com/windbird123/play-web"))
    ),
    h1("메뉴"),
    ul(
      li(make_anchor("component", "/component")),
      span("Laminar 에서 bootstrap css 의 사용"),
      li(make_anchor("network", "/network")),
      span("URL path, query param 에 호출 정보 포함하기"),
      li(make_anchor("facade", "/facade")),
      span("javascripts library 를 scala.js facade 로 만들기")
    )
  )
}
