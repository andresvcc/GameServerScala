package Server.Actor

case object msgLogin {
  case class LoginClient(name:String)
  case class Connecting(name:String, senderName:String, host:String)
  case class Disconnect(senderName:String)
  case class PlayerSelect(senderName:String, name: String)
  case class InfoPlayer(name: String)
  case class InfoServerStatus(name:String)
  case class InfoServerConnexion(name:String)
  case class InfoPlayerError(name:String, textError:String)
  final case class Greeting(greeting:String)
}
