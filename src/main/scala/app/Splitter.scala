package app

import scala.collection.mutable.ArrayBuffer

object Splitter {

  def countWords(inputArg: String): Int = {
    val input: String = inputArg.replace("'", "")
    var wordCount: Int = 0
    var word: Boolean = false
    val endOfLine: Int = input.length - 1
    for (i <- 0 until input.length) {
      // if the char is a letter, word = true.
      if (Character.isLetter(input.charAt(i)) && i != endOfLine) {
        word = true
      }
      else {
        if (!(Character.isLetter(input.charAt(i))) && word) {
          wordCount += 1
          word = false
        }
        else {
          if (Character.isLetter(input.charAt(i)) && i == endOfLine) {
            wordCount += 1
          }
        }
      }
    }
    return wordCount
  }
}

class Splitter(private var wordsPerPage: Int) {
  def splitText(inputArg: String): List[String] = {
    val input: String = inputArg.replaceAll("\r\n", "\n")
    val result: ArrayBuffer[String] = ArrayBuffer[String]()
    var lastParagraphEndIdx: Int = -(1)
    var prevParagraphsWordCount: Int = 0
    for (i <- 0 until input.length) {
      if (input.charAt(i) == '\n') { // when paragraph ends
        val fullParagraph: String = input.substring(lastParagraphEndIdx + 1, i)
        val wordCount: Int = Splitter.countWords(fullParagraph)
        if ((prevParagraphsWordCount + wordCount) > (wordsPerPage * (result.size + 1))) {
          prevParagraphsWordCount += wordCount
          result += fullParagraph
          lastParagraphEndIdx = i
        }
      } else {
        if (i == input.length - 1) { // end of text
          result += input.substring(lastParagraphEndIdx + 1)
        }
      }
    }
    return result.toList
  }
}