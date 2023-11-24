package com.github.windbird.playweb.component
import com.raquo.laminar.api.L._

case class SpinnerButton(name: String) extends Component {
  private val spinner: HtmlElement = span(cls := "spinner-border spinner-border-sm", hidden := true)

  override def body: HtmlElement =
    button(
      typ := "button",
      cls := "btn btn-success",
      spinner,
      name
    )

  def stop(): Unit =
    spinner.amend(hidden := true)

  def run(): Unit =
    spinner.amend(hidden := false)
}
