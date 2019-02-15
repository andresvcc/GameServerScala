/*
este modelo de actor recibe los mensajes se el usuario al que sirve a fin de
ejecutar sus comandos
*/

package Server

import akka.actor.Actor
import akka.io.Tcp.{ConnectionClosed, Received}

class ActorTCPcHandler(arg:String) extends Actor {
  import Command._ // para usar los metodos getCommand, sendMessage
  import Comunicated._// para usar las clases de mensaje Connecting y Disconnect
  override def receive: Actor.Receive = {
    case Received(data) =>
      val decoded = data.utf8String
      //cmd es un array para guardar el comando recibido y sus parametros cmd(0) es el comando y cmd(1) el parametro
      val cmd:Array[String] = getCommand(decoded)
      cmd(0) match {//cmd(0)-> command recibido //
        case "~login" =>
            //si el comando recibido tiene almenos 1 parametro y ese parametro tiene almenos 3 caracteres
            //entonces enviar el mensaje Connecting a loginActor1 con el parametro y el identificador de conexcion de ese usuario
            if (cmd.length > 1 && cmd(1).length > 3) loginActor1 ! Connecting(cmd(1), sender.path.name)
            else sendMessage(sender.path.name,"the username is not correct")
        case _ =>
            sendMessage(sender.path.name,"Command "+cmd(0)+" not found")
        }
    // en caso de perder la conexion
    case message: ConnectionClosed =>
      print("Connection has been closed "+arg+" -> ")
      loginActor1 ! Disconnect(sender.path.name) // suprimir ese usuario de la lista de usuarios conectados e identificados
      context.stop(self) // matar este actor porque ya no tiene usuario a quien servir
  }
}
