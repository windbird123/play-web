package com.github.windbird.playweb.component

import com.raquo.laminar.api.L._

import scala.language.implicitConversions

trait Component  {
  def body: HtmlElement
}
object Component {
  implicit def component2HtmlElement(component: Component): HtmlElement = component.body
}
