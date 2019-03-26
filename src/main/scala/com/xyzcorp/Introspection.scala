package com.xyzcorp

trait Introspection {
  def whoAmI_?() = s"${getClass.getSimpleName}"
}
