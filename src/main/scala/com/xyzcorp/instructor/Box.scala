package com.xyzcorp.instructor

case class Box[A](a:A) {
  def tupleItWith[B](b:B):Box[Tuple2[A, B]] = {
    //new Box(a -> b)
    new Box(Tuple2(a, b))
  }

  def map[B](f:A => B) = new Box(f(a))

  def flatMap[B](f: A => Box[B]) = new Box(f(a).a)
}
