/*
este objeto pertenece a un conjunto de metodos del projecto GameServer en scala
contiene los metodos publica que usan los actores y tambien informaciones necesarias
para el funcionamiento de varias clases
*/

package Server

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.io.Tcp.Write
import akka.util.ByteString

case object Command{
  //-----------------------------------------
  // caracter usado para iniciar cualquier comando, es solo para verificar.
  val CommandCharacter = "~"
  //-----------------------------------------
  // default Actor constructor
  val system = ActorSystem("serverCOM")
  val loginActor1: ActorRef = system.actorOf(Props[LoginActor], name = "loginActor1")
  val infoPlayerActor: ActorRef = system.actorOf(Props[InfoPlayerActor], name = "selecPlayerActor")
  //-----------------------------------------

  //***********************************************************************************
  //extrae el comando de una linea de string Ex: ~login andres -> return ~login.
  def getCommand(command:String):Array[String] = {
    command.split(" ")
  }

  //determina si una linea de string es un comando.
  def isCommand(message: String): Boolean = {
    message.startsWith(CommandCharacter)
  }

  //ayade los identificadores de un usuario a BdClient.
  def connectingUser(name:String, senderName:String): Boolean ={

    if(BdClient.senderExistsIdentities(senderName)){
      sendMessage( senderName,"is not possible, your are connected!")
      false
    }else if (BdClient.nameExistsIdentities(name)) {
      sendMessage( senderName,"There is already an user with this username!")
      false
    } else {
      BdClient.addClientIdentities(name, senderName)
      true
    }
  }

  //elimina los identificadores de un usuario de BdClient.
  def disconnectUser(senderName:String): Boolean ={
    BdClient.findActiveClient(senderName)
    BdClient.supActiveClient(senderName)
    if (BdClient.senderExistsIdentities(senderName)) {
      BdClient.supClientIdentities2(senderName)
      BdClient.supActiveClient(senderName)
      true
    } else {
      println("not user in activeClient!")
      false
    }
  }

  //recive el nombre de usuario y retorna el ActorRef ligado a ese usuario.
  def getActorRefByName(name: String): ActorRef = {
    BdClient.findActiveClient(name)
  }

  //envia un mensaje TCP/ip a un usuario.
  def sendMessage(clientActorName: String, message: String, serverMessage: Boolean = false): Unit = {
    val actorRef = getActorRefByName(clientActorName)
    if(actorRef != null){
      if (serverMessage) {
        actorRef ! Write(ByteString("[SERVER]: " + message))
      } else {
        actorRef ! Write(ByteString(message))
      }
    }else{
      println("user no found "+clientActorName)
    }
  }

  def sendMessageName(name: String, message: String, serverMessage: Boolean = false): Unit = {
    val actorRef = getActorRefByName(BdClient.findSenderIdentities(name))
    if(actorRef != null){
      if (serverMessage) {
        actorRef ! Write(ByteString("[SERVER]: " + message))
      } else {
        actorRef ! Write(ByteString(message))
      }
    }else{
      println("user no found "+name)
    }
  }

  //envia un mensaje TCP/ip a todos los usuarios.
  def sendToAll(messageSender: String, message: String, serverMessage: Boolean = false): Unit = {
    if (serverMessage) {
      BdClient.allActorRef().foreach(actorRef => actorRef ! Write(ByteString("[SERVER for ALL]: " + message)))
    } else {
      BdClient.allActorRef().foreach(actorRef => actorRef ! Write(ByteString("<" + messageSender + ">: " + message)))
    }
  }
}

