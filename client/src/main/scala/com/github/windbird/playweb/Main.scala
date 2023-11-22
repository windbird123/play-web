package com.github.windbird.playweb

import com.github.windbird.playweb.ajax.NetworkRequest
import com.github.windbird.playweb.component.{Select, _}
import com.github.windbird.playweb.facade.PlotlyDemo
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import frontroute._
import org.scalajs.dom
import org.scalajs.dom.HTMLInputElement

object Main {
  def main(args: Array[String]): Unit = {
    val home = div(
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
      (path("blog") & maybeParam("url")) { urlOpt =>
        blog(urlOpt.getOrElse(""))
      }
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, app)
  }
}

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

  val inputGroup: HtmlElement = div(
    cls := "input-group mb-3",
    textInput.amend(
      value <-- urlVar
    ),
    button.amend(
      onClick --> { _ => urlVar.update(_ => textInput.ref.value) }
    )
  )

  def demo: HtmlElement =
    div(
      Util.summary("Ajax Request", None),
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
