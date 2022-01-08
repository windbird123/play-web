package com.github.windbird.playweb
import com.raquo.laminar.api.L._
import org.scalajs.dom
import org.scalajs.dom.MouseEvent

object Main {
  def main(args: Array[String]): Unit = {
    val totalVar  = Var(0)
    val incButton = button("+", onClick --> totalVar.updater((current, _: MouseEvent) => current + 1))
    val decButton = button("-", onClick --> totalVar.updater((current, _: MouseEvent) => current - 1))

    val content = div(
      incButton,
      decButton,
      br(),
      label("total: "),
      span(
        child.text <-- totalVar.signal.map(_.toString)
      )
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, content)
  }
}
