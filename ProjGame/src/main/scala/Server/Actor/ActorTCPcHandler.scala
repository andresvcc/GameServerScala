/*
este modelo de actor recibe los mensajes se el usuario al que sirve a fin de
ejecutar sus comandos
*/

package Server.Actor

import Server.Command
import akka.actor.Actor
import akka.io.Tcp.{ConnectionClosed, Received}

class ActorTCPcHandler(host:String) extends Actor {
  import Command._
  import ManagerActor._
  import MsgBdPlayer.{PlayerRequestBD, PongBD}
  import msgLogin._

  override def receive: Actor.Receive = {
    case Received(data) =>
      val decoded = data.utf8String
      val cmd:Array[String] = getCommand(decoded)
      cmd(0) match {//cmd(0)-> command recibido  cmd(1)-> primer parametro ...> //
        case "~login" =>
            if (cmd.length > 1 && cmd(1).length > 3)
              loginActor ! Connecting(cmd(1), sender.path.name,host)
            else sendMessageBySender(sender.path.name,"the username is not correct")
        case "~info" =>
          bdPlayerActor ! PlayerRequestBD(self,"info","playerNameBySender",sender.path.name, sender.path.name)
          println("mensaje enviado a bdPlayerActor")
        case _ =>
            sendMessageBySender(sender.path.name,"Command "+cmd(0)+" not found")
        }
    case message: ConnectionClosed => // en caso de perder la conexion
      print("Connection has been closed "+host+" -> ")
      loginActor ! Disconnect(sender.path.name) // suprimir ese usuario de la lista de usuarios conectados e identificados
      context.stop(self) // matar este actor porque ya no tiene usuario a quien servir
    case PongBD(selectR,argument, senderName)=>
      selectR match {
        case "info" =>
          println("mensaje recibido de BD")
          if(argument == "null") {
            sendMessageBySender(senderName,"your are not login")
            println("no esta Logueado "+senderName)
          }else
            infoPlayerActor ! InfoPlayer(argument)
        case "msg" =>
          sendMessageBySender(senderName,argument)
        case _ =>
          println("se recibio "+selectR+" : "+argument)
      }

  }
}
