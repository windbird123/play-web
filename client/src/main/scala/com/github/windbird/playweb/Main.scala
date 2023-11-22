package com.github.windbird.playweb

import com.github.windbird.playweb.ajax.NetworkRequest
import com.github.windbird.playweb.component.{Check, InputGroup, Radio, Select}
import com.github.windbird.playweb.facade.PlotlyDemo
import com.raquo.laminar.api.L._
import org.scalajs.dom

object Main {
  def main(args: Array[String]): Unit = {

    val content = div(
      cls := "container-fluid mt-3",
      InputGroup.demo,
      Select.demo,
      Check.demo,
      Radio.demo,
      NetworkRequest.demo,
      PlotlyDemo.demo
    )

    val containerNode = dom.document.getElementById("main_content")
    render(containerNode, content)
  }
}
