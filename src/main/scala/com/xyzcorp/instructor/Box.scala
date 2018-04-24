package com.xyzcorp.instructor

class Box[A](val a:A) {
  def tupleItWith[B](b:B):Box[Tuple2[A, B]] = {
    //new Box(a -> b)
    new Box(Tuple2(a, b))
  }
}
