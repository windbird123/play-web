package com.github.windbird.playweb

import com.raquo.laminar.api.L._
import org.scalajs.dom

object Main {

  def main(args: Array[String]): Unit = {
    val nameVar = Var(initial = "world")

    val rootElement = div(
      label("Your name: "),
      input(
        onMountFocus,
        placeholder := "Enter your name here",
        inContext(thisNode => onInput.map(_ => thisNode.ref.value) --> nameVar)
      ),
      span(
        "Hello, ",
        child.text <-- nameVar.signal.map(_.toUpperCase)
      )
    )

    def dangerButton =
      button(
        "Danger",
        cls := "button is-danger"
      )

    val bulmaElement = section(
      cls := "section",
      div(
        cls := "container",
        h1(
          "Hello world",
          cls := "title"
        ),
        p(
          "hi",
          cls := "subtitle"
        )
      ),
      dangerButton
    )

    // In most other examples, containerNode will be set to this behind the scenes
    val containerNode = dom.document.getElementById("main_content")

//    render(containerNode, rootElement)
    render(containerNode, bulmaElement)
  }
}
