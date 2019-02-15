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
    case Connecting(name,senderName) =>
      if(connectingUser(name, senderName)){
        println("User Connect:" + name +": "+ senderName)
        sendMessage(senderName,"you are is now connected",serverMessage = true)
        sendToAll("", name+" is connected", serverMessage = true)
      }
    // en el caso de perder la conexion, se suprime de la lista de usuarios conectados
    case Disconnect(senderName)=>
      val name = BdClient.findNameIdentities(senderName)
      if(disconnectUser(senderName)){
        println("User Disconnect: "+name+": "+ senderName)
        sendToAll("", name+" is disconnect§", serverMessage = true)
      }
    case _ => println("huh type command?")
  }
}