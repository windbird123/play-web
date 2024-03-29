package com.github.windbird.playweb.component
import com.github.windbird.playweb.Util
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.{HTMLDivElement, HTMLSelectElement}

object Select {
  val selectedVar: Var[String] = Var("1")

  def selectBox(opts: Seq[(String, String)]): ReactiveHtmlElement[HTMLSelectElement] =
    select(
      cls := "form-select",
      opts.map {
        case (name, v) => option(value := v, name)
      }.toList
    )

  val selectStep: ReactiveHtmlElement[HTMLSelectElement] =
    selectBox(List("One" -> "1", "Two" -> "2", "Three" -> "3")).amend(onChange.mapToValue --> selectedVar)

  def demo: ReactiveHtmlElement[HTMLDivElement]          =
    div(
      Util.summary("Select", Some("https://getbootstrap.com/docs/5.3/forms/select/")),
      selectStep,
      child.text <-- selectedVar.signal
    )
}
