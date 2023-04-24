package com.github.windbird.playweb
import com.raquo.laminar.api.L.{Owner => _, _}

import scala.language.implicitConversions

trait Component {
  def body: HtmlElement
}

object Component {
  implicit def component2HtmlElement(component: Component): HtmlElement =
    component.body
}
