package com.github.windbird.playweb

import com.raquo.airstream.web.AjaxEventStream
import com.raquo.laminar.api.L._
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax

object Main {
  def main(args: Array[String]): Unit = {
    val urlVar       = Var("https://api.zippopotam.us/us/90210")
    val otherButton  = button("other url", onClick.mapTo("https://jsonplaceholder.typicode.com/todos/2") --> urlVar)
    val submitButton = button("submit")

    val responseOnClick: EventStream[String] = for {
      _        <- submitButton.events(onClick)
      response <- AjaxEventStream.get(url = urlVar.now()).map(_.responseText)
      // response <- EventStream.fromFuture(Ajax.get(url = urlVar.now()).map(_.responseText))
    } yield response

    val content = div(
      input(
        typ := "text",
        width := "300px",
        controlled(value <-- urlVar.signal, onInput.mapToValue --> urlVar)
      ),
      otherButton,
      submitButton,
      br(),
      label("response: "),
      child.text <-- responseOnClick
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, content)
  }
}