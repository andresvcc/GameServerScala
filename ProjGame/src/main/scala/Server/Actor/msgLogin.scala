package Server.Actor

case object msgLogin {
  case class Connecting(name:String, senderName:String, host:String)
  case class Disconnect(senderName:String)
  case class PlayerSelect(senderName:String, name: String)
  case class InfoPlayer(name: String)
  final case class Greeting(greeting:String)
}
