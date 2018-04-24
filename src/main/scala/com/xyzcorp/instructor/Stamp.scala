package com.xyzcorp.instructor

case class Stamp (name:String, year:Int) {
  /*This space is what to do with primary constructor*/


  require(!name.isEmpty, "Name cannot be empty")

//  def copy(name:String = this.name, year:Int = this.year):Stamp = {
//     new Stamp(name, year)
//  }
//
//  override def toString: String = s"Stamp: $name, $year"
//
//  override def equals(obj: scala.Any): Boolean = {
//    if (!obj.isInstanceOf[Stamp]) false
//    else {
//      val other = obj.asInstanceOf[Stamp]
//      this.name == other.name && this.year == other.year
//    }
//  }
//
//  override def hashCode(): Int =
//    (this.name.hashCode + this.year.hashCode()) % 159
}
