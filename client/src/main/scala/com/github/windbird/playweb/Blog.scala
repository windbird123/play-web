package com.github.windbird.playweb

import com.github.windbird.playweb.component._
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import frontroute.BrowserNavigation
import org.scalajs.dom.HTMLInputElement

import scala.scalajs.js.URIUtils

object Blog {
  val urlVar: Var[String] = Var("")

  val textInput: ReactiveHtmlElement[HTMLInputElement] =
    input(
      typ := "text",
      cls := "form-control",
      onMountFocus,
      placeholder := "request url"
    )

  val button: HtmlElement =
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

  val inputGroup: HtmlElement = div(
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

  def demo: HtmlElement =
    div(
      Util.summary("Routing Demo", None),
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
