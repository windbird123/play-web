package com.github.windbird.playweb

import com.raquo.laminar.api.L._
import org.scalajs.dom

object Main {
  def main(args: Array[String]): Unit = {
    val selected = Var("NoWay")

    val content = div(
      cls := "dropdown",
      button(
        tpe := "button",
        cls := "btn btn-primary dropdown-toggle",
        dataAttr("bs-toggle") := "dropdown",
        "Dropdown button"
      ),
      ul(
        cls := "dropdown-menu",
        li(
          a(cls := "dropdown-item", "Link 1", onClick.mapTo("LINK1") --> selected)
        ),
        li(
          a(cls := "dropdown-item", "Link 2", onClick.mapTo("LINK2") --> selected)
        ),
        li(
          a(cls := "dropdown-item", "Link 3", onClick.mapTo("LINK3") --> selected)
        )
      ),
      div(
        child.text <-- selected.signal
      )
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, content)
  }
}
