package com.xyzcorp

class Box[A](val contents:A) {
  def map[B](f: A => B):Box[B] = new Box(f(contents))
}