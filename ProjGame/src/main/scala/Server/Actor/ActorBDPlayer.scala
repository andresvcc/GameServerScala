package Server.Actor

import Server.Actor.ManagerActor.{loginActor,infoPlayerActor}
import Server.Actor.MsgBdPlayer._
import Server.Actor.msgLogin.{InfoPlayerError, LoginClient}
import Server.BD.BdPlayerTempConnect._
import akka.actor.Actor

class ActorBDPlayer extends Actor { //para usar las clase de mensajes Connecting y Disconnect
  def receive:Receive = {
    case AddPlayer(name, senderName, host) =>
      val (senderExist, nameExist) = addPlayer(name, senderName, host)
      (senderExist,nameExist) match {
        case (false,false) =>
          loginActor ! LoginClient(name)
          println("registro de clienteID:"+senderName+" <"+name+"> exitoso")
        case (true,true) =>
          infoPlayerActor ! InfoPlayerError(senderName, "ya esta conectado en clienteID:"+senderName+" pero intenta reconectar <"+name+">")
          println("ya esta conectado en clienteID:"+senderName+" pero intenta reconectar <"+name+">")
        case (true,false) =>
          infoPlayerActor ! InfoPlayerError(senderName, "intento de conectar dos o mas cuentas clienteID:"+senderName+" <"+name+">")
          println("intento de conectar dos o mas cuentas clienteID:"+senderName+" <"+name+">")
        case (false,true) =>
          infoPlayerActor ! InfoPlayerError(senderName, "intento de reconexion de una cuenta")
            println("intento de reconexion de una cuenta")
      }
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
    case PlayerRequestBD(actorRef,selectR,request, argument, senderName) =>
      println("base de datos recibe requete de "+actorRef)
      request match {
        case "playerNameBySender" =>
          actorRef ! PongBD(selectR,playerNameBySender(senderName), senderName)
          println("se envio respuesta a "+actorRef)
        case _ =>
          actorRef ! "null"
          println("BD no puede enviar respuesta porque actorRef es null")
      }

    case _ => println("ActorBdPlayer huh type command?"+sender()+" : "+receive)
  }
}
