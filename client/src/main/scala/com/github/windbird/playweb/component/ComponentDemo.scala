package com.github.windbird.playweb.component

import com.github.windbird.playweb.ajax.NetworkRequest
import com.github.windbird.playweb.component.Select
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object ComponentDemo {
  val demo: ReactiveHtmlElement[HTMLDivElement] = div(
    cls := "container-fluid mt-3",
    InputGroup.demo,
    Select.demo,
    Check.demo,
    Radio.demo,
    NetworkRequest.demo
  )
}
