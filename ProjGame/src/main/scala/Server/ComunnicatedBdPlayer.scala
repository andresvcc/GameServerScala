package Server

case object ComunnicatedBdPlayer{
  case class AddPlayer(name:String, senderName:String, host:String)
  case class SupPlayerBySender(senderName:String)
  case class SupPlayerByName(name:String)
  case class PlayerNameBySender(senderName:String)
  case class PlayerSenderByName(name:String)
  case class PlayerHostBySender(senderName:String)
  case class PlayerHostByName(name:String)
  case class PlayerActorRefBySender(senderName:String)
  case class PlayerActorRefByName(name:String)
  case class ExistName(name:String)
  case class ExistSender(SenderName:String)
  case class AllActorRef()
  case class AllName()
  case class AllHost()
  case class AllSenderName()
  final case class GreetingBdPlayer(greeting:String)
}
