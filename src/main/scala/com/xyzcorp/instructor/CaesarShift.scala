package com.xyzcorp.instructor

class CaesarShift(shift: Int) {
  def encode(str: String):String = {
    if (str.isEmpty) str
    else "" + (str.charAt(0) + shift).toChar
  }
}
