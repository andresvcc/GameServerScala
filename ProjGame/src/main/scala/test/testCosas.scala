package test

class testClass(nombreA:String,datoA:String,edadA:Int){
  var nombre:String = nombreA
  var dato:String = datoA
  var edad:Int = edadA
}

object testCosas extends App {
  private val ActiveClients1 = scala.collection.mutable.HashMap.empty[String, testClass]
  private val ActiveClients2 = scala.collection.mutable.HashMap.empty[String, testClass]

  val clase = new testClass("andres", "corre", 30)

  ActiveClients1 += ("andres" -> clase)
  ActiveClients2 += ("1" -> clase)
  println(ActiveClients2("1").nombre)
  ActiveClients1("andres").nombre = "juan"
  println(ActiveClients2("1").nombre)

}
