package app

import scala.scalajs.js
import scala.scalajs.js.typedarray.Int8Array
import scala.scalajs.js.typedarray.Uint8Array
import scala.scalajs.js.typedarray.ArrayBuffer
import org.scalajs.dom.Blob

import scalajs.js.JSConverters.iterableOnceConvertible2JSRichIterableOnce

object Interop {

  def toScalaArray(input: Uint8Array): Array[Byte] = {
    // Create a view as Int8 on the same underlying data.
    new Int8Array(input.buffer, input.byteOffset, input.length).toArray
  }

  def toScalaArray(input: ArrayBuffer): Array[Byte] = {
    new Int8Array(input).toArray
  }

  def toJsUint8Array(input: Array[Byte]): js.typedarray.Uint8Array =
    js.typedarray.Uint8Array.from(input.map(uInt8ToShort).toJSArray)

  def toJsArray(input: Array[Byte]): js.Array[Double] =
    input
      .map(b => uInt8ToShort(b).toDouble)
      .toJSArray

  private def uInt8ToShort(b: Byte): Short =
    (b & 0xFF).toShort

  def toBlob(input: ArrayBuffer): Blob = {
    new Blob(js.Array(input))
  }
}
