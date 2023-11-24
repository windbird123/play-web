package com.github.windbird.playweb.facade

import com.github.windbird.playweb.Util
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.HTMLDivElement

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
@JSGlobal
object Sortable extends js.Object {
  def create(el: dom.HTMLElement, options: js.UndefOr[js.Any] = js.undefined): Unit = js.native
}

@js.native
trait SortableEvent extends js.Object {
  def to: js.Any    = js.native
  def from: js.Any  = js.native
  def oldIndex: Int = js.native
  def newIndex: Int = js.native
}

class SortedIndex(n: Int) {
  var state: List[Int] = List((0 until n): _*)

  private def insert[T](list: List[T], i: Int, value: T): List[T] =
    list.take(i) ++ List(value) ++ list.drop(i)

  private def remove[T](list: List[T], i: Int): List[T] =
    list.take(i) ++ list.drop(i + 1)

  def move(targetIndex: Int, toIndex: Int): Unit = {
    val targetValue = state(targetIndex)

    state = if (targetIndex <= toIndex) {
      val inserted = insert(state, toIndex + 1, targetValue)
      remove(inserted, targetIndex)
    } else {
      val inserted = insert(state, toIndex, targetValue)
      remove(inserted, targetIndex + 1)
    }
  }
}

object SortableDemo {
  val targetList: ReactiveHtmlElement[HTMLDivElement] = div(
    cls := "list-group",
    div(cls := "list-group-item list-group-item-primary", "0:A"),
    div(cls := "list-group-item list-group-item-secondary", "1:B"),
    div(cls := "list-group-item list-group-item-success", "2:C"),
    div(cls := "list-group-item list-group-item-danger", "3:D"),
    div(cls := "list-group-item list-group-item-info", "4:E")
  )

  // 주의: item 개수를 정확히 맞춰야 한다.
  // 5=targetList 의 item 개수
  val sortedIndex: SortedIndex = new SortedIndex(5)
  val state: Var[List[Int]]    = Var(sortedIndex.state)

  val callbackFun: js.Function1[SortableEvent, Unit] = (e: SortableEvent) => {
    sortedIndex.move(e.oldIndex, e.newIndex)
    state.update(_ => sortedIndex.state)
  }

  val demo: ReactiveHtmlElement[HTMLDivElement] = div(
    Util.summary("Sortable.js Drag&Drop", None),
    a("sortable.js", href := "https://sortablejs.github.io/Sortable/", target := "_blank", display := "block"),
    h6("아래 항목 drag & drop 가능"),
    hr(),
    child.text <-- state.signal.map(sorted => "Sorted: " + sorted.mkString(" -> ")),
    targetList,
    onMountCallback(_ =>
      Sortable.create(
        SortableDemo.targetList.ref,
        js.Dictionary(
          "animation" -> 150,
          "onEnd"     -> callbackFun
        )
      )
    )
  )
}
