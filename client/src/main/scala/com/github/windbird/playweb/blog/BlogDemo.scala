package com.github.windbird.playweb.blog

import com.github.windbird.playweb.Util
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import frontroute.BrowserNavigation
import org.scalajs.dom.{HTMLDivElement, HTMLInputElement}

import scala.scalajs.js.URIUtils

object BlogDemo {
  val urlVar: Var[String] = Var("")

  val textInput: ReactiveHtmlElement[HTMLInputElement] =
    input(
      typ := "text",
      cls := "form-control",
      onMountFocus,
      placeholder := "request url"
    )

  val button: ReactiveHtmlElement[HTMLInputElement] =
    input(
      typ := "button",
      cls := "btn btn-primary",
      defaultValue := "Submit"
    )

  // Browser URL 관련 모든 Var 들 값을 확인해 업데이트 한다.
  def updateBrowserUrl(): Unit = {
    val requestUrl = urlVar.now()
    BrowserNavigation.pushState(url = s"/blog?url=${URIUtils.encodeURI(requestUrl)}")
  }

  val inputGroup: ReactiveHtmlElement[HTMLDivElement] = div(
    cls := "input-group mb-3",
    textInput.amend(
      value <-- urlVar
    ),
    button.amend(
      onClick --> { _ =>
        urlVar.update(_ => textInput.ref.value)
        updateBrowserUrl()
      }
    )
  )

  def demo: ReactiveHtmlElement[HTMLDivElement] =
    div(
      Util.summary("Routing Demo", None),
      p(
        cls := "lead",
        """https://wefree-scalajs.fly.dev/blog?url=https%3A%2F%2Fapi.zippopotam.us%2Fus%2F90210 처럼 요청 하면, input form 에
          |url param 으로 지정된 값이 채워지고, 요청된 결과까지 화면에 보여준다.
          |(자동으로 submit 버튼을 누른 것과 같은 효과)
          |""".stripMargin
      ),
      a(
        "request: https://api.zippopotam.us/us/90210",
        href := "/blog?url=https%3A%2F%2Fapi.zippopotam.us%2Fus%2F90210",
        display := "block"
      ),
      a(
        "request: https://jsonplaceholder.typicode.com/todos/2",
        href := "/blog?url=https%3A%2F%2Fjsonplaceholder.typicode.com%2Ftodos%2F2",
        display := "block"
      ),
      inputGroup,
      div(child.text <-- urlVar.signal.flatMap { url =>
        // https://laminar.dev/documentation#network-requests
        if (url.nonEmpty) FetchStream.get(url)
        else EventStream.fromValue("")
      })
    )
}
