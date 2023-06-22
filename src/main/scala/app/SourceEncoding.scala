package app

import java.nio.charset.StandardCharsets

enum SourceEncoding {
  case UTF_8, WINDOWS_1251

  def canonicalStr(): String = {
    this match {
      case UTF_8 => 
        "utf-8"
      case WINDOWS_1251 =>
        "windows-1251"
    }
  }
}
