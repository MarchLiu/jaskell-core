trait Sam {
  def applyIt(i:Int) : Int
}

val sam: Sam = (i: Int) => {
  i * i
}

println(sam applyIt 5)