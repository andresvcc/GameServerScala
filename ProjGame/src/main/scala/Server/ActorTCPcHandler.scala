/*
este modelo de actor recibe los mensajes se el usuario al que sirve a fin de
ejecutar sus comandos
*/

package Server

import akka.actor.Actor
import akka.io.Tcp.{ConnectionClosed, Received}

class ActorTCPcHandler(host:String) extends Actor {
  import Command._ // para usar los metodos getCommand, sendMessage
  import Comunicated._// para usar las clases de mensaje Connecting y Disconnect

  override def receive: Actor.Receive = {
    case Received(data) =>
      val decoded = data.utf8String
      val cmd:Array[String] = getCommand(decoded)
      cmd(0) match {//cmd(0)-> command recibido  cmd(1)-> primer parametro ...> //
        case "~login" =>
            if (cmd.length > 1 && cmd(1).length > 3)
              loginActor1 ! Connecting(cmd(1), sender.path.name,host)
            else sendMessageBySender(sender.path.name,"the username is not correct")
        case "~info" =>
          val name = BdPlayerTempConnect.playerNameBySender(sender.path.name)
          if(name == "null") sendMessageBySender(sender.path.name,"your are not login")
          else infoPlayerActor ! InfoPlayer(name)
        case _ =>
            sendMessageBySender(sender.path.name,"Command "+cmd(0)+" not found")
        }
    case message: ConnectionClosed => // en caso de perder la conexion
      print("Connection has been closed "+host+" -> ")
      loginActor1 ! Disconnect(sender.path.name) // suprimir ese usuario de la lista de usuarios conectados e identificados
      context.stop(self) // matar este actor porque ya no tiene usuario a quien servir
  }
}
