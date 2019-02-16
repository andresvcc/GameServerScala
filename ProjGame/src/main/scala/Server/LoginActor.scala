/*
este modelo de actor gestiona el login entre el usuario y el server
name: nombre de usuario
senderName: identificador de usuario
*/

package Server

import akka.actor.Actor

class LoginActor extends Actor {
  import Command._ //para usar sus metodos sendMessage y sendToAll
  import Comunicated._ //para usar las clase de mensajes Connecting y Disconnect
  def receive:Receive = {
    // en el caso de recibir una solisitud de conexion
    case Connecting(name,senderName,host) =>
      if(connectingUser(name, senderName,host)){
        println("#login User Name:" + name +" -> userID"+ senderName)
        sendMessageBySender(senderName,"you are is now connected\n",serverMessage = true)
        sendToAll("", name+" is connected", serverMessage = true)
      }
    // en el caso de perder la conexion, se suprime de la lista de usuarios conectados
    case Disconnect(senderName)=>
      val name = BdPlayerTempConnect.playerNameBySender(senderName)
      if(disconnectUser(senderName)){
        println("User Disconnect: "+name+": "+ senderName)
        sendToAll("", name+" is disconnect§", serverMessage = true)
      }
    case _ => println("huh type command?")
  }
}