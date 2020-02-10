package com.xyzcorp

/**
  * This is terrible code. Do not do this
  *
  */
class ScenicParkTerrible {
  private var _name: String = _ //Yuck
  private var _closestCity: String = _ //Yuck
  private var _numberOfTrails: Int = _ //Yuck

  def this(name: String, closestCity: String, numberOfTrails: Int) = {
    this
    _name = name
    _closestCity = closestCity
    _numberOfTrails = numberOfTrails
  }

  def name: String = _name

  def closestCity: String = _closestCity

  def numberOfTrails: Int = _numberOfTrails

  def name_=(n: String): Unit = _name = n

  def closestCity_=(n: String): Unit = _closestCity = n

  def numberOfTrails_=(n: Int): Unit = _numberOfTrails = n
}


/**
  * Do this instead!
  * @param name is the name of the scenic park
  * @param closestCity closest major city
  * @param numberOfTrails number of trails in the park
  */
class ScenicPark (val name:String, val closestCity:String, val numberOfTrails:Int)


