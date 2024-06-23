package app

import scala.collection.mutable.ArrayBuffer

class Splitter(private var charsPerPage: Int) {
  def splitText(input: String): List[String] = {
    val blocks: ArrayBuffer[String] = ArrayBuffer[String]()

    var lastBlockLatestCharIdx: Option[Int] = Option.empty
    for (i <- 0 until input.length) {
      if (i == input.length - 1) { // end of text
        // append the block
        val block = if lastBlockLatestCharIdx.isEmpty 
          then
            input.substring(0, input.length)
          else
            input.substring(lastBlockLatestCharIdx.get + 1, input.length)
        blocks += block
      } else {
        if (input.charAt(i) == '\n') { // when paragraph ends
          val currLength = if lastBlockLatestCharIdx.isEmpty then i + 1 else i + 1 - lastBlockLatestCharIdx.get         
          if (currLength > charsPerPage) {
            // append the block
            val block = if lastBlockLatestCharIdx.isEmpty
              then
                input.substring(0, i + 1)
              else
                input.substring(lastBlockLatestCharIdx.get + 1, i + 1)
            blocks += block
            lastBlockLatestCharIdx = Some(i)
          }
        }
      }
    }
    return blocks.toList
  }
}