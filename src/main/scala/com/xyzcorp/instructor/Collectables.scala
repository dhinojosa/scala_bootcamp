package com.xyzcorp

abstract class Collectible {
  def year:Int
}

class SportsCard(val name:String, val manufacturer:String, val year:Int)
  extends Collectible

class BaseballCard(name:String, manufacturer:String, year:Int,
                   val league:String, val division:String)
                      extends SportsCard(name, manufacturer, year)

