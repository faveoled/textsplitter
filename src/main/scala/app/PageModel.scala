package app

import org.scalajs.dom

object PageModel {

  def setWordsPerPage(toSet: Int): Unit = {
    val wordsPerPage = dom.document.getElementById("wordsPerPage").asInstanceOf[dom.html.Input]
    wordsPerPage.value = toSet.toString
  }

  def getWordsPerPage(): Int = {
    val wordsPerPage = dom.document.getElementById("wordsPerPage").asInstanceOf[dom.html.Input]
    wordsPerPage.value.toInt   
  }
}
