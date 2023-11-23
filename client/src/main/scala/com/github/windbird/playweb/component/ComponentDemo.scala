package com.github.windbird.playweb.component

import com.github.windbird.playweb.ajax.NetworkRequest
import com.github.windbird.playweb.component.Select
import com.github.windbird.playweb.facade.PlotlyDemo
import com.raquo.laminar.api.L._

object ComponentDemo {
  val demo = div(
    cls := "container-fluid mt-3",
    InputGroup.demo,
    Select.demo,
    Check.demo,
    Radio.demo,
    NetworkRequest.demo,
    PlotlyDemo.demo
  )
}
