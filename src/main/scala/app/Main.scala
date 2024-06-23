package app

import app.StringExtensions.substringBeforeLast
import org.scalajs.dom
import org.scalajs.dom.Blob
import org.scalajs.dom.HTMLAnchorElement
import org.scalajs.dom.HTMLLinkElement
import org.scalajs.dom.URL
import org.scalajs.dom.document
import org.scalajs.dom.raw.UIEvent
import org.scalajs.dom.window
import org.scalajs.dom.window.alert
import typings.jszip.jszipStrings.arraybuffer
import typings.jszip.jszipStrings.blob
import typings.jszip.mod.JSZipGeneratorOptions
import typings.jszip.mod as JSZip

import java.nio.charset.StandardCharsets
import scala.scalajs.js.typedarray.ArrayBuffer
import scala.util.Failure
import scala.util.Success
import org.scalajs.dom.HTMLButtonElement
import scala.concurrent.Future

implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

object Main {

  def generateZip(content: String): Future[Blob] = {
    val splitter = Splitter(PageModel.getWordsPerPage())
    val pieces = splitter.splitText(content)
    val zip = JSZip()
    for (i <- 0 until pieces.length) {
      val pieceBytes = bomFileBytes(pieces(i))
      zip.file(s"chapter${paddedInt(i + 1)}.txt", Interop.toJsUint8Array(pieceBytes))
    }
    val opts = JSZipGeneratorOptions[blob]()
    opts.setType(typings.jszip.jszipStrings.blob)
    zip.generateAsync_blob(opts).toFuture
  }

  def registerFileProcessor(): Unit = {
    //SelfTest.runTests()
    PageModel.setWordsPerPage(7000)
  }

  def onDownloadButtonClicked(): Unit = {
    val fileInput = dom.document.getElementById("srcFile").asInstanceOf[dom.html.Input]
    val reader = new dom.FileReader()
    if (fileInput.files.length == 0) {
      alert("Error: no files chosen")
      return
    }
    reader.readAsText(fileInput.files(0), PageModel.getSrcEncoding().canonicalStr())
    reader.onload = event => {
      val blobFuture = generateZip(reader.result.asInstanceOf[String])
      blobFuture.onComplete {
        case Success(sblob) => 
          FileDownloader.download(newName(fileInput.files(0).name), sblob)
        case Failure(t) => 
          t.printStackTrace()
          alert("An error has occurred: " + t.getMessage)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    window.addEventListener("load", (e) => registerFileProcessor(), false);
    val downloadButton = document.getElementById("downloadButton").asInstanceOf[HTMLButtonElement]
    downloadButton.onclick = (_) => onDownloadButtonClicked()
  }

  private def paddedInt(input: Int): String = {
    if (input < 10) {
      return "0" + input.toString()
    }
    return input.toString()
  }

  private def newName(oldName: String): String = {
    if (oldName.contains(".")) {
      return oldName.substringBeforeLast(".") + ".zip"
    } else {
      return oldName + ".zip"
    }
  }

  private def bomFileBytes(content: String): Array[Byte] = {
    val result = scala.collection.mutable.ArrayBuffer[Byte]()
    result += 0xEF.toByte
    result += 0xBB.toByte
    result += 0xBF.toByte
    result ++= content.getBytes(StandardCharsets.UTF_8)
    result.toArray
  }

}
