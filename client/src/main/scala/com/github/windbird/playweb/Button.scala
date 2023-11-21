package com.github.windbird.playweb

import com.raquo.laminar.api.L._
import com.raquo.laminar.tags.HtmlTag
import org.scalajs.dom

import scala.scalajs.js

object Button extends WebComponent {
  @js.native
  trait RawElement extends js.Object {}

  type Ref = dom.html.Element with RawElement

  override protected def tag: HtmlTag[Ref] = htmlTag("button")
}
