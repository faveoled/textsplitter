package app

import org.scalajs.dom
import org.scalajs.dom.HTMLSelectElement

object PageModel {

  def setWordsPerPage(toSet: Int): Unit = {
    val wordsPerPage = dom.document.getElementById("wordsPerPage").asInstanceOf[dom.html.Input]
    wordsPerPage.value = toSet.toString
  }

  def getWordsPerPage(): Int = {
    val wordsPerPage = dom.document.getElementById("wordsPerPage").asInstanceOf[dom.html.Input]
    wordsPerPage.value.toInt   
  }

  def getSrcEncoding(): SourceEncoding = {
    val elem = dom.document.getElementById("srcEncoding").asInstanceOf[HTMLSelectElement]
    elem.value match {
      case "utf8" =>
        SourceEncoding.UTF_8
      case "windows1251" =>
        SourceEncoding.WINDOWS_1251
      case _ =>
        throw new Exception("Bug")
    }
  }
}
