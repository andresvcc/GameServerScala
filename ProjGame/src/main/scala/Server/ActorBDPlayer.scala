package Server

import akka.actor.Actor

class ActorBDPlayer extends Actor { //para usar las clase de mensajes Connecting y Disconnect
  import BdPlayerTempConnect._
  import Command._
  import ComunnicatedBdPlayer._
  def receive:Receive = {
    case AddPlayer(name, senderName, host) =>
      if(addPlayer(name, senderName, host))
        sendMessageByName(name,"Addplayer: "+name+" OK")
      else
        sendMessageByName(name,"Addplayer: "+name+" Failure")
    case SupPlayerBySender(senderName) =>
      if(supPlayerBySender(senderName))
        println("sup Player: "+senderName+" OK")
      else
        println("sup Player: "+senderName+" Failure")
    case SupPlayerByName(name) =>
      if(supPlayerByName(name))
        println("sup Player: "+name+" OK")
      else
        println("sup Player: "+name+" Failure")
    case PlayerNameBySender(senderName) =>
      playerNameBySender(senderName)
    case PlayerSenderByName(name) =>
      playerSenderByName(name)
    case PlayerHostBySender(senderName) =>
      playerHostBySender(senderName)
    case PlayerHostByName(name) =>
      playerHostByName(name)
    case PlayerActorRefBySender(senderName) =>
      playerActorRefBySender(senderName)
    case PlayerActorRefByName(name) =>
      playerActorRefBySender(name)
    case ExistName(name) =>
      existName(name)
    case ExistSender(senderName) =>
      existSender(senderName)
    case AllSenderName =>
      allSenderName()
    case AllName =>
      allName()
    case AllActorRef =>
      allActorRef()
    case AllHost =>
      allHost()
    case _ => println("huh type command?")
  }
}
