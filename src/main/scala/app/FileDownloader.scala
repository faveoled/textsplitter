package app

import org.scalajs.dom.document
import org.scalajs.dom.window
import org.scalajs.dom.URL
import org.scalajs.dom.Blob

object FileDownloader {
  def download(filename: String, contents: Blob): Unit = {
    val elem = window.document.createElement("a").asInstanceOf[HTMLAnchorElementMine];
    elem.href = URL.createObjectURL(contents);
    elem.download = filename;        
    document.body.appendChild(elem);
    elem.click();        
    document.body.removeChild(elem);
  }
}
