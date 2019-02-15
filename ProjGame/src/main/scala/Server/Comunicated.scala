/*
en este objeto se definen las classes de mensaje usados por los actores
*/

package Server

case object Comunicated {
  case class Connecting(name:String, senderName:String)
  case class Disconnect(senderName:String)
  final case class Greeting(greeting:String)
}
