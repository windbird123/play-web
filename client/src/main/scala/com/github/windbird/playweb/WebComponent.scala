// https://github.com/sherpal/LaminarSAPUI5Bindings/blob/master/web-components/src/main/scala/be/doeraene/webcomponents/WebComponent.scala

package com.github.windbird.playweb

import com.raquo.laminar.api.L._
import com.raquo.laminar.codecs.StringAsIsCodec
import com.raquo.laminar.nodes.ReactiveHtmlElement
import com.raquo.laminar.tags.HtmlTag
import org.scalajs.dom

trait WebComponent {
  val id: HtmlProp[String] = idAttr

  lazy val typ: HtmlAttr[String] = htmlAttr("type", StringAsIsCodec)
  lazy val cls: HtmlAttr[String] = htmlAttr("class", StringAsIsCodec)

  type Ref <: dom.HTMLElement
  private type ModFunction = this.type => Mod[ReactiveHtmlElement[Ref]]

  protected def tag: HtmlTag[Ref]

  final def of(mods: ModFunction*): HtmlElement = tag(mods.map(_(this)): _*)
}
